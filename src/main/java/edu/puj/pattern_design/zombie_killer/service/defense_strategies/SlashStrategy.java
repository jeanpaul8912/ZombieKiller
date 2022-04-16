package edu.puj.pattern_design.zombie_killer.service.defense_strategies;

import edu.puj.pattern_design.zombie_killer.gui.ZombieKillerGUI;
import edu.puj.pattern_design.zombie_killer.service.zombies.Zombie;
import org.apache.commons.lang3.math.NumberUtils;

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
        interfaz.setCuchillo(interfaz.getCampo().getPersonaje().getKnife());

        if (slash()) {
            interfaz.setCursor(interfaz.getCursorCuchillo());
            interfaz.reproducir("leDioCuchillo");
            interfaz.getFacade().initializeWeaponsThread("cuchillo");
        } else if (interfaz.getCampo().getPersonaje().getPrincipalWeapon().getAvailableBullets() == NumberUtils.INTEGER_ZERO) {
            interfaz.reproducir("sin_balas");
        }
    }


    public boolean slash() {
        Zombie aAcuchillar = interfaz.getCampo().getZombNodoCercano().getAtras();
        boolean seEncontro = false;

        while (!aAcuchillar.getEstadoActual().equals(NODO) && !seEncontro) {
            if (aAcuchillar.getEstadoActual().equals(ATACANDO)
                    && aAcuchillar.comprobarDisparo(xPosition, yPosition, KNIFE_DAMAGE)) {
                if (aAcuchillar.getEstadoActual().equals(MURIENDO))
                    interfaz.getCampo().getPersonaje().increaseScore(40);
                seEncontro = true;
                interfaz.getCampo().getPersonaje().setBlooded(false);
                interfaz.getCampo().getPersonaje().getKnife().setEstado(CARGANDO);
            }
            aAcuchillar = aAcuchillar.getAtras();
        }

        if (interfaz.getCampo().getJefe() != null) {
            if (interfaz.getCampo().getJefe().getEstadoActual().equals(ATACANDO) &&
                    interfaz.getCampo().getJefe().comprobarDisparo(xPosition, yPosition, KNIFE_DAMAGE)) {
                interfaz.getCampo().getPersonaje().setBlooded(false);
                interfaz.getCampo().getPersonaje().getKnife().setEstado(CARGANDO);
                seEncontro = true;

                if (interfaz.getCampo().getJefe().getEstadoActual().equals(DERROTADO)) {
                    interfaz.getCampo().getPersonaje().increaseScore(100);
                    interfaz.getCampo().setEstadoJuego(interfaz.getCampo().getSinPartida());
                }
            }
        }

        return seEncontro;
    }
}