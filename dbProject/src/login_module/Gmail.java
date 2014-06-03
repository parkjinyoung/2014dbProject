package login_module;
  
import java.util.Properties;    
import javax.mail.Address;
import javax.mail.Message;  
import javax.mail.PasswordAuthentication;  
import javax.mail.Session;  
import javax.mail.Transport;  
import javax.mail.internet.InternetAddress;  
import javax.mail.internet.MimeMessage;  

//인증 번호 메일을 보내기 위한 모듈
public class Gmail extends javax.mail.Authenticator
{
	String email;
	String key;
	Properties p;
	public Gmail(String em, String ke) 
	{
		 p = new Properties();
		 p.put("mail.smtp.user", "snumenu1@gmail.com"); // Google계정@gmail.com으로 설정
		 p.put("mail.smtp.host", "smtp.gmail.com");
		 p.put("mail.smtp.port", "465");
		 p.put( "mail.smtp.auth", "true");
		 p.put("mail.smtp.socketFactory.port", "465"); 
		 p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); 
		 p.put("mail.smtp.socketFactory.fallback", "false");
		 p.setProperty("mail.smtp.quitwait", "false");
		 
		 key = ke;
		 email = em;

		 //Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());	 
	}
	
	public boolean send()
	{
		 try
		 {
			 Session session = Session.getDefaultInstance(p, this);
			 session.setDebug(true); // 메일을 전송할 때 상세한 상황을 콘솔에 출력한다.

			 //session = Session.getDefaultInstance(p);
			 MimeMessage msg = new MimeMessage(session);
			 String message = key;
			 msg.setSubject("snumenu 인증번호");
			 Address fromAddr = new InternetAddress("snumenu1@gmail.com"); // 보내는 사람의 메일주소
			 msg.setFrom(fromAddr);
			 Address toAddr = new InternetAddress(email);  // 받는 사람의 메일주소
			 msg.addRecipient(Message.RecipientType.TO, toAddr); 
			 msg.setContent(message, "text/plain;charset=KSC5601");
			 Transport.send(msg);
			 Thread.sleep(1000);
		 }
		 catch (Exception mex)
		 { // Prints all nested (chained) exceptions as well
			 return false;	 
		 }
		 return true;
			   
	}
	public PasswordAuthentication getPasswordAuthentication()
	{
		return new PasswordAuthentication("snumenu1", "elqlvmfwpr"); // Google id, pwd, 주의) @gmail.com 은 제외하세요	
	}
}