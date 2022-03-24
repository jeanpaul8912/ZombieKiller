package mundo;

public class WeaponFactoryImpl implements WeaponFactory {

	@Override
	public Arma createWeapon(String typeWeapon) {
		if (typeWeapon.equalsIgnoreCase("granada")) {
			return Granada.getInstancia();
		} else if (typeWeapon.equalsIgnoreCase("remington")) {
			return Remington.getInstancia();
		} else if (typeWeapon.equalsIgnoreCase("m1911")) {
			return M1911.getInstancia();
		} else if (typeWeapon.equalsIgnoreCase("cuchillo")) {
			return Cuchillo.getInstancia();
		} else {
			throw new IllegalStateException("Unexpected value: " + typeWeapon);
		}
	}
}