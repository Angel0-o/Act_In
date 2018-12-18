package final_hilos;

import javax.swing.JTextArea;

public class FibonacciPares {
	private JTextArea jt1;
	ArchivoLog logi2 = new ArchivoLog();
	//Mandar temp[2] y tiempo
	private long[] temp = new long[3];
	private int tempo =0;

	private int temp1h2 = 0;
	public FibonacciPares(JTextArea jt1){
		this.jt1 = jt1;
	}
        
	private void fibonacci(){
		ArchivoLog.log2.debug("\n\nFibonacci\n\n");
		jt1.append("Fibonacci \n");
		for(int i=0; i<5;i++){
			if(i<2 && (temp[0]==0 || temp[1]==0)){
				temp[2]=1;
			}
			else{
				temp[2]= temp[1] + temp[0];
			}
			try{
				Thread.sleep(400);
			}
			catch(InterruptedException e){}
			jt1.append(Long.toString(temp[2]) + "\n");
			temp[0]= temp[1];
			temp[1]= temp[2];
			temp[2]= 0;
			
		}
	}
	
	private void pares() {
		int i = temp1h2;
		ArchivoLog.log.debug("\n\nInicio de los pares\n\n");
		jt1.append("Pares\n");
		for (int z = 0; z < 10; z++) {
			i = i + 2;
			try {
				Thread.sleep(200);
				temp1h2 = i;
			} catch (InterruptedException e) {
			}
			jt1.append(Integer.toString(temp1h2) + "\n");
		}
	}
	
	public void invocaF(){
		fibonacci();
	}
	
	public void invocaP() {
		pares();
	}
}