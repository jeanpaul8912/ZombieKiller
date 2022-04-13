package mundo.weapons.guns;

public interface WeaponWithAmmunition {

	/**
	 * obtiene la municion del arma en cuestion
	 *
	 * @return municion
	 */
	byte getMunicion();

	/**
	 * cambia la municion del arma presente
	 *
	 * @param municion
	 */
	void setMunicion(byte municion);
}
