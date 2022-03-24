package mundo;

public abstract class ArmaDeFuego extends Arma implements ArmaConMunicion{

	private static final long serialVersionUID = 1L;
	/**
	 * Cadena de caracteres incambaible que representa el estado de recalentamiento o recarga de un arma de efuego
	 */
	public static final String RECARGANDO = "recarga";

	/**
	 * valor numerico que representa el tiempo de bombeo o recarga
	 */
	private short tBombeo;
	/**
	 * valor numerico que representa la cantidad de balas disponible
	 */
	private byte limBalas;
	/**
	 * estado temporal para dibujar la sangre del zombie en pantalla
	 */
	private boolean ensangrentada;
	/**
	 * valor numerico que representa la municion del arma
	 */
	private byte municion;

	/**
	 * Constructor abstracto del arma de fuego
	 */
	public ArmaDeFuego() {
		super();
		ensangrentada = false;
	}

	@Override
	public long calcularDescanso() {
		long descanso = 0;
		if(getEstado().equals(RECARGANDO))
			descanso = gettBombeo();
		else if(getEstado().equals(CARGANDO))
			descanso = getTiempoCarga();
		return descanso;
	}
	/**
	 * cambia el limite de balas del arma en cuestion
	 * @param limBalas
	 */
	protected void setLimBalas(byte limBalas) {
		this.limBalas = limBalas;
	}

	/**
	 * obtiene el limite de balas del arma presente
	 * @return limBalas
	 */
	public byte getLimBalas() {
		return limBalas;
	}

	/**
	 * obtiene el tiempo en el que el arma se calienta o bombea
	 * @return tBombeo
	 */
	public short gettBombeo() {
		return tBombeo;
	}

	/**
	 * cambia el tiempo de recalentamiento o bombeo
	 * @param tBombeo
	 */
	protected void settBombeo(short tBombeo) {
		this.tBombeo = tBombeo;
	}

	/**
	 * pregunta si el arma presente acaba de darle a algun enemigo
	 * @return ensangrentada
	 */
	public boolean isEnsangrentada() {
		return ensangrentada;
	}

	/**
	 * cambia el estado ensangrentado 
	 * @param ensangrentada
	 */
	public void setEnsangrentada(boolean ensangrentada) {
		this.ensangrentada = ensangrentada;
	}
	@Override
	public byte getMunicion() {
		return municion;
	}
	@Override
	public void setMunicion(byte municion) {
		this.municion = municion;
	}
}
