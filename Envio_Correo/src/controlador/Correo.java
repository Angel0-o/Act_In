package controlador;

import java.util.*;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class Correo 
{
	public static boolean send(String destinatario,String usr,String pass)
	{
		try
		{
			String emisor = "ssrh@grupobmv.com.mx";
			String host = "10.100.161.1";
			String port = "25";
			String 	mensaje = "";
				   	mensaje+= "Hola, buen día.\n\n";
				   	mensaje+= "El usuario y nueva clave de acceso para ingresar al Sitio de Factor Humano son los siguientes:\n\n";
				   	mensaje+= "\tUsuario:  " + usr + "\n\tClave: \t" + pass +"\n\n";
				   	mensaje+= "Una vez que ingreses al sitio, este te solicitará cambiar tu clave de acceso, esto con la finalidad de que tú seas el único en tener conocimiento de la misma.\n\n";
				   	mensaje+= "La liga para ingresar al sitio es la siguiente:\n\n";
				   	mensaje+= "(http://10.100.229.51/Sitio_CH/)\n\n";
				   	mensaje+= "Nota:\n\n";
				   	mensaje+= "Te recomendamos limpiar en tu navegador de internet (IExplorer, Chrome, Mozilla, etc.)  el historial de navegación.";
		    Properties props = new Properties();
		    props.put("mail.smtp.user", "");
		    props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.port", port);
			//props.put("mail.smtp.ssl.trust", emisor);
			props.put("mail.smtp.socketFactory.port", port);
			props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.socketFactory.fallback", "true");
			Session session = Session.getInstance(props,new javax.mail.Authenticator() 
				{
		            protected PasswordAuthentication getPasswordAuthentication()
		            {
		               return new PasswordAuthentication("ssrh@grupobmv.com.mx", "");
		            }
		         }
			);
			Message message = new MimeMessage(session);
		    message.setFrom(new InternetAddress(emisor,"Factor Humano"));
		    message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
		    message.setSubject("Nueva contraseña de acceso al Sitio de Factor Humano");
		    message.setText(mensaje);
		    Transport.send(message);
		    System.out.println("Mensaje enviado");
		    return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
}
