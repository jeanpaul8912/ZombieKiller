package edu.puj.pattern_design.zombie_killer.service.zombies;

import java.util.Formatter;

import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.ATACANDO;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.CAMINANDO;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.GRUNIENDO;

public class WalkerZombie extends ZombieZigZag {

    public WalkerZombie() {

    }

    @Override
    public String getURL(int level) {
        try (Formatter formatter = new Formatter()) {
            String round = "/";

            if (level >= 2 && (getCurrentStatus().equals(ATACANDO) || getCurrentStatus().equals(GRUNIENDO))) {
                round += "knife/";
            } else if (level >= 3 && getCurrentStatus().equals(CAMINANDO)) {
                round += "shield/";
            }

            return "/img/" + getClass().getSimpleName() + "/" + getCurrentStatus() + round
                    + formatter.format("%02d", getCurrentFrame()) + ".png";
        }
    }

    public WalkerZombie(int posX, int posY, int direccionX, int direccionY, String estadoActual, byte frameActual,
                        byte salud, int ronda) {
        super(posX, posY, direccionX, direccionY, estadoActual, frameActual, salud, ronda);
    }

}
