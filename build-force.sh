./gradlew build -x detekt -x test
nohup java -jar build/libs/here-city-backend-0.0.1-SNAPSHOT.jar > app.log &
