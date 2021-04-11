FROM ghcr.io/graalvm/graalvm-ce:java11-21.0.0 AS builder
RUN gu install native-image
WORKDIR /home/app
COPY . /home/app
RUN ./mvnw package -Dpackaging=native-image


FROM frolvlad/alpine-glibc:alpine-3.12
RUN apk update && apk add libstdc++
COPY --from=builder /home/app/target/backend /app/application

EXPOSE 8080
ENTRYPOINT ["/app/application"]