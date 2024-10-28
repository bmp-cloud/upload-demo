# 빌드 단계
FROM amazoncorretto:23 AS build

WORKDIR /app

# Maven Wrapper 및 소스 파일 복사
COPY . .

# Maven Wrapper를 사용하여 애플리케이션 빌드
RUN ./mvnw clean package

# 실행 단계
FROM amazoncorretto:23

# `tar` 설치
RUN yum update -y && \
    yum install -y tar gzip

# Tomcat 10 다운로드 및 설치
ENV TOMCAT_VERSION=10.1.31
RUN curl -O https://dlcdn.apache.org/tomcat/tomcat-10/v${TOMCAT_VERSION}/bin/apache-tomcat-${TOMCAT_VERSION}.tar.gz && \
    tar xvf apache-tomcat-${TOMCAT_VERSION}.tar.gz && \
    mv apache-tomcat-${TOMCAT_VERSION} /usr/local/tomcat && \
    rm apache-tomcat-${TOMCAT_VERSION}.tar.gz

# 기존 ROOT 애플리케이션 삭제
RUN rm -rf /usr/local/tomcat/webapps/ROOT

# 빌드된 WAR 파일을 ROOT.war로 복사하여 루트 컨텍스트에 배포
COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/ROOT.war

# Tomcat 포트 공개
EXPOSE 8080

# Tomcat 실행 명령어
CMD ["/usr/local/tomcat/bin/catalina.sh", "run"]