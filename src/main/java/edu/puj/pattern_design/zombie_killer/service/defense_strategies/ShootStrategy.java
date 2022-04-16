package edu.puj.pattern_design.zombie_killer.service.defense_strategies;

import edu.puj.pattern_design.zombie_killer.gui.ZombieKillerGUI;
import edu.puj.pattern_design.zombie_killer.service.zombies.Zombie;

import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.DERROTADO;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.MURIENDO_HEADSHOT;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.NODO;
import static edu.puj.pattern_design.zombie_killer.service.weapons.guns.GunWeapon.RECARGANDO;

public class ShootStrategy extends DefenseStrategy {

    private final int xPosition;
    private final int yPosition;
    private final ZombieKillerGUI interfaz;

    public ShootStrategy(ZombieKillerGUI interfaz, int xPosition, int yPosition) {
        this.interfaz = interfaz;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    @Override
    public void executeDefense() {
        if (shoot()) {
            interfaz.reproducir("leDio" + interfaz.getCampo().getPersonaje().getPrincipalWeapon().getClass().getSimpleName());
        } else {
            interfaz.reproducir("disparo" + interfaz.getCampo().getPersonaje().getPrincipalWeapon().getClass().getSimpleName());
        }

        interfaz.getPanelCampo().incorporarJefe(interfaz.getBoss());
        interfaz.getFacade().initializeWeaponsThread("armaDeFuego");
    }


    public boolean shoot() {
        interfaz.getCampo().getPersonaje().getPrincipalWeapon().shoot();
        interfaz.getCampo().getPersonaje().getPrincipalWeapon().setEstado(RECARGANDO);
        boolean leDio = false;
        Zombie actual = interfaz.getCampo().getZombNodoCercano().getAtras();

        while (!actual.getEstadoActual().equals(NODO) && !leDio) {
            if (actual.comprobarDisparo(xPosition, yPosition, interfaz.getCampo().getPersonaje().getPrincipalWeapon().getDamage())) {
                leDio = true;
                interfaz.getCampo().getPersonaje().getPrincipalWeapon().setEnsangrentada(true);

                if (actual.getHealth() <= 0) {
                    interfaz.getCampo().getPersonaje().increaseScore(10 + actual.getHealth() * (-10));

                    if (actual.getEstadoActual().equals(MURIENDO_HEADSHOT)) {
                        interfaz.getCampo().getPersonaje().increaseHeadShoots();
                    }
                }

                interfaz.getCampo().getPersonaje().setBlooded(false);
            }

            actual = actual.getAtras();
        }

        if (interfaz.getCampo().getJefe() != null)
            if (interfaz.getCampo().getJefe().comprobarDisparo(xPosition, yPosition, interfaz.getCampo().getPersonaje().getPrincipalWeapon().getDamage())) {
                interfaz.getCampo().getPersonaje().getPrincipalWeapon().setEnsangrentada(true);
                interfaz.getCampo().getPersonaje().setBlooded(false);
                leDio = true;

                if (interfaz.getCampo().getJefe().getEstadoActual().equals(DERROTADO)) {
                    interfaz.getCampo().getPersonaje().increaseScore(20 + interfaz.getCampo().getJefe().getHealth() * (-20));
                    interfaz.getCampo().setEstadoJuego(interfaz.getCampo().getSinPartida());
                }
            }

        return leDio;
    }
}