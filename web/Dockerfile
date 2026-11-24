ARG BUILDPLATFORM

# Package the Spring Boot WAR with Java 25 (matches the build.gradle toolchain and the
# Gradle 9.3.0 wrapper). The Angular bundle is not built here; the WAR serves the
# frontend assets already committed under src/main/resources/static (see `make frontend`).
FROM --platform=$BUILDPLATFORM gradle:9.3.0-jdk25 AS gradle-build

WORKDIR /workspace

COPY --chown=gradle:gradle build.gradle settings.gradle ./

COPY --chown=gradle:gradle src/main ./src/main
RUN gradle --no-daemon bootWar -x test -x javadoc

# Run the Linux deployment image as an unprivileged user.
FROM eclipse-temurin:25-jre-noble

WORKDIR /app

RUN groupadd --system reportwriter \
    && useradd --system --gid reportwriter --home-dir /app --shell /usr/sbin/nologin reportwriter

COPY --from=gradle-build --chown=reportwriter:reportwriter \
    /workspace/build/libs/ncireportwriter2-*.war /app/ncireportwriter2.war

USER reportwriter

# Listen on 8080 inside the container; remap with `docker run -p <host-port>:8080`.
ENV RW_API_SERVER_PORT=8080
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/ncireportwriter2.war"]
