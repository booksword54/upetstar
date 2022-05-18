#版本为java11
FROM openjdk:11
#将所有jar包复制
COPY ./target/*.jar /app.jar
#服务端口号
CMD ["--server.port=9000"]
#向外面暴露的端口
EXPOSE 9000
#容器启动的时候运行的命令
ENTRYPOINT ["java","-jar","-Dfile.encoding=utf-8","/app.jar"]
