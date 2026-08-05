FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

RUN groupadd --system reportwriter \
    && useradd --system --gid reportwriter --home-dir /app --shell /usr/sbin/nologin reportwriter

COPY build/libs/ncireportwriter2-*.war /app/ncireportwriter2.war

USER reportwriter

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/ncireportwriter2.war"]