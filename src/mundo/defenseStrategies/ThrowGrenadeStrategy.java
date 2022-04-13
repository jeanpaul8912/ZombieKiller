package mundo.defenseStrategies;

import interfaz.InterfazZombieKiller;
import mundo.weapons.Weapon;
import mundo.zombies.Zombie;

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
        while (!actual.getEstadoActual().equals(Zombie.NODO)) {

            if (!actual.getEstadoActual().equals(Zombie.MURIENDO) && !actual.getEstadoActual().equals(Zombie.MURIENDO_INCENDIADO)) {
                actual.setEstadoActual(Zombie.MURIENDO_INCENDIADO);
                interfaz.getCampo().getPersonaje().aumentarScore(50);
                actual = actual.getAtras();
            }
        }

        interfaz.getCampo().getPersonaje().getGranadas().shoot();
        interfaz.getCampo().getPersonaje().getGranadas().setEstado(Weapon.CARGANDO);
    }
}