package mundo.armas.fuego;

import mundo.armas.fuego.ammunition.Ammunition;

import static mundo.constants.ZombieKillerConstants.REMINGTON_DAMAGE;
import static mundo.constants.ZombieKillerConstants.REMINGTON_INITIAL_BACKWARD;
import static mundo.constants.ZombieKillerConstants.REMINGTON_INITIAL_BULLETS;
import static mundo.constants.ZombieKillerConstants.REMINGTON_INITIAL_RECHARGE_TIME;

public class Remington extends ArmaDeFuego {

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
	 * valor incambiable que representa el tiempo en milisegundos que tarda en
	 * cargar el arma
	 */
	public static final short TIEMPO_CARGA = 1400;
	public static final short RETROCESO = 400;
	private static Remington remington;

	/**
	 * Constructor del arma de fuego Remington con sus caracteristicas
	 */
	private Remington(Ammunition ammunition) {
		super();
		setRetroceso(REMINGTON_INITIAL_RECHARGE_TIME);
		this.ammunition = ammunition;
	}

	public static Remington getInstancia() {
		if (remington == null) {
			Ammunition ammunition = new Ammunition(REMINGTON_INITIAL_BACKWARD,
					REMINGTON_INITIAL_BULLETS, REMINGTON_DAMAGE);
			remington = new Remington(ammunition);
		}

		return remington;
	}
}
