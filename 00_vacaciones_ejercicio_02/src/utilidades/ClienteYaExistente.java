package utilidades;

public class ClienteYaExistente extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ClienteYaExistente(String mensaje) {
		super(mensaje);
		
	}

}
