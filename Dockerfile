FROM maven:3

WORKDIR /app
COPY . /app
RUN mvn clean package

EXPOSE 8080
CMD ["mvn", "spring-boot:run"]
