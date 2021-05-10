FROM mysql:5.7
EXPOSE 3306

ENV MYSQL_ROOT_PASSWORD=malefiz21
ENV MYSQL_DATABASE=malefizdb
ENV MYSQL_USER=malefiz
ENV MYSQL_PASSWORD=malefiz21

ENV TZ=Europe/Berlin

RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && \
	echo $TZ > /etc/timezone