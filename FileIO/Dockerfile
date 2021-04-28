FROM hseeberger/scala-sbt:8u222_1.3.5_2.13.1
EXPOSE 8081
WORKDIR /malefiz
ADD target/scala-2.13/fileio.jar /malefiz
CMD java -jar fileio.jar
ENV DOCKERENV="TRUE"