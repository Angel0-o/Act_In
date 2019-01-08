package controlador;

public class Validation 
{
	public static String getCorrectValue(String datum)
	{
		if(datum == null)
			return "";
		else
			return datum;
	}
	
	public static String getDate(String date)
	{
		if(date == null)
			return " ";
		else
			return date.substring(0,10).trim();
	}
	
	public static String getCorrectMoney(String money)
	{
		if(money == null)
			return "";
		else
		{
			try
			{
				int aux = Math.round(Float.parseFloat(money)*100);
				if(aux%10==0)
					return (aux/100.0)+"0";
				else
					return "" + (aux/100.0);
			}
			catch(NumberFormatException e)
			{
				e.printStackTrace();
				return money;
			}
		}
			
	}
	public static boolean isEmptyField(String value)
	{
		return value.equals("");
	}
}
