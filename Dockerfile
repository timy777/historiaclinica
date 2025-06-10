FROM openjdk:17-jdk-slim

# Crea un directorio de trabajo
WORKDIR /app

# Copia el JAR generado por Maven
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Exponer el puerto que usa la app
EXPOSE 8080

# Comando de inicio
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
