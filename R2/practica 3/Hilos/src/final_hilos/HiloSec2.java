package final_hilos;

import javax.swing.JTextArea;

public class HiloSec2 extends Thread{
	private JTextArea jtext2;
	private JTextArea jtext3;
	/**
	 * @param args
	 */
	public HiloSec2(JTextArea jtext2, JTextArea jtext3){
		this.jtext2 = jtext2;
		this.jtext3 = jtext3;
	}
	public void run(){
		PrimosAbecedario h = new PrimosAbecedario(jtext2);
	    int v=0;
	    for(int t=1; t<11;t++){
	    	if(v%2==0){
	    		ArchivoLog.log.debug("Interrupción número "+t+" del segundo hilo");
	    		h.invocaPr();
	    		jtext3.append("Interrupción número "+t+" del segundo hilo \n");
	    		v++;
	    	}
	    	else{
	    		ArchivoLog.log.debug("Interrupción número "+t+" del segundo hilo");
	    		h.invocaA();
	    		jtext3.append("Interrupción número "+t+" del segundo hilo \n");
	    		v++;
	    	}
	    }
	    ArchivoLog.log.info("--------------------------------------------");
	    ArchivoLog.log.info("Final del segundo hilo");
	    ArchivoLog.log.info("--------------------------------------------");
	    ArchivoLog.log3.info("Final del hilo Interrupciones");
	    ArchivoLog.log3.info("--------------------------------------------");
	}
}

