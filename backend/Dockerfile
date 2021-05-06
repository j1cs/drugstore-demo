FROM ghcr.io/graalvm/graalvm-ce:java11-21.1.0 AS builder
RUN gu install native-image
WORKDIR /home/app
COPY . /home/app
ENV MAVEN_OPTS="-XX:+TieredCompilation -XX:TieredStopAtLevel=1"
RUN ./mvnw -T 1C package -Dpackaging=native-image -P prod


FROM frolvlad/alpine-glibc:alpine-3.12
RUN apk update && apk add libstdc++
COPY --from=builder /home/app/target/backend /app/application

EXPOSE 8080
ENTRYPOINT ["/app/application"]
