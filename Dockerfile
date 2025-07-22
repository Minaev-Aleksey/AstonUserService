# ���������� ����������� ����� OpenJDK
FROM openjdk:17-jdk-slim as builder

# ������������� Maven
RUN apt-get update && apt-get install -y maven git

# ��������� �����������
# RUN git clone https://github.com/Minaev-Aleksey/AstonUserService.git /AstonUserService

# ��������� � ������� ����������
WORKDIR /AstonUserService

# �������� ������ � ������� Maven
RUN mvn clean package

# ������ ���� - ������� �������� �����
FROM openjdk:17-jdk-slim

# �������� ��������� JAR �� ������� �����
COPY --from=builder /AstonUserService/target/UserService.jar /AstonUserService/app.jar

# ��������� ����, ������� ���������� ����������
EXPOSE 8082

# ��������� ����������
ENTRYPOINT ["java", "-jar", "/AstonUserService/app.jar"]