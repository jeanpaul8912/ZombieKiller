package edu.puj.pattern_design.zombie_killer.service.defense_strategies;

import edu.puj.pattern_design.zombie_killer.gui.ZombieKillerGUI;
import edu.puj.pattern_design.zombie_killer.service.zombies.Zombie;
import org.apache.commons.lang3.math.NumberUtils;

import static edu.puj.pattern_design.zombie_killer.service.constants.SurvivorCampConstants.SIN_PARTIDA;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.CARGANDO;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.KNIFE_DAMAGE;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.ATACANDO;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.DERROTADO;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.MURIENDO;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.NODO;

public class SlashStrategy extends DefenseStrategy {

    private final int xPosition;

    private final int yPosition;

    private final ZombieKillerGUI interfaz;

    public SlashStrategy(ZombieKillerGUI interfaz, int xPosition, int yPosition) {
        this.interfaz = interfaz;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    @Override
    public void executeDefense() {
        interfaz.setKnife(interfaz.getCamp().getCharacter().getKnife());

        if (slash()) {
            interfaz.setCursor(interfaz.getKnifeCursor());
            interfaz.reproduceSound("leDioCuchillo");
            interfaz.getFacade().initializeWeaponsThread("cuchillo");
        } else if (interfaz.getCamp().getCharacter().getPrincipalWeapon().getAvailableBullets() == NumberUtils.INTEGER_ZERO) {
            interfaz.reproduceSound("sin_balas");
        }
    }


    public boolean slash() {
        Zombie aAcuchillar = interfaz.getCamp().getZombieNearNode().getInBack();
        boolean seEncontro = false;

        while (!aAcuchillar.getCurrentStatus().equals(NODO) && !seEncontro) {
            if (aAcuchillar.getCurrentStatus().equals(ATACANDO)
                    && aAcuchillar.checkShoot(xPosition, yPosition, KNIFE_DAMAGE)) {
                if (aAcuchillar.getCurrentStatus().equals(MURIENDO))
                    interfaz.getCamp().getCharacter().increaseScore(40);
                seEncontro = true;
                interfaz.getCamp().getCharacter().setBlooded(false);
                interfaz.getCamp().getCharacter().getKnife().setStatus(CARGANDO);
            }
            aAcuchillar = aAcuchillar.getInBack();
        }

        if (interfaz.getCamp().getBoss() != null) {
            if (interfaz.getCamp().getBoss().getCurrentStatus().equals(ATACANDO) &&
                    interfaz.getCamp().getBoss().checkShoot(xPosition, yPosition, KNIFE_DAMAGE)) {
                interfaz.getCamp().getCharacter().setBlooded(false);
                interfaz.getCamp().getCharacter().getKnife().setStatus(CARGANDO);
                seEncontro = true;

                if (interfaz.getCamp().getBoss().getCurrentStatus().equals(DERROTADO)) {
                    interfaz.getCamp().getCharacter().increaseScore(100);
                    interfaz.getCamp().setGameStatus(SIN_PARTIDA);
                }
            }
        }

        return seEncontro;
    }
}