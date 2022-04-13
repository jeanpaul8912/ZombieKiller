package interfaz.panels;

import interfaz.InterfazZombieKiller;
import mundo.camp.Puntaje;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PanelPuntajes extends JPanel implements ActionListener {

	private static final String ORDEN_HEADSHOT = "Filtrar por tiros a la cabeza";
	private static final String ORDEN_BAJAS = "Filtrar por bajas";
	private static final String ORDEN_SCORE = "Filtrar por puntaje";
	private static final String BUSCAR_NOMBRE = "Buscar por nombre";
	private static final String SALIR = "salir";

	private final JLabel titulo;
	private final JButton butFiltroHeadShot;
	private final JButton butBuscarNombre;
	private final JButton butFiltroBajas;
	private final JButton butFiltroScore;
	private final JButton butSalir;
	private InterfazZombieKiller principal;

	private static PanelPuntajes panelSingleton;

	public PanelPuntajes() {
		setBackground(Color.black);
		Font f = new Font("Chiller", Font.BOLD, 26);
		titulo = new JLabel("Puntajes");
		titulo.setForeground(Color.WHITE);
		butFiltroHeadShot = new JButton(ORDEN_HEADSHOT);
		butFiltroHeadShot.addActionListener(this);
		butFiltroHeadShot.setActionCommand(ORDEN_HEADSHOT);
		butBuscarNombre = new JButton(BUSCAR_NOMBRE);
		butBuscarNombre.addActionListener(this);
		butBuscarNombre.setActionCommand(BUSCAR_NOMBRE);
		butFiltroBajas = new JButton(ORDEN_BAJAS);
		butFiltroBajas.addActionListener(this);
		butFiltroBajas.setActionCommand(ORDEN_BAJAS);
		butFiltroScore = new JButton(ORDEN_SCORE);
		butFiltroScore.addActionListener(this);
		butFiltroScore.setActionCommand(ORDEN_SCORE);
		butSalir = new JButton();
		configurarBoton(butSalir, getClass().getResource("/img/Palabras/volver.png"), SALIR);
		butSalir.setActionCommand(SALIR);
		JLabel texto = new JLabel("No se encontraron puntajes");
		texto.setForeground(Color.WHITE);
		titulo.setFont(f);
		setLayout(new BorderLayout());
		add(titulo, BorderLayout.NORTH);
		add(texto, BorderLayout.CENTER);
		add(butSalir, BorderLayout.SOUTH);
	}

	public static PanelPuntajes getPanel() {
		if (panelSingleton == null) {
			panelSingleton = new PanelPuntajes();
		}

		return panelSingleton;
	}

	public void setPrincipal(InterfazZombieKiller interfazZombieKiller) {
		principal = interfazZombieKiller;
	}

	public void actualizarPuntajes(List<Puntaje> scores) {
		if (scores.size() != 0) {
			removeAll();
			add(titulo, BorderLayout.NORTH);
			generaryAgregarLabels(scores);
			generarYAgregarBotones();
			updateUI();
		}
	}

	private void generaryAgregarLabels(List<Puntaje> scores) {
		JLabel[] labScores = new JLabel[scores.size()];
		JLabel[] labNombres = new JLabel[scores.size()];
		JLabel[] labHeadShots = new JLabel[scores.size()];
		JLabel[] labBajas = new JLabel[scores.size()];
		JPanel auxPuntajes = new JPanel();
		auxPuntajes.setBackground(Color.black);
		if (scores.size() > 10) {
			auxPuntajes.setLayout(new GridLayout(11, 4));
			titulo.setText("Top 10 Mejores Puntajes");
		} else
			auxPuntajes.setLayout(new GridLayout(scores.size() + 1, 4));
		JLabel labScore = new JLabel("Score");
		labScore.setForeground(Color.WHITE);
		JLabel labNombre = new JLabel("Nombre");
		labNombre.setForeground(Color.WHITE);
		JLabel labTC = new JLabel("Headshots");
		labTC.setForeground(Color.WHITE);
		JLabel labKills = new JLabel("Bajas");
		labKills.setForeground(Color.WHITE);
		auxPuntajes.add(labNombre);
		auxPuntajes.add(labScore);
		auxPuntajes.add(labKills);
		auxPuntajes.add(labTC);
		for (int i = 0; i < scores.size() && i < 10; i++) {
			labScores[i] = new JLabel(scores.get(i).getPuntaje() + "");
			labScores[i].setForeground(Color.WHITE);
			labNombres[i] = new JLabel(scores.get(i).getNombreKiller());
			labNombres[i].setForeground(Color.WHITE);
			labHeadShots[i] = new JLabel(scores.get(i).getTirosALaCabeza() + "");
			labHeadShots[i].setForeground(Color.WHITE);
			labBajas[i] = new JLabel(scores.get(i).getBajas() + "");
			labBajas[i].setForeground(Color.WHITE);
			auxPuntajes.add(labNombres[i]);
			auxPuntajes.add(labScores[i]);
			auxPuntajes.add(labBajas[i]);
			auxPuntajes.add(labHeadShots[i]);
		}
		add(auxPuntajes, BorderLayout.CENTER);
	}

	private void generarYAgregarBotones() {
		JPanel auxBotones = new JPanel();
		auxBotones.setBackground(Color.black);
		auxBotones.setLayout(new GridLayout(5, 1));
		auxBotones.add(butFiltroHeadShot);
		auxBotones.add(butFiltroBajas);
		auxBotones.add(butFiltroScore);
		auxBotones.add(butBuscarNombre);
		auxBotones.add(butSalir);
		add(auxBotones, BorderLayout.SOUTH);
	}

	public void mostrarPuntajeDe(Puntaje buscado) {
		if (buscado != null) {
			JLabel encontrado = new JLabel("Mejor puntaje del nombre buscado");
			Font f = new Font("Chiller", Font.BOLD, 26);
			encontrado.setFont(f);
			encontrado.setForeground(Color.WHITE);
			removeAll();
			add(encontrado, BorderLayout.NORTH);
			generarYAgregarBotones();
			ArrayList<Puntaje> aMostrar = new ArrayList<>();
			aMostrar.add(buscado);
			generaryAgregarLabels(aMostrar);
			updateUI();
		} else
			JOptionPane.showMessageDialog(this, "No se encontro el nombre buscado en los puntajes");
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String c = arg0.getActionCommand();
		switch (c) {
			case ORDEN_BAJAS:
				principal.ordenarPorBajas();
				break;
			case ORDEN_HEADSHOT:
				principal.ordenarPorHeadshot();
				break;
			case BUSCAR_NOMBRE:
				principal.buscarPorNombre();
				break;
			case ORDEN_SCORE:
				principal.ordenarPorScore();
				break;
		}
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
				principal.mostrarPuntajes();
			}
		});
	}
}
