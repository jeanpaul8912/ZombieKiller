package mundo;

public class DatosErroneosException extends Exception {

	/**
	 * Constructor de la excepcion lanzada por no tener los datos correctos
	 */
	public DatosErroneosException () {
		super("Hay valores no validos en el juego");
	}
	/**
	 * Constructor de la exception lanzada por tener mas zombies en el archivo de texto plano de los estipulados por la ronda
	 * @param excedente
	 */
	public DatosErroneosException(int excedente) {
		super("El archivo ha excedido el numero de Zombies generados en " + excedente);
	}
}
