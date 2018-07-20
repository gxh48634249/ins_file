package com.ins.csdn.tool;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SaveFile {
	
	StorageClient storageClient = null;
	TrackerServer trackerServer = null;
	
	@Value("${fdfs.view.address}")
	private String view;
	
	public Integer len = 0;
	
	public static String GROUP = "group";
	
	public static String FILENAME = "fileName";
	
	public void setUp() {
		this.len = this.view.length();
		String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		try {
			System.out.println("加载配置文件"+"_________"+path+"client.conf"+"_______成功");
			ClientGlobal.init(path+"client.conf");
			TrackerClient trackerClient = new TrackerClient();
	        trackerServer = trackerClient.getConnection();
	        StorageServer storageServer = null;
	        storageClient = new StorageClient(trackerServer, storageServer);
		} catch (IOException | MyException e) {
			System.out.println("加载配置文件"+"_________"+path+"client.conf"+"_______失败");
			e.printStackTrace();
		}
	}
	
    public String smallUpload(String path) throws Exception{
    	String[] strs=path.split("\\.");
		String tfsSuffix=strs[strs.length-1];
        String[] strings = null;
		byte[] buffer = getBytes(path);
		strings = storageClient.upload_file(buffer, tfsSuffix, null);
		String group = "";
		String dfsName = "";
        if (strings.length==2){
        	group = strings[0];
        	dfsName = strings[1];
        }
        String str = view+File.separator+group+File.separator+dfsName;
        str = str.replace("\\", "/");
        System.out.println(str);
        return str;
    }
    
    public String largeUpload(String path) throws Exception{
    	String[] strs=path.split("\\.");
		String tfsSuffix=strs[strs.length-1];
        String[] strings = null;
		strings = storageClient.upload_file(path, tfsSuffix, null);
		String group = "";
		String dfsName = "";
        if (strings.length==2){
        	group = strings[0];
        	dfsName = strings[1];
        }
        String str = view+File.separator+group+File.separator+dfsName;
        str = str.replace("\\", "/");
        System.out.println(str);
        return str;
    }
    
	@SuppressWarnings("static-access")
	public void delete(String url){
		try {
			Map<String, String> map = this.getFileInfo(url);
			System.out.println("文件信息"+map);
			storageClient.delete_file(map.get(this.GROUP), map.get(this.FILENAME));
		} catch (IOException | MyException e) {
			e.printStackTrace();
		}  
    }
	
	@SuppressWarnings("static-access")
	private Map<String, String> getFileInfo(String path){
		path = path.substring(this.len,path.length());
		if(path.startsWith("/")) {
			path = path.substring(1);
		}
		String[] temp = path.split("/");
		Map<String, String> result = new HashMap<>();
		List<String> temps = new ArrayList<>();
		for(int i=0;i<temp.length;i++) {
			if(i==0) {
				result.put(this.GROUP, temp[i]);				
			}
			else {
				temps.add(temp[i]);
			}
			
		}
		result.put(this.FILENAME, String.join("/", temps));
		return result;
	}
    public void destory(){
    	try {
			trackerServer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    private byte[] getBytes(String filePath){  
        byte[] buffer = null;  
        try {  
        	filePath = filePath.replace("\\", File.separator);
            File file = new File(filePath);  
            FileInputStream fis = new FileInputStream(file);  
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);  
            byte[] b = new byte[1000];  
            int n;  
            while ((n = fis.read(b)) != -1) {  
                bos.write(b, 0, n);  
            }  
            fis.close();  
            bos.close();  
            buffer = bos.toByteArray();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return buffer;  
    }
    
    @SuppressWarnings("unused")
	private int getFileSize(String path){
    	File file=new File(path);
    	if (file.exists()){
    		if(file.length()<104857600){
    			return 1;//小文件
    		}
    		return 2;//大文件
    	}else{
    		return 0;//不存在
    	}
    }
    
    public static void main(String[] args) throws Exception {
		SaveFile saveFile = new SaveFile();
		saveFile.setUp();
		System.out.println(saveFile.smallUpload("C:\\Users\\高秀和\\Pictures\\Screenshots\\屏幕截图(5).png"));
//		saveFile.destory();
//    	saveFile.delete("http://10.20.30.220:80/group22/M00/00/0A/ChQeVlm0-G2AalJ1AATk5p3BzTs869.png");
    	saveFile.destory();
	}
    
    @SuppressWarnings("static-access")
	public String fetch(String URL){
    	String path = System.getProperty("user.dir");
    	path = path.endsWith(File.separator)?path:path+File.separator;
    	String name = URL.substring(URL.lastIndexOf("/")+1);
    	byte[] b = null;
		try {
			Map<String, String> map = this.getFileInfo(URL);
			b = storageClient.download_file(map.get(this.GROUP), map.get(this.FILENAME));
		} catch (IOException | MyException e) {
			e.printStackTrace();
		} 
		File file = new File(path+name);
		if (!file.getParentFile().exists()){
			file.getParentFile().mkdirs();
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			fos.write(b);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return path+name;
    }
    
    @SuppressWarnings("static-access")
	public byte[] fetchByte(String URL){
    	byte[] b = null;
		try {
			Map<String, String> map = this.getFileInfo(URL);
			b = storageClient.download_file(map.get(this.GROUP), map.get(this.FILENAME));
		} catch (IOException | MyException e) {
			e.printStackTrace();
		} 
		return b;
    }
    
}
