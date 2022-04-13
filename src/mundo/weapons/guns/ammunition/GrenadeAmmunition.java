package mundo.weapons.guns.ammunition;

public class GrenadeAmmunition extends Ammunition {

    public GrenadeAmmunition(int rechargeTime, int maxBullets, byte damage) {
        super(rechargeTime, maxBullets, damage);
    }

    public void improveAmmunition() {
        maxBullets += 1;
        availableBullets = maxBullets;
        damage++;
    }
    
}
