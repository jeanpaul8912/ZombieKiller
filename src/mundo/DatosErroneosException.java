package mundo;

public class DatosErroneosException extends Exception {

	/**
	 * Constructor de la excepción lanzada por no tener los datos correctos
	 */
	public DatosErroneosException () {
		super("Hay valores no válidos en el juego");
	}
	/**
	 * Constructor de la exceptión lanzada por tener más zombies en el archivo de texto plano de los estipulados por la ronda
	 * @param excedente
	 */
	public DatosErroneosException(int excedente) {
		super("El archivo ha excedido el número de Zombies generados en " + excedente);
	}
}
