package final_hilos;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;


public class ArchivoLog {
	//Nombre y declaracion del objeto para las trazas
		public static Logger log, log2, log3;
		static Date fecha=new Date();
		static{
                    BasicConfigurator.configure();
                    log = Logger.getLogger(PrimosAbecedario.class.getName());
                    log2 = Logger.getLogger(FibonacciPares.class.getName());
                    log3 = Logger.getLogger(Interrupciones.class.getName());
                    SimpleDateFormat formato = new SimpleDateFormat("dd.MM.yyyy");
		    String fechaAc = formato.format(fecha);
		    System.out.println(fechaAc);
		    PatternLayout defaultLayout = new PatternLayout(" %p %c\t%d{dd.MM.yyyy/HH:mm:ss}\t%m\t%n");
		    RollingFileAppender rollingFileAppender = new RollingFileAppender();
		    try {
                        rollingFileAppender.setFile("registro_del_"+fechaAc+".log", true, false, 0);
		    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
		    }
   
		    rollingFileAppender.setLayout(defaultLayout);
		    log.removeAllAppenders();
		    log.addAppender(rollingFileAppender);
		    log2.removeAllAppenders();
		    log2.addAppender(rollingFileAppender);
		    log3.removeAllAppenders();
		    log3.addAppender(rollingFileAppender);
		}
}
