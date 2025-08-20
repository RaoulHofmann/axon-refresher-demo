FROM eclipse-temurin:24-jdk
WORKDIR /app
# copy Maven/Gradle wrapper
COPY mvnw* pom.xml ./
COPY .mvn .mvn
# download deps first (cached)
RUN ./mvnw dependency:go-offline -B
# copy all sources
COPY . .
CMD ["./mvnw", "spring-boot:run"]
