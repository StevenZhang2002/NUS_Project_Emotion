FROM openjdk:17
LABEL version=<VSERSION>
ADD ./<JAR_NAME> /opt/
RUN fc-cache -f -v
WORKDIR /opt/
EXPOSE <JAR_PORT>
CMD ["java","<MEMORY_SIZE>","-jar","<JAR_NAME>"]
