package defenseStrategies;

import interfaz.InterfazZombieKiller;
import mundo.Arma;
import mundo.Zombie;

public class ThrowGrenadeStrategy extends DefenseStrategy {
	
	private InterfazZombieKiller interfaz;


	public ThrowGrenadeStrategy(InterfazZombieKiller interfaz) {
		this.interfaz = interfaz;
	}


	@Override
	public void executeAttack() {
		
		throwGranada();
		interfaz.setGranada(interfaz.getCampo().getPersonaje().getGranadas());
		interfaz.getFacade().initializeWeaponsThread("granada");
		interfaz.reproducir("bomba");
	}
	

	public void throwGranada () {
		
		Zombie actual = interfaz.getCampo().getZombNodoCercano().getAtras();
		interfaz.getCampo().getPersonaje().setEnsangrentado(false);
		while (!actual.getEstadoActual().equals(Zombie.NODO)) {

			if(!actual.getEstadoActual().equals(Zombie.MURIENDO) && !actual.getEstadoActual().equals(Zombie.MURIENDO_INCENDIADO)){
				actual.setEstadoActual(Zombie.MURIENDO_INCENDIADO);
				interfaz.getCampo().getPersonaje().aumentarScore(50);
				actual = actual.getAtras();
			}
		}
		interfaz.getCampo().getPersonaje().getGranadas().setMunicion((byte) (interfaz.getCampo().getPersonaje().getGranadas().getMunicion() - 1));
		interfaz.getCampo().getPersonaje().getGranadas().setEstado(Arma.CARGANDO);
	}
}