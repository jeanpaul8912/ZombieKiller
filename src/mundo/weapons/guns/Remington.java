package mundo.weapons.guns;

import mundo.weapons.guns.ammunition.Ammunition;

import static mundo.constants.ZombieKillerConstants.REMINGTON_DAMAGE;
import static mundo.constants.ZombieKillerConstants.REMINGTON_INITIAL_BACKWARD;
import static mundo.constants.ZombieKillerConstants.REMINGTON_INITIAL_BULLETS;
import static mundo.constants.ZombieKillerConstants.REMINGTON_INITIAL_RECHARGE_TIME;

public class Remington extends GunWeapon {

    private static final long serialVersionUID = 1L;

    /**
     * valor incambiable que representa el alcance de la escopeta
     */
    public static final int RANGO = 100;

    private static Remington remington;

    /**
     * Constructor del arma de fuego Remington con sus caracteristicas
     */
    private Remington() {
        super();
        setRetroceso(REMINGTON_INITIAL_RECHARGE_TIME);
    }

    public static Remington getInstancia() {
        if (remington == null) {
            remington = new Remington();
        }

        Ammunition ammunition = new Ammunition(REMINGTON_INITIAL_BACKWARD,
                REMINGTON_INITIAL_BULLETS, REMINGTON_DAMAGE);
        remington.setAmmunition(ammunition);
        return remington;
    }
}
