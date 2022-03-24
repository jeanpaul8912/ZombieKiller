package mundo;

public class Cuchillo extends Arma {

	private static final long serialVersionUID = 1L;
	private static Cuchillo cuchillo;
	/**
	 * valor incambiable del danio que causa el cuchillo
	 */
	public static final byte DANIO = 4;
	/**
	 * Constructor del cuchillo con su respectivo danio
	 */
	public Cuchillo() {
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
	
	public static Cuchillo getInstancia() {
		if(cuchillo == null) {
			cuchillo = new Cuchillo();
		}
		
		return cuchillo;
	}
}