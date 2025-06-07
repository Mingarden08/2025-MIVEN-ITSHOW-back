AWS 접속하는 명령어 
ssh -i 3301_keypair.pem ubuntu@3.38.185.232

jar실행명령어 
java -jar -Dspring.profiles.active=dev ./api-0.0.1-SNAPSHOT.jar 1> /dev/null 2>&1 &

실행중인 프로세스 찾기 
ps -ef | grep api

프로세스 종료
kill -9 PID 

로그 확인 경로 
tail -f /home/ubuntu/project/log/dev_app.log


