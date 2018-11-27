package modelo;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.opencsv.CSVReader;

import bs.Pool;
import modelo.UserBean;

//import modelo.ConnectionManagerSOFT;

public class UserDAO {

//	static Connection currentCon = null;
//	static Statement stmt = null;
//	static Statement stmt1 = null;
//	static ResultSet rs = null;
//	static ResultSet rs1 = null;
	public static String errorJSP = "";
	
	static String sql;

	public static UserBean login(UserBean bean) {

		if (Pool.conecta(bean.getUsername(), bean.getPassword())) {
			bean.setValid(true);
			System.out.println("Bienvenido " + bean.getUsername());
		} else {
			System.out.println("USUARIO FALSO!!!!!");
			bean.setValid(false);
		}
		
		return bean;
	}
	
	public static ArrayList<String> leeCSV(String archCSV) throws IOException
	{
		String[] nextLine;
		String lineS;
		ArrayList<String> filas = new ArrayList<String>();
		try {
			CSVReader reader = new CSVReader(new FileReader(archCSV));
			reader.readNext();
			while ((nextLine = reader.readNext()) != null) 
			{
				lineS = "";
				if (nextLine != null) 
				{
					lineS = Arrays.toString(nextLine);
					//System.out.println("lineS = " + lineS);
					filas.add(Arrays.toString(nextLine));
				}
			}errorJSP = "Error en el archivo";
			reader.close();
			return filas;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
