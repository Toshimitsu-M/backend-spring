package com.example.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "dynamodb")
public class DynamoDbProperties {

    private String endpoint;
    private String region;
    private String accessKeyId;
    private String secretAccessKey;

    // Getter / Setter
    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

	public String getAccessKeyId() {
        return accessKeyId;
		
	}
    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

	public String getSecretAccessKey() {
        return secretAccessKey;
	}
    public void setSecretAccessKey(String secretAccessKey) {
        this.secretAccessKey = secretAccessKey;
    }
}
