package interfaz;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelCreditos extends JPanel implements KeyListener{

	private JLabel labCreditos;
	private InterfazZombieKiller principal;
	
	public PanelCreditos(InterfazZombieKiller interfazZombieKiller) {
		addKeyListener(this);
		setFocusable(true);
		setBackground(Color.black);
		principal = interfazZombieKiller;
		labCreditos = new JLabel("En proceso, presiona \"Esc\" para volver");
		labCreditos.setForeground(Color.white);
		add(labCreditos);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if(arg0.getKeyCode() == KeyEvent.VK_ESCAPE)
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
