package modelo;

public class PermisosBean {
	
	private String usuario;
	public boolean valid;
	private String operacion;
	private String descripcion;
	private String estatus;
	
	private String usuarioC;
	private String mensaje;
	public boolean valid2;
	
	private String usuarioDelete;


    public String getUsuarioP() {
        return usuario;
    }

    public void setUsuarioP(String newUsuario) {
        usuario = newUsuario;
    }
    
    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean newValid) {
        valid = newValid;
    }
    
	 public String getOperacion() {
			return operacion;
		}
	 public void setOperacion(String newOperacion) {
	    	this.operacion = newOperacion;
	    }
	 
	 public String getDescripcion() {
			return descripcion;
		}
	 public void setDescripcion(String newDescripcion) {
	    	this.descripcion = newDescripcion;
	    }
	 
	 public String getEstatus() {
			return estatus;
		}
	 public void setEstatus(String newEstatus) {
	    	this.estatus = newEstatus;
	    }
	 
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
