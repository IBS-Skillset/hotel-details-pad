FROM amazoncorretto:11
COPY target/hotel-details-pad.jar hotel-details-pad.jar
ENTRYPOINT ["java","-jar","/hotel-details-pad.jar"]

