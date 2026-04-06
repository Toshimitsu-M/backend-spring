# 1. Maven & Javaが入ったビルド用イメージを使う
# 1. 共通モジュールをビルドしてローカルMavenリポジトリにインストール
FROM maven:3.9-eclipse-temurin-17 AS common-builder
WORKDIR /common
COPY backend-spring-common /common
RUN mvn install -DskipTests

# 2. Spring Bootアプリをビルド
FROM maven:3.9-eclipse-temurin-17 AS builder
WORKDIR /app
COPY backend-spring/pom.xml .
COPY backend-spring/. .
COPY --from=common-builder /root/.m2 /root/.m2
RUN mvn clean package -DskipTests

# 3. 実行用イメージ
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
CMD ["java", "-jar", "app.jar"]