FROM gradle:8.11.0-jdk21-corretto AS quick-base

WORKDIR /app

# A quick development environment
FROM quick-base AS quick-dev

RUN dnf install vim -y

WORKDIR /app
CMD ["/bin/sh"]

FROM quick-base AS quick-build

COPY . .
WORKDIR /app
RUN ./gradlew app:assembleBootDist

FROM quick-base AS quick-pos

RUN useradd -ms /bin/sh app
RUN mkdir -p /app && chown -R app:app /app
WORKDIR /app
COPY --chown=app --from=quick-build /app/app/build/libs/app.jar /app/app.jar
CMD ["java", "-jar", "/app/app.jar"]
