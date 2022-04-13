package mundo;

public abstract class Zombie extends Enemigo {

	/**
	 * valor incambiable de la lentitud rango 1
	 */
	public static final short LENTITUD1 = 50;
	/**
	 * valor incambiable de la lentitud rango 2
	 */
	public static final short LENTITUD2 = 45;
	/**
	 * valor incambiable de la lentitud rango 3
	 */
	public static final short LENTITUD3 = 40;
	/**
	 * valor incambiable de la lentitud rango 4
	 */
	public static final short LENTITUD4 = 30;

	/**
	 * valor incambiable de la salud rango 1
	 */
	public static final byte SALUD1 = 3;
	/**
	 * valor incambiable de la salud rango 2
	 */
	public static final byte SALUD2 = 5;
	/**
	 * valor incambiable de la salud rango 3
	 */
	public static final byte SALUD3 = 6;
	/**
	 * valor incambiable de la salud rango 4
	 */
	public static final byte SALUD4 = 8;

	/**
	 * caracteres que representan el estado Caminando del zombie
	 */
	public static final String CAMINANDO = "caminando";
	/**
	 * caracteres que representan el estado Muriendo del zombie
	 */
	public static final String MURIENDO = "muriendo";
	/**
	 * caracteres que representan el estado Muriendo de tiro a la cabeza del zombie
	 */
	public static final String MURIENDO_HEADSHOT = "muriendo";
	/**
	 * caracteres que representan el estado Muriendo incendiado o por granada del
	 * zombie
	 */
	public static final String MURIENDO_INCENDIADO = "muriendoIncendiado";
	/**
	 * caracteres que representan el estado Nodo del zombie, este no se cambia nunca
	 */
	public static final String NODO = "nodo";

	/**
	 * valor incambiable que representa el ancho de toda imagen zombie
	 */
	public static final int ANCHO_IMAGEN = 150;

	/**
	 * zombie que se encuentra al frente o fue generado antes que este
	 */
	private Zombie alFrente;
	/**
	 * zombie que se encuentra atras o fue generado despues que este
	 */
	private Zombie atras;

	public int posX;

	/**
	 * Constructor de un zombie nodo
	 */
	protected Zombie() {
		setEstadoActual(NODO);
	}

	protected Zombie(int posY, String estadoActual, byte frameActual, byte salud, int ronda) {
		super(posY, estadoActual, frameActual);
		determinarDificultadZombie(ronda);
		setSalud(salud);
	}

	protected Zombie(short nivel, Zombie atras) {
		determinarDificultadZombie(nivel);
		setEstadoActual(CAMINANDO);
		this.atras = atras;
	}

	/**
	 * metodo auxiliar que determina la dificultad de un zombie cuando se crea o se
	 * carga
	 *
	 * @param ronda
	 */
	public void determinarDificultadZombie(int ronda) {
		switch (ronda) {
			case 9:
			case 8:
				setLentitud(LENTITUD3);
				setSalud(SALUD4);
				break;
			case 7:
				setLentitud(LENTITUD4);
				setSalud(SALUD2);
				break;
			case 6:
				setLentitud(LENTITUD3);
				setSalud(SALUD3);
				break;
			case 5:
			case 4:
				setLentitud(LENTITUD3);
				setSalud(SALUD2);
				break;
			case 3:
				setLentitud(LENTITUD2);
				setSalud(SALUD2);
				break;
			case 2:
				setLentitud(LENTITUD1);
				setSalud(SALUD2);
				break;
			case 1:
			default:
				setLentitud(LENTITUD1);
				setSalud(SALUD1);
				break;
		}
	}

	/**
	 * obtiene el zombie que fue creado antes que el correspondiente
	 *
	 * @return
	 */
	public Zombie getAlFrente() {
		return alFrente;
	}

	/**
	 * Cambia el zombie que se encuentra al frente del correspondiente
	 *
	 * @param alFrente
	 */
	public void setAlFrente(Zombie alFrente) {
		this.alFrente = alFrente;
	}

	/**
	 * obtiene el zombie que fue creado despues del correspondiente
	 *
	 * @return zombie de atras
	 */
	public Zombie getAtras() {
		return atras;
	}

	/**
	 * Cambia el zombie que se encuentra al atras del correspondiente
	 *
	 * @param atras
	 */
	public void setAtras(Zombie atras) {
		this.atras = atras;
	}

	/**
	 * se elimina a si mismo cambiando las asociaciones de los zombies laterales
	 */
	public void eliminarse() {
		atras.alFrente = alFrente;
		alFrente.atras = atras;
	}

	@Override
	public abstract boolean comprobarDisparo(int x, int y, int danio);

	/**
	 * entra en la lista enlazada relacionando los parametros zombie atras y al
	 * frente
	 *
	 * @param zombAlFrente
	 * @param zombAtras
	 */
	public void introducirse(Zombie zombAlFrente, Zombie zombAtras) {
		atras = zombAtras;
		alFrente = zombAlFrente;
		zombAlFrente.atras = this;
		zombAtras.alFrente = this;
	}

	public void inicializar(short nivel, Zombie atras) {
		determinarDificultadZombie(nivel);
		setEstadoActual(CAMINANDO);
		setPosX();
		this.atras = atras;
	}

	public void setPosX() {
		this.posX = posAleatoriaX();
	}

	public int getPosX() {
		return this.posX;
	}

}