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
    BCryptPasswordEncoder passwordEncoder; //���� �������Ͽ� ������ ���
    public boolean sendMail(EmailVO email) throws Exception {
        try{
	        MimeMessage msg = mailSender.createMimeMessage();
	        
	        InternetAddress addr = new InternetAddress("someone@paran.com");//���ʿ�
	        msg.setFrom(addr); // �۽��ڸ� �����ص� �ҿ������ ������ ������ �߻��Ѵ�
	        
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
    	 // �̿����� ��й�ȣ�� bcrypt �˰������� ��ȣȭ�Ͽ� DB�� �����Ѵ�
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
