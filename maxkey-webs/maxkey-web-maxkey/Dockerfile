FROM eclipse-temurin:17-jdk-alpine

LABEL authors="MaxKey <support@maxsso.net>"

RUN mkdir -p /maxkey

WORKDIR /maxkey

ARG JAR_FILE=build/libs/maxkey-boot-*-ga.jar

COPY ${JAR_FILE} maxkey-boot.jar

EXPOSE 9527

ENV TZ=Asia/Shanghai JAVA_OPTS="-Xms256m -Xmx2048m -Djava.security.egd=file:/dev/./urandom"

CMD sleep 60; java -jar maxkey-boot.jar $JAVA_OPTS