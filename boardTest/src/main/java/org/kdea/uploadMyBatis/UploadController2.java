package org.kdea.uploadMyBatis;
 
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/board")
@Controller 
public class UploadController2{
	@Autowired
	private PdsSvc2 svc;
	@Autowired
	private FileValidator fileValidator;
	
   //upload
	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
	public String fileUploaded(
			@ModelAttribute("title") String title,
			@ModelAttribute("uploadedFile") UploadedFile uploadedFile, 
			BindingResult result) {
		fileValidator.validate(uploadedFile, result);
		
		boolean svcResult;
		if (result.hasErrors()) {
			return "board/boardList";
		} else {
			svcResult = svc.getFileInput(title, uploadedFile);
		}
		return null;

	}
	 @RequestMapping("/upload/download")
	 @ResponseBody
	 //@ResponseBody 응답을 구성한다 , view 에 바로 전달될 데이터
	 public byte[] getImage(HttpServletResponse response,
	            @RequestParam (value="upNum")int num){
		 
		 	PdsVO2 vo = svc.getDownload(num);
		 	String filename = vo.getFilename();
		 	
	        File file = new File("C:/test/upload/"+filename);
	        byte[] bytes = null;
	        String fn = null;
			try {
				bytes = org.springframework.util.FileCopyUtils.copyToByteArray(file);
				 fn = new String(file.getName().getBytes(), "iso_8859_1");
				 //한글이라면 영문조합으로 변경 -"iso_8859_1"
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        response.setHeader("Content-Disposition", "attachment; filename=\"" + fn + "\"");
	        response.setContentLength(bytes.length);
	        response.setContentType("image/jpeg");
	 
	        return bytes;
	 } 
}