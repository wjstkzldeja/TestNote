package org.kdea.uploadMyBatis;
 
import org.springframework.web.multipart.MultipartFile;  
 
public class UploadedFile {  
   
 private MultipartFile file;  
 //�ѹ��� �ټ����� ������ ���� ���� List<MultipartFile> files �� ���� �����ϸ� �ȴ�
 
 public MultipartFile getFile() {  
  return file;  
 }  
   
 public void setFile(MultipartFile file) {  
  this.file = file;  
 }  
}