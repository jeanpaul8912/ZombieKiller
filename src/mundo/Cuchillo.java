package mundo;

public class Cuchillo extends Arma{

	/**
	 * valor incambiable del daño que causa el cuchillo
	 */
	public static final byte DANIO = 4;
	/**
	 * Constructor del cuchillo con su respectivo daño
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
}
