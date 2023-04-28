FROM openjdk:8u332-jre
COPY target/cheese-boot.jar /app.jar
ENV JVM_MEM_OPTS="-Xms512m -Xmx512m"
ENV JVM_GC_OPTS="-XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+UseCMSCompactAtFullCollection"
#ENV REMOTE_DEBUG="-Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=50001,suspend=n"
#ENV REMOTE_MONITOR="-Djava.rmi.server.hostname=192.168.84.106 -Dcom.sun.management.jmxremote.port=10066 -Dcom.sun.management.jmxremote.rmi.port=10066 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false"
#ENV APP_ACTIVE=dev
#ENV APP_ACTIVE=test
ENV APP_ACTIVE=prod
CMD java  ${JVM_MEM_OPTS} ${JVM_GC_OPTS} -jar /app.jar --spring.profiles.active=${APP_ACTIVE}