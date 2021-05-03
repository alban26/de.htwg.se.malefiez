FROM hseeberger/scala-sbt:8u222_1.3.5_2.13.1
RUN \
    apt-get update && \
    apt-get install -y sbt libxrender1 libxtst6 libxi6
EXPOSE 8084
WORKDIR /malefiz
ADD target/scala-2.13/root.jar /malefiz
CMD java -jar root.jar
ENV DOCKERENV="TRUE"