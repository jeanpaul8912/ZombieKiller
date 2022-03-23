package mundo;

public class NombreInvalidoException extends Exception{

	/**
	 * Constructor de la excepcion lanzada por escribir caracteres no alfabeticos
	 * @param caracter
	 */
	public NombreInvalidoException (char caracter) {
		super("El nombre no puede contener numeros ni simbolos \n" + "Caracter invalido: " + caracter);
	}
}
