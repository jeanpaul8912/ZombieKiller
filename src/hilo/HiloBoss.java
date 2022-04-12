package hilo;

import interfaz.InterfazZombieKiller;
import mundo.Boss;
import mundo.Enemigo;
import mundo.SurvivorCamp;
import mundo.attackStrategies.AttackStrategyContext;
import mundo.attackStrategies.BossAttackStrategy;

public class HiloBoss extends Thread{
	
		private InterfazZombieKiller principal;
		private Boss jefe;
		private SurvivorCamp campo;

		public HiloBoss(InterfazZombieKiller principal, Boss jefe, SurvivorCamp campo) {
			this.principal = principal;
			this.jefe = jefe;
			this.campo = campo;
		}

		@Override
		public void run() {
			try {
			int valorJefeCambiaPosicion = 0;
			AttackStrategyContext attackStrategy;
			attackStrategy = new AttackStrategyContext(new BossAttackStrategy());	
			while (campo.getEstadoJuego()!=SurvivorCamp.SIN_PARTIDA) {			
				attackStrategy.executeAttack(jefe);
					String estado = jefe.getEstadoActual();
					if (estado.equals(Enemigo.ATACANDO)) {
							if(jefe.getFrameActual()==19)
								principal.leDaAPersonaje();
							else if(jefe.getFrameActual()==21) {
								//campo.enemigoTerminaSuGolpe(jefe);
								attackStrategy.enemigoTerminaSuGolpe(campo);
								}
								
					}
				while (campo.getEstadoJuego()==SurvivorCamp.PAUSADO){
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