import java.util.*;
import javax.swing.JTextPane;
import javax.swing.text.Document;
import java.sql.*;
import java.util.List;
import java.io.PrintWriter;
import javax.swing.ImageIcon;
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
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

class Christian implements Runnable {
	PrintWriter output;
	Scanner reader;
	String nombre;
	String ip;
	Connection conn;
	static int idHoraCentral = 0;
	static int idHoraEquipos = 0;
	static int idEquipos = 0;

	public Christian(PrintWriter _output, Scanner _reader, String _nombre, String _ip, Connection _conn) {
		output = _output;
		reader = _reader;
		nombre = _nombre;
		ip = _ip;
		conn = _conn;
	}

	public String obtenerHoraActual(java.util.Date now, int s) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.SECOND, s);
		java.util.Date later = cal.getTime();
		return new SimpleDateFormat("HH:mm:ss").format(later);
	}

	public String obtenerHoraUTC(String horaLocal) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
			java.util.Date date = sdf.parse(horaLocal);
			return sdf.format(date);
		} catch(Exception e) {
      System.err.println(e.getMessage()); 
		}
		return "";
	}

	public void HoraCentral(int id, String hUTC, String hLocal) {
		try{
			String query = " insert into horacentral (ID, hUTC, hLocal)" + " values (?, ?, ?)";

			PreparedStatement preparedStmt = conn.prepareStatement(query);
      preparedStmt.setInt    (1, id);
      preparedStmt.setString (2, hUTC);
      preparedStmt.setString (3, hLocal);

			preparedStmt.execute();
		} catch(Exception e) {
			System.out.println("ERROR");
      System.err.println(e.getMessage()); 
		}
	}

	public void HoraEquipos(int id, int idUTC, int idEquipo, String hEquipo, String aEquipo, double ralentizar) {
		try{
			String query = " insert into horaequipos (ID, IDhUTC, IDEquipo, hEquipo, aEquipo, ralentizar)" + " values (?, ?, ?, ?, ? ,?)";

			PreparedStatement preparedStmt = conn.prepareStatement(query);
      preparedStmt.setInt    (1, id);
      preparedStmt.setInt    (2, idUTC);
      preparedStmt.setInt    (3, idEquipo);
      preparedStmt.setString (4, hEquipo);
      preparedStmt.setString (5, aEquipo);
      preparedStmt.setDouble (6, ralentizar);

			preparedStmt.execute();
		} catch(Exception e) {
			System.out.println("ERROR");
      System.err.println(e.getMessage()); 
		}
	}

	public void Equipos(int id, String IP, String nombre, int latencia) {
		try{
			String query = " insert into equipos (ID, IP, Nombre, Latencia)" + " values (?, ?, ?, ?)";

			PreparedStatement preparedStmt = conn.prepareStatement(query);
      preparedStmt.setInt    (1, id);
      preparedStmt.setString (2, IP);
      preparedStmt.setString (3, nombre);
      preparedStmt.setInt    (4, latencia);

			preparedStmt.execute();
		} catch(Exception e) {
			System.out.println("ERROR");
      System.err.println(e.getMessage()); 
		}
	}

	public void guardarInformacion(String horaCliente, String horaServidor, String actualizaCliente, int latencia, double ralentizar) {
		String horaUTC = obtenerHoraUTC(horaServidor);
		HoraCentral(idHoraCentral, horaUTC, horaServidor);
		Equipos(idEquipos, this.ip, this.nombre, latencia);
		HoraEquipos(idHoraEquipos, idHoraCentral, idEquipos, horaCliente, actualizaCliente, ralentizar);

		idHoraCentral = idHoraCentral + 1;
		idHoraEquipos = idHoraEquipos + 1;
		idEquipos = idEquipos + 1;
	}

	public void run() {
		output.println("Manda tu hora");
		output.flush();
		long startTime = System.nanoTime();

		try {
			if(reader.hasNext() == true) {
				String [] parts = (reader.nextLine()).split(",");

				String horaCliente = parts[0];
				String auxiliar = parts[1];
				System.out.println(horaCliente + " " + auxiliar);

				double ralentizar = Double.parseDouble(auxiliar);
				long endTime = System.nanoTime();
				long duration = (endTime - startTime) / 2000000000;

				Calendar cal = Calendar.getInstance();
				java.util.Date now = cal.getTime();
				String horaServidor = new SimpleDateFormat("HH:mm:ss").format(now);

				String actualizaCliente = obtenerHoraActual(now, (int) duration);
				System.out.println(actualizaCliente);

				output.println(actualizaCliente);
				output.flush();

				guardarInformacion(horaCliente, horaServidor, actualizaCliente, (int) duration, ralentizar);
			}
		} catch(Exception e) {
		}
	}
}

class ExclusionMutua implements Runnable {
	List<PrintWriter> writers = new ArrayList<PrintWriter>();
	List<Scanner> readers = new ArrayList<Scanner>();
	List<String> ips = new ArrayList<String>();
	int numeroJugadores = 2;
	JTextPane textPane = null;
	int portaToken;
	HashSet<Integer> pokemonUsados = new HashSet<>();
	List<String> informe = new ArrayList<String>();
	boolean soy_coordinador = true;

	public ExclusionMutua(JTextPane _textPane) {
		textPane = _textPane;
	}

	public void limpiarPanel() {
		textPane.selectAll();
	  textPane.replaceSelection("");
		textPane.setLayout(new BorderLayout());
		textPane.removeAll();
		textPane.revalidate();
	}

	public void agregarLog(String log) {
		try
		{
			StyledDocument doc2 = (StyledDocument) textPane.getDocument();
			Style style = doc2.addStyle("StyleName", null);
			StyleConstants.setFontSize(style, 30);

			Document doc = textPane.getDocument();
			doc.insertString(doc.getLength(), log + "\n\n", style);
		}
		catch(Exception e) { System.out.println(e); }
	}

	void mostrarPokemon(int num, String nombre, int hp, int ataque, int defensa) {
		agregarLog(nombre + " " + hp + " " + ataque + " " + defensa + "						");
		textPane.add(new JLabel(new ImageIcon(getClass().getClassLoader().getResource("Imagenes/" + num + ".png"))));
		agregarLog("\n\n\n\n");
		textPane.validate();
	}

	String driver = "com.mysql.jdbc.Driver";
	String db = "pokemon";
	String url = "jdbc:mysql://localhost/" + db;
	String user = "root";
	String pass = "";
	String query = "SELECT * FROM datos ORDER BY RAND() LIMIT 1;";

	public String obtenerCartaAleatoria() {
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, pass); 
			Statement st = conn.createStatement();

			while(true) {
				ResultSet rs = st.executeQuery(query);
				while (rs.next())
				{
					int num = rs.getInt("num");
					String nombre = rs.getString("nombre");
					String tipo1 = rs.getString("tipo1");
					String tipo2 = rs.getString("tipo2");
					int hp = rs.getInt("hp");
					int ataque = rs.getInt("ataque");
					int defensa = rs.getInt("defensa");

					if(pokemonUsados.contains(num)) {
						continue;
					}

					mostrarPokemon(num, nombre, hp, ataque, defensa);
					pokemonUsados.add(num);
					st.close();
					return num + " " + nombre + " " + tipo1 + " " + tipo2 + " " + String.valueOf(hp) + " " + String.valueOf(ataque) + " " + String.valueOf(defensa);
				}
			}
		} catch (Exception e) { 
			System.err.println("Got an exception! "); 
      System.err.println(e.getMessage()); 
		}

		return "";
	}

	public List<String> ordenarCartas(List<String> cartas) {
		for(int i=0;i<cartas.size();i++) {
			for(int j=i+1;j<cartas.size();j++) {
				String []uno = cartas.get(i).split(" ");
				String []dos = cartas.get(j).split(" ");
				if(uno.length < 8 || dos.length < 8) {
					continue;
				}
				if(Integer.valueOf(uno[5]) < Integer.valueOf(dos[5])) {
					Collections.swap(cartas, i, j);
				}
				else if(uno[5].equals(dos[5]) && Integer.valueOf(uno[6]) < Integer.valueOf(dos[6])) {
					Collections.swap(cartas, i, j);
				}
				else if(uno[5].equals(dos[5]) && uno[6].equals(dos[6]) && Integer.valueOf(uno[7]) < Integer.valueOf(dos[7])) {
					Collections.swap(cartas, i, j);
				}
			}
		}
		return cartas;
	}

	public List<String> obtenerCartasAleatorias(int tam) {
		List<String> cartas = new ArrayList<String>();

		while(cartas.size() < tam) {
			cartas.add(obtenerCartaAleatoria());
		}
		agregarLog("\n\n\n\n");
		System.out.println("Cartas agregadas");

		return ordenarCartas(cartas);
	}

	public void enviarMensaje(int id, String mensaje) {
		if(mensaje.equals("Ten el token") == true) {
			portaToken = id;
		}
		System.out.println(ips.get(id) + " " + mensaje);
		writers.get(id).println(mensaje);
		writers.get(id).flush();
	}

	public void iniciaTokenRing() {
		System.out.println("	" + numeroJugadores);
		for(int i=0;i<numeroJugadores;i++) {
			enviarMensaje(i, "Habilita boton");
			System.out.println("Habilita " + ips.get(i));
		}
		enviarMensaje(0, "Ten el token");
	}

	public void continuarJuego(List<String> cartas) {
		HashSet<Integer> used = new HashSet<>();
		while (cartas.size() > 0) {
			try {
				Thread.sleep(500);
			} catch(Exception e) {}
			while(readers.get(portaToken).hasNext() == false) {
				continue;
			}

			String mensaje = readers.get(portaToken).nextLine();
			System.out.println(mensaje + " " + portaToken + " " + numeroJugadores + " " + used.size());

			if(mensaje.equals("Dame la carta") == true) {
				informe.add("Jugador con ip " + ips.get(portaToken) + " recibio la carta " + cartas.get(0));
				enviarMensaje(portaToken, cartas.get(0));
				cartas.remove(0);
				used.add(portaToken);
				continue;
			}

			if(mensaje.equals("Pasa el token") == true) {
				while(used.contains(portaToken) == true) {
					portaToken = (portaToken + 1) % numeroJugadores;
				}
				enviarMensaje(portaToken, "Ten el token");
			}
		}
	}

	public int preguntar() {
		int ronda = 1000;
		for(int i=0;i<numeroJugadores;i++) {
			enviarMensaje(i, "Numero cartas");
			while(readers.get(i).hasNext() == false);
			String mensaje = readers.get(i).nextLine();
			System.out.println(mensaje);
			int nuevo = Integer.valueOf(mensaje);
			if(nuevo < ronda) {
				ronda = nuevo;
			}
		}
		return ronda;
	}

	public void iniciaJuego(int ronda) {
		for(int i=ronda+1;i<=5;i++) {
			limpiarPanel();
			agregarLog("Generando cartas para la ronda " + i);
			List<String> cartas = obtenerCartasAleatorias(numeroJugadores);
			iniciaTokenRing();
			continuarJuego(cartas);
		}
	}

	public void escuchar() {
		int puerto = 9091;
		try (ServerSocket serverSocket = new ServerSocket(puerto)) {
			while (writers.size() < numeroJugadores) {
				Socket s = serverSocket.accept();
				PrintWriter output = new PrintWriter(s.getOutputStream());
				Scanner reader = new Scanner(s.getInputStream());
				String ip = s.getInetAddress().getHostAddress();

				writers.add(output);
				readers.add(reader);
				ips.add(ip);

				agregarLog("Cliente " + ip + " se unio al juego.");
				System.out.println("conectado");
					try {
						Thread.sleep(2000);
					} catch(Exception e) {
						e.printStackTrace();
					}
			}
		} catch (IOException e) {
				System.err.println("Could not listen on port " + puerto);
				System.exit(-1);
		}
	}

	public void imprimirInforme() {
		limpiarPanel();
		agregarLog("Fin de la partida");
		for(int i=0;i<informe.size();i++) {
			agregarLog(informe.get(i));
		}
	}

	List<PrintWriter> writersC = new ArrayList<PrintWriter>();
	List<Scanner> readersC = new ArrayList<Scanner>();
	List<String> ipsC = new ArrayList<String>();

	public void algCoordinacion() {
		new Thread(new Runnable() {
			int puerto = 9092;
			public void run() {
				try (ServerSocket serverSocket = new ServerSocket(puerto)) {
					while (true) {
						Socket s = serverSocket.accept();
						PrintWriter output = new PrintWriter(s.getOutputStream());
						Scanner reader = new Scanner(s.getInputStream());
						String ip = s.getInetAddress().getHostAddress();

						writersC.add(output);
						readersC.add(reader);
						ipsC.add(ip);
						coordina(writersC.size() - 1);
					}
				} catch (IOException e) {
						System.err.println("Could not listen on port " + puerto);
						System.exit(-1);
				}
			}
		}).start();
	}

	public void enviarMensajeCoordinacion(int id, String mensaje) {
		writersC.get(id).println(mensaje);
		writersC.get(id).flush();
	}

	public void coordina(final int id) {
		new Thread(new Runnable() {
			public void run() {
				while(true) {
					if(readersC.get(id).hasNext() == true) {
						String mensaje = readersC.get(id).nextLine();
						if(mensaje.equals("Sigues vivo?")) {
							if(soy_coordinador == true) {
								enviarMensajeCoordinacion(id, "Sigo vivo");
							} else {
								enviarMensajeCoordinacion(id, "No soy coordinador");
							}
						} else if(mensaje.equals("Eres el coordinador")) {
							System.out.println(mensaje);
							if(soy_coordinador == false) {
								soy_coordinador = true;
								agregarLog("Soy coordinador, identificado por " + ipsC.get(id));
							}
						}
					}
					try {
						Thread.sleep(2000);
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	public void mandarPokemon() {
		limpiarPanel();
		agregarLog("Pokemon a disputa");
		List<String> cartas = obtenerCartasAleatorias(1);
		enviarMensaje(0, "Ten el token");
		String []parts = cartas.get(0).split(" ");
		int poder = Integer.parseInt(parts[4]) * 10;
		while (cartas.size() > 0) {
			try {
				Thread.sleep(500);
			} catch(Exception e) {}
			System.out.println("Mandando token a " + portaToken + " Pokemon " + poder);
			while(readers.get(portaToken).hasNext() == false) {
				continue;
			}

			String mensaje = readers.get(portaToken).nextLine();
			if(mensaje.equals("Pasa el token") == true) {
				portaToken = (portaToken + 1) % numeroJugadores;
				enviarMensaje(portaToken, "Ten el token");
				continue;
			}

			int ataque = Integer.parseInt(mensaje);
			informe.add("Jugador con ip " + ips.get(portaToken) + " ataco al pokemon con " + ataque);
			poder -= ataque;
			if(poder <= 0) {
				enviarMensaje(portaToken, cartas.get(0));
				cartas.remove(0);
			}
		}
	}

	public void run() {
		algCoordinacion();
		if(soy_coordinador == false) {
			agregarLog("Servidor de respaldo");
		} else {
			agregarLog("Servidor principal");
		}
		while(true) {
			if(soy_coordinador == true) {
				System.out.println("Y");
				limpiarPanel();
				agregarLog("Inicio del juego");
				escuchar();
				iniciaJuego(preguntar());
				mandarPokemon();
				imprimirInforme();
				try {
					Thread.sleep(200000);
				} catch(Exception e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("N");
			}
		}
	}
};

public class Servidor extends Frame {
	JTextPane textPane = null;
	Panel panel = null;
	Frame frame = null;
	List<PrintWriter> writers = new ArrayList<PrintWriter>();
	List<Scanner> readers = new ArrayList<Scanner>();
	List<String> ips = new ArrayList<String>();
	List<String> nombres = new ArrayList<String>();
	Connection conn_christian;

	public void mostrar() {
		textPane = new JTextPane();

		panel = new Panel();
		panel.add(textPane);

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
						String ip = s.getInetAddress().getHostAddress();
						String nombre = "Equipo "+ (ips.size() + 1);

						writers.add(output);
						readers.add(reader);
						ips.add(ip);
						nombres.add(nombre);
					}
				} catch (IOException e) {
						System.err.println("Could not listen on port " + puerto);
						System.exit(-1);
				}
			}
		}).start();
	}

	public String obtenerHoraActual() {
		Calendar cal = Calendar.getInstance();
		java.util.Date now = cal.getTime();
		cal.setTime(now);
		java.util.Date later = cal.getTime();
		return new SimpleDateFormat("HH:mm:ss").format(later);
	}

	public void enviarDatos() {
		int lambda = 2;
		while(true) {
			try {
				Thread.sleep(lambda * 1000);
			} catch(Exception e) {
				e.printStackTrace();
			}
			for(int i=0;i<writers.size();i++) {
				PrintWriter output = writers.get(i);
				Scanner reader = readers.get(i);
				String ip = ips.get(i);
				String nombre = nombres.get(i);
				Christian c = new Christian(output, reader, nombre, ip, conn_christian);
				new Thread(c).start();
			}
		}
	}

	String driver = "com.mysql.jdbc.Driver";
	String db = "sincronizacion";
	String url = "jdbc:mysql://localhost/" + db;
	String user = "root";
	String pass = "";

	public void conectar() {
		try { 
			Class.forName(driver);
			conn_christian = DriverManager.getConnection(url, user, pass); 

			Statement stmt = conn_christian.createStatement();
			stmt.execute("SET FOREIGN_KEY_CHECKS=0");
			stmt.close();
		} catch (Exception e) { 
			System.err.println("Got an exception! "); 
      System.err.println(e.getMessage()); 
		}
	}

	public void sincronizar() {
		conectar();
		escuchar();
		enviarDatos();
	}

	public void exclusionMutua() {
		ExclusionMutua e = new ExclusionMutua(textPane);
		new Thread(e).start();
	}

	public static void main (String[] args) throws java.lang.Exception {
		Servidor servidor = new Servidor();
		servidor.mostrar();
		servidor.exclusionMutua();
		servidor.sincronizar();
	}
}
