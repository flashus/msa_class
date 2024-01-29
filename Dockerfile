FROM openjdk:17
RUN mkdir app/
COPY build/libs/users-0.0.1-SNAPSHOT.jar /app/users.jar
EXPOSE 8080
ENV JAVA_OPTS=""
WORKDIR /app
CMD java $JAVA_OPTS -jar users.jar

# COPY target/users-0.0.1-SNAPSHOT.jar app/users.jar
# ENTRYPOINT ["java", "-jar", "app/users.jar"]