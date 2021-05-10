FROM mongo:latest
EXPOSE 27017
ENV MONGO_INITDB_DATABASE: malefiz-mongodb
ENV MONGO_INITDB_ROOT_USERNAME: malefiz
ENV MONGO_INITDB_ROOT_PASSWORD: malefiz21

ENV TZ=Europe/Berlin

RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && \
	echo $TZ > /etc/timezone