package com.ins.csdn.file.domain;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author 高秀和
 */
@Entity
@Table(name = "file_info", schema = "csdn_fs", catalog = "")
public class FileInfoEntity {
    private String fileId;
    private String fileUrl;
    private String fileName;
    private Long downloadTime;
    private Long fileSize;
    private String fileSuffix;
    private String belongWebsite;
    private String fileAuthor;
    private Long uploadTime;
    private Integer times;
    private String fdfsUrl;

    @Id
    @Column(name = "file_id")
    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    @Column(name = "fdfs_url")
    public String getFdfsUrl() {
        return fdfsUrl;
    }

    public void setFdfsUrl(String fdfsUrl) {
        this.fdfsUrl = fdfsUrl;
    }

    @Basic
    @Column(name = "file_url")
    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    @Basic
    @Column(name = "file_name")
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Basic
    @Column(name = "download_time")
    public Long getDownloadTime() {
        return downloadTime;
    }

    public void setDownloadTime(Long downloadTime) {
        this.downloadTime = downloadTime;
    }

    @Basic
    @Column(name = "file_size")
    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    @Basic
    @Column(name = "file_suffix")
    public String getFileSuffix() {
        return fileSuffix;
    }

    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }

    @Basic
    @Column(name = "belong_website")
    public String getBelongWebsite() {
        return belongWebsite;
    }

    public void setBelongWebsite(String belongWebsite) {
        this.belongWebsite = belongWebsite;
    }

    @Basic
    @Column(name = "file_author")
    public String getFileAuthor() {
        return fileAuthor;
    }

    public void setFileAuthor(String fileAuthor) {
        this.fileAuthor = fileAuthor;
    }

    @Basic
    @Column(name = "upload_time")
    public Long getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Long uploadTime) {
        this.uploadTime = uploadTime;
    }

    @Basic
    @Column(name = "times")
    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileInfoEntity that = (FileInfoEntity) o;
        return Objects.equals(fileId, that.fileId) &&
                Objects.equals(fileUrl, that.fileUrl) &&
                Objects.equals(fileName, that.fileName) &&
                Objects.equals(downloadTime, that.downloadTime) &&
                Objects.equals(fileSize, that.fileSize) &&
                Objects.equals(fileSuffix, that.fileSuffix) &&
                Objects.equals(belongWebsite, that.belongWebsite) &&
                Objects.equals(fileAuthor, that.fileAuthor) &&
                Objects.equals(uploadTime, that.uploadTime) &&
                Objects.equals(times, that.times);
    }

    @Override
    public int hashCode() {

        return Objects.hash(fileId, fileUrl, fileName, downloadTime, fileSize, fileSuffix, belongWebsite, fileAuthor, uploadTime, times);
    }
}
