package mundo.defenseStrategies;

import interfaz.InterfazZombieKiller;
import mundo.weapons.Weapon;
import mundo.weapons.whites.Knife;
import mundo.zombies.Zombie;

import static mundo.constants.ZombiesConstants.ATACANDO;
import static mundo.constants.ZombiesConstants.DERROTADO;
import static mundo.constants.ZombiesConstants.MURIENDO;
import static mundo.constants.ZombiesConstants.NODO;

public class SlashStrategy extends DefenseStrategy {

    private final int xPosition;

    private final int yPosition;

    private final InterfazZombieKiller interfaz;

    public SlashStrategy(InterfazZombieKiller interfaz, int xPosition, int yPosition) {
        this.interfaz = interfaz;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    @Override
    public void executeDefense() {

        interfaz.setCuchillo(interfaz.getCampo().getPersonaje().getCuchillo());
        if (slash()) {
            interfaz.setCursor(interfaz.getCursorCuchillo());
            interfaz.reproducir("leDioCuchillo");
            interfaz.getFacade().initializeWeaponsThread("cuchillo");
        } else if (interfaz.getCampo().getPersonaje().getPrincipal().getAvailableBullets() == 0)
            interfaz.reproducir("sin_balas");
    }


    public boolean slash() {
        Zombie aAcuchillar = interfaz.getCampo().getZombNodoCercano().getAtras();
        boolean seEncontro = false;

        while (!aAcuchillar.getEstadoActual().equals(NODO) && !seEncontro) {
            if (aAcuchillar.getEstadoActual().equals(ATACANDO)
                    && aAcuchillar.comprobarDisparo(xPosition, yPosition, Knife.DANIO)) {
                if (aAcuchillar.getEstadoActual().equals(MURIENDO))
                    interfaz.getCampo().getPersonaje().aumentarScore(40);
                seEncontro = true;
                interfaz.getCampo().getPersonaje().setEnsangrentado(false);
                interfaz.getCampo().getPersonaje().getCuchillo().setEstado(Weapon.CARGANDO);
            }
            aAcuchillar = aAcuchillar.getAtras();
        }

        if (interfaz.getCampo().getJefe() != null) {
            if (interfaz.getCampo().getJefe().getEstadoActual().equals(ATACANDO) &&
                    interfaz.getCampo().getJefe().comprobarDisparo(xPosition, yPosition, Knife.DANIO)) {
                interfaz.getCampo().getPersonaje().setEnsangrentado(false);
                interfaz.getCampo().getPersonaje().getCuchillo().setEstado(Weapon.CARGANDO);
                seEncontro = true;
                if (interfaz.getCampo().getJefe().getEstadoActual().equals(DERROTADO)) {
                    interfaz.getCampo().getPersonaje().aumentarScore(100);
                    interfaz.getCampo().setEstadoJuego(interfaz.getCampo().getSinPartida());
                }
            }
        }

        return seEncontro;
    }
}