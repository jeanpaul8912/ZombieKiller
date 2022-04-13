package mundo.weapons;

import mundo.weapons.blancas.Cuchillo;
import mundo.weapons.fuego.Granada;
import mundo.weapons.fuego.M1911;
import mundo.weapons.fuego.Remington;

public class WeaponFactoryImpl implements WeaponFactory {

	@Override
	public Arma createWeapon(String typeWeapon) {
		if (typeWeapon.equalsIgnoreCase("granada")) {
			return Granada.getInstance();
		} else if (typeWeapon.equalsIgnoreCase("remington")) {
			return Remington.getInstancia();
		} else if (typeWeapon.equalsIgnoreCase("m1911")) {
			return M1911.getInstance();
		} else if (typeWeapon.equalsIgnoreCase("cuchillo")) {
			return Cuchillo.getInstancia();
		} else {
			throw new IllegalStateException("Unexpected value: " + typeWeapon);
		}
	}
}