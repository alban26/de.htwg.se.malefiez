docker build . -t root
docker build ./FileIO -t fileio
docker build ./GameBoard -t gameboard
docker build -f GameBoard/src/main/scala/de/htwg/se/malefiz/gameBoardModule/model/dbComponent/mongoDbImpl/Dockerfile . -t malefiz-mongodb
docker build -f GameBoard/src/main/scala/de/htwg/se/malefiz/gameBoardModule/model/dbComponent/slickImpl/Dockerfile . -t malefiz-mysql