package final_hilos;

import javax.swing.JTextArea;

public class Interrupciones extends Thread{
	private JTextArea areaClase1;
	private JTextArea areaClase2;
	private JTextArea areaClase3;
	/**
	 * @param args
	 */
	public Interrupciones(JTextArea jtext1, JTextArea jtext2, JTextArea jtext3){
		areaClase1 = jtext1;
		areaClase2 = jtext2;
		areaClase3 = jtext3;
	}
	public void run() {
		HiloSec1 hilo1= new HiloSec1(areaClase1, areaClase3);
		HiloSec2 hilo2 = new HiloSec2(areaClase2, areaClase3);
		String nombre1, nombre2;
		long hil1, hil2;
		nombre1 = hilo1.getName();
		hil1 = hilo1.getId();
		nombre2 = hilo2.getName();
		hil2 = hilo2.getId();
		ArchivoLog.log.info("Nombre del hilo 1: "+nombre1+ " con el ID "+ hil1);
		ArchivoLog.log2.info("Nombre del hilo 2: "+nombre2+ " con el ID "+ hil2+ "\n\n");
		ArchivoLog.log.info("Iniciando el primer hilo");
		ArchivoLog.log2.info("Iniciando el segundo hilo\n\n");
		ArchivoLog.log3.info("");
		ArchivoLog.log2.info("--------------------------------------------");
		ArchivoLog.log.info("--------------------------------------------");
		
		hilo1.start();
		hilo2.start();
		
		
	}

}
