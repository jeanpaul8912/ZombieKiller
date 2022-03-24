package mundo;

public class M1911 extends ArmaDeFuego{

	private static final long serialVersionUID = 1L;
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
	
	private static M1911 m1911;
	
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
	
	public static M1911 getInstancia() {
		if(m1911 == null) {
			m1911 = new M1911();
		}
		
		return m1911;
	}
}
