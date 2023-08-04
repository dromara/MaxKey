FROM adoptopenjdk:8-jre

RUN mkdir -p /maxkey

WORKDIR /maxkey

ARG JAR_FILE=build/libs/maxkey-boot-*-ga.jar

COPY ${JAR_FILE} maxkey-boot.jar

EXPOSE 9527

ENV TZ=Asia/Shanghai JAVA_OPTS="-Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom"

CMD sleep 60; java -jar maxkey-boot.jar $JAVA_OPTS