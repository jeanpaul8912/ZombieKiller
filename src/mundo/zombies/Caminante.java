package mundo.zombies;

import java.util.Formatter;

import static mundo.constants.ZombiesConstants.ATACANDO;
import static mundo.constants.ZombiesConstants.CAMINANDO;
import static mundo.constants.ZombiesConstants.GRUNIENDO;

public class Caminante extends ZombieZigZag {

    public Caminante() {

    }

    @Override
    public String getURL(int level) {
        try (Formatter formatter = new Formatter()) {
            String round = "/";

            if (level >= 2 && (getEstadoActual().equals(ATACANDO) || getEstadoActual().equals(GRUNIENDO))) {
                round += "knife/";
            } else if (level >= 3 && getEstadoActual().equals(CAMINANDO)) {
                round += "shield/";
            }

            return "/img/" + getClass().getSimpleName() + "/" + getEstadoActual() + round
                    + formatter.format("%02d", getFrameActual()) + ".png";
        }
    }

    public Caminante(int posX, int posY, int direccionX, int direccionY, String estadoActual, byte frameActual,
                     byte salud, int ronda) {
        super(posX, posY, direccionX, direccionY, estadoActual, frameActual, salud, ronda);
    }

    @Override
    public String getImageUrl() {
        return null;
    }

}
