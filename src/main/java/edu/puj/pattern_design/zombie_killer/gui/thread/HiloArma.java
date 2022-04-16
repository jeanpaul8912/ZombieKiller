package edu.puj.pattern_design.zombie_killer.gui.thread;

import edu.puj.pattern_design.zombie_killer.gui.ZombieKillerGUI;
import edu.puj.pattern_design.zombie_killer.service.weapons.Weapon;
import edu.puj.pattern_design.zombie_killer.service.weapons.guns.GunWeapon;
import edu.puj.pattern_design.zombie_killer.service.weapons.guns.Remington;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;

import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.CARGANDO;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.LISTA;

@Slf4j
public class HiloArma extends Thread {

    private final Weapon weapon;
    private final ZombieKillerGUI principal;

    public HiloArma(ZombieKillerGUI inter, Weapon weapon) {
        this.weapon = weapon;
        principal = inter;
    }

    @Override
    public void run() {
        try {
            if (weapon instanceof GunWeapon) {
                GunWeapon armaDeFuego = (GunWeapon) weapon;

                if (armaDeFuego.isEnsangrentada()) {
                    sleep(100);
                    principal.terminarEfectoDeSangre();
                }

                if (weapon.getEstado().equals(GunWeapon.RECARGANDO)) {
                    // descanso mientras suena el disparo
                    sleep(200);
                    if (weapon instanceof Remington && armaDeFuego.getAvailableBullets() > NumberUtils.INTEGER_ZERO) {
                        principal.reproducir("recarga_escopeta");
                    }
                } else {
                    principal.reproducir(CARGANDO + weapon.getClass().getSimpleName());
                }
            }

            sleep(weapon.calcularDescanso());
            weapon.setEstado(LISTA);
            principal.cambiarPuntero();
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }
}
