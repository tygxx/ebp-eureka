FROM registry.yy.com:5000
VOLUME /app/tmp
ADD ebp-eureka-0.0.1.jar /app/ebp-eureka.jar
ADD lib /app/lib
ADD config /app/config
RUN sh -c 'touch /app/ebp-eureka.jar'
ENV JAVA_OPTS="-XX:MaxPermSize=128MB"
WORKDIR /app
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar ebp-eureka.jar"]
