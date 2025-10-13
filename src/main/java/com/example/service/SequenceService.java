package com.example.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.HashMap;
import java.util.Map;

import software.amazon.awssdk.services.dynamodb.model.AttributeAction;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.AttributeValueUpdate;
import software.amazon.awssdk.services.dynamodb.model.ReturnValue;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemResponse;

@Service
@RequiredArgsConstructor
public class SequenceService {

        private final DynamoDbClient dynamoDbClient;

        public long getNextSequence(String sequenceName) {

                Map<String, AttributeValue> key = new HashMap<>();
                key.put("keyName", AttributeValue.builder().s(sequenceName).build());

                Map<String, AttributeValueUpdate> updates = new HashMap<>();
                updates.put("currentValue", AttributeValueUpdate.builder()
                                .action(AttributeAction.ADD)
                                .value(AttributeValue.builder().n("1").build())
                                .build());

                UpdateItemRequest request = UpdateItemRequest.builder()
                                .tableName("Sequences")
                                .key(key)
                                .attributeUpdates(updates)
                                .returnValues(ReturnValue.UPDATED_NEW)
                                .build();

                UpdateItemResponse response = dynamoDbClient.updateItem(request);
                String newValue = response.attributes().get("currentValue").n();
                return Long.parseLong(newValue);
        }
}
