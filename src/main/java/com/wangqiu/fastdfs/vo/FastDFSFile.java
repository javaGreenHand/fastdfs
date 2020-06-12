package com.wangqiu.fastdfs.vo;

/**
 * @Description
 * @Author qiu.wang
 * @Date 2020/6/12 13:04
 * @Version 1.0
 **/
public class FastDFSFile {

    //文件名
    private String name;
    //文件内容
    private byte[] content;
    //文件类型
    private String ext;
    //作者 (上传者)
    private String author;

    public FastDFSFile() {
    }


    public FastDFSFile(String name, byte[] content, String ext, String author) {
        this.name = name;
        this.content = content;
        this.ext = ext;
        this.author = author;
    }

    public FastDFSFile(String name, byte[] content, String ext) {
        this.name = name;
        this.content = content;
        this.ext = ext;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
