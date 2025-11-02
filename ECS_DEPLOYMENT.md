# ECS デプロイメントガイド

このドキュメントでは、AWS ECS への本番デプロイメントに必要な環境変数の設定方法を説明します。

## 📋 環境変数の設定

### ECS Task Definition での環境変数設定

以下は ECS Task Definition に設定する必要がある環境変数の一覧です。

#### 1. 必須環境変数（全環境共通）

| 環境変数名 | 説明 | 例 |
|-----------|------|-----|
| `SPRING_PROFILES_ACTIVE` | Spring Boot のプロファイル指定 | `prod` |

#### 2. データベース設定（本番環境）

| 環境変数名 | 説明 | 例 |
|-----------|------|-----|
| `DB_HOST` | RDS PostgreSQL エンドポイント | `prod-db.xxxxx.ap-northeast-1.rds.amazonaws.com` |
| `DB_PORT` | RDS ポート番号 | `5432` |
| `DB_NAME` | データベース名 | `appdb` |
| `DB_USERNAME` | RDS ユーザー名 | `admin` |
| `DB_PASSWORD` | RDS パスワード | `（Secrets Manager 推奨）` |

#### 3. AWS DynamoDB 設定

| 環境変数名 | 説明 | 例 |
|-----------|------|-----|
| `AWS_ACCESS_KEY_ID` | DynamoDB 用アクセスキー | `AKIA...` |
| `AWS_SECRET_ACCESS_KEY` | DynamoDB 用シークレットキー | `（Secrets Manager 推奨）` |

#### 4. オプション環境変数

| 環境変数名 | 説明 | 例 |
|-----------|------|-----|
| `OPENAI_API_KEY` | OpenAI API キー（チャット機能使用時） | `sk-...` |

## 🔐 Secrets Manager の使用（推奨）

本番環境では、機密情報は AWS Secrets Manager を使用して管理することを強く推奨します。

### Secrets Manager での設定例

#### Secret ARN の取得

```bash
# Secrets Manager にシークレットを作成
aws secretsmanager create-secret \
  --name prod/appdb/password \
  --secret-string "your-secure-password"
```

#### Task Definition での参照方法

ECS Task Definition の JSON で以下のように設定：

```json
{
  "containerDefinitions": [
    {
      "name": "animeapp-api-spring",
      "environment": [
        {
          "name": "SPRING_PROFILES_ACTIVE",
          "value": "prod"
        },
        {
          "name": "DB_HOST",
          "value": "prod-db.xxxxx.ap-northeast-1.rds.amazonaws.com"
        },
        {
          "name": "DB_PORT",
          "value": "5432"
        },
        {
          "name": "DB_NAME",
          "value": "appdb"
        },
        {
          "name": "DB_USERNAME",
          "value": "admin"
        }
      ],
      "secrets": [
        {
          "name": "DB_PASSWORD",
          "valueFrom": "arn:aws:secretsmanager:ap-northeast-1:123456789012:secret:prod/appdb/password-abc123"
        },
        {
          "name": "AWS_SECRET_ACCESS_KEY",
          "valueFrom": "arn:aws:secretsmanager:ap-northeast-1:123456789012:secret:prod/dynamodb/secretkey-xyz789"
        },
        {
          "name": "OPENAI_API_KEY",
          "valueFrom": "arn:aws:secretsmanager:ap-northeast-1:123456789012:secret:prod/openai/apikey-def456"
        }
      ]
    }
  ]
}
```

## 🚀 デプロイフロー

1. **CodeBuild でビルド**
   - `buildspec.yml` により Docker イメージをビルド
   - ECR にイメージをプッシュ

2. **ECS Task Definition の作成/更新**
   - 環境変数を設定
   - Secrets Manager ARN を指定

3. **ECS Service の更新**
   - 新しい Task Definition でサービスを更新
   - ロードバランサー経由でトラフィックを流す

## 📝 注意事項

### セキュリティ

- パスワードやシークレットキーは **決して環境変数として直接指定しない** でください
- 必ず **Secrets Manager** を使用してください
- IAM ロールで適切な権限を付与してください

### トラブルシューティング

#### 環境変数が設定されていない

```
APPLICATION FAILED TO START
***************************
Description:
The 'DB_PASSWORD' environment variable is not set
```

**対処法:**
- ECS Task Definition の environment または secrets セクションを確認
- CloudWatch Logs で環境変数を確認

#### データベース接続エラー

```
The connection attempt failed
```

**対処法:**
- RDS のセキュリティグループで ECS からの接続を許可
- `DB_HOST`, `DB_PORT`, `DB_NAME` が正しいか確認
- RDS のパブリックアクセシビリティ設定を確認

## 🔍 環境変数の確認方法

### CloudWatch Logs で確認

ECS タスクのログから環境変数を確認：

```bash
aws logs tail /ecs/animeapp-api-spring --follow
```

### ECS Exec で確認

実行中のコンテナに接続して確認：

```bash
aws ecs execute-command \
  --cluster your-cluster-name \
  --task your-task-id \
  --container animeapp-api-spring \
  --interactive \
  --command "/bin/sh"
```

コンテナ内で：
```bash
env | grep -E "DB_|AWS_|SPRING"
```

## 📚 参考資料

- [AWS Secrets Manager](https://aws.amazon.com/secrets-manager/)
- [ECS Task Definition Parameters](https://docs.aws.amazon.com/AmazonECS/latest/developerguide/task_definition_parameters.html)
- [環境変数の使用 - AWS ECS](https://docs.aws.amazon.com/AmazonECS/latest/developerguide/task_definition_parameters.html#container_definition_environment)

