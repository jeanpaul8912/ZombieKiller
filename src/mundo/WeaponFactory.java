package mundo;

import mundo.armas.Arma;

public interface WeaponFactory {

	Arma createWeapon(String typeWeapon);
}