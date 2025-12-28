# Criado para construir e rodar uma aplicação Spring Boot usando Maven e Java 8
FROM maven:3.8.4-jdk-8 AS build
WORKDIR /app

# 1. Copia SÓ o pom.xml primeiro
# Isso permite que o Docker use o cache das dependências se o pom.xml não mudou
COPY pom.xml .

# 2. Baixa as dependências (sem copiar o código fonte ainda)
RUN mvn dependency:go-offline

# 3. Agora sim, copia o código fonte
COPY src ./src

# 4. Compila o projeto e gera o .jar (Pula os testes para agilizar o build no Docker)
RUN mvn clean package -DskipTests

# Estágio final: imagem para rodar a aplicação
# Usamos uma imagem JRE (apenas execução), que é muito mais leve que o JDK completo
FROM openjdk:8-jre-slim

WORKDIR /app

# Copia o .jar gerado no estágio de build para a imagem final
# O *.jar pega qualquer nome de arquivo gerado, renomeando para app.jar
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta padrão do Spring Boot
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]