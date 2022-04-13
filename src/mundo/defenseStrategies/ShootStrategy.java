package mundo.defenseStrategies;

import interfaz.InterfazZombieKiller;
import mundo.Boss;
import mundo.Zombie;
import mundo.armas.fuego.ArmaDeFuego;

public class ShootStrategy extends DefenseStrategy {

    private int xPosition;
    private int yPosition;
    private InterfazZombieKiller interfaz;

    public ShootStrategy(InterfazZombieKiller interfaz, int xPosition, int yPosition) {
        this.interfaz = interfaz;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    @Override
    public void executeAttack() {

        if (shoot()) {
            interfaz.reproducir("leDio" + interfaz.getCampo().getPersonaje().getPrincipal().getClass().getSimpleName());
        } else {
            interfaz.reproducir("disparo" + interfaz.getCampo().getPersonaje().getPrincipal().getClass().getSimpleName());
        }
        
        interfaz.getPanelCampo().incorporarJefe(interfaz.getBoss());
        interfaz.getFacade().initializeWeaponsThread("armaDeFuego");
    }


    public boolean shoot() {
        interfaz.getCampo().getPersonaje().getPrincipal().shoot();
        interfaz.getCampo().getPersonaje().getPrincipal().setEstado(ArmaDeFuego.RECARGANDO);
        boolean leDio = false;
        Zombie actual = interfaz.getCampo().getZombNodoCercano().getAtras();

        while (!actual.getEstadoActual().equals(Zombie.NODO) && !leDio) {
            if (actual.comprobarDisparo(xPosition, yPosition, interfaz.getCampo().getPersonaje().getPrincipal().getDanio())) {
                leDio = true;
                interfaz.getCampo().getPersonaje().getPrincipal().setEnsangrentada(true);
                if (actual.getSalud() <= 0) {
                    interfaz.getCampo().getPersonaje().aumentarScore(10 + actual.getSalud() * (-10));
                    if (actual.getEstadoActual().equals(Zombie.MURIENDO_HEADSHOT))
                        interfaz.getCampo().getPersonaje().aumentarTirosALaCabeza();
                }

                interfaz.getCampo().getPersonaje().setEnsangrentado(false);
            }
            actual = actual.getAtras();
        }

        if (interfaz.getCampo().getJefe() != null)
            if (interfaz.getCampo().getJefe().comprobarDisparo(xPosition, yPosition, interfaz.getCampo().getPersonaje().getPrincipal().getDanio())) {
                interfaz.getCampo().getPersonaje().getPrincipal().setEnsangrentada(true);
                interfaz.getCampo().getPersonaje().setEnsangrentado(false);
                leDio = true;
                if (interfaz.getCampo().getJefe().getEstadoActual().equals(Boss.DERROTADO)) {
                    interfaz.getCampo().getPersonaje().aumentarScore(20 + interfaz.getCampo().getJefe().getSalud() * (-20));
                    interfaz.getCampo().setEstadoJuego(interfaz.getCampo().getSinPartida());
                }
            }

        return leDio;
    }
}