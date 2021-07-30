FROM adoptopenjdk/maven-openjdk11 AS build
COPY . /root
ENV MAVEN_OPTS="-XX:+TieredCompilation -XX:TieredStopAtLevel=1"
RUN mvn -T 1C -f /root/pom.xml clean package -Dmaven.test.skip=true

FROM adoptopenjdk/openjdk11:alpine-slim
COPY --from=build /root/target/micronaut-*.jar app.jar
EXPOSE 8080 5005
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "app.jar"]