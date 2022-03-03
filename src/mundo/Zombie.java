package mundo;

import java.io.Serializable;
import java.util.Formatter;

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
	 * caracteres que representan el estado Muriendo incendiado o por granada del zombie
	 */
	public static final String MURIENDO_INCENDIADO = "muriendoIncendiado";
	/**
	 * caracteres que representan el estado Nodo del zombie, éste no se cambia nunca
	 */
	public static final String NODO = "nodo";

	/**
	 * valor incambiable que representa el ancho de toda imagen zombie
	 */
	public static final int ANCHO_IMAGEN = 150;
	
	/**
	 * zombie que se encuentra al frente o fue generado antes que éste
	 */
	private Zombie alFrente;
	/**
	 * zombie que se encuentra atrás o fue generado después que éste
	 */
	private Zombie atras;

	/**
	 * Constructor de un zombie nodo
	 */
	public Zombie () {
		setEstadoActual(NODO);
	}
	/**
	 * Constructor de un zombie cargado por una partida
	 * @param posY 
	 * @param estadoActual
	 * @param frameActual
	 * @param salud
	 * @param ronda
	 */
	public Zombie (int posY, String estadoActual, byte frameActual, byte salud, int ronda) {
		super(posY, estadoActual, frameActual);
		determinarDificultadZombie(ronda);
		setSalud(salud);
	}
	/**
	 * Constructor de un zombie recién generado con respecto a la ronda actual
	 * @param nivel
	 * @param atras
	 */
	public Zombie(short nivel, Zombie atras) {
		determinarDificultadZombie(nivel);
		setEstadoActual(CAMINANDO);
		this.atras = atras;
	}

	/**
	 * método auxiliar que determina la dificultad de un zombie cuando se crea o se carga
	 * @param ronda
	 */
	public void determinarDificultadZombie (int ronda) {
		switch (ronda) {
		case 9:
			setLentitud(LENTITUD3);
			setSalud(SALUD4);
			break;
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
			setLentitud(LENTITUD3);
			setSalud(SALUD2);
			break;
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
			setLentitud(LENTITUD1);
			setSalud(SALUD1);
			break;
		default:
			setLentitud(LENTITUD1);
			setSalud(SALUD1);
			break;
		}
	}
	/**
	 * obtiene el zombie que fue creado antes que el correspondiente
	 * @return
	 */
	public Zombie getAlFrente() {
		return alFrente;
	}
	/**
	 * Cambia el zombie que se encuentra al frente del correspondiente
	 * @param alFrente
	 */
	public void setAlFrente(Zombie alFrente) {
		this.alFrente = alFrente;
	}

	/**
	 * obtiene el zombie que fue creado después del correspondiente
	 * @return zombie de atrás
	 */
	public Zombie getAtras() {
		return atras;
	}
	/**
	 * Cambia el zombie que se encuentra al atrás del correspondiente
	 * @param atras
	 */
	public void setAtras(Zombie atras) {
		this.atras = atras;
	}

	/**
	 * se elimina a sí mismo cambiando las asociaciones de los zombies laterales
	 */
	public void eliminarse () {
		atras.alFrente = alFrente;
		alFrente.atras = atras;
	}
	
	@Override
	public abstract void terminaDeAtacar();
	@Override
	public abstract boolean comprobarDisparo(int x, int y, byte danio);

	@Override
	public abstract String ataco();
	@Override
	public boolean recibeGranada() {
		boolean afectaGranada = false;
		if(!getEstadoActual().equals(MURIENDO) && !getEstadoActual().equals(MURIENDO_INCENDIADO)){
			setEstadoActual(MURIENDO_INCENDIADO);
			afectaGranada = true;
		}
		return afectaGranada;
	}

	/**
	 * entra en la lista enlazada relacionando los parámetros zombie atrás y al frente
	 * @param zombAlFrente
	 * @param zombAtras
	 */
	public void introducirse(Zombie zombAlFrente, Zombie zombAtras) {
		atras = zombAtras;
		alFrente = zombAlFrente;
		zombAlFrente.atras = this;
		zombAtras.alFrente = this;
	}

}
