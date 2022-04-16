package edu.puj.pattern_design.zombie_killer.service.weapons;

public interface WeaponFactory {

    Weapon createWeapon(String typeWeapon);
}