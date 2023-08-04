FROM adoptopenjdk:8-jre

RUN mkdir -p /maxkey-mgt

WORKDIR /maxkey-mgt

ARG JAR_FILE=build/libs/maxkey-mgt-boot-*-ga.jar

COPY ${JAR_FILE} maxkey-mgt-boot.jar

EXPOSE 9526

ENV TZ=Asia/Shanghai JAVA_OPTS="-Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom"

CMD sleep 60; java -jar maxkey-mgt-boot.jar $JAVA_OPTS