package com.sd.blueblood;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl {

	@Autowired(required=true)
	public JavaMailSender emailSender;
	
	@Value("${spring.mail.username}")
	String fromuser;
	
	@Value("${spring.mail.mailname}")
	String fromname;
	
	@Value("${spring.mail.image.pulso}")
	String imgPulso;
	
	@Value("${spring.mail.image.pulso2}")
	String imgPulso2;

	public void sendSimpleMessage(Map<String, String> parametros) throws MessagingException {
		MimeMessage mimeMessage = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
		String htmlMsg = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\r\n" + 
				"<html lang=\"en\">\r\n" + 
				"\r\n" + 
				"<head>\r\n" + 
				"    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n" + 
				"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n" + 
				"    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n" + 
				"\r\n" + 
				"    <title></title>\r\n" + 
				"\r\n" + 
				"    <style type=\"text/css\">\r\n" + 
				"        body {\r\n" +
//				"			 color:#FFF;\r\n" +
				"            font-family: Arial, Helvetica, sans-serif;\r\n" + 
				"        }\r\n" + 
				"\r\n" + 
				"        .button {\r\n" + 
				"            text-align: center;\r\n" + 
				"            font-size: 18px;\r\n" + 
				"            font-family: sans-serif;\r\n" + 
				"            font-weight: bold;\r\n" + 
				"            padding: 0 30px 0 30px;\r\n" + 
				"            border-radius: 50px;\r\n" + 
				"            border: 2px solid #09aced;\r\n" + 
				"            color: #09aced;\r\n" + 
				"            text-decoration: none;\r\n" + 
				//"            background: #09aced;\r\n" + 
				"            padding: 10px 40px;\r\n" + 
				"        }\r\n" + 
				"\r\n" + 
				"    </style>\r\n" + 
				"</head>\r\n" + 
				"\r\n" + 
				"<body style=\"margin:0; padding:0; background-color:#fff;\">\r\n" + 
				"    <center>\r\n" + 
				"        <table width=\"640\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" class=\"wrapper\" >\r\n" + //background=\"http://sangretigo.com/fondo.jpg\"
				"            <tr>\r\n" + 
				"                <td height=\"10\" style=\"font-size:10px; line-height:10px;\">&nbsp;</td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td align=\"center\" valign=\"top\">\r\n" + 
				"\r\n" + 
				"                    <table width=\"600\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" class=\"container\">\r\n" + 
				"                        <tr>\r\n" + 
				"                            <td align=\"center\" valign=\"top\" class=\"innerpadding borderbottom\">\r\n" + 
				"                                <img src=\"cid:pulsoST\" width=\"100%\" border=\"0\" alt=\"\" />\r\n" + 
				"                            </td>\r\n" + 
				"                        </tr>\r\n" + 
				"                        <tr>\r\n" + 
				"                            <td align=\"center\" valign=\"top\">\r\n" + 
				"                                <strong style=\"font-size:30px\">"+parametros.get("jefe_nm")+" </strong><span style=\"font-size:24px\"> te envi&oacute; un pulso de</span>\r\n" + 
				"                            </td>\r\n" + 
				"                        </tr>\r\n" + 
				"                        <tr>\r\n" + 
				"                            <td align=\"center\" valign=\"bottom\" style=\"padding: 30px 20px 20px 20px;\">\r\n" + 
				"                                <span style=\"font-size:24px\">"+parametros.get("tipo")+"</span>\r\n" + 
				"                            </td>\r\n" + 
				"                        </tr>\r\n" + 
//				"                        <tr>\r\n" + 
//				"                            <td align=\"center\" valign=\"top\" class=\"innerpadding borderbottom\">\r\n" + 
//				"                                <img src=\"http://sangretigo.com/pulsos.png\" width=\"100%\" border=\"0\" alt=\"\" />\r\n" + 
//				"                            </td>\r\n" + 
//				"                        </tr>\r\n" + 
				"                        <tr>\r\n" + 
				"                            <td align=\"center\" class=\" innerpadding borderbottom\" height=\"45\" >\r\n" + 
				"                                <a href=\"https://rrhh.tigo.net.bo/juntosganamos/#/login?href=/noticia/"+parametros.get("post_id")+"\" class=\"button\" >Ver Historia</a>\r\n" + 
				"                            </td>\r\n" + 
				"                        </tr>\r\n" +
				"<tr>\r\n" + 
				"                            <td align=\"center\" class=\" innerpadding borderbottom\" height=\"50\" >  \r\n" + 
				"                            </td>\r\n" + 
				"                        </tr>"+
				"                    </table>\r\n" + 
				"\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td height=\"10\" style=\"font-size:10px; line-height:10px;\">&nbsp;</td>\r\n" + 
				"            </tr>\r\n" + 
				"        </table>\r\n" + 
				"    </center>\r\n" + 
				"</body>\r\n" + 
				"\r\n" + 
				"</html>";
		SimpleMailMessage message = new SimpleMailMessage();
		try {
			System.out.println(fromuser);
			if (parametros.get("usuario_mail") != null) {
				helper.setText(htmlMsg, true);
				File arch=new File(imgPulso);
				System.out.println(fromname);
				helper.addInline("pulsoST", arch);
				helper.setTo(parametros.get("usuario_mail"));
				helper.setSubject("Tu historia acaba de recibir un Pulso!");
				helper.setFrom(fromuser,fromname);
				emailSender.send(mimeMessage);
			}
		} catch (MessagingException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Use this or above line.

	}
	public void sendSuperMessage(Map<String, String> parametros) throws MessagingException {
		MimeMessage mimeMessage = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
		String htmlMsg = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\r\n" + 
				"<html lang=\"en\">\r\n" + 
				"\r\n" + 
				"<head>\r\n" + 
				"    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n" + 
				"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n" + 
				"    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n" + 
				"\r\n" + 
				"    <title></title>\r\n" + 
				"\r\n" + 
				"    <style type=\"text/css\">\r\n" + 
				"        body {\r\n" +
//				"			 color:#FFF;\r\n" +
				"            font-family: Arial, Helvetica, sans-serif;\r\n" + 
				"        }\r\n" + 
				"\r\n" + 
				"        .button {\r\n" + 
				"            text-align: center;\r\n" + 
				"            font-size: 18px;\r\n" + 
				"            font-family: sans-serif;\r\n" + 
				"            font-weight: bold;\r\n" + 
				"            padding: 0 30px 0 30px;\r\n" + 
				"            border-radius: 50px;\r\n" + 
				"            border: 2px solid #09aced;\r\n" + 
				"            color: #09aced;\r\n" + 
				"            text-decoration: none;\r\n" + 
				//"            background: #09aced;\r\n" + 
				"            padding: 10px 40px;\r\n" + 
				"        }\r\n" + 
				"\r\n" + 
				"    </style>\r\n" + 
				"</head>\r\n" + 
				"\r\n" + 
				"<body style=\"margin:0; padding:0; background-color:#fff;\">\r\n" + 
				"    <center>\r\n" + 
				"        <table width=\"640\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" class=\"wrapper\" >\r\n" + //background=\"http://sangretigo.com/fondo.jpg\"
				"            <tr>\r\n" + 
				"                <td height=\"10\" style=\"font-size:10px; line-height:10px;\">&nbsp;</td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td align=\"center\" valign=\"top\">\r\n" + 
				"\r\n" + 
				"                    <table width=\"600\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" class=\"container\">\r\n" + 
				"                        <tr>\r\n" + 
				"                            <td align=\"center\" valign=\"top\" class=\"innerpadding borderbottom\">\r\n" + 
				"                                <img src=\"cid:pulsoST\" width=\"100%\" border=\"0\" alt=\"\" />\r\n" + 
				"                            </td>\r\n" + 
				"                        </tr>\r\n" + 
				"                        <tr>\r\n" + 
				"                            <td align=\"center\" valign=\"top\">\r\n" + 
				"                                <strong style=\"font-size:30px\">"+parametros.get("jefe_nm")+" </strong><span style=\"font-size:24px\"> te envi&oacute; un pulso de</span>\r\n" + 
				"                            </td>\r\n" + 
				"                        </tr>\r\n" + 
				"                        <tr>\r\n" + 
				"                            <td align=\"center\" valign=\"bottom\" style=\"padding: 30px 20px 20px 20px;\">\r\n" + 
				"                                <span style=\"font-size:24px\">"+parametros.get("tipo")+"</span>\r\n" + 
				"                            </td>\r\n" + 
				"                        </tr>\r\n" + 
//				"                        <tr>\r\n" + 
//				"                            <td align=\"center\" valign=\"top\" class=\"innerpadding borderbottom\">\r\n" + 
//				"                                <img src=\"http://sangretigo.com/pulsos.png\" width=\"100%\" border=\"0\" alt=\"\" />\r\n" + 
//				"                            </td>\r\n" + 
//				"                        </tr>\r\n" + 
				"                        <tr>\r\n" + 
				"                            <td align=\"center\" class=\" innerpadding borderbottom\" height=\"45\" >\r\n" + 
				"                                <a href=\"https://rrhh.tigo.net.bo/juntosganamos/#/login?href=/noticia/"+parametros.get("post_id")+"\" class=\"button\" >Ver Historia</a>\r\n" + 
				"                            </td>\r\n" + 
				"                        </tr>\r\n" +
				"<tr>\r\n" + 
				"                            <td align=\"center\" class=\" innerpadding borderbottom\" height=\"50\" >  \r\n" + 
				"                            </td>\r\n" + 
				"                        </tr>"+
				"                    </table>\r\n" + 
				"\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td height=\"10\" style=\"font-size:10px; line-height:10px;\">&nbsp;</td>\r\n" + 
				"            </tr>\r\n" + 
				"        </table>\r\n" + 
				"    </center>\r\n" + 
				"</body>\r\n" + 
				"\r\n" + 
				"</html>";
		SimpleMailMessage message = new SimpleMailMessage();
		try {
			System.out.println(fromuser);
			if (parametros.get("usuario_mail") != null) {
				helper.setText(htmlMsg, true);
				File arch=new File(imgPulso2);
				System.out.println(fromname);
				helper.addInline("pulsoST", arch);
				helper.setTo(parametros.get("usuario_mail"));
				helper.setSubject("Tu historia acaba de recibir un Pulso!");
				helper.setFrom(fromuser,fromname);
				emailSender.send(mimeMessage);
			}
		} catch (MessagingException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Use this or above line.

	}
}