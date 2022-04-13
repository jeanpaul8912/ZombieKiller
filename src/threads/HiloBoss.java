package threads;

import interfaz.InterfazZombieKiller;
import mundo.attackStrategies.AttackStrategyContext;
import mundo.attackStrategies.BossAttackStrategy;
import mundo.camp.SurvivorCamp;
import mundo.zombies.Boss;
import mundo.zombies.Enemigo;

public class HiloBoss extends Thread {

	private final InterfazZombieKiller principal;
	private final Boss jefe;
	private final SurvivorCamp campo;

	public HiloBoss(InterfazZombieKiller principal, Boss jefe, SurvivorCamp campo) {
		this.principal = principal;
		this.jefe = jefe;
		this.campo = campo;
	}

	@Override
	public void run() {
		try {
			AttackStrategyContext attackStrategy;
			attackStrategy = new AttackStrategyContext(new BossAttackStrategy());
			while (campo.getEstadoJuego() != SurvivorCamp.SIN_PARTIDA) {
				attackStrategy.executeAttack(jefe);
				String estado = jefe.getEstadoActual();
				if (estado.equals(Enemigo.ATACANDO)) {
					if (jefe.getFrameActual() == 19)
						principal.leDaAPersonaje();
					else if (jefe.getFrameActual() == 21) {
						attackStrategy.enemigoTerminaSuGolpe(campo);
					}
				}

				while (campo.getEstadoJuego() == SurvivorCamp.PAUSADO) {
					sleep(500);
				}

				sleep(jefe.getLentitud());
				principal.refrescar();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}