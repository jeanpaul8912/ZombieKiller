package mundo.defenseStrategies;

import interfaz.InterfazZombieKiller;
import mundo.weapons.Weapon;
import mundo.zombies.Zombie;

import static mundo.constants.ZombiesConstants.MURIENDO;
import static mundo.constants.ZombiesConstants.MURIENDO_INCENDIADO;
import static mundo.constants.ZombiesConstants.NODO;

public class ThrowGrenadeStrategy extends DefenseStrategy {

    private final InterfazZombieKiller interfaz;

    public ThrowGrenadeStrategy(InterfazZombieKiller interfaz) {
        this.interfaz = interfaz;
    }

    @Override
    public void executeDefense() {

        throwGranada();
        interfaz.setGranada(interfaz.getCampo().getPersonaje().getGranadas());
        interfaz.getFacade().initializeWeaponsThread("granada");
        interfaz.reproducir("bomba");
    }

    public void throwGranada() {
        Zombie actual = interfaz.getCampo().getZombNodoCercano().getAtras();
        interfaz.getCampo().getPersonaje().setEnsangrentado(false);
        while (!actual.getEstadoActual().equals(NODO)) {

            if (!actual.getEstadoActual().equals(MURIENDO) && !actual.getEstadoActual().equals(MURIENDO_INCENDIADO)) {
                actual.setEstadoActual(MURIENDO_INCENDIADO);
                interfaz.getCampo().getPersonaje().aumentarScore(50);
                actual = actual.getAtras();
            }
        }

        interfaz.getCampo().getPersonaje().getGranadas().shoot();
        interfaz.getCampo().getPersonaje().getGranadas().setEstado(Weapon.CARGANDO);
    }
}