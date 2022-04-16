package edu.puj.pattern_design.zombie_killer.service.attack_strategies;

import edu.puj.pattern_design.zombie_killer.service.zombies.Enemy;

public interface IAttackMovement {

    void moveInDirection(Enemy enemy);
}
