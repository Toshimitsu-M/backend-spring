# 環境設定ガイド

このプロジェクトは、以下の3つの環境をサポートしています。

## 📁 設定ファイル構成

```
backend-spring/src/main/resources/
├── application.yml           # 共通設定（全環境で適用）
├── application-dev.yml       # 開発環境設定
├── application-staging.yml   # ステージング環境設定
├── application-prod.yml      # 本番環境設定
└── schema.sql                # Spring Boot初期化SQL（任意）
```

## 🌍 環境ごとの設定

### 開発環境（dev）
**ファイル:** `application-dev.yml`  
**データベース:** Docker Compose内のPostgreSQLコンテナ  
**特徴:**
- ローカル開発用の設定
- 詳細ログ出力（DEBUG）
- LocalStack等のローカルDynamoDBエンドポイント

**起動方法:**
```bash
docker compose -f docker-compose.animeapp.yml up -d
```

### ステージング環境（staging）
**ファイル:** `application-staging.yml`  
**データベース:** AWS RDS PostgreSQL  
**特徴:**
- 本番前の検証環境
- 適度なログ出力（INFO）
- AWS RDSとDynamoDBに接続

**必要な環境変数:**
```bash
export SPRING_PROFILES_ACTIVE=staging
export DB_HOST=staging-db.example.com
export DB_USERNAME=admin
export DB_PASSWORD=your_secure_password
export AWS_ACCESS_KEY_ID=your_aws_key
export AWS_SECRET_ACCESS_KEY=your_aws_secret
```

### 本番環境（prod）
**ファイル:** `application-prod.yml`  
**データベース:** AWS RDS PostgreSQL  
**特徴:**
- 本番環境設定
- 最小限のログ出力（WARN以上のみ）
- 高負荷対応のHikariCP設定
- AWS RDSとDynamoDBに接続

**必要な環境変数:**
```bash
export SPRING_PROFILES_ACTIVE=prod
export DB_HOST=prod-db.example.com
export DB_USERNAME=admin
export DB_PASSWORD=your_secure_password
export AWS_ACCESS_KEY_ID=your_aws_key
export AWS_SECRET_ACCESS_KEY=your_aws_secret
```

## 🔧 環境変数の指定方法

### Docker Compose起動時
```bash
# 開発環境（デフォルト）
docker compose -f docker-compose.animeapp.yml up -d

# ステージング環境
SPRING_PROFILES_ACTIVE=staging docker compose -f docker-compose.animeapp.yml up -d

# 本番環境
SPRING_PROFILES_ACTIVE=prod docker compose -f docker-compose.animeapp.yml up -d
```

### Spring Boot直接起動時
```bash
# 開発環境
java -jar app.jar --spring.profiles.active=dev

# ステージング環境
java -jar app.jar --spring.profiles.active=staging

# 本番環境
java -jar app.jar --spring.profiles.active=prod
```

### IDE起動時
IntelliJ IDEA や Eclipse の Run Configuration で以下を指定：
```
VM options: -Dspring.profiles.active=dev
```

## 🗄️ データベース設定の違い

| 環境 | データベース | 接続方法 |
|------|------------|---------|
| dev | PostgreSQL (Docker) | `jdbc:postgresql://db:5432/appdb` |
| staging | AWS RDS PostgreSQL | `jdbc:postgresql://${DB_HOST}:5432/appdb` |
| prod | AWS RDS PostgreSQL | `jdbc:postgresql://${DB_HOST}:5432/appdb` |

## 📊 ログレベルの違い

| 環境 | ログレベル | 説明 |
|------|-----------|------|
| dev | DEBUG | 詳細なデバッグ情報を出力 |
| staging | INFO | 一般的な動作情報を出力 |
| prod | WARN | 警告とエラーのみ出力 |

## 🔐 セキュリティ注意事項

- **パスワード等の機密情報は環境変数で管理**
- **本番環境の認証情報はGitにコミットしない**
- **AWS認証情報はIAMロールの利用を推奨**

## 📝 設定ファイルの優先順位

Spring Bootは以下の順序で設定を読み込みます：

1. `application.yml` - 共通設定
2. `application-{profile}.yml` - 環境固有の設定（上書き）

環境固有の設定が共通設定を上書きします。

