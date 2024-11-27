FROM eclipse-temurin:17-jdk-alpine

LABEL authors="MaxKey <support@maxsso.net>"

RUN mkdir -p /maxkey-openapi

WORKDIR /maxkey-openapi

ARG JAR_FILE=build/libs/maxkey-openapi-boot-*-ga.jar

COPY ${JAR_FILE} maxkey-openapi-boot.jar

EXPOSE 9525

ENV TZ=Asia/Shanghai JAVA_OPTS="-Xms256m -Xmx2048m -Djava.security.egd=file:/dev/./urandom"

CMD sleep 60; java -jar maxkey-openapi-boot.jar $JAVA_OPTS