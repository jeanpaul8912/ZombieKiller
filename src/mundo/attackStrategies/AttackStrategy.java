package mundo.attackStrategies;

import mundo.camp.SurvivorCamp;
import mundo.zombies.Enemigo;

import static mundo.constants.CampConstants.SIN_PARTIDA;

public abstract class AttackStrategy {

	public abstract void executeAttack(Enemigo enemy);

	public abstract void terminaDeAtacar(); 
	
	public void enemigoTerminaSuGolpe(SurvivorCamp campo) {
		campo.getPersonaje().setEnsangrentado(false);
		terminaDeAtacar();
	}
	
	public void enemigoAtaca(SurvivorCamp campo) {
		campo.getPersonaje().setEnsangrentado(true);
		campo.getPersonaje().setSalud((byte) (campo.getPersonaje().getSalud() - 1));
		if (campo.getPersonaje().getSalud() <= 0) {
			campo.setEstadoJuego(SIN_PARTIDA);
		}
	}
}