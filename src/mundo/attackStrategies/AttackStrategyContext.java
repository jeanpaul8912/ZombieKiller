package mundo.attackStrategies;

import mundo.camp.SurvivorCamp;
import mundo.zombies.Enemigo;

public class AttackStrategyContext {
	
	private final AttackStrategy attackStrategy;

	public AttackStrategyContext(AttackStrategy attackStrategy) {
		this.attackStrategy = attackStrategy;
	}

	public void executeAttack(Enemigo enemy) {
		this.attackStrategy.executeAttack(enemy);
	}
	
	public void terminaDeAtacar() {
		this.attackStrategy.terminaDeAtacar();
	}
	
	public void enemigoTerminaSuGolpe(SurvivorCamp campo) {
		this.attackStrategy.enemigoTerminaSuGolpe(campo);
	}
	
	public void enemigoAtaca(SurvivorCamp campo) {
		this.attackStrategy.enemigoAtaca(campo);
	}
}