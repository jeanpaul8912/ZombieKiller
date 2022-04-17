package edu.puj.pattern_design.zombie_killer.gui.thread;

import edu.puj.pattern_design.zombie_killer.gui.ZombieKillerGUI;
import edu.puj.pattern_design.zombie_killer.service.attack_strategies.AttackStrategyContext;
import edu.puj.pattern_design.zombie_killer.service.attack_strategies.BossZombieAttackStrategy;
import edu.puj.pattern_design.zombie_killer.service.camp.SurvivorCamp;
import edu.puj.pattern_design.zombie_killer.service.zombies.Boss;

import static edu.puj.pattern_design.zombie_killer.service.constants.CampConstants.PAUSADO;
import static edu.puj.pattern_design.zombie_killer.service.constants.CampConstants.SIN_PARTIDA;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.ATACANDO;

public class WeaponBoss extends Thread {

    private final ZombieKillerGUI principal;
    private final Boss jefe;
    private final SurvivorCamp campo;

    public WeaponBoss(ZombieKillerGUI principal, Boss jefe, SurvivorCamp campo) {
        this.principal = principal;
        this.jefe = jefe;
        this.campo = campo;
    }

    @Override
    public void run() {
        try {
            AttackStrategyContext attackStrategy;
            attackStrategy = new AttackStrategyContext(new BossZombieAttackStrategy());

            while (campo.getGameStatus() != SIN_PARTIDA) {
                attackStrategy.executeAttack(jefe);
                String estado = jefe.getCurrentStatus();

                if (estado.equals(ATACANDO)) {
                    if (jefe.getCurrentFrame() == 19) {
                        principal.leDaAPersonaje();
                    } else if (jefe.getCurrentFrame() == 21) {
                        attackStrategy.enemyFinishAttack(campo);
                    }
                }

                while (campo.getGameStatus() == PAUSADO) {
                    sleep(500);
                }

                sleep(jefe.getSpeed());
                principal.refrescar();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}