package interfaz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PanelCreditos extends JPanel implements KeyListener {

	private InterfazZombieKiller principal;

	private static PanelCreditos panelSingleton;

	public PanelCreditos() {
		addKeyListener(this);
		setFocusable(true);
		setBackground(Color.black);
		JLabel labCreditos = new JLabel("En proceso, presiona \"Esc\" para volver");
		labCreditos.setForeground(Color.white);
		add(labCreditos);
	}

	public static PanelCreditos getPanel() {
		if (panelSingleton == null) {
			panelSingleton = new PanelCreditos();
		}

		return panelSingleton;
	}

	public void setPrincipal(InterfazZombieKiller interfazZombieKiller) {
		principal = interfazZombieKiller;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE)
			principal.mostrarCreditos();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
