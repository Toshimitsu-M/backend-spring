package com.example.config;

import java.net.URI;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;

@Configuration
@EnableConfigurationProperties(S3Properties.class)
public class S3Config {

    @Bean
    public S3Client s3Client(S3Properties properties) {
        S3ClientBuilder builder = S3Client.builder()
                .region(Region.of(properties.getRegion()));

        if (StringUtils.hasText(properties.getEndpoint())) {
            builder = builder.endpointOverride(URI.create(properties.getEndpoint()))
                    .forcePathStyle(true);
        }

        builder = builder.credentialsProvider(resolveCredentialsProvider(properties));

        return builder.build();
    }

    private AwsCredentialsProvider resolveCredentialsProvider(S3Properties properties) {
        boolean hasAccessKey = StringUtils.hasText(properties.getAccessKeyId());
        boolean hasSecretKey = StringUtils.hasText(properties.getSecretAccessKey());

        if (hasAccessKey && hasSecretKey) {
            return StaticCredentialsProvider.create(
                    AwsBasicCredentials.create(properties.getAccessKeyId(), properties.getSecretAccessKey()));
        }

        return DefaultCredentialsProvider.create();
    }
}
