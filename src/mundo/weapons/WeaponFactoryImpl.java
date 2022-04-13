package mundo.weapons;

import mundo.weapons.guns.Grenade;
import mundo.weapons.guns.M1911;
import mundo.weapons.guns.Remington;
import mundo.weapons.whites.Knife;

public class WeaponFactoryImpl implements WeaponFactory {

	@Override
	public Weapon createWeapon(String typeWeapon) {
		if (typeWeapon.equalsIgnoreCase("granada")) {
			return Grenade.getInstance();
		} else if (typeWeapon.equalsIgnoreCase("remington")) {
			return Remington.getInstancia();
		} else if (typeWeapon.equalsIgnoreCase("m1911")) {
			return M1911.getInstance();
		} else if (typeWeapon.equalsIgnoreCase("cuchillo")) {
			return Knife.getInstancia();
		} else {
			throw new IllegalStateException("Unexpected value: " + typeWeapon);
		}
	}
}