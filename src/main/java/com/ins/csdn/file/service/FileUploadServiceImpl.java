package com.ins.csdn.file.service;

import com.ins.csdn.FileUpLoadConstant;
import com.ins.csdn.file.domain.FileInfoEntity;
import com.ins.csdn.file.domain.FileRepository;
import com.ins.csdn.file.domain.QFileInfoEntity;
import com.ins.csdn.tool.MD5;
import com.ins.csdn.tool.SaveFile;
import com.ins.csdn.tool.StringUtil;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class FileUploadServiceImpl implements FileUploadService {
		
	@Autowired
	private SaveFile saveFile;

	private JPAQueryFactory queryFactory;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private FileRepository fileRepository;

	@PostConstruct
	public void initFactory() {
		queryFactory = new JPAQueryFactory(entityManager);
	}
	
	@Override
	public String uploadFileToLocal(MultipartFile file,String storePath, String fileName) throws Exception {
		String separator = File.separator;
		String fileInfo = file.getOriginalFilename();
		if(StringUtil.checkNull(fileName)) {
			fileName = new Date().getTime()+"";
		}
		if(StringUtil.checkNull(storePath)) {
			storePath = System.getProperty("java.io.tmpdir");
		}
		String fileType = fileInfo.substring(fileInfo.lastIndexOf("."));
		storePath =  storePath.endsWith(separator)?storePath:(storePath+separator);
		File temp = new File(storePath+fileName+fileType);
		temp.setWritable(true, false);
		if (!temp.getParentFile().exists()) {
			temp.getParentFile().mkdirs();
		}
		file.transferTo(temp);
		return temp.getAbsolutePath();
	}
	
	@Override
	public FileInfoEntity uploadToTFS(MultipartFile file, boolean delete,String url,String author,String webSite,Long uploadTime) throws Exception {
		Map<String, String> result = new HashMap<>();
		QFileInfoEntity qFileInfoEntity = QFileInfoEntity.fileInfoEntity;
		JPAQuery<FileInfoEntity> query = queryFactory.select(qFileInfoEntity).from(qFileInfoEntity)
				.where(qFileInfoEntity.fileUrl.eq(url));
		String fileInfo = file.getOriginalFilename();
		Long size = file.getSize();
		boolean flag = true;
		if(null==query.fetch()||query.fetch().size()<1) {
			saveFile.setUp();
			String tempPath = this.uploadFileToLocal(file, null, null);
			String serverPath = saveFile.largeUpload(tempPath);
			saveFile.destory();
			FileInfoEntity fileInfoEntity = new FileInfoEntity();
			fileInfoEntity.setUploadTime(uploadTime);
			fileInfoEntity.setFileId(MD5.encrypt(serverPath));
			fileInfoEntity.setDownloadTime(new Date().getTime());
			fileInfoEntity.setBelongWebsite(webSite);
			fileInfoEntity.setFdfsUrl(serverPath);
			fileInfoEntity.setFileAuthor(author);
			fileInfoEntity.setFileName(fileInfo);
			fileInfoEntity.setFileSize(size);
			fileInfoEntity.setFileSuffix(fileInfo.substring(fileInfo.lastIndexOf(".")));
			fileInfoEntity.setTimes(0);
			fileInfoEntity.setFileUrl(url);
			fileRepository.save(fileInfoEntity);
			if(delete) {
				File tempFile = new File(tempPath);
				tempFile.delete();
				result.remove(FileUpLoadConstant.LOCAL_PATH);
			}
			return fileInfoEntity;
		}else {
			return null;
		}
	}
	
}
