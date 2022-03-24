package mundo;

public class Granada extends Arma  implements ArmaConMunicion{

	private static final long serialVersionUID = 1L;
	/**
	 * valor incambiable que representa el danio causado por la granada
	 */
	public static final byte DANIO = 6;
	/**
	 * valor que representa la cantidad de granadas
	 */
	private byte cantidad;
	
	private static Granada granada;

	/**
	 * Constructor de la granada con su respectiva cantidad y danio
	 */
	public Granada() {
		super();
		setMunicion((byte) 2);
		setTiempoCarga((short) 200);
		setDanio(DANIO);
	}
	
	@Override
	public long calcularDescanso() {
		long descanso = 0;
		if(getEstado().equals(CARGANDO))
			descanso = getTiempoCarga();
		return descanso;
	}
	@Override
	public byte getMunicion() {
		return cantidad;
	}
	@Override
	public void setMunicion(byte municion) {
		cantidad = municion;
	}
	
	public static Granada getInstancia() {
		if(granada == null) {
			granada = new Granada();
		}
		
		return granada;
	}

}
