FROM hseeberger/scala-sbt
WORKDIR /malefiz
ADD . /malefiz
CMD sbt test