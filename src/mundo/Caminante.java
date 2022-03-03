package mundo;

public class Caminante extends Zombie implements SeMueveEnZigzag {

	public static final String GRUNIENDO = "gruñendo";

	private int direccionX;
	/**
	 * posición en el ejeX
	 */
	private int posHorizontal;
	private int direccionY;

	/**
	 * Constructor del zombie caminante con sus características con corde a la ronda
	 * @param ronda
	 * @param siguiente
	 */
	public Caminante(short ronda, Zombie siguiente) {
		super(ronda, siguiente);
		moverEnDireccion();
		posHorizontal = posAleatoriaX();
	}

	/**
	 * Constructor que carga las características que se guardaron en texto plano
	 * @param posX
	 * @param posY
	 * @param direccionX
	 * @param direccionY
	 * @param estadoActual
	 * @param frameActual
	 * @param salud
	 * @param ronda
	 */
	public Caminante(int posX, int posY, int direccionX, int direccionY, String estadoActual, byte frameActual, byte salud, int ronda) {
		super(posY, estadoActual, frameActual, salud, ronda);
		posHorizontal = posX;
		this.direccionX = direccionX;
		this.direccionY = direccionY;
	}

	@Override
	public int getDireccionX() {
		return direccionX;
	}
	@Override
	public int getDireccionY() {
		return direccionY;
	}
	/**
	 * Constructor del zombie nodo inicializable en caminante
	 */
	public Caminante() {
		super();
	}

	@Override
	public String ataco() {
		if (getEstadoActual().equals(CAMINANDO)) {
			if (getPosY() > POS_ATAQUE) {
				setEstadoActual(ATACANDO);
			} else {
				if (posHorizontal > SurvivorCamp.ANCHO_PANTALLA - ANCHO_IMAGEN || posHorizontal < 0)
					moverEnDireccion();
				posHorizontal = posHorizontal + direccionX;
				setPosY(getPosY() + direccionY);

				if (getFrameActual() == 24)
					setFrameActual((byte) 0);
				else
					setFrameActual((byte) (getFrameActual() + 1));
			}
		} else if (getEstadoActual().equals(ATACANDO)) {
			if (getFrameActual() < 16)
				setFrameActual((byte) (getFrameActual() + 1));
		} else if (getEstadoActual().equals(MURIENDO) || getEstadoActual().equals(MURIENDO_INCENDIADO)) {
			if (getFrameActual() < 17)
				setFrameActual((byte) (getFrameActual() + 1));
		} else if (getEstadoActual().equals(GRUNIENDO)) {
			if (getFrameActual() < 17)
				setFrameActual((byte) (getFrameActual() + 1));
			else
				setEstadoActual(ATACANDO);
		}
		return getEstadoActual();
	}

	@Override
	public void moverEnDireccion() {
		direccionX = (int) (Math.random() * 9) - 4;
		if (Math.abs(direccionX) < 4)
			direccionY = 4 - Math.abs(direccionX);
		else
			direccionY = 2;
	}

	@Override
	public boolean comprobarDisparo(int x, int y, byte danio) {
		boolean leDio = false;
		int danioResultante = danio;
		if (x > posHorizontal + 36 && x < posHorizontal + 118 && y > getPosY() + 5 && y < getPosY() + 188) {
			if (getEstadoActual() != MURIENDO) {
				if (y < getPosY() + 54)
					danioResultante = ((byte) (danio + 2));
				// el 320 define la distancia entre el zombie y el personaje
				if (danio == Remington.DANIO) {
					danioResultante = danioResultante - (POS_ATAQUE - getPosY()) / Remington.RANGO;
					// System.out.println(danioResultante);
				}
				setSalud((byte) (getSalud() - danioResultante));
				if (getSalud() <= 0) {
					setEstadoActual(MURIENDO);
				}
				leDio = true;
			}
		}
		return leDio;
	}

	@Override
	public void terminaDeAtacar() {
		setEstadoActual(GRUNIENDO);
	}

	@Override
	public int getPosX() {
		return posHorizontal;
	}

}
