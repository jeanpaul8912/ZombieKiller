package mundo.weapons.whites;

import mundo.weapons.Weapon;

public class Knife extends Weapon {

	private static final long serialVersionUID = 1L;

	private static Knife knife;
	/**
	 * valor incambiable del danio que causa el cuchillo
	 */
	public static final byte DANIO = 4;

	/**
	 * Constructor del cuchillo con su respectivo danio
	 */
	public Knife() {
		setRetroceso(200);
		setDamage(DANIO);
	}

	public static Knife getInstancia() {
		if (knife == null) {
			knife = new Knife();
		}

		return knife;
	}

}