# FastDFS

1. 下载
https://github.com/happyfish100/fastdfs-client-java.git
进行编译打包

2. 参考文章
https://blog.csdn.net/wufewu/article/details/84801600

3. groupName: group1
   fileName: M00/00/00/wKjrgF7jJbmAfnDiABa87KhJCH4468.jpg
   
   
# MinIO
docker run -p 9000:9000 --name minio -d --restart=always -e "MINIO_ACCESS_KEY=admin" -e "MINIO_SECRET_KEY=admin123456" -v /home/data:/data -v /home/config:/root/.minio minio/minio server /data