# 1. Maven & Javaが入ったビルド用イメージを使う
FROM maven:3.9-eclipse-temurin-17 AS builder

# 2. 作業ディレクトリ作成
WORKDIR /app

# 3. Maven依存のキャッシュ効率のために、pomファイルを先にコピー
COPY pom.xml .
RUN mvn dependency:go-offline

# 4. ソースコードをコピーしてビルド
COPY . .
RUN mvn clean package -DskipTests

# 5. 実行専用イメージ（軽量）にjarをコピー
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

# 6. Spring Bootアプリの起動
CMD ["java", "-jar", "app.jar"]
