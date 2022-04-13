package interfaz.panels;

import interfaz.InterfazZombieKiller;
import mundo.camp.Puntaje;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


public class PanelPuntajes extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String ORDEN_HEADSHOT = "Filtrar por tiros a la cabeza";
	private static final String ORDEN_BAJAS = "Filtrar por bajas";
	private static final String ORDEN_SCORE = "Filtrar por puntaje";
	private static final String BUSCAR_NOMBRE = "Buscar por nombre: ";
	private static final String SALIR = "salir";

	private final JLabel titulo;
	private final JButton butFiltroHeadShot;
	private final JButton butFiltroBajas;
	private final JButton butFiltroScore;
	private final JButton butSalir;
	private InterfazZombieKiller principal;
	private JTextField entradaBusqueda;

	private static PanelPuntajes panelSingleton;

	public PanelPuntajes() {
		setBackground(Color.BLACK);
		this.setBorder(new EmptyBorder(0,0,20,0));
		Font f = new Font("Agency FB", Font.BOLD, 50);
		titulo = new JLabel("Puntajes");
		titulo.setForeground(Color.WHITE);
		butFiltroHeadShot = new JButton(ORDEN_HEADSHOT);
		butFiltroHeadShot.addActionListener(this);
		butFiltroHeadShot.setActionCommand(ORDEN_HEADSHOT);
		butFiltroBajas = new JButton(ORDEN_BAJAS);
		butFiltroBajas.addActionListener(this);
		butFiltroBajas.setActionCommand(ORDEN_BAJAS);
		butFiltroScore = new JButton(ORDEN_SCORE);
		butFiltroScore.addActionListener(this);
		butFiltroScore.setActionCommand(ORDEN_SCORE);
		entradaBusqueda = new JTextField("",20);
		entradaBusqueda.addActionListener(this);
		configurarCampoBusqueda(entradaBusqueda);
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

	public void agregarPanelSuperior(JLabel titulo) {
		JPanel principalEncabezado = new JPanel();
		JPanel panelBuscar = new JPanel();
		JPanel panelBorde = new JPanel(); 
		JPanel panelTitulo = new JPanel();
		JLabel etiquetaBuscar = new JLabel(BUSCAR_NOMBRE);
		etiquetaBuscar.setForeground(Color.WHITE);
		panelTitulo.setLayout( new BorderLayout());
		panelTitulo.add(titulo, BorderLayout.WEST);
		panelBuscar.add(etiquetaBuscar);
		panelBuscar.add(entradaBusqueda);

		panelBuscar.setBackground(Color.BLACK);
		panelTitulo.setBackground(Color.BLACK);

		panelTitulo.setBorder(BorderFactory.createEmptyBorder(0,30,0,0));
		panelBorde.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
		panelBuscar.setBorder(BorderFactory.createEmptyBorder(0,500,0,0));
		principalEncabezado.setLayout( new BorderLayout());
		principalEncabezado.add(panelTitulo, BorderLayout.NORTH);
		principalEncabezado.add(panelBorde, BorderLayout.CENTER);
		principalEncabezado.add(panelBuscar, BorderLayout.SOUTH);
		add(principalEncabezado, BorderLayout.NORTH);
	}

	private void generaryAgregarLabels(List<Puntaje> scores) {

		JLabel[] labScores = new JLabel[scores.size()];
		JLabel[] labNombres = new JLabel[scores.size()];
		JLabel[] labHeadShots = new JLabel[scores.size()];
		JLabel[] labBajas = new JLabel[scores.size()];
		JPanel auxPuntajes = new JPanel();
		auxPuntajes.setBorder(new EmptyBorder(0,150,0,50));
		Font font = new Font("Agency FB", Font.BOLD, 24);
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
		labNombre.setFont(font);
		labScore.setFont(font);
		labKills.setFont(font);
		labTC.setFont(font);
		auxPuntajes.add(labNombre);
		auxPuntajes.add(labScore);
		auxPuntajes.add(labKills);
		auxPuntajes.add(labTC);

		for (int i = 0; i < scores.size() && i < 10; i++) {
			labScores[i] = new JLabel(scores.get(i).getPuntaje() + "");
			labScores[i].setForeground(Color.WHITE);
			labScores[i].setVerticalAlignment(JLabel.TOP);
			labNombres[i] = new JLabel(scores.get(i).getNombreKiller());
			labNombres[i].setForeground(Color.WHITE);
			labNombres[i].setVerticalAlignment(JLabel.TOP);
			labHeadShots[i] = new JLabel(scores.get(i).getTirosALaCabeza() + "");
			labHeadShots[i].setForeground(Color.WHITE);
			labHeadShots[i].setVerticalAlignment(JLabel.TOP);
			labBajas[i] = new JLabel(scores.get(i).getBajas() + "");
			labBajas[i].setForeground(Color.WHITE);
			labBajas[i].setVerticalAlignment(JLabel.TOP);
			auxPuntajes.add(labNombres[i]);
			auxPuntajes.add(labScores[i]);
			auxPuntajes.add(labBajas[i]);
			auxPuntajes.add(labHeadShots[i]);
		}
		auxPuntajes.setFont(font);
		add(auxPuntajes, BorderLayout.CENTER);
	}

	private void generarYAgregarBotones() {
		JPanel auxBotones = new JPanel();
		JPanel auxBotonSalir = new JPanel();
		JPanel botones = new JPanel();
		auxBotones.setBackground(Color.BLACK);
		auxBotonSalir.setBackground(Color.BLACK);
		auxBotones.add(butFiltroHeadShot);
		auxBotones.add(butFiltroBajas);
		auxBotones.add(butFiltroScore);
		auxBotonSalir.add(butSalir);
		auxBotones.setBorder(BorderFactory.createEmptyBorder(0,380,0,0));
		botones.setLayout( new BorderLayout());
		botones.add(auxBotones,BorderLayout.NORTH);
		botones.add(auxBotonSalir,BorderLayout.SOUTH);
		add(botones, BorderLayout.SOUTH);
		
	}

	public void mostrarPuntajeDe(Puntaje buscado) {
		if (buscado != null) {
			JLabel encontrado = new JLabel("Mejor puntaje del nombre buscado");
			Font f = new Font("Agency FB", Font.BOLD, 50);
			encontrado.setFont(f);
			encontrado.setForeground(Color.WHITE);
			removeAll();
			agregarPanelSuperior(encontrado);
			ArrayList<Puntaje> aMostrar = new ArrayList<>();
			aMostrar.add(buscado);
			generaryAgregarLabels(aMostrar);
			generarYAgregarBotones();
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
	
	public void configurarCampoBusqueda(JTextField nombreBuscado) {
		nombreBuscado.addActionListener(new ActionListener(){
		                public void actionPerformed(ActionEvent e){
		                	String nombre = nombreBuscado.getText();
		                	principal.buscarPorNombre(nombre);
		                }});
	}
	
	public void actualizarPuntajes(List<Puntaje> scores) {
		if (scores.size() != 0) {
			removeAll();	
			agregarPanelSuperior(titulo);
			generaryAgregarLabels(scores);
			generarYAgregarBotones();
			updateUI();

		}
	}
}
