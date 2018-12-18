package final_hilos;

import java.util.*;

import javax.swing.JTextArea;

public class PrimosAbecedario {
	private JTextArea jt2;
	// Pasar temp1h2 y temp2h2
	private char temp2h2=(char)((int)'a'-1);
	private int temp1h2 = 0;
        private int tempo =0;

	public PrimosAbecedario(JTextArea jt2) {
		this.jt2 = jt2;
	}

	ArchivoLog logi = new ArchivoLog();
        
        private void primos(){
		int limite = tempo;
		limite = limite + 33;
		// Empty String
		ArchivoLog.log2.debug("\n\nPrimos\n\n");
		jt2.append("Primos\n");
		int i = 0;
		int num = 0;
		// Empty String
		for (i = tempo+1; i <= limite; i++) {
			int counter = 0;
			
			for (num = i; num >= 1; num--) {
				if (i % num == 0) {
					counter = counter + 1;
				}
			}
			if (counter == 2) {
				try{
					Thread.sleep(300);
					tempo = i;
				}
				catch(InterruptedException e){}
				jt2.append(Long.toString(tempo) + "\n");
			}
	  }
	}

	

	private void abecedario() {

                
		char p;
                
		ArchivoLog.log.debug("\n\nInicio del Abecedario\n\n");
		jt2.append("Abecedario \n");
		for (int y = 0; y < 10; y++) {
			if ((int)temp2h2 > 121){
                            temp2h2=(char)((int)'a'-1);
                        }                              
                        p = (char)((int)temp2h2+1);
			try {
				Thread.sleep(300);
				temp2h2 = p;
			} catch (InterruptedException e) {
			}
			jt2.append(temp2h2 + "\n");
                        
                        
		}
	}
        
        public void invocaPr(){
		primos();
        }

	public void invocaA() {
		abecedario();
	}
}
