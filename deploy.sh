#!/bin/bash

APP_DIR="/home/ubuntu/cicd"
LOG_FILE="$APP_DIR/nohup.out"

# 최신 JAR 파일 가져오기
JAR_FILE=$(ls -t $APP_DIR/build/libs/bookmark-0.0.1-SNAPSHOT.jar | head -n 1)
echo "Found JAR file: $JAR_FILE"

# 실행 중인 애플리케이션 종료
PID=$(pgrep -f $JAR_FILE) # JAR 파일의 PID 가져오기
if [ -n "$PID" ]; then
  echo "Killing running application with PID: $PID"
  kill -9 $PID
  sleep 2
else
  echo "No running application found."
fi

echo "Starting new application: $JAR_FILE"
sudo -E nohup java -jar $JAR_FILE > $LOG_FILE 2>&1 &
if [ $? -eq 0 ]; then
  echo "Application started successfully."
  echo "Logs can be found in: $LOG_FILE"
else
  echo "Failed to start application." >&2
  exit 1
fi
