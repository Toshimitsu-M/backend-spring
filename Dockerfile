# ベースイメージ
FROM openjdk:17-jdk-slim

# JARファイルをコンテナにコピー
COPY target/spring-mybatis-0.0.1-SNAPSHOT.jar app.jar

# アプリ起動
ENTRYPOINT ["java", "-jar", "/app.jar"]
