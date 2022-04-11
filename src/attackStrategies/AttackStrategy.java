package attackStrategies;

import mundo.Enemigo;
import mundo.SurvivorCamp;

public abstract class AttackStrategy {

	public abstract void executeAttack(Enemigo enemy);
	//public abstract void moverEnDireccion(Enemigo boss);
	public abstract void terminaDeAtacar(); 
	
	public void enemigoTerminaSuGolpe(SurvivorCamp campo) {
		campo.getPersonaje().setEnsangrentado(false);
		terminaDeAtacar();
	}
	
	public void enemigoAtaca(SurvivorCamp campo) {
		campo.getPersonaje().setEnsangrentado(true);
		campo.getPersonaje().setSalud((byte) (campo.getPersonaje().getSalud() - 1));
		if (campo.getPersonaje().getSalud() <= 0) {
			campo.setEstadoJuego(campo.SIN_PARTIDA);
		}
	}
}