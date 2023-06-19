BUILD_JAR='/home/ubuntu/here-city/*.jar'
JAR_NAME=$(basename ${BUILD_JAR})
TARGET_PATH='/home/ubuntu/here-city/application.jar'
JAR_BOX_PATH='/home/ubuntu/here-city/jar/'

echo "  > 배포 JAR: "${JAR_NAME}

echo "  > chmod 770 ${BUILD_JAR}"
sudo chmod 770 ${BUILD_JAR}

echo "  > cp ${BUILD_JAR} ${JAR_BOX_PATH}"
sudo cp ${BUILD_JAR} ${JAR_BOX_PATH}

echo "  > chown -h ubuntu:ubuntu ${JAR_BOX_PATH}${JAR_NAME}"
sudo chown -h ubuntu:ubuntu ${JAR_BOX_PATH}${JAR_NAME}

echo "  > sudo ln -s -f ${JAR_BOX_PATH}${JAR_NAME} ${TARGET_PATH}"
sudo ln -s -f ${JAR_BOX_PATH}${JAR_NAME} ${TARGET_PATH}
