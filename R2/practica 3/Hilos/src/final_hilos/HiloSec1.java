package final_hilos;

import javax.swing.JTextArea;

public class HiloSec1 extends Thread{
	private JTextArea jtext1;
	private JTextArea jtext3;
	/**
	 * @param args
	 */
	public HiloSec1(JTextArea jtext1, JTextArea jtext3){
		this.jtext1 = jtext1;
		this.jtext3 = jtext3;
	}
	ArchivoLog lo = new ArchivoLog();
	public void run(){
		FibonacciPares h1 = new FibonacciPares(jtext1);
		
		 int v=0;
		   for(int w=1; w<11;w++){
		    	if(v%2==0){
		    		ArchivoLog.log2.debug("Interrupción número "+w+" del primer hilo");
		    		h1.invocaF();
		    		jtext3.append("Interrupción número "+w+" del primer hilo \n");
		    		v++;
		    	}
		    	else{
		    		ArchivoLog.log2.debug("Interrupción número "+w+ " del primer hilo");
		    		h1.invocaP();
		    		jtext3.append("Interrupción número "+w+" del primer hilo\n");
		    		v++;
		    	}
		    }
		   ArchivoLog.log2.info("**********************");
		   ArchivoLog.log2.info("Final del primer hilo");
		   ArchivoLog.log2.info("***********************");
	}
}
