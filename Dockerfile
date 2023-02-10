FROM openjdk:17
EXPOSE 8080
COPY ./build/libs/delivery-0.0.1-SNAPSHOT.jar ./delivery.jar
CMD ["java","-jar","delivery.jar"]