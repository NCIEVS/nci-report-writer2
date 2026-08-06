# Build the application in the container so an image build does not depend on
# a locally installed JDK, Gradle distribution, or locally produced WAR.
FROM gradle:8.14.2-jdk17 AS gradle-build

WORKDIR /workspace

# Keep the dependency descriptor separate from application sources so Docker
# can reuse the dependency layer when only source files change.
COPY build.gradle ./
RUN gradle dependencies --no-daemon

COPY src ./src
RUN gradle clean bootWar -x test --no-daemon

FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

RUN groupadd --system reportwriter \
    && useradd --system --gid reportwriter --home-dir /app --shell /usr/sbin/nologin reportwriter

COPY --from=gradle-build --chown=reportwriter:reportwriter \
    /workspace/build/libs/ncireportwriter2-*.war /app/ncireportwriter2.war

USER reportwriter

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/ncireportwriter2.war"]
