FROM openjdk:17
EXPOSE 8080
ADD build/libs/monkeysoft-1.0.0.jar monkeysoft.jar
ENTRYPOINT ["java", "-jar", "monkeysoft.jar"]
