import java.util.*;
import java.util.List;
import java.io.PrintWriter;
import java.awt.Panel;
import java.awt.Frame;
import java.awt.Font;
import java.awt.ComponentOrientation;
import java.io.IOException;
import java.io.OutputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.ServerSocket;
import javax.swing.JTextArea;
import java.text.SimpleDateFormat;

class Christian implements Runnable {
	PrintWriter output;
	Scanner reader;

	public Christian(PrintWriter o, Scanner s) {
		output = o;
		reader = s;
	}

	public String obtenerHoraActual(int s) {
		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();
		cal.add(Calendar.SECOND, s);
		Date later = cal.getTime();
		return new SimpleDateFormat("HH:mm:ss").format(later);
	}

	public void run() {
		output.println("Manda tu hora");
		output.flush();
		long startTime = System.nanoTime();

		if(reader.hasNext() == false) {
			return;
		}

		String horaCliente = reader.nextLine();
		System.out.println(horaCliente);
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 2000000000;

		System.out.println(obtenerHoraActual((int)duration));
		output.println(obtenerHoraActual((int) duration));
		output.flush();

	}
}

public class Servidor extends Frame {
	JTextArea textArea = null;
	Panel panel = null;
	Frame frame = null;
	List<PrintWriter> writers = new ArrayList<PrintWriter>();
	List<Scanner> readers = new ArrayList<Scanner>();
	Set<String> IPs;

	public void agregarLog(String log) {
		textArea.append(log + "\n");
	}
	
	public void mostrar() {
		textArea = new JTextArea();
		agregarLog("Inicializando servidor...");
		textArea.setFont(new Font("Serif", Font.PLAIN, 28));
		textArea.setEditable(false);
		textArea.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		textArea.setWrapStyleWord(true);

		panel = new Panel();
		panel.add(textArea);

		frame = new Frame();
		frame.setSize(1000, 1000);
		frame.add(panel);
		frame.setVisible(true);
	}

	public void escuchar() {
		new Thread(new Runnable() {
			int puerto = 9090;
			public void run() {
				try (ServerSocket serverSocket = new ServerSocket(puerto)) {
					while (true) {
						Socket s = serverSocket.accept();
						PrintWriter output = new PrintWriter(s.getOutputStream());
						Scanner reader = new Scanner(s.getInputStream());

						writers.add(output);
						readers.add(reader);
						agregarLog("Cliente conectado");
					}
				} catch (IOException e) {
						System.err.println("Could not listen on port " + puerto);
						System.exit(-1);
				}
			}
		}).start();
	}

	public void enviarDatos() {
		while(true) {
			for(int i=0;i<writers.size();i++) {
				PrintWriter output = writers.get(i);
				Scanner reader = readers.get(i);
				Christian c = new Christian(output, reader);
				new Thread(c).start();
			}
			try {
				Thread.sleep(5000);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main (String[] args) throws java.lang.Exception {
		Servidor servidor = new Servidor();
		servidor.mostrar();
		servidor.escuchar();
		servidor.enviarDatos();
	}
}
