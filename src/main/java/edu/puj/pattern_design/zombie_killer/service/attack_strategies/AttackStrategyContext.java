package edu.puj.pattern_design.zombie_killer.service.attack_strategies;

import edu.puj.pattern_design.zombie_killer.service.camp.SurvivorCamp;
import edu.puj.pattern_design.zombie_killer.service.zombies.Enemy;

public class AttackStrategyContext {

    private final AttackStrategy attackStrategy;

    public AttackStrategyContext(AttackStrategy attackStrategy) {
        this.attackStrategy = attackStrategy;
    }

    public void executeAttack(Enemy enemy) {
        this.attackStrategy.executeAttack(enemy);
    }

    public void terminaDeAtacar() {
        this.attackStrategy.terminaDeAtacar();
    }

    public void enemigoTerminaSuGolpe(SurvivorCamp campo) {
        this.attackStrategy.enemigoTerminaSuGolpe(campo);
    }

    public void enemigoAtaca(SurvivorCamp campo) {
        this.attackStrategy.enemigoAtaca(campo);
    }
}