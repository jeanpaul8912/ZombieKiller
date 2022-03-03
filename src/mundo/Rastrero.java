package mundo;

public class Rastrero extends Zombie {

	/**
	 * posición en el ejeX
	 */
	private int posX;
	
	/**
	 * Constructor del zombie rastrero con sus características con corde a la ronda
	 * @param ronda
	 * @param siguiente
	 */
	public Rastrero(short ronda, Zombie siguiente) {
		super(ronda, siguiente);
		posX = posAleatoriaX();
	}

	/**
	 * Constructor que carga las características que se guardaron en texto plano
	 * @param posX
	 * @param posY
	 * @param estadoActual
	 * @param frameActual
	 * @param salud
	 * @param ronda
	 */
	public Rastrero (int posX, int posY, String estadoActual, byte frameActual, byte salud, int ronda) {
		super(posY, estadoActual, frameActual, salud, ronda);
		this.posX = posX;
	}
	@Override
	public String ataco() {
		if (getEstadoActual().equals(CAMINANDO)) {
			if (getPosY() > POS_ATAQUE) {
				setEstadoActual(ATACANDO);
			} else {
				setPosY(getPosY() + 3);
				if (getFrameActual() == 31)
					setFrameActual((byte) 0);
				else 
					setFrameActual((byte) (getFrameActual() + 1));
			}
		} else if (getEstadoActual().equals(ATACANDO)) {
			if (getFrameActual() < 16)
				setFrameActual((byte) (getFrameActual() + 1));
		} else if (getEstadoActual().equals(MURIENDO) || getEstadoActual().equals(MURIENDO_INCENDIADO)) {
			if (getFrameActual() < 11)
				setFrameActual((byte) (getFrameActual() + 1));
		}
		return getEstadoActual();
	}

	@Override
	public boolean comprobarDisparo(int x, int y, byte danio) {
		boolean leDio = false;
		int danioResultante = danio;
		if (!getEstadoActual().equals(MURIENDO)) {
		if (x > posX + 36 && x < posX + 118 && y > getPosY() + 120 && y < getPosY() + 196) {
				// comprueba headshot
				if (y < getPosY() + 162)
					danioResultante = ((byte) (danio+2));
					if (danio == Remington.DANIO) {
						danioResultante = danioResultante - (POS_ATAQUE - getPosY()) / Remington.RANGO;
//						 System.out.println(danioResultante);
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
		setEstadoActual(MURIENDO);
	}

	@Override
	public int getPosX() {
		return posX;
	}

}
