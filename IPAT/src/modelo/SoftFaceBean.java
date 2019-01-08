package modelo;

public class SoftFaceBean {
	
	private String usuario;
	public boolean valid;
	private String companiaS;
	private String Fdesde;
	private String Fhasta;
	private String documento;
	private String[] series;
	
	private String usuarioC;
	private String mensaje;
	public boolean valid2;
	
	private String usuarioDelete;


    public String getUsuarioS() {
        return usuario;
    }

    public void setUsuarioS(String newUsuario) {
        usuario = newUsuario;
    }
    
    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean newValid) {
        valid = newValid;
    }
    
	 public String getCompaniaS() {
			return companiaS;
		}
	 public void setCompaniaS(String newCompaniaS) {
	    	this.companiaS = newCompaniaS;
	    }
	 
	 public String getFdesde() {
			return Fdesde;
		}
	 public void setFdesde(String newFdesde) {
	    	this.Fdesde = newFdesde;
	    }
	 
	 public String getFhasta() {
			return Fhasta;
		}
	 public void setFhasta(String newFhasta) {
	    	this.Fhasta = newFhasta;
	    }
	 
	 public String getDocumento() {
			return documento;
		}
	 public void setDocumento(String newDocumento) {
	    	this.documento = newDocumento;
	    }
	 
	 public String[] getSeries() {
			return series;
		}
	 public void setSeries(String[] newSeries) {
	    	this.series = newSeries;
	    }
	 
	/* AQUI LO DEJE*/ 
	 public String getUsuarioC() {
	        return usuarioC;
	    }

	 public void setUsuarioC(String newUsuarioC) {
	        usuarioC = newUsuarioC;
	    }
	 
	 public String getMensaje() {
			return mensaje;
		}
	 public void setMensaje(String newMensaje) {
	    	this.mensaje = newMensaje;
	    }
	 
	    public boolean isValid2() {
	        return valid2;
	    }

	    public void setValid2(boolean newValid2) {
	        valid2 = newValid2;
	    }
	    
	    
	    public String getUsuarioD() {
	        return usuarioDelete;
	    }

	    public void setUsuarioD(String newUsuarioD) {
	        usuarioDelete = newUsuarioD;
	    }
	    

}
