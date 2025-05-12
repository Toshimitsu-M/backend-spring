package com.example.sequence.model;

import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamoDbBean
public class Sequence {
    private String sequenceId;
    private Long value;

    @DynamoDbPartitionKey
    public String getSequenceId() {
        return sequenceId;
    }
}
