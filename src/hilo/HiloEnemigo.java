package hilo;

import attackStrategies.AttackStrategyContext;
import attackStrategies.CaminanteAttackStrategy;
import attackStrategies.RastreroAttackStrategy;
import interfaz.InterfazZombieKiller;
import mundo.Caminante;
import mundo.Enemigo;
import mundo.Rastrero;
import mundo.SurvivorCamp;
import mundo.Zombie;

public class HiloEnemigo extends Thread {

	private InterfazZombieKiller principal;
	private Zombie nodoCercano;
	private SurvivorCamp campo;

	public HiloEnemigo(InterfazZombieKiller principal, Zombie nodoCercano, SurvivorCamp campo) {
		this.principal = principal;
		this.nodoCercano = nodoCercano;
		this.campo = campo;
	}

	@Override
	public void run() {
		try {
			while (campo.getEstadoJuego() != SurvivorCamp.SIN_PARTIDA) {
				Zombie enMovimiento = nodoCercano.getAtras();
				AttackStrategyContext attackStrategy=null;
				
				while (!enMovimiento.getEstadoActual().equals(Zombie.NODO)) {	
					
					if (enMovimiento instanceof Caminante) {
							attackStrategy = new AttackStrategyContext(new CaminanteAttackStrategy());	
						}else if(enMovimiento instanceof Rastrero) {
							attackStrategy = new AttackStrategyContext(new RastreroAttackStrategy());
						}
					attackStrategy.executeAttack(enMovimiento);

					String estado = enMovimiento.getEstadoActual();
					if (estado.equals(Enemigo.ATACANDO)) {
						if (enMovimiento instanceof Caminante) {
							if (enMovimiento.getFrameActual() == 8)
								principal.leDaAPersonaje();
							else if (enMovimiento.getFrameActual() == 13)
								attackStrategy.enemigoTerminaSuGolpe(campo);
		
						} else if (enMovimiento instanceof Rastrero) {
							if (enMovimiento.getFrameActual() == 13)
								principal.leDaAPersonaje();
							else if (enMovimiento.getFrameActual() == 16) {
	
								attackStrategy.enemigoTerminaSuGolpe(campo);
							}
						}
					} else if (estado.equals(Zombie.MURIENDO) || estado.equals(Zombie.MURIENDO_INCENDIADO)) {
						// System.out.println(chombi.getFrameActual());
						if (enMovimiento instanceof Caminante) {
							if (enMovimiento.getFrameActual() == 17) {
								if (estado.equals(Zombie.MURIENDO))
									enMovimiento.eliminarse();
								else {
									nodoCercano.setAtras(campo.getZombNodoLejano());
									campo.getZombNodoLejano().setAlFrente(nodoCercano);
									enMovimiento = nodoCercano;
								}
							}
						} else if (enMovimiento instanceof Rastrero) {
							if (enMovimiento.getFrameActual() == 11) {
								if (estado.equals(Zombie.MURIENDO))
									enMovimiento.eliminarse();
								else {
									nodoCercano.setAtras(campo.getZombNodoLejano());
									campo.getZombNodoLejano().setAlFrente(nodoCercano);
									enMovimiento = nodoCercano;
								}
							}
						}
					}
					enMovimiento = enMovimiento.getAtras();
				}
				while (campo.getEstadoJuego() == SurvivorCamp.PAUSADO) {
					sleep(500);
				}
				// System.out.println("Corre el hilo de los enemigos");
				sleep(nodoCercano.getAtras().getLentitud());
				principal.refrescar();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}