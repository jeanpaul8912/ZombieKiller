package mundo;

public class ArmaConMunicionFactory implements AbstractFactoryWeapon {

	@Override
	public Arma createWeapon(String typeWeapon) {
		if (typeWeapon.equalsIgnoreCase("granada")) {
			return new Granada();
		} else if (typeWeapon.equalsIgnoreCase("remington")) {
			return new Remington();
		} else if (typeWeapon.equalsIgnoreCase("m1911")) {
			return new M1911();
		} else {
			throw new IllegalStateException("Unexpected value: " + typeWeapon);
		}
	}
}