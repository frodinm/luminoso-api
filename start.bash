./gradlew build -x Test

docker image prune -f
docker container prune -f
docker-compose build --no-cache
docker-compose up --force-recreate -d users-management
