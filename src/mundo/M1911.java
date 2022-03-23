package mundo;

public class M1911 extends ArmaDeFuego{

	/**
	 * valor incambiable que representa el danio que causa la pistola M1911
	 */
	public static final byte DANIO = 10;
	/**
	 * valor incambiable que representa el tiempo de carga en milisegundos
	 */
	public static final short TIEMPO_CARGA = 100;
	/**
	 * valor incambiable que representa el tiempo de retroceso en milisegundos
	 */
	public static final short RETROCESO = 10;
	
	/**
	 * Constructor del arma M1911 con sus caracteristicas
	 */
	public M1911 () {
		super();
		settBombeo(RETROCESO);
		setTiempoCarga(TIEMPO_CARGA);
		setDanio(DANIO);
		setLimBalas((byte) 108);
		setMunicion(getLimBalas());
	}
}
