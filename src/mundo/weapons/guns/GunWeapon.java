package mundo.weapons.guns;

import mundo.weapons.Weapon;
import mundo.weapons.guns.ammunition.Ammunition;

public abstract class GunWeapon extends Weapon {

	private static final long serialVersionUID = 1L;
	/**
	 * Cadena de caracteres incambaible que representa el estado de recalentamiento
	 * o recarga de un arma de efuego
	 */
	public static final String RECARGANDO = "recarga";

	/**
	 * estado temporal para dibujar la sangre del zombie en pantalla
	 */
	private boolean ensangrentada;

	protected Ammunition ammunition;

	/**
	 * Constructor abstracto del arma de fuego
	 */
	public GunWeapon() {
	}

	@Override
	public long calcularDescanso() {
		long descanso = 0;

		if (getEstado().equals(RECARGANDO)) {
			descanso = ammunition.getRechargeTime();
		} else if (getEstado().equals(CARGANDO)) {
			descanso = getRetroceso();
		}

		return descanso;
	}

	/**
	 * pregunta si el arma presente acaba de darle a algun enemigo
	 *
	 * @return ensangrentada
	 */
	public boolean isEnsangrentada() {
		return ensangrentada;
	}

	/**
	 * cambia el estado ensangrentado
	 *
	 * @param ensangrentada
	 */
	public void setEnsangrentada(boolean ensangrentada) {
		this.ensangrentada = ensangrentada;
	}

	public void shoot() {
		ammunition.setAvailableBullets(ammunition.getAvailableBullets() - 1);
	}

	public void reload() {
		ammunition.reload();
	}

	public int getMaxBullets() {
		return ammunition.getMaxBullets();
	}

	public int getAvailableBullets() {
		return ammunition.getAvailableBullets();
	}

	public void improveGun() {
		ammunition.improveAmmunition();
	}

	@Override
	public int getDamage() {
		return ammunition.getDamage();
	}

	public void setAmmunition(Ammunition ammunition) {
		this.ammunition = ammunition;
	}
}
