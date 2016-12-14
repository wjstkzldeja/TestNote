package org.kdea.email;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/user/")
public class EmailController {
	
   @Autowired
    private EmailService emailService;

    @RequestMapping("email")
	public String list() {
		return "user/userJoin";
	}
   @RequestMapping(value="emailCheck",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Boolean> emailTest(@RequestParam("emailAddr")String emailAddr,
    		HttpSession session,Model model)
    {
    	ServletContext application = session.getServletContext();
    	EmailVO email = new EmailVO();
    	String receiver = emailAddr;
    	String subject = "Email 인증용 메일";
    	String sId = session.getId();
    	
    	System.out.println("receiver: "+receiver);
    	
    	application.setAttribute(sId, emailAddr);
    	
    	String content = "http://192.168.2.25:8082/boardTest/user/auth?auth="+sId+"&emailid="+receiver;
    	
    	email.setReceiver(receiver);
    	email.setSubject(subject);
    	email.setContent(content);
    	System.out.println("이메일 아이디"+receiver);
    	boolean sent = false;
    	try{
    		sent = emailService.sendMail(email);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	Map<String, Boolean> result = new HashMap<>();
    	result.put("result", sent);
    	return result;
    }
    @RequestMapping("auth")
    public String auth(@RequestParam("auth") String sid,
    		@RequestParam("emailid") String emailid,HttpSession session,Model model){
    	ServletContext application = session.getServletContext();
    	String email = (String) application.getAttribute(sid);
    	if(email != null){
    		model.addAttribute("email",email);
    		model.addAttribute("emailid",emailid);
    		return "user/userJoin";
    	}
    	return "user/userJoin";
    }
    @RequestMapping("idcheck")
    @ResponseBody
    public boolean idcheck(@RequestParam(value="id")String id){
    	boolean idresult= emailService.getidcheck(id);
    	System.out.println(idresult);
    	return idresult;
    }
    @RequestMapping("join")
    public String join(Model model,EmailVO vo){
    	System.out.println("id: "+vo.getId());
    	System.out.println("pwd: "+vo.getPwd());
    	System.out.println("email: "+vo.getEmail());
   
    	int sucess = emailService.getInsert(vo);
    	return "user/joinSucess";
    }
    @RequestMapping("emailform")
	public String sendform(@RequestParam("emailid")String emailid,Model model) {
		model.addAttribute("emailid",emailid);
		return "board/emailsend";
	}
    
    @RequestMapping(value="emailsend",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Boolean> emailSend(@RequestParam("emailAddr")String emailAddr,EmailVO vo,
    		HttpSession session)
    {
    	ServletContext application = session.getServletContext();
    	
    	System.out.println("들오어니?");
    	System.out.println("주소: "+emailAddr);
    	System.out.println("타이틀: "+vo.getEmailtitle());
    	System.out.println("컨텐츠: "+vo.getEmailcontent());
    	
    	EmailVO email = new EmailVO();
    	String receiver = emailAddr;
    	String subject = vo.getEmailtitle();
    	System.out.println("emadd: "+ emailAddr);
    	String sId = session.getId();
    	application.setAttribute(sId, emailAddr);
    	
    	String content = vo.getEmailcontent();
    	
    	email.setReceiver(receiver);
    	email.setSubject(subject);
    	email.setContent(content);
    	boolean sent = false;
    	try{
    		sent = emailService.sendMail(email);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	Map<String, Boolean> result = new HashMap<>();
    	result.put("result", sent);
    	return result;
    }
}






