FROM maven:3.6.3-jdk-11-openj9 as maven
WORKDIR /app
COPY . .
RUN ["mvn","install"]

FROM tomcat:8.5.60
COPY --from=maven /app/target/my-first-ws-1.war /usr/local/tomcat/webapps
EXPOSE 8080




