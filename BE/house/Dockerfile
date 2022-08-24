# JDK11 이미지 사용
FROM openjdk:11-jdk

#마운트에 /tmp를 사용하는 이유
#spring boot의 Tomcat의 default 저장소가 /tmp인데
#위와 같이 볼륨 마운트를 해주면 호스트의 /var/lib/docker에 임시파일을 만들고
#컨테이너 안의 /tmp 와 연결할 수 있다는 뜻입니다.
VOLUME /tmp

# JAR_FILE 변수에 값을 저장
ARG JAR_FILE=./build/libs/*.jar

# 변수에 저장된 것을 컨테이너 실행시 이름을 app.jar파일로 변경하여 컨테이너에 저장
COPY ${JAR_FILE} app.jar

# 빌드된 이미지가 run 될 때 실행할 명령어
ENTRYPOINT ["java","-jar","/app.jar"]