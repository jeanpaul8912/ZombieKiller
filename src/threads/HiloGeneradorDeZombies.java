package threads;

import interfaz.InterfazZombieKiller;
import mundo.camp.SurvivorCamp;

import static mundo.constants.CampConstants.EN_CURSO;
import static mundo.constants.CampConstants.PAUSADO;
import static mundo.constants.CampConstants.SIN_PARTIDA;
import static mundo.constants.ZombiesConstants.MURIENDO_INCENDIADO;
import static mundo.constants.ZombiesConstants.NODO;
import static mundo.constants.ZombiesConstants.NUMERO_ZOMBIES_RONDA;

public class HiloGeneradorDeZombies extends Thread {

    private final InterfazZombieKiller principal;
    private final SurvivorCamp campo;

    public HiloGeneradorDeZombies(InterfazZombieKiller principal, SurvivorCamp campo) {
        this.principal = principal;
        this.campo = campo;
    }

    @Override
    public void run() {
        try {
            while (principal.estaCargando()) {
                sleep(5000);
            }

            principal.cambiarPuntero();
            int contadorZombiesPorNivel = campo.getCantidadZombiesGenerados();
            int nivel = campo.getRondaActual();

            while (campo.getEstadoJuego() != SIN_PARTIDA) {
                if (contadorZombiesPorNivel % NUMERO_ZOMBIES_RONDA == 0) { // Si se terminan los Zombies
                    while (!campo.getZombNodoLejano().getAlFrente().getEstadoActual().equals(NODO) && campo.getPersonaje().getSalud() > 0) {
                        sleep(1000);
                    }

                    // Si aun existe partida , subir nivel e iniciar
                    if (campo.getEstadoJuego() != SIN_PARTIDA) {
                        while (campo.getEstadoJuego() == PAUSADO) {
                            sleep(500);
                        }

                        nivel++;
                        principal.subirDeRonda(nivel);
                        sleep(2000);
                        principal.iniciarGemi2();
                        campo.setEstadoJuego(EN_CURSO);
                    }
                }

                if (nivel < 10 && campo.getEstadoJuego() != SIN_PARTIDA) {
                    if (!campo.getZombNodoLejano().getAlFrente().getEstadoActual().equals(MURIENDO_INCENDIADO))
                        principal.generarZombie(nivel);
                    contadorZombiesPorNivel++;
                    sleep(1400);
                } else if (nivel == 10) {
                    principal.generarBoss();

                    while (campo.getEstadoJuego() != SIN_PARTIDA) {
                        sleep(500);
                    }
                }

                while (campo.getEstadoJuego() == PAUSADO) {
                    sleep(500);
                }
            }

            if (campo.getPersonaje().getSalud() <= 0) {
                principal.reproducir("meMuero");
                principal.juegoTerminado();
            } else if (campo.getJefe() != null && campo.getJefe().getSalud() <= 0) {
                principal.victoria();
            }
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }
}
