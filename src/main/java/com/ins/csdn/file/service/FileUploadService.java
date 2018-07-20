package com.ins.csdn.file.service;

import com.ins.csdn.file.domain.FileInfoEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface FileUploadService {

	/**
	 * 支持将文件存放到本地位置
	 * 注意：文件存放位置的name必须为“file”
	 * @param file	需上传文件
	 * @param storePath	自定义本地存储路径
	 * @param fileName	自定义文件名
	 * @return 返回文件本地存放绝对路径
	 * @throws Exception
	 * @version 1.1.0-Beta-standard
	 */
	String uploadFileToLocal(MultipartFile file, String storePath, String fileName) throws Exception;
//	
//	/**
//	 * 支持将文件存放到本地位置
//	 * 注意：文件存放位置的name必须为“file”
//	 * @param file	需上传文件
//	 * @param storePath	自定义本地存储路径
//	 * @return 返回文件本地存放绝对路径
//	 * @throws Exception
//	 */
//	@Deprecated
//	String uploadFileToLocal(MultipartFile file,String storePath) throws Exception;

	/**
	 * 
	 * @param file	需上传文件
	 * @param fileName	自定义文件名，默认为32为随机字符串
	 * @param delete	文件上传服务器后是否删除本地文件 ，默认为true
	 * @return	文件在服务器访问路径+文件本地存放路径（若delete为true）
	 * @throws Exception
	 * @version 1.1.0-Beta-standard
	 */
	FileInfoEntity uploadToTFS(MultipartFile file, boolean delete, String url, String author, String webSite, Long uploadTime) throws Exception;
	
}
