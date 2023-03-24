./gradlew build -x detekt -x test
java -jar build/libs/here-city-backend-0.0.1-SNAPSHOT.jar -Dspring.profiles.active=prod
