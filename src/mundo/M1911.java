package mundo;

public class M1911 extends ArmaDeFuego{

	/**
	 * valor incambiable que representa el daño que causa la pistola M1911
	 */
	public static final byte DANIO = 1;
	/**
	 * valor incambiable que representa el tiempo de carga en milisegundos
	 */
	public static final short TIEMPO_CARGA = 1300;
	/**
	 * valor incambiable que representa el tiempo de retroceso en milisegundos
	 */
	public static final short RETROCESO = 100;
	
	/**
	 * Constructor del arma M1911 con sus características
	 */
	public M1911 () {
		super();
		settBombeo(RETROCESO);
		setTiempoCarga(TIEMPO_CARGA);
		setDanio(DANIO);
		setLimBalas((byte) 8);
		setMunicion(getLimBalas());
	}
}
