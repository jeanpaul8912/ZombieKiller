package edu.puj.pattern_design.zombie_killer.service.defense_strategies;

public class DefenseStrategyContext {

    private final DefenseStrategy attackStrategy;

    public DefenseStrategyContext(DefenseStrategy attackStrategy) {
        this.attackStrategy = attackStrategy;
    }

    public void executeDefense() {
        this.attackStrategy.executeDefense();
    }

}
