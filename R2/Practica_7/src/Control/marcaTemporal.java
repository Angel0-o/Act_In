package Control;

import java.io.IOException;
import javax.swing.JTextField;


public class marcaTemporal extends Thread
{
	JTextField reloj;
	Thread h;
	int contador;
	
	public marcaTemporal(JTextField reloj1) 
	{
		this.reloj = reloj1;
        h = new Thread(this);
        h.start();
        this.contador = 0;
	}
	
	public void run() 
	{
		while(true) 
		{
			try {
				sleep(1000);
				this.contador++;
				String t1=String.valueOf(this.contador);
				this.reloj.setText(t1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
