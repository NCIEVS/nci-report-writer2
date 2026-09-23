# Build the deployable WAR in an isolated Linux Gradle environment.
FROM gradle:8.14.2-jdk17 AS gradle-build

WORKDIR /workspace

COPY --chown=gradle:gradle build.gradle settings.gradle ./

COPY --chown=gradle:gradle src/main ./src/main
RUN gradle --no-daemon bootWar -x test -x javadoc

FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

RUN groupadd --system reportwriter \
    && useradd --system --gid reportwriter --home-dir /app --shell /usr/sbin/nologin reportwriter

COPY --from=gradle-build --chown=reportwriter:reportwriter \
    /workspace/build/libs/ncireportwriter2-*.war /app/ncireportwriter2.war

USER reportwriter

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/ncireportwriter2.war"]
