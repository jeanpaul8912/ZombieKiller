package edu.puj.pattern_design.zombie_killer.service.defense_strategies;

import edu.puj.pattern_design.zombie_killer.gui.ZombieKillerGUI;
import edu.puj.pattern_design.zombie_killer.service.zombies.Zombie;

import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.CARGANDO;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.MURIENDO;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.MURIENDO_INCENDIADO;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.NODO;

public class ThrowGrenadeStrategy extends DefenseStrategy {

    private final ZombieKillerGUI interfaz;

    public ThrowGrenadeStrategy(ZombieKillerGUI interfaz) {
        this.interfaz = interfaz;
    }

    @Override
    public void executeDefense() {

        throwGranada();
        interfaz.setGrenade(interfaz.getCamp().getCharacter().getGrenades());
        interfaz.getFacade().initializeWeaponsThread("granada");
        interfaz.reproducir("bomba");
    }

    public void throwGranada() {
        Zombie actual = interfaz.getCamp().getZombieNearNode().getInBack();
        interfaz.getCamp().getCharacter().setBlooded(false);
        while (!actual.getEstadoActual().equals(NODO)) {

            if (!actual.getEstadoActual().equals(MURIENDO) && !actual.getEstadoActual().equals(MURIENDO_INCENDIADO)) {
                actual.setEstadoActual(MURIENDO_INCENDIADO);
                interfaz.getCamp().getCharacter().increaseScore(50);
                actual = actual.getInBack();
            }
        }

        interfaz.getCamp().getCharacter().getGrenades().shoot();
        interfaz.getCamp().getCharacter().getGrenades().setEstado(CARGANDO);
    }
}