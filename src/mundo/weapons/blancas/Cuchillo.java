package mundo.weapons.blancas;

import mundo.weapons.Arma;

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
		setRetroceso(200);
		setDanio(DANIO);
	}

	public static Cuchillo getInstancia() {
		if (cuchillo == null) {
			cuchillo = new Cuchillo();
		}

		return cuchillo;
	}

}