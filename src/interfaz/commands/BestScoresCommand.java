package interfaz.commands;

import java.io.IOException;

import javax.swing.JOptionPane;

import interfaz.InterfazZombieKiller;
import interfaz.PanelPuntajes;
import mundo.SurvivorCamp;

public class BestScoresCommand implements Command {

	@Override
	public void execute(InterfazZombieKiller interfazZombieKiller) {
		try {
			PanelPuntajes panelPuntajes = PanelPuntajes.getPanel();
			panelPuntajes.setPrincipal(interfazZombieKiller);
			interfazZombieKiller.setPanelPuntajes(panelPuntajes);
			SurvivorCamp camp = interfazZombieKiller.getCampo();

			if (camp == null) {
				camp = new SurvivorCamp();
				interfazZombieKiller.setCampo(camp);
			}

			camp.cargarPuntajes();
			interfazZombieKiller.mostrarPuntajes();
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Hubo un error al guardar los ultimos puntajes");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "No se han encontrado puntajes anteriores");
		}
	}

}
