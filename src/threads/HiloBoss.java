package threads;

import interfaz.InterfazZombieKiller;
import mundo.attackStrategies.AttackStrategyContext;
import mundo.attackStrategies.BossAttackStrategy;
import mundo.camp.SurvivorCamp;
import mundo.zombies.Boss;

import static mundo.constants.CampConstants.PAUSADO;
import static mundo.constants.CampConstants.SIN_PARTIDA;
import static mundo.constants.ZombiesConstants.ATACANDO;

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
			while (campo.getEstadoJuego() != SIN_PARTIDA) {
				attackStrategy.executeAttack(jefe);
				String estado = jefe.getEstadoActual();
				if (estado.equals(ATACANDO)) {
					if (jefe.getFrameActual() == 19)
						principal.leDaAPersonaje();
					else if (jefe.getFrameActual() == 21) {
						attackStrategy.enemigoTerminaSuGolpe(campo);
					}
				}

				while (campo.getEstadoJuego() == PAUSADO) {
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