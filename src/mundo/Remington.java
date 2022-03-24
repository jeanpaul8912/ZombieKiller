package mundo;

public class Remington extends ArmaDeFuego{

	private static final long serialVersionUID = 1L;
	/**
	 * valor incambiable del danio que genera el arma
	 */
	public static final byte DANIO = 3;
	/**
	 * valor incambiable que representa el alcance de la escopeta
	 */
	public static final int RANGO = 100;
	/**
	 * valor incambiable que representa el tiempo en milisegundos que tarda en cargar el arma
	 */
	public static final short TIEMPO_CARGA = 1400;
	public static final short RETROCESO = 400;
	private static Remington remington;
	/**
	 * Constructor del arma de fuego Remington con sus caracteristicas
	 */
	public Remington () {
		super();
		settBombeo(RETROCESO);
		setLimBalas((byte) 3);
		setMunicion(getLimBalas());
		setTiempoCarga(TIEMPO_CARGA);
		setDanio(DANIO);
	}
	
	public static Remington getInstancia() {
		if(remington == null) {
			remington = new Remington();
		}
		
		return remington;
	}
}
