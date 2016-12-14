package org.kdea.uploadMyBatis;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PdsSvc2 {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	public boolean getFileInput(String title, UploadedFile uploadedFile) {
		 InputStream inputStream = null;  
		 OutputStream outputStream = null; 
		
		  	MultipartFile file = uploadedFile.getFile();
		    //MultipartFile 웹에서 서버로 저장할때 사용
		  	String basicName = file.getOriginalFilename();  
		    String fileName = file.getOriginalFilename();  
		    //업로드된 fileName 나중에 쓰려고 받아둔다
		    
		    File newFile=null;
		    Date date = new Date();
		    long dateNum = date.getTime();
		    
		    long filesize=0;
		    //upload 중복검사
		    boolean isValid = isValid(fileName);
		    if(isValid){
		    	newFile = new File("C:/test/upload/" + fileName);
		    }else{
		    	fileName = fileName+dateNum;
        		newFile = new File("C:/test/upload/" + fileName);
		    }
		  //upload 중복검사 끝
		    try {
		        inputStream = file.getInputStream();  
		        if (!newFile.exists()) {  
		            newFile.createNewFile();

		        }
		        outputStream = new FileOutputStream(newFile);  
		        int read = 0;  
		        byte[] bytes = new byte[1024];  
		   
		        while ((read = inputStream.read(bytes)) != -1) {  
		            outputStream.write(bytes, 0, read);
		            //이만큼 , 0부터 , 읽어온만큼 써라
		        }  
		    } catch (IOException e) {  
		        e.printStackTrace();  
		    } finally {
		        try {
		        	inputStream.close();
		            outputStream.close();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    }
		    //file size
	        filesize = newFile.length();
		    //확장자 구하기
		    int Idx = basicName.lastIndexOf(".");
		    String ext = basicName.substring(Idx + 1 );
		    //확장자 구하기 끝
		    System.out.println(title);
		    System.out.println(basicName);
		    System.out.println(fileName);
		    System.out.println(filesize);
		    System.out.println(ext);
		    PdsVO2 vo = new PdsVO2();
		    vo.setTitle(title);
		    vo.setFilename(basicName);
		    vo.setSubFilename(fileName);
		    vo.setFilesize(filesize);
		    vo.setExt(ext);
		    int upResult = getUpload(vo);
			boolean bResult = upResult>0 ? true: false;
		    
		    return bResult;	
	}
	//////////////////fileInput
	public int getUpload(PdsVO2 vo){
		PdsDAO2 dao = sqlSessionTemplate.getMapper(PdsDAO2.class);
		int vo2 = dao.upload(vo);
		System.out.println("vo2"+vo2);
		return vo2;
	}
	//upload 중복검사
	public boolean isValid(String fname){
		File dir = new File("C:/test/upload/");
		String[] names = dir.list();
		if(names==null || names.length==0) return true;
		for(int i=0;i<names.length;i++){
			if(names[i].equals(fname)) return false;
		}
		return true;
	}

	public PdsVO2 getDownload(int num){
		PdsDAO2 dao = sqlSessionTemplate.getMapper(PdsDAO2.class);
		PdsVO2 vo = dao.download(num);
		return vo;
	}
 

}
