FROM maven:3.6.3-jdk-11-openj9 as maven
WORKDIR /app
COPY . .
RUN ["mvn","install"]
RUN mv target/my-first-ws-1.jar target/my-first-ws.jar

FROM openjdk:11.0.8
COPY --from=maven /app/target/my-first-ws.jar /usr/app/
RUN export AWS_ACCESS_KEY_ID=AKIAWKI24ASFYEPKHSQJ
RUN export AWS_SECRET_ACCESS_KEY=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
WORKDIR /usr/app
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","my-first-ws.jar"]

#FROM tomcat:10
#COPY --from=maven /app/target/my-first-ws.war /usr/local/tomcat/webapps.dist
#COPY tomcat-users.xml /usr/local/tomcat/conf/
#WORKDIR /usr/local/tomcat/
#RUN mv webapps webapps2
#RUN mv webapps.dist/ webapps
#COPY context.xml webapps/manager/META-INF
#EXPOSE 8080
#CMD ["catalina.sh", "run"]
