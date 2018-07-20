package com.ins.csdn.file.web;

import com.ins.csdn.FileUpLoadConstant;
import com.ins.csdn.file.domain.FileInfoEntity;
import com.ins.csdn.file.domain.FileRepository;
import com.ins.csdn.file.domain.QFileInfoEntity;
import com.ins.csdn.file.service.FileUploadService;
import com.ins.csdn.tool.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * 用于处理文件的上传
 *
 */
@RestController
@RequestMapping("/ap-system")
@Api(value="文件上传服务",tags={"文件上传服务"})
public class FileLoadController {
	
	@Autowired
	private FileUploadService fileUploadService;
	
	@Autowired
	private SaveFile saveFile;

	@Autowired
	private FileController fileController;
	private JPAQueryFactory queryFactory;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private FileRepository fileRepository;

	@PostConstruct
	public void initFactory() {
		queryFactory = new JPAQueryFactory(entityManager);
	}
	
	/**
	 * 多文件上传
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/uploadFilesOnFDFS")
	@ApiOperation(value="多文件服务器上传",response=Result.class,httpMethod="POST")
	public Result uploadFilesOnFDFS(HttpServletRequest request,String url,String author,String webSite,Long uploadTime)throws Exception{
		try {
			List<MultipartFile> files = new ArrayList<>();
			files = ((MultipartHttpServletRequest)request).getFiles("file");
			if (files.isEmpty()) {
				return new Result(ResultConstant.FAIL_STATU, ResultConstant.UPLOAD_FILE_FAIL);
			}
			List<FileInfoEntity> entities = new ArrayList<>();
			for (MultipartFile file:files) {
				FileInfoEntity temp = this.fileUploadService.uploadToTFS(file,true,url,author,webSite,uploadTime);
				entities.add(temp);
			}
			return new Result(ResultConstant.SUCCESS_STATU, ResultConstant.UPLOAD_FILE_SUCCESS,entities);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, ResultConstant.UPLOAD_FILE_FAIL);
		}
	}

	/**
	 * 多文件上传
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/uploadFilesOnFDFSAjax")
	@ApiOperation(value="多文件服务器上传",response=Result.class,httpMethod="POST")
	public Result uploadFilesOnFDFSAjax(MultipartFile file,String url,String author,String webSite,Long uploadTime)throws Exception{
		try {
			if (file.isEmpty()) {
				return new Result(ResultConstant.FAIL_STATU, ResultConstant.UPLOAD_FILE_FAIL);
			}
			FileInfoEntity temp = this.fileUploadService.uploadToTFS(file,true,url,author,webSite,uploadTime);
			if(null==temp) {
				return new Result(ResultConstant.FAIL_STATU, "已经存在的下载连接");
			}
			return new Result(ResultConstant.SUCCESS_STATU, ResultConstant.UPLOAD_FILE_SUCCESS,temp);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, ResultConstant.UPLOAD_FILE_FAIL);
		}
	}

	
	@RequestMapping("/deleteFilesOnFDFS")
	@ApiOperation(value="服务器删除文件",response=Result.class,httpMethod="POST")
	public Result deleteFilesOnFDFS(String URLS)throws Exception{
		try {
			if(StringUtil.checkNull(URLS)) {
				return new Result(ResultConstant.FAIL_STATU, ResultConstant.UPDATA_FAIL_ERROE_ATTRIBUTE);
			}

			String[] temps = URLS.split(",");
			saveFile.setUp();
			Stream.of(temps).forEach(saveFile::delete);
			Stream.of(temps).forEach(n -> {
				fileController.deleteFile(MD5.encrypt(n));
			});
			saveFile.destory();
			return new Result(ResultConstant.SUCCESS_STATU, ResultConstant.DELETE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, ResultConstant.UPLOAD_FILE_FAIL);
		}
	}
	
	@RequestMapping("/downLoadFile")
	@ApiOperation(value="从文件服务器下载文件",response=Result.class,httpMethod="POST")
	public Result downLoadFile(HttpServletResponse response,String URL)throws Exception{
		try {
			if(StringUtil.checkNull(URL)) {
				return new Result(ResultConstant.FAIL_STATU, ResultConstant.UPDATA_FAIL_ERROE_ATTRIBUTE);
			}
			QFileInfoEntity qFileInfoEntity = QFileInfoEntity.fileInfoEntity;
			List<FileInfoEntity> entities = queryFactory.select(qFileInfoEntity)
					.from(qFileInfoEntity)
					.where(qFileInfoEntity.fileId.eq(MD5.encrypt(URL))).fetch();
			response.setContentType("application/x-gzip");
			String sufx = URL.substring(URL.lastIndexOf("."));
			String name = entities.get(0).getFileName();
			response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(name, "UTF-8"));
			fileController.getFile(MD5.encrypt(URL));
			OutputStream os = response.getOutputStream();
			byte[] bs = null;
			saveFile.setUp();
			bs = saveFile.fetchByte(URL);
			if(null==bs) {
				return new Result(ResultConstant.FAIL_STATU, ResultConstant.UPDATA_FAIL_ERROE_ATTRIBUTE);
			}
			os.write(bs);
			os.flush();
			os.close();
			return new Result(ResultConstant.SUCCESS_STATU, ResultConstant.DELETE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, ResultConstant.UPLOAD_FILE_FAIL);
		}
	}
	
}
