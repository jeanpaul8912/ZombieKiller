package edu.puj.pattern_design.zombie_killer.gui.thread;

import edu.puj.pattern_design.zombie_killer.gui.ZombieKillerGUI;
import edu.puj.pattern_design.zombie_killer.service.attack_strategies.AttackStrategyContext;
import edu.puj.pattern_design.zombie_killer.service.attack_strategies.BossAttackStrategy;
import edu.puj.pattern_design.zombie_killer.service.camp.SurvivorCamp;
import edu.puj.pattern_design.zombie_killer.service.zombies.Boss;

import static edu.puj.pattern_design.zombie_killer.service.constants.CampConstants.PAUSADO;
import static edu.puj.pattern_design.zombie_killer.service.constants.CampConstants.SIN_PARTIDA;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.ATACANDO;

public class HiloBoss extends Thread {

    private final ZombieKillerGUI principal;
    private final Boss jefe;
    private final SurvivorCamp campo;

    public HiloBoss(ZombieKillerGUI principal, Boss jefe, SurvivorCamp campo) {
        this.principal = principal;
        this.jefe = jefe;
        this.campo = campo;
    }

    @Override
    public void run() {
        try {
            AttackStrategyContext attackStrategy;
            attackStrategy = new AttackStrategyContext(new BossAttackStrategy());

            while (campo.getEstadoJuego() != SIN_PARTIDA) {
                attackStrategy.executeAttack(jefe);
                String estado = jefe.getEstadoActual();

                if (estado.equals(ATACANDO)) {
                    if (jefe.getFrameActual() == 19) {
                        principal.leDaAPersonaje();
                    } else if (jefe.getFrameActual() == 21) {
                        attackStrategy.enemigoTerminaSuGolpe(campo);
                    }
                }

                while (campo.getEstadoJuego() == PAUSADO) {
                    sleep(500);
                }

                sleep(jefe.getLentitud());
                principal.refrescar();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}