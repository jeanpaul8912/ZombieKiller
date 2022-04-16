package edu.puj.pattern_design.zombie_killer.service.weapons.guns;

import edu.puj.pattern_design.zombie_killer.service.weapons.Weapon;
import edu.puj.pattern_design.zombie_killer.service.weapons.guns.ammunition.Ammunition;
import lombok.Getter;
import lombok.Setter;

import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.CARGANDO;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.RECHARGING;

@Getter
@Setter
public abstract class GunWeapon extends Weapon {

    private static final long serialVersionUID = 1L;

    /**
     * estado temporal para dibujar la sangre del zombie en pantalla
     */
    private boolean blooded;

    protected Ammunition ammunition;

    /**
     * Constructor abstracto del arma de fuego
     */
    public GunWeapon() {
    }

    @Override
    public long calcularDescanso() {
        long descanso = 0;

        if (getEstado().equals(RECHARGING)) {
            descanso = ammunition.getRechargeTime();
        } else if (getEstado().equals(CARGANDO)) {
            descanso = getRetroceso();
        }

        return descanso;
    }

    /**
     * pregunta si el arma presente acaba de darle a algun enemigo
     *
     * @return ensangrentada
     */
    public boolean isBlooded() {
        return blooded;
    }


    public void shoot() {
        ammunition.setAvailableBullets(ammunition.getAvailableBullets() - 1);
    }

    public void reload() {
        ammunition.reload();
    }

    public int getMaxBullets() {
        return ammunition.getMaxBullets();
    }

    public int getAvailableBullets() {
        return ammunition.getAvailableBullets();
    }

    public void improveGun() {
        ammunition.improveAmmunition();
    }

    @Override
    public int getDamage() {
        return ammunition.getDamage();
    }

}
