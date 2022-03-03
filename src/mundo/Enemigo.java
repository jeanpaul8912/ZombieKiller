package mundo;

import java.util.Formatter;

public abstract class Enemigo implements SerViviente{
	/**
	 * cadena de caracteres incambiable que representa un estado que posee todo enemigo
	 */
	public static final String ATACANDO = "atacando";
	
	/**
	 * todo enemigo se rige por posiciones representativas de aparición
	 */
	public static final short POS_INICIAL = 140;
	/**
	 * todo enemigo se rige por posiciones representativas de ataque
	 */
	public static final short POS_ATAQUE = 460;
	
	/**
	 * todo enemigo tiene una posición en el eje Y
	 */
	private int posY;
	/**
	 * lentitud representativa con corde al nivel del enemigo
	 */
	private short lentitud;
	/**
	 * número representativo de la imágen actual con corde al estado
	 */
	private byte frameActual;
	/**
	 * cadena de caracteres que representa el estado actual del enemigo
	 */
	private String estadoActual;
	/**
	 * número que representa la salud del enemigo
	 */
	private byte salud;
	
	/**
	 * constructor básico que inicializa unicamente la posición en Y
	 */
	public Enemigo () {
		posY = POS_INICIAL;
	}
	
	/**
	 * Constructor compuesto para cargar información
	 * @param posY
	 * @param estadoActual
	 * @param frameActual
	 */
	public Enemigo (int posY, String estadoActual, byte frameActual) {
	this.posY = posY;
	this.estadoActual = estadoActual;
	this.frameActual = frameActual;
	}
	
	/**
	 * cadena de caracteres que representa el estado actual del zombie
	 * @return estadoActual
	 */
	public String getEstadoActual() {
		return estadoActual;
	}

	/**
	 * cambia el estado actual del enemigo
	 * @param estadoActual
	 */
	public void setEstadoActual(String estadoActual) {
		this.estadoActual = estadoActual;
		frameActual= 0;
	}

	/**
	 * obtiene el valor numérico de la imagen actual con corde al estado
	 * @return frameActual
	 */
	public byte getFrameActual() {
		return frameActual;
	}

	/**
	 * cambia el frame o número de la imagen con respecto al estado actual
	 * @param frameActual
	 */
	protected void setFrameActual(byte frameActual) {
		this.frameActual = frameActual;
	}

	/**
	 * obtiene la posición X del enemigo
	 * @return posicionX
	 */
	public abstract int getPosX();

	/**
	 * obtiene la posición Y del enemigo
	 * @return posY
	 */
	public int getPosY() {
		return posY;
	}

	/**
	 * cambia la posición en el eje Y
	 * @param posY
	 */
	protected void setPosY(int posY) {
		this.posY = posY;
	}

	/**
	 * obtiene la lentitud del enemigo (tiempo de espera en milisegundos por cada movimiento)
	 * @return lentitud
	 */
	public short getLentitud() {
		return lentitud;
	}

	/**
	 * cambia la lentitud del enemigo
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
	 * ejecuta la respectiva reacción del enemigo que recibe una granada
	 * @return true si es afectado por la granada
	 */
	public abstract boolean recibeGranada();
	/**
	 * crea una posición aleatoria en el eje X para la aparición del enemigo
	 * @return posAleatoria
	 */
	protected short posAleatoriaX() {
		int posAleatoria = (int) (Math.random() * SurvivorCamp.ANCHO_PANTALLA/3) + SurvivorCamp.ANCHO_PANTALLA/3 - 75;
		return (short) posAleatoria;
	}
	
	/**
	 * ejecuta cierta acción al terminar de atacar al personaje
	 */
	public abstract void terminaDeAtacar();
	
	/**
	 * comprueba que las posiciones de la bala coincidan con la posición del enemigo
	 * y reduzca su salud en caso de ser afectado
	 * @param x
	 * @param y
	 * @param danio
	 * @return true si fue afectado por el disparo
	 */
	public abstract boolean comprobarDisparo(int x, int y, byte danio);
	
	/**
	 * obtiene la ruta de la imagen desde la carpeta img
	 * @return
	 */
	public String getURL() {
		Formatter formato = new Formatter();
		return "/img/" + getClass().getSimpleName() + "/" + estadoActual + "/" + formato.format("%02d",getFrameActual()) + ".png";
	}
}
