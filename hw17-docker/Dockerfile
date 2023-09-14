FROM openjdk:17

EXPOSE 8080

COPY target/hw17-docker-3.0.4.jar /hw17-docker-3.0.4.jar

RUN groupadd -r myuser && \
    useradd --no-log-init -r -g myuser myuser

USER myuser

ENTRYPOINT ["java", "-jar", "/hw17-docker.jar"]