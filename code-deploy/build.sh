BUILD_JAR='/home/ubuntu/build/here-city-backend-0.0.1-SNAPSHOT.jar'
JAR_NAME=$(basename ${BUILD_JAR})

echo "  > 배포 JAR: "${JAR_NAME}

echo "  > chmod 770 ${BUILD_JAR}"
sudo chmod 770 ${BUILD_JAR}

echo "  > 현재 실행중인 애플리케이션 pid 확인" >> /home/ubuntu/build/deploy.log
CURRENT_PID=$(pgrep -f $JAR_NAME)
echo "  > 현재 실행중인 애플리케이션 pid: $CURRENT_PID"

if [ -z $CURRENT_PID ]
then
  echo "  > 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다." >> /home/ubuntu/build/deploy.log
else
  echo "  > kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "  > LogBack 로그저장 디렉토리 생성"
sudo mkdir -p /home/ubuntu/build/logs

echo "  > BUILD_JAR 배포"    >> /home/ubuntu/build/deploy.log
nohup java -Dlogback.configurationFile=/home/ubuntu/build/logback-spring.xml -Dspring.profiles.active=prod -jar $BUILD_JAR >> /home/ubuntu/build/deploy.log 2>/home/ubuntu/build/deploy_err.log &
