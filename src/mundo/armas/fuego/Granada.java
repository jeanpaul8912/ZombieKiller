package mundo.armas.fuego;

import mundo.armas.fuego.ammunition.Ammunition;
import mundo.armas.fuego.ammunition.GrenadeAmmunition;

import static mundo.constants.ZombieKillerConstants.GREANDE_INITIAL_RECHARGE_TIME;
import static mundo.constants.ZombieKillerConstants.GRENADE_DAMAGE;
import static mundo.constants.ZombieKillerConstants.GRENADE_INITIAL_BULLETS;

public class Granada extends ArmaDeFuego {

	private static final long serialVersionUID = 1L;

	private static Granada granada;

	/**
	 * Constructor de la granada con su respectiva cantidad y danio
	 */
	private Granada(Ammunition ammunition) {
		super();
		setRetroceso(0);
		this.ammunition = ammunition;
	}

	public static Granada getInstance() {
		if (granada == null) {
			Ammunition ammunition = new GrenadeAmmunition(GREANDE_INITIAL_RECHARGE_TIME,
					GRENADE_INITIAL_BULLETS, GRENADE_DAMAGE);
			granada = new Granada(ammunition);
		}

		return granada;
	}

	@Override
	public void improveGun() {
		ammunition.improveAmmunition();
	}

}
