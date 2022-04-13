package threads;

import interfaz.InterfazZombieKiller;
import mundo.weapons.Weapon;
import mundo.weapons.guns.Remington;
import mundo.weapons.guns.WeaponDeFuego;

public class HiloArma extends Thread {

	private final Weapon weapon;
	private final InterfazZombieKiller principal;

	public HiloArma(InterfazZombieKiller inter, Weapon weapon) {
		this.weapon = weapon;
		principal = inter;
	}

	@Override
	public void run() {
		try {
			if (weapon instanceof WeaponDeFuego) {
				WeaponDeFuego armaDeFuego = (WeaponDeFuego) weapon;

				if (armaDeFuego.isEnsangrentada()) {
					sleep(100);
					principal.terminarEfectoDeSangre();
				}

				if (weapon.getEstado().equals(WeaponDeFuego.RECARGANDO)) {
					// descanso mientras suena el disparo
					sleep(200);
					if (weapon instanceof Remington && armaDeFuego.getAvailableBullets() > 0)
						principal.reproducir("recarga_escopeta");
				} else
					principal.reproducir(Weapon.CARGANDO + weapon.getClass().getSimpleName());
			}

			sleep(weapon.calcularDescanso());
			weapon.setEstado(Weapon.LISTA);
			principal.cambiarPuntero();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
