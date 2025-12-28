# --- Estágio 1: Build (Maven + Java 21) ---
# Usamos a imagem oficial do Maven que já vem com o Eclipse Temurin JDK 21
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# 1. Copia apenas o pom.xml para baixar as dependências primeiro (cache)
COPY pom.xml .

# 2. Baixa as dependências para o cache local do Docker
RUN mvn dependency:go-offline

# 3. Copia o código fonte do projeto
COPY src ./src

# 4. Faz o build e gera o .jar (pulando testes para ser mais rápido na criação da imagem)
RUN mvn clean package -DskipTests

# --- Estágio 2: Runtime (Apenas JRE 21) ---
# Usamos a imagem leve do Eclipse Temurin JRE 21 para rodar
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copia o artefato gerado no estágio anterior
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta 8080 (padrão do Spring)
EXPOSE 8080

# Comando de inicialização
ENTRYPOINT ["java", "-jar", "app.jar"]