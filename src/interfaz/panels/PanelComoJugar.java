package interfaz.panels;

import interfaz.InterfazZombieKiller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

public class PanelComoJugar extends JPanel {

	private static final String SALIR = "Salir";
	private final JButton butSalir;
	private InterfazZombieKiller principal;

	private static PanelComoJugar panelSingleton;

	public PanelComoJugar() {
		setBackground(Color.BLACK);
		setLayout(new BorderLayout());
		PanelDatosCuriosos panelDatosC = new PanelDatosCuriosos();
		PanelArmas panelArmas = new PanelArmas(principal);
		butSalir = new JButton();
		configurarBoton(butSalir, getClass().getResource("/img/Palabras/volver.png"), SALIR);
		butSalir.setActionCommand(SALIR);
		JPanel aux = new JPanel();
		aux.setLayout(new BorderLayout());
		aux.add(panelDatosC, BorderLayout.NORTH);
		aux.add(panelArmas, BorderLayout.CENTER);
		JScrollPane scroll = new JScrollPane(aux);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		add(scroll, BorderLayout.CENTER);
		add(butSalir, BorderLayout.SOUTH);
	}

	public static PanelComoJugar getPanel() {
		if (panelSingleton == null) {
			panelSingleton = new PanelComoJugar();
		}

		return panelSingleton;
	}

	public void setPrincipal(InterfazZombieKiller interfazZombieKiller) {
		principal = interfazZombieKiller;
	}

	public void configurarBoton(JButton aEditar, URL rutaImagen, String comando) {
		aEditar.setBorder(null);
		aEditar.setFocusable(false);
		aEditar.setContentAreaFilled(false);
		aEditar.setActionCommand(comando);
		ImageIcon letras = new ImageIcon(rutaImagen);
		aEditar.setIcon(letras);
		aEditar.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
				ImageIcon iI = new ImageIcon(getClass().getResource("/img/Palabras/volver.png"));
				butSalir.setIcon(iI);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				ImageIcon iI = new ImageIcon(getClass().getResource("/img/Palabras/volver_p.png"));
				butSalir.setIcon(iI);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				principal.mostrarComoJugar();
			}
		});
	}
}
