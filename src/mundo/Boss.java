package mundo;

import mundo.armas.fuego.Remington;

public class Boss extends Enemigo implements SeMueveEnZigzag {

	/**
	 * cadena de caracteres incambiable que representa el estado del jefe volando
	 */
	public static final String VOLANDO = "volando";
	/**
	 * cadena de caracteres incambiable que representa el estado del jefe derrotado
	 */
	public static final String DERROTADO = "derrotado";
	/**
	 * valor incambiable que representa la salud del jefe
	 */
	public static final byte SALUD = 16;
	/**
	 * valor incambiable que representa la lentitud del jefe
	 */
	public static final short LENTITUD = 14;
	/**
	 * valor incambiable que representa el ancho de la imagen del boss
	 */
	public static final int ANCHO_IMAGEN = 294;

	/**
	 * valor numerico entero que representa la direccion o velocidad en el eje X
	 */
	private int direccionX;
	/**
	 * valor numerico entero que representa la direccion o velocidad en el eje Y
	 */
	private int direccionY;
	/**
	 * valor que representa la posicion en el eje X del jefe
	 */
	private int posHorizontal;

	/**
	 * Constructor del jefe al iniciar la ronda 10
	 */
	public Boss() {
		super();
		setEstadoActual(VOLANDO);
		setSalud(SALUD);
		setLentitud(LENTITUD);
	}

	/**
	 * Constructor del jefe al cargar la partida si fue guardada en la ronda del
	 * jefe
	 *
	 * @param salud
	 */
	public Boss(byte salud) {
		super();
		setEstadoActual(VOLANDO);
		setSalud(salud);
		setLentitud(LENTITUD);
		//moverEnDireccion();
	}

	/*@Override
	public void terminaDeAtacar() {
		setEstadoActual(VOLANDO);
		setPosY(POS_INICIAL);
		//moverEnDireccion();
		posHorizontal = posAleatoriaX();
	}*/

	@Override
	public boolean comprobarDisparo(int x, int y, int danio) {
		boolean leDio = false;
		int danioResultante = danio;
		if (x > posHorizontal + 108 && x < posHorizontal + 160 && y > getPosY() + 110 && y < getPosY() + 190) {
			if (danio == Remington.DANIO) {
				danioResultante = danioResultante - (POS_ATAQUE - getPosY()) / Remington.RANGO;
//						 System.out.println(danioResultante);
			}
			setSalud((byte) (getSalud() - danioResultante));
			//hace lo mismo cuando termina de atacar que cuando lo atacan
			//terminaDeAtacar();
			if (getSalud() <= 0) {
				setEstadoActual(DERROTADO);
				posHorizontal = 365;
				setPosY(POS_INICIAL);
			}
			leDio = true;
		}
		return leDio;
	}

	@Override
	public int getPosX() {
		return posHorizontal;
	}

	public void setPosX(int posHorizontal) {
		this.posHorizontal = posHorizontal;
	}
	@Override
	public int getDireccionX() {
		return direccionX;
	}

	public void setDireccionX(int direccionX) {
		this.direccionX = direccionX;
	}
	@Override
	public int getDireccionY() {
		return direccionY;
	}
	public void setDireccionY(int direccionY) {
		this.direccionY = direccionY;
	}

}
