FROM hseeberger/scala-sbt:8u181_2.13.1_1.2.6
RUN apt-get update && \
    apt-get install -y --no-install-recommends openjfx && \
    rm -rf /var/lib/apt/lists/* && \
    apt-get install -y sbt libxrender1 libxtst6 libxi6
WORKDIR /Malefiz
ADD . /Malefiz
CMD sbt run