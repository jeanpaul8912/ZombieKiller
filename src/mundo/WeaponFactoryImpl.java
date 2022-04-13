package mundo;

import mundo.armas.Arma;
import mundo.armas.blancas.Cuchillo;
import mundo.armas.fuego.Granada;
import mundo.armas.fuego.M1911;
import mundo.armas.fuego.Remington;

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