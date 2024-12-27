FROM openjdk:23-jdk-oracle AS builder

ARG COMPILE_DIR=/compiledir 

WORKDIR ${COMPILE_DIR}

# Copy Maven wrapper and pom.xml
COPY ./mvnw . 
COPY .mvn .mvn
COPY pom.xml .

# Copy the source code into the container
COPY src src

RUN chmod a+x ./mvnw && ./mvnw package -Dmaven.test.skip=true 

ENV PORT=8080
EXPOSE ${SERVER_PORT}

#Stage 2
FROM openjdk:23-jdk-oracle

ARG WORK_DIR=/app

WORKDIR  ${WORK_DIR}

COPY --from=builder /compiledir/target/ProjectA-0.0.1-SNAPSHOT.jar projectA.jar


ENV PORT=8080
ENV API_KEY=abc123
ENV API_ID=hello
ENV SPRING_DATA_REDIS_HOST=localhost
ENV SPRING_DATA_REDIS_PORT=6379
ENV SPRING_DATA_REDIS_DATABASE=0
ENV SPRING_DATA_REDIS_USERNAME= 
ENV SPRING_DATA_REDIS_PASSWORD= 


EXPOSE ${PORT}

ENTRYPOINT java -jar projectA.jar

HEALTHCHECK --interval=30s --timeout=5s --retries=3 \
CMD curl -s -f http:/localhost:${PORT}/health || exit 1 
