package dev.itboot.mb.Repository;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.CreateTableEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public class UserRepository {
    private final DynamoDbTable<User> table;

    public UserRepository(DynamoDbEnhancedClient enhancedClient) {
        this.table = enhancedClient.table("users", TableSchema.fromBean(User.class));
        createTableIfNotExists(); // DynamoDB Local 用
    }

    public void createTableIfNotExists() {
        try {
            table.describeTable();
        } catch (ResourceNotFoundException e) {
            table.createTable(CreateTableEnhancedRequest.builder().build());
        }
    }

    public void save(User user) {
        table.putItem(user);
    }

    public User findById(String id) {
        return table.getItem(Key.builder().partitionValue(id).build());
    }

    public void deleteById(String id) {
        table.deleteItem(Key.builder().partitionValue(id).build());
    }

    public List<User> findAll() {
        return table.scan().items().stream().toList();
    }
}
