FROM openjdk:15

ARG BUILD_VERSION=0.0.0
ARG JAR_FILENAME=app.jar

COPY "${JAR_FILENAME}" /app.jar
COPY docker/entrypoint.sh /entrypoint.sh

ENTRYPOINT ["/entrypoint.sh"]
