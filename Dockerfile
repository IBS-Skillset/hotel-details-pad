FROM amazoncorretto:11

WORKDIR /opt/app

COPY target/hotel-details-pad.jar /opt/app/hotel-details-pad.jar

ENTRYPOINT ["/usr/bin/java"]
CMD ["-Dspring.profiles.active=dev", "-Dorg.apache.catalina.STRICT_SERVLET_COMPLIANCE=true", "-jar", "/opt/app/hotel-details-pad.jar"]

EXPOSE 8080