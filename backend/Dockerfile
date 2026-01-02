## ---- Build stage ----
FROM gradle:8.3-jdk17 AS build

WORKDIR /workspace

# Leverage caching: copy only gradle descriptors first
COPY build.gradle settings.gradle ./
COPY gradle gradle
COPY gradlew ./
RUN chmod +x ./gradlew

# Download dependencies using Gradle (wrapper jar not in repo)
RUN gradle --no-daemon clean build -x test || true

# Copy sources and build
COPY src src
RUN gradle --no-daemon clean bootJar -x test


## ---- Runtime stage ----
FROM eclipse-temurin:17-jre

WORKDIR /app
COPY --from=build /workspace/build/libs/e4l-server.jar /app/app.jar

EXPOSE 8080
ENV JAVA_OPTS=""
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
