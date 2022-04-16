package edu.puj.pattern_design.zombie_killer.service.camp;

import edu.puj.pattern_design.zombie_killer.service.weapons.Weapon;
import edu.puj.pattern_design.zombie_killer.service.weapons.WeaponFactory;
import edu.puj.pattern_design.zombie_killer.service.weapons.WeaponFactoryImpl;
import edu.puj.pattern_design.zombie_killer.service.weapons.guns.Grenade;
import edu.puj.pattern_design.zombie_killer.service.weapons.guns.GunWeapon;
import edu.puj.pattern_design.zombie_killer.service.zombies.LivingBeing;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import static edu.puj.pattern_design.zombie_killer.service.constants.MainCharacterConstants.SALUD;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.CARGANDO;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.WEAPON_GRENADE_TYPE;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.WEAPON_KNIFE_TYPE;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.WEAPON_M1911_TYPE;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.WEAPON_REMINGTON_TYPE;

@Getter
@Setter
public class Character implements LivingBeing, Serializable {

    private static final long serialVersionUID = -9073363089990894813L;

    private byte health;

    private short killing;

    private int score;

    private int headShots;

    private GunWeapon principalWeapon;

    private GunWeapon secondaryWeapon;

    private final GunWeapon grenades;

    private final Weapon knife;

    private boolean blooded;

    public Character() {
        health = SALUD;
        WeaponFactory armaFactory = new WeaponFactoryImpl();
        grenades = (Grenade) armaFactory.createWeapon(WEAPON_GRENADE_TYPE);
        principalWeapon = (GunWeapon) armaFactory.createWeapon(WEAPON_M1911_TYPE);
        secondaryWeapon = (GunWeapon) armaFactory.createWeapon(WEAPON_REMINGTON_TYPE);
        knife = armaFactory.createWeapon(WEAPON_KNIFE_TYPE);
    }

    public void increaseScore(int score) {
        this.score = this.score + score;
        killing = (short) (killing + 1);
    }

    public void reloadPrincipalWeapon() {
        principalWeapon.reload();
        principalWeapon.setEstado(CARGANDO);
    }

    public void changeWeapon() {
        GunWeapon temporal = principalWeapon;
        principalWeapon = secondaryWeapon;
        secondaryWeapon = temporal;
    }

    public void increaseHeadShoots() {
        headShots++;
    }

}
