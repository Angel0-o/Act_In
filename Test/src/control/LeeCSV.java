package control;

import java.util.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.opencsv.CSVReader;

public class LeeCSV {
	
	

	public static void main(String[] args) throws IOException 
	{
		String archivo = null;
		JFileChooser chooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif");
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showSaveDialog(chooser);
	    if(returnVal == JFileChooser.APPROVE_OPTION) 
	    {
	       archivo = chooser.getSelectedFile().getName();
	       System.out.println("Selecciona el excel: " + archivo);
	    }
		ArrayList<String> filas = null;
		//String archivo = "M:\\My Documents\\Test_CSV\\test _intranet.csv"; 
		archivo = "M:\\My Documents\\Test_CSV\\"+archivo; 
		String auxiliar, sqlInsert;
		String[] fila;
		filas = leeCSV(archivo);
		for(int x=0;x<filas.size();x++) {
			  //System.out.println(filas.get(x));
			  auxiliar = filas.get(x);
				fila = auxiliar.split(",");
				System.out.println(filas.get(x));
				
				sqlInsert = "insert into bmv_trabajadores_intranet values('" + fila[0].substring(1)
						+ "','" + fila[1]
						+ "','" + fila[2]
						+ "','" + fila[3]
						+ "','" + fila[4].substring(1,fila[4].lastIndexOf("]"))
						+ "')";
				System.out.println("SQL: "+sqlInsert);
			}
		
	}
	
	public static ArrayList<String> leeCSV(String archCSV) throws IOException
	{
		String[] nextLine;
		String lineS;
		ArrayList<String> filas = new ArrayList<String>();
		try {
			CSVReader reader = new CSVReader(new FileReader(archCSV));
			while ((nextLine = reader.readNext()) != null) 
			{
				lineS = "";
				if (nextLine != null) 
				{
					lineS = Arrays.toString(nextLine);
					//System.out.println("lineS = " + lineS);
					filas.add(Arrays.toString(nextLine));
				}
			}
			reader.close();
			return filas;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
