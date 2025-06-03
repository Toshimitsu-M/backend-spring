package com.example.sequence.model;

import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamoDbBean
public class Sequence {
    private String keyName;
    private Long currentValue;

    @DynamoDbPartitionKey
    public String getKeyName() {
        return keyName;
    }
}
