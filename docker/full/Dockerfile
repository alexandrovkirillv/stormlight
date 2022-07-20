FROM gradle:6.9.1-jdk15 as builder

ARG BUILD_VERSION=0.0.0

RUN mkdir -p -v /src
WORKDIR /src
ADD . /src

RUN sh gradlew build -PbuildVersion="${BUILD_VERSION}"
RUN cp -v build/libs/secretnet-*.jar /app.jar

FROM openjdk:15

COPY --from=builder /app.jar /app.jar
COPY docker/entrypoint.sh /entrypoint.sh

ENTRYPOINT ["/entrypoint.sh"]
