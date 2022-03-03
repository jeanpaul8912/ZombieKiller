package hilo;

import interfaz.InterfazZombieKiller;
import mundo.Boss;
import mundo.Caminante;
import mundo.Enemigo;
import mundo.Rastrero;
import mundo.SurvivorCamp;
import mundo.Zombie;

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
			while (campo.getEstadoJuego()!=SurvivorCamp.SIN_PARTIDA) {
					String estado = jefe.ataco();
					if (estado.equals(Enemigo.ATACANDO)) {
							if(jefe.getFrameActual()==19)
								principal.leDaAPersonaje();
							else if(jefe.getFrameActual()==21)
								campo.enemigoTerminaSuGolpe(jefe);
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
