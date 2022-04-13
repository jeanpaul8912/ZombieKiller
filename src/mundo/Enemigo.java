package mundo;

import java.util.Formatter;

public abstract class Enemigo implements SerViviente, Cloneable {
	/**
	 * cadena de caracteres incambiable que representa un estado que posee todo
	 * enemigo
	 */
	public static final String ATACANDO = "atacando";

	/**
	 * todo enemigo se rige por posiciones representativas de aparicion
	 */
	public static final short POS_INICIAL = 140;
	/**
	 * todo enemigo se rige por posiciones representativas de ataque
	 */
	public static final short POS_ATAQUE = 460;

	/**
	 * todo enemigo tiene una posicion en el eje Y
	 */
	private int posY;
	/**
	 * lentitud representativa con corde al nivel del enemigo
	 */
	private short lentitud;
	/**
	 * numero representativo de la imagen actual con corde al estado
	 */
	private byte frameActual;
	/**
	 * cadena de caracteres que representa el estado actual del enemigo
	 */
	private String estadoActual;
	/**
	 * numero que representa la salud del enemigo
	 */
	private byte salud;

	/**
	 * constructor basico que inicializa unicamente la posicion en Y
	 */
	public Enemigo() {
		posY = POS_INICIAL;
	}

	/**
	 * Constructor compuesto para cargar informacion
	 *
	 * @param posY
	 * @param estadoActual
	 * @param frameActual
	 */
	public Enemigo(int posY, String estadoActual, byte frameActual) {
		this.posY = posY;
		this.estadoActual = estadoActual;
		this.frameActual = frameActual;
	}

	/**
	 * cadena de caracteres que representa el estado actual del zombie
	 * 
	 * @return estadoActual
	 */
	public String getEstadoActual() {
		return estadoActual;
	}

	/**
	 * cambia el estado actual del enemigo
	 * 
	 * @param estadoActual
	 */
	public void setEstadoActual(String estadoActual) {
		this.estadoActual = estadoActual;
		frameActual = 0;
	}

	/**
	 * obtiene el valor numerico de la imagen actual con corde al estado
	 * 
	 * @return frameActual
	 */
	public byte getFrameActual() {
		return frameActual;
	}

	/**
	 * cambia el frame o numero de la imagen con respecto al estado actual
	 * 
	 * @param frameActual
	 */
	public void setFrameActual(byte frameActual) {
		this.frameActual = frameActual;
	}

	/**
	 * obtiene la posicion X del enemigo
	 * 
	 * @return posicionX
	 */
	public abstract int getPosX();

	/**
	 * obtiene la posicion Y del enemigo
	 * 
	 * @return posY
	 */
	public int getPosY() {
		return posY;
	}

	/**
	 * cambia la posicion en el eje Y
	 *
	 * @param posY
	 */
	public void setPosY(int posY) {
		this.posY = posY;
	}

	/**
	 * obtiene la lentitud del enemigo (tiempo de espera en milisegundos por cada
	 * movimiento)
	 *
	 * @return lentitud
	 */
	public short getLentitud() {
		return lentitud;
	}

	/**
	 * cambia la lentitud del enemigo
	 *
	 * @param lentitud
	 */
	public void setLentitud(short lentitud) {
		this.lentitud = lentitud;
	}

	@Override
	public byte getSalud() {
		return salud;
	}

	@Override
	public void setSalud(byte salud) {
		this.salud = salud;
	}

	/**
	 * crea una posicion aleatoria en el eje X para la aparicion del enemigo
	 *
	 * @return posAleatoria
	 */
	public short posAleatoriaX() {
		int posAleatoria = (int) (Math.random() * SurvivorCamp.ANCHO_PANTALLA/3) + SurvivorCamp.ANCHO_PANTALLA/3 - 75;
		return (short) posAleatoria;
	}

	/**
	 * ejecuta cierta accion al terminar de atacar al personaje
	 */
	//public abstract void terminaDeAtacar();

	/**
	 * comprueba que las posiciones de la bala coincidan con la posicion del enemigo
	 * y reduzca su salud en caso de ser afectado
	 *
	 * @param x
	 * @param y
	 * @param danio
	 * @return true si fue afectado por el disparo
	 */
	public abstract boolean comprobarDisparo(int x, int y, int danio);

	/**
	 * obtiene la ruta de la imagen desde la carpeta img
	 *
	 * @return
	 */
	public String getURL() {
		try (Formatter formato = new Formatter()) {
			return "/img/" + getClass().getSimpleName() + "/" + estadoActual + "/"
					+ formato.format("%02d", getFrameActual()) + ".png";
		}
	}

	public Enemigo clonar() {
		try {
			return (Enemigo) super.clone();
		} catch (CloneNotSupportedException cloneNotSupportedException) {
			throw new RuntimeException(cloneNotSupportedException.getMessage());
		}
	}
}