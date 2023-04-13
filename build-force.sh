# ps -ef | grep java
# kill <PID>

export SPRING_PROFILES_ACTIVE=prod
./gradlew build -x detekt -x test
nohup java -jar build/libs/here-city-backend-0.0.1-SNAPSHOT.jar > app.log &
Private-0.0.1-SNAPSHOT.jar > app.log &
