package edu.puj.pattern_design.zombie_killer.service.defense_strategies;

import edu.puj.pattern_design.zombie_killer.gui.ZombieKillerGUI;
import edu.puj.pattern_design.zombie_killer.service.zombies.Zombie;

import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.RECHARGING;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.DERROTADO;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.MURIENDO_HEADSHOT;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.NODO;

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
            interfaz.reproducir("leDio" + interfaz.getCamp().getCharacter().getPrincipalWeapon().getClass().getSimpleName());
        } else {
            interfaz.reproducir("disparo" + interfaz.getCamp().getCharacter().getPrincipalWeapon().getClass().getSimpleName());
        }

        interfaz.getSurvivorCampoPanel().incorporarJefe(interfaz.getBoss());
        interfaz.getFacade().initializeWeaponsThread("armaDeFuego");
    }


    public boolean shoot() {
        interfaz.getCamp().getCharacter().getPrincipalWeapon().shoot();
        interfaz.getCamp().getCharacter().getPrincipalWeapon().setEstado(RECHARGING);
        boolean leDio = false;
        Zombie actual = interfaz.getCamp().getZombieNearNode().getInBack();

        while (!actual.getCurrentStatus().equals(NODO) && !leDio) {
            if (actual.checkShoot(xPosition, yPosition, interfaz.getCamp().getCharacter().getPrincipalWeapon().getDamage())) {
                leDio = true;
                interfaz.getCamp().getCharacter().getPrincipalWeapon().setBlooded(true);

                if (actual.getHealth() <= 0) {
                    interfaz.getCamp().getCharacter().increaseScore(10 + actual.getHealth() * (-10));

                    if (actual.getCurrentStatus().equals(MURIENDO_HEADSHOT)) {
                        interfaz.getCamp().getCharacter().increaseHeadShoots();
                    }
                }

                interfaz.getCamp().getCharacter().setBlooded(false);
            }

            actual = actual.getInBack();
        }

        if (interfaz.getCamp().getBoss() != null)
            if (interfaz.getCamp().getBoss().checkShoot(xPosition, yPosition, interfaz.getCamp().getCharacter().getPrincipalWeapon().getDamage())) {
                interfaz.getCamp().getCharacter().getPrincipalWeapon().setBlooded(true);
                interfaz.getCamp().getCharacter().setBlooded(false);
                leDio = true;

                if (interfaz.getCamp().getBoss().getCurrentStatus().equals(DERROTADO)) {
                    interfaz.getCamp().getCharacter().increaseScore(20 + interfaz.getCamp().getBoss().getHealth() * (-20));
                    interfaz.getCamp().setGameStatus(interfaz.getCamp().getSinPartida());
                }
            }

        return leDio;
    }
}