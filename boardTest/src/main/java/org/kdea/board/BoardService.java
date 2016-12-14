package org.kdea.board;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import org.kdea.uploadMyBatis.UploadedFile;
import org.kdea.uploadMyBatis.PdsDAO2;
import org.kdea.uploadMyBatis.PdsVO2;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class BoardService {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	public List<BoardVO> getList(SearchPageVO vo) {	
		BoardDAO dao = sqlSessionTemplate.getMapper(BoardDAO.class);
		List<BoardVO> list;
		if(vo.getSearchText()==null||vo.getSearchText().equals("")){
		list = dao.list(vo);
		}else{
		list = dao.search(vo);
		}
		return list;
	}
	public BoardVO getDetail(BoardVO vo) {
		BoardDAO dao = sqlSessionTemplate.getMapper(BoardDAO.class);
		BoardVO detail = dao.detail(vo);
		return detail;
	}
	public boolean getDelete(int boardnum) {
		BoardDAO dao = sqlSessionTemplate.getMapper(BoardDAO.class);
		int update = dao.delete(boardnum);
		boolean result = update > 0 ? true : false; 
		return result;
	}
	public boolean getUpdate(BoardVO vo) {
		BoardDAO dao = sqlSessionTemplate.getMapper(BoardDAO.class);
		int update = dao.update(vo);
		boolean result = update > 0 ? true : false; 
		return result;
	}
	public boolean getWriting(BoardVO vo) {
		BoardDAO dao = sqlSessionTemplate.getMapper(BoardDAO.class);
		int update = dao.writing(vo);
		boolean result = update > 0 ? true : false; 
		return result;
	}
	public BoardVO writingbefore() {
		BoardDAO dao = sqlSessionTemplate.getMapper(BoardDAO.class);
		BoardVO vo = dao.writingbefore();
		return vo;
	}
	public boolean getComment(BoardVO vo) {
		BoardDAO dao = sqlSessionTemplate.getMapper(BoardDAO.class);
		int update = dao.comment(vo);
		boolean result = update > 0 ? true : false; 
		return result;
	}
	//���ε����
	public boolean getFileInput(UploadedFile uploadedFile, int boardnum) {
		System.out.println("����?");
		 InputStream inputStream = null;  
		 OutputStream outputStream = null; 
		
		  	MultipartFile file = uploadedFile.getFile();
		    //MultipartFile ������ ������ �����Ҷ� ���
		  	String basicName = file.getOriginalFilename();  
		    String fileName = file.getOriginalFilename();  
		    //���ε�� fileName ���߿� ������ �޾Ƶд�
		    
		    File newFile=null;
		    Date date = new Date();
		    long dateNum = date.getTime();
		    
		    long filesize=0;
		    //upload �ߺ��˻�
		    boolean isValid = isValid(fileName);
		    if(isValid){
		    	newFile = new File("C:/test/upload/" + fileName);
		    }else{
		    	fileName = fileName+dateNum;
       		newFile = new File("C:/test/upload/" + fileName);
		    }
		  //upload �ߺ��˻� ��
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
		            //�̸�ŭ , 0���� , �о�¸�ŭ ���
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
		    //Ȯ���� ���ϱ�
		    int Idx = basicName.lastIndexOf(".");
		    String ext = basicName.substring(Idx + 1 );
		    //Ȯ���� ���ϱ� ��
		    System.out.println(basicName);
		    System.out.println(fileName);
		    System.out.println(filesize);
		    System.out.println(ext);
		    PdsVO2 vo = new PdsVO2();
		    vo.setUpNum(boardnum);
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
		System.out.println(vo2);
		return vo2;
	}
	//upload �ߺ��˻�
	public boolean isValid(String fname){
		File dir = new File("C:/test/upload/");
		String[] names = dir.list();
		if(names==null || names.length==0) return true;
		for(int i=0;i<names.length;i++){
			if(names[i].equals(fname)) return false;
		}
		return true;
	}
	//���ε峡
	public PdsVO2 getfileDetail(int upNum) {
		PdsDAO2 dao = sqlSessionTemplate.getMapper(PdsDAO2.class);
		PdsVO2 vo = dao.info(upNum);
		return vo;
	}
	public PdsVO2 getDownload(int num){
		PdsDAO2 dao = sqlSessionTemplate.getMapper(PdsDAO2.class);
		PdsVO2 vo = dao.download(num);
		return vo;
	}
}
