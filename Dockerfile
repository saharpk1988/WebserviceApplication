FROM maven:3.6.3-jdk-11-openj9 as maven
WORKDIR /app
COPY . .
RUN ["mvn","package"]

FROM tomcat:8.5.60
COPY --from=maven /app/my-first-ws/target/my-first-ws-1.war /usr/local/tomcat/webapps
EXPOSE 8080
#RUN ls -a



