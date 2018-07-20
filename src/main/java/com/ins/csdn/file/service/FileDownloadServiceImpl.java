package com.ins.csdn.file.service;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Service
public class FileDownloadServiceImpl implements FileDownloadService {

	@Override
	public boolean downloadFile(HttpServletResponse response ,String file) throws Exception {
		String separator = System.getProperty("file.separator");
		String fileName = file.substring(file.lastIndexOf(separator), file.lastIndexOf("."));
		response.setHeader("content-type", "application/octet-stream");
	    response.setContentType("application/octet-stream");
	    response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
	    byte[] buff = new byte[1024];
	    BufferedInputStream bis = null;
	    OutputStream os = null;
	    try {
	      os = response.getOutputStream();
	      bis = new BufferedInputStream(new FileInputStream(new File(file)));
	      int i = bis.read(buff);
	      while (i != -1) {
	        os.write(buff, 0, buff.length);
	        os.flush();
	        i = bis.read(buff);
	      }
	      return true;
	    } catch (IOException e) {
	      e.printStackTrace();
	      return false;
	    } finally {
	      if (bis != null) {
	        try {
	          os.close();
	          bis.close();
	        } catch (IOException e) {
	          e.printStackTrace();
	        }
	      }
	    }

	}

}
