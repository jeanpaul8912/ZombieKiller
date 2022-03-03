package mundo;

public class NombreInvalidoException extends Exception{

	/**
	 * Constructor de la excepción lanzada por escribir caracteres no alfabéticos
	 * @param caracter
	 */
	public NombreInvalidoException (char caracter) {
		super("El nombre no puede contener números ni símbolos \n" + "Caracter inválido: " + caracter);
	}
}
