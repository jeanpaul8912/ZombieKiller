package threads;

import interfaz.InterfazZombieKiller;
import mundo.weapons.Arma;
import mundo.weapons.fuego.ArmaDeFuego;
import mundo.weapons.fuego.Remington;

public class HiloArma extends Thread {

	private Arma weapon;
	private InterfazZombieKiller principal;

	public HiloArma(InterfazZombieKiller inter, Arma weapon) {
		this.weapon = weapon;
		principal = inter;
	}

	@Override
	public void run() {
		try {
			if (weapon instanceof ArmaDeFuego) {
				ArmaDeFuego armaDeFuego = (ArmaDeFuego) weapon;

				if (armaDeFuego.isEnsangrentada()) {
					sleep(100);
					principal.terminarEfectoDeSangre();
				}

				if (weapon.getEstado().equals(ArmaDeFuego.RECARGANDO)) {
					// descanso mientras suena el disparo
					sleep(200);
					if (weapon instanceof Remington && armaDeFuego.getAvailableBullets() > 0)
						principal.reproducir("recarga_escopeta");
				} else
					principal.reproducir(Arma.CARGANDO + weapon.getClass().getSimpleName());
			}

			sleep(weapon.calcularDescanso());
			weapon.setEstado(Arma.LISTA);
			principal.cambiarPuntero();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
