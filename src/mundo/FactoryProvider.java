package mundo;

public class FactoryProvider {
	public static AbstractFactoryWeapon getFactory(String choice) {

		if ("armaSinMunicion".equalsIgnoreCase(choice)) {
			return new ArmaSinMunicionFactory();
		} else if ("armaConMunicion".equalsIgnoreCase(choice)) {
			return new ArmaConMunicionFactory();
		} else {
			throw new IllegalStateException("Unexpected value: " + choice);
		}
	}
}