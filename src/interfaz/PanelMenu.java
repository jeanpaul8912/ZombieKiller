package interfaz;

import interfaz.commands.BestScoresCommand;
import interfaz.commands.Command;
import interfaz.commands.ContinueGameCommand;
import interfaz.commands.CreditsCommand;
import interfaz.commands.HowToPlayCommand;
import interfaz.commands.LoadGameCommand;
import interfaz.commands.SaveGameCommand;
import interfaz.commands.StartGameCommand;
import mundo.camp.SurvivorCamp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

public class PanelMenu extends JPanel implements KeyListener, ActionListener, MouseListener {

	private static final long serialVersionUID = 6794669918135042094L;

	private static final String CONTINUAR = "Continuar";
	private static final String INICIAR = "Iniciar Nuevo Juego";
	private static final String CARGAR = "Cargar Partida";
	private static final String GUARDAR = "Guardar Partida";
	private static final String COMO_JUGAR = "Como jugar";
	private static final String CREDITOS = "Creditos";
	private static final String MEJORES_PUNTAJES = "Mejores Puntajes";

	private final InterfazZombieKiller principal;
	private final JButton butIniciarJuego;
	private final JButton butContinuar;
	private final JButton butCargar;
	private final JButton butGuardar;
	private final JButton butComoJugar;
	private final JButton butCreditos;
	private final JButton butPuntajes;

	public PanelMenu(InterfazZombieKiller interfazZombieKiller) {
		setFocusable(true);
		setLayout(new GridLayout(9, 2));
		principal = interfazZombieKiller;
		addKeyListener(this);

		JLabel aux = new JLabel();
		add(aux);
		aux = new JLabel();
		add(aux);

		aux = new JLabel();
		add(aux);
		butIniciarJuego = new JButton();
		configurarBoton(butIniciarJuego, getClass().getResource("/img/Palabras/nuevo.png"), INICIAR);
		add(butIniciarJuego);

		aux = new JLabel();
		add(aux);
		butContinuar = new JButton();
		configurarBoton(butContinuar, getClass().getResource("/img/Palabras/continuar.png"), CONTINUAR);
		add(butContinuar);

		aux = new JLabel();
		add(aux);
		butCargar = new JButton();
		configurarBoton(butCargar, getClass().getResource("/img/Palabras/cargar.png"), CARGAR);
		add(butCargar);

		aux = new JLabel();
		add(aux);
		butGuardar = new JButton();
		configurarBoton(butGuardar, getClass().getResource("/img/Palabras/guardar.png"), GUARDAR);
		add(butGuardar);

		aux = new JLabel();
		add(aux);
		butComoJugar = new JButton();
		configurarBoton(butComoJugar, getClass().getResource("/img/Palabras/como jugar.png"), COMO_JUGAR);
		add(butComoJugar);

		aux = new JLabel();
		add(aux);
		butPuntajes = new JButton();
		configurarBoton(butPuntajes, getClass().getResource("/img/Palabras/puntajes.png"), MEJORES_PUNTAJES);
		add(butPuntajes);

		aux = new JLabel();
		add(aux);
		butCreditos = new JButton();
		configurarBoton(butCreditos, getClass().getResource("/img/Palabras/creditos.png"), CREDITOS);
		add(butCreditos);
	}

	public void configurarBoton(JButton aEditar, URL rutaImagen, String comando) {
		aEditar.setBorder(null);
		aEditar.setContentAreaFilled(false);
		aEditar.setActionCommand(comando);
		ImageIcon letras = new ImageIcon(rutaImagen);
		aEditar.setIcon(letras);
		aEditar.addActionListener(this);
		aEditar.addMouseListener(this);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		requestFocusInWindow();
		Image Palabras = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/Fondo/fondoMenu.png"));
		g.drawImage(Palabras, 0, 0, null);

		if (principal.getEstadoPartida() == SurvivorCamp.PAUSADO) {
			butContinuar.setEnabled(true);
			butGuardar.setEnabled(true);
		} else {
			butContinuar.setEnabled(false);
			butGuardar.setEnabled(false);
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if (arg0.getKeyCode() == 80 && principal.getEstadoPartida() == SurvivorCamp.PAUSADO)
			principal.pausarJuego();
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		String commandName = actionEvent.getActionCommand();
		Command command;

		switch (commandName) {
		case INICIAR:
			command = new StartGameCommand();
			break;
		case CONTINUAR:
			command = new ContinueGameCommand();
			break;
		case CARGAR:
			command = new LoadGameCommand();
			break;
		case GUARDAR:
			command = new SaveGameCommand();
			break;
		case COMO_JUGAR:
			command = new HowToPlayCommand();
			break;
		case MEJORES_PUNTAJES:
			command = new BestScoresCommand();
			break;
		case CREDITOS:
			command = new CreditsCommand();
			break;
		default:
			throw new RuntimeException("Unknown Command.");
		}

		command.execute(principal);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		JButton but = (JButton) e.getComponent();
		ImageIcon defaultIcon;
		if (but == butIniciarJuego) {
			defaultIcon = new ImageIcon(getClass().getResource("/img/Palabras/nuevo_p.png"));
			butIniciarJuego.setIcon(defaultIcon);
		} else if (but == butCargar) {
			defaultIcon = new ImageIcon(getClass().getResource("/img/Palabras/cargar_p.png"));
			butCargar.setIcon(defaultIcon);
		} else if (but == butContinuar && butContinuar.isEnabled()) {
			defaultIcon = new ImageIcon(getClass().getResource("/img/Palabras/continuar_p.png"));
			butContinuar.setIcon(defaultIcon);
		} else if (but == butGuardar && butGuardar.isEnabled()) {
			defaultIcon = new ImageIcon(getClass().getResource("/img/Palabras/guardar_p.png"));
			butGuardar.setIcon(defaultIcon);
		} else if (but == butCreditos) {
			defaultIcon = new ImageIcon(getClass().getResource("/img/Palabras/creditos_p.png"));
			butCreditos.setIcon(defaultIcon);
		} else if (but == butComoJugar) {
			defaultIcon = new ImageIcon(getClass().getResource("/img/Palabras/como jugar_p.png"));
			butComoJugar.setIcon(defaultIcon);
		} else if (but == butPuntajes) {
			defaultIcon = new ImageIcon(getClass().getResource("/img/Palabras/puntajes_p.png"));
			butPuntajes.setIcon(defaultIcon);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		JButton but = (JButton) e.getComponent();
		ImageIcon defaultIcon;
		if (but == butIniciarJuego) {
			defaultIcon = new ImageIcon(getClass().getResource("/img/Palabras/nuevo.png"));
			butIniciarJuego.setIcon(defaultIcon);
		} else if (but == butCargar) {
			defaultIcon = new ImageIcon(getClass().getResource("/img/Palabras/cargar.png"));
			butCargar.setIcon(defaultIcon);
		} else if (but == butContinuar) {
			defaultIcon = new ImageIcon(getClass().getResource("/img/Palabras/continuar.png"));
			butContinuar.setIcon(defaultIcon);
		} else if (but == butGuardar) {
			defaultIcon = new ImageIcon(getClass().getResource("/img/Palabras/guardar.png"));
			butGuardar.setIcon(defaultIcon);
		} else if (but == butCreditos) {
			defaultIcon = new ImageIcon(getClass().getResource("/img/Palabras/creditos.png"));
			butCreditos.setIcon(defaultIcon);
		} else if (but == butComoJugar) {
			defaultIcon = new ImageIcon(getClass().getResource("/img/Palabras/como jugar.png"));
			butComoJugar.setIcon(defaultIcon);
		} else if (but == butPuntajes) {
			defaultIcon = new ImageIcon(getClass().getResource("/img/Palabras/puntajes.png"));
			butPuntajes.setIcon(defaultIcon);
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

}
