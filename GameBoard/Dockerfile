FROM hseeberger/scala-sbt:8u222_1.3.5_2.13.1
RUN \
    apt-get update && \
    apt-get install -y sbt libxrender1 libxtst6 libxi6
EXPOSE 8083
WORKDIR /malefiz
ADD target/scala-2.13/gameboard.jar /malefiz
CMD java -jar gameboard.jar
ENV DOCKERENV="TRUE"