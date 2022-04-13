package mundo.defenseStrategies;

import interfaz.InterfazZombieKiller;
import mundo.weapons.Arma;
import mundo.weapons.blancas.Cuchillo;
import mundo.zombies.Boss;
import mundo.zombies.Enemigo;
import mundo.zombies.Zombie;

public class SlashStrategy extends DefenseStrategy {


    private int xPosition;
    private int yPosition;
    private InterfazZombieKiller interfaz;

    public SlashStrategy(InterfazZombieKiller interfaz, int xPosition, int yPosition) {
        this.interfaz = interfaz;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    @Override
    public void executeAttack() {

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

        while (!aAcuchillar.getEstadoActual().equals(Zombie.NODO) && !seEncontro) {
            if (aAcuchillar.getEstadoActual().equals(Zombie.ATACANDO)
                    && aAcuchillar.comprobarDisparo(xPosition, yPosition, Cuchillo.DANIO)) {
                if (aAcuchillar.getEstadoActual().equals(Zombie.MURIENDO))
                    interfaz.getCampo().getPersonaje().aumentarScore(40);
                seEncontro = true;
                interfaz.getCampo().getPersonaje().setEnsangrentado(false);
                interfaz.getCampo().getPersonaje().getCuchillo().setEstado(Arma.CARGANDO);
            }
            aAcuchillar = aAcuchillar.getAtras();
        }

        if (interfaz.getCampo().getJefe() != null) {
            if (interfaz.getCampo().getJefe().getEstadoActual().equals(Enemigo.ATACANDO) &&
                    interfaz.getCampo().getJefe().comprobarDisparo(xPosition, yPosition, Cuchillo.DANIO)) {
                interfaz.getCampo().getPersonaje().setEnsangrentado(false);
                interfaz.getCampo().getPersonaje().getCuchillo().setEstado(Arma.CARGANDO);
                seEncontro = true;
                if (interfaz.getCampo().getJefe().getEstadoActual().equals(Boss.DERROTADO)) {
                    interfaz.getCampo().getPersonaje().aumentarScore(100);
                    interfaz.getCampo().setEstadoJuego(interfaz.getCampo().getSinPartida());
                }
            }
        }

        return seEncontro;
    }
}