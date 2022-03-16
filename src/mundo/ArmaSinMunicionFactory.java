package mundo;

public class ArmaSinMunicionFactory implements AbstractFactoryWeapon {

	@Override
	public Arma createWeapon(String typeWeapon) {

		if (typeWeapon.equalsIgnoreCase("cuchillo")) {
			return new Cuchillo();
		} else {
			throw new IllegalStateException("Unexpected value: " + typeWeapon);
		}
	}
}