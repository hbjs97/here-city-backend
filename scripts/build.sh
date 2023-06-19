BUILD_JAR='/home/ubuntu/here-city/*.jar'
JAR_NAME=$(basename ${BUILD_JAR})

echo "  > 배포 JAR: "${JAR_NAME}

echo "  > chmod 770 ${BUILD_JAR}"
sudo chmod 770 ${BUILD_JAR}

echo "  > build 파일 복사" >> /home/ubuntu/here-city/deploy.log
DEPLOY_PATH='/home/ubuntu/here-city/application.jar'
cp $BUILD_JAR $DEPLOY_PATH

echo "  > 현재 실행중인 애플리케이션 pid 확인" >> /home/ubuntu/here-city/deploy.log
CURRENT_PID=$(pgrep -f $JAR_NAME)

if [ -z $CURRENT_PID ]
then
  echo "  > 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다." >> /home/ubuntu/here-city/deploy.log
else
  echo "  > kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

DEPLOY_JAR=$DEPLOY_PATH$JAR_NAME
echo "  > DEPLOY_JAR 배포"    >> /home/ubuntu/here-city/deploy.log
nohup java -jar $DEPLOY_JAR >> /home/ubuntu/here-city/deploy.log 2>/home/ubuntu/here-city/deploy_err.log &