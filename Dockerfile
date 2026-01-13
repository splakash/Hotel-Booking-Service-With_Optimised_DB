
FROM eclipse-temurin:17-jdk-alpine
ADD target/hotelManage.jar hotelManage.jar
ENTRYPOINT ["java", "-jar", "hotelManage.jar"]
