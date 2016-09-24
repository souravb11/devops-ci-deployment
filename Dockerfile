FROM tomcat:7.0.69-jre8
MAINTAINER "Rolind Roy <hello@rolindroy.com>"
USER root
COPY CounterWebApp.war /usr/local/tomcat/webapps/