# Используем официальный образ OpenJDK
FROM openjdk:17-jdk-slim as builder

# Устанавливаем Maven
RUN apt-get update && apt-get install -y maven git

# Клонируем репозиторий
RUN git clone https://github.com/Minaev-Aleksey/AstonUserService.git /app

# Переходим в рабочую директорию
WORKDIR /app

# Собираем проект с помощью Maven
RUN mvn clean package

# Второй этап - создаем итоговый образ
FROM openjdk:17-jdk-slim

# Копируем собранный JAR из первого этапа
COPY --from=builder /app/target/UserService.jar /app/app.jar

# Открываем порт, который использует приложение
EXPOSE 8082

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "/app/app.jar"]