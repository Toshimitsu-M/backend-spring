package com.example.sequence.service;

import com.example.sequence.model.Sequence;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.*;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Service
@RequiredArgsConstructor
public class SequenceService {

    private final DynamoDbClient dynamoDbClient;

    public long getNextSequence(String sequenceName) {
        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();

        DynamoDbTable<Sequence> table = enhancedClient.table("SEQUENCES", TableSchema.fromBean(Sequence.class));

        // 条件付き更新（value + 1）
        Sequence updated = table.updateItem(r -> r.key(k -> k.partitionValue(sequenceName))
                .updateExpression("SET #v = if_not_exists(#v, :zero) + :inc")
                .expressionAttributeNames(Map.of("#v", "value"))
                .expressionAttributeValues(Map.of(
                        ":inc", AttributeValue.fromN("1"),
                        ":zero", AttributeValue.fromN("0")
                ))
                .returnValues(ReturnValue.ALL_NEW)
        );

        return updated.getValue();
    }
}

