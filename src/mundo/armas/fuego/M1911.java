package mundo.armas.fuego;

import mundo.armas.fuego.ammunition.Ammunition;

import static mundo.constants.ZombieKillerConstants.M1911_DAMAGE;
import static mundo.constants.ZombieKillerConstants.M1911_INITIAL_BACKWARD;
import static mundo.constants.ZombieKillerConstants.M1911_INITIAL_BULLETS;
import static mundo.constants.ZombieKillerConstants.M1911_INITIAL_RECHARGE_TIME;

public class M1911 extends ArmaDeFuego {

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
	private M1911(Ammunition ammunition) {
		super();
		setRetroceso(M1911_INITIAL_RECHARGE_TIME);
		this.ammunition = ammunition;
	}

	public static M1911 getInstance() {
		if (m1911 == null) {
			Ammunition ammunition = new Ammunition(M1911_INITIAL_BACKWARD,
					M1911_INITIAL_BULLETS, M1911_DAMAGE);
			m1911 = new M1911(ammunition);
		}

		return m1911;
	}
}