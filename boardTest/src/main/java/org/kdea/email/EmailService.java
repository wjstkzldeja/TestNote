package org.kdea.email;

import javax.mail.internet.InternetAddress; 
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.json.simple.JSONObject;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("emailService")
public class EmailService {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
    @Autowired
    protected JavaMailSender  mailSender;
    @Autowired
    BCryptPasswordEncoder passwordEncoder; //서블릿 설정파일에 빈으로 등록
    public boolean sendMail(EmailVO email) throws Exception {
        try{
	        MimeMessage msg = mailSender.createMimeMessage();
	        
	        InternetAddress addr = new InternetAddress("someone@paran.com");//노필요
	        msg.setFrom(addr); // 송신자를 설정해도 소용없지만 없으면 오류가 발생한다
	        
	        msg.setSubject(email.getSubject());
	        msg.setText(email.getContent());
	        msg.setRecipient(RecipientType.TO , new InternetAddress(email.getReceiver()));
	         
	        mailSender.send(msg);
	        return true;
        }catch(Exception ex) {
        	ex.printStackTrace();
        }
        return false;
    }
    public int getInsert(EmailVO vo){
    	EmailDAO dao =  sqlSessionTemplate.getMapper(EmailDAO.class);
    	System.out.println(vo.getId());
    	System.out.println(vo.getPwd());
    	System.out.println(vo.getEmail());
    	 // 이용자의 비밀번호를 bcrypt 알고리듬으로 암호화하여 DB에 저장한다
        String encodedPwd = passwordEncoder.encode(vo.getPwd());
        vo.setId(vo.getId());
        vo.setPwd(encodedPwd);
        vo.setEmail(vo.getEmail());
        vo.setEnabled("1");
        vo.setAuthority("USER");
        int sucess = dao.insert(vo);
         
      /*  JSONObject jsObj = new JSONObject();
        jsObj.put("ok", added);
        jsObj.put("pwd", encodedPwd);
        return jsObj.toJSONString();*/
    	return sucess;
    }
	public boolean getidcheck(String id) {
		System.out.println("id : "+id);
		EmailDAO dao =  sqlSessionTemplate.getMapper(EmailDAO.class);	
		int idresult = dao.idcheck(id);
		System.out.println("idresult: "+idresult);
		boolean result = idresult == 0 ? true : false; 
		System.out.println("result: "+result);
		return result;
	}
}
