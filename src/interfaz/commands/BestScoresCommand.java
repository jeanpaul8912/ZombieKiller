package interfaz.commands;

import interfaz.InterfazZombieKiller;
import interfaz.panels.PanelPuntajes;
import mundo.camp.SurvivorCamp;

import javax.swing.*;
import java.io.IOException;

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
