package dev.itboot.mb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.beans.factory.annotation.Value;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
// import software.amazon.awssdk.services.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import java.net.URI;


@Configuration
public class DynamoDbConfig {

    @Value("${aws.dynamodb.endpoint}")
    private String dynamoDbEndpoint;

    @Value("${aws.dynamodb.region}")
    private String region;

    @Bean
    public DynamoDbClient dynamoDbClient() {
        return DynamoDbClient.builder()
                .endpointOverride(URI.create(dynamoDbEndpoint))
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create("dummy", "dummy") // Localでは適当でOK
                ))
                .build();
    }

    // @Bean
    // public DynamoDbEnhancedClient enhancedClient(DynamoDbClient dynamoDbClient) {
    //     return DynamoDbEnhancedClient.builder()
    //             .dynamoDbClient(dynamoDbClient)
    //             .build();
    // }
}

