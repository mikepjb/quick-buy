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
# doesn't seem to build the jar.. I can't see it in the final image anyway.
RUN ./gradlew app:jar

FROM quick-base AS quick-pos

WORKDIR /app
CMD ["pwd"] 
