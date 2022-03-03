package interfaz;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.DebugGraphics;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import hilo.HiloArma;
import hilo.HiloBoss;
import hilo.HiloEnemigo;
import hilo.HiloGeneradorDeZombies;
import hilo.HiloSonido;
import mundo.ArmaDeFuego;
import mundo.Boss;
import mundo.NombreInvalidoException;
import mundo.Puntaje;
import mundo.Remington;
import mundo.SurvivorCamp;
import mundo.Zombie;

public class InterfazZombieKiller extends JFrame {

	/**
	 * Hilo que reproduce el sonido de los zombies
	 */
	private HiloSonido sonidoFondo;
	/**
	 * Campo de juego que contiene a todo el mundo
	 */
	private SurvivorCamp campo;
	/**
	 * Arma que el jugador tiene equipada
	 */
	private ArmaDeFuego armaActual;
	/**
	 * Panel del menú principal cualquier botón muestra otro panel
	 * representatitvo a él
	 */
	private PanelMenu panelMenu;
	/**
	 * Panel del campo de juego
	 */
	private PanelCamp panelCampo;
	/**
	 * Panel que muestra las instrucciones de juego Muestra las estadísticas de
	 * las armas
	 */
	private PanelComoJugar panelComoJugar;
	/**
	 * Panel que muestra los puntajes de los jugadores
	 */
	private PanelPuntajes panelPuntajes;
	/**
	 * Panel que muestra los créditos de las personas que participaron
	 */
	private PanelCreditos panelCreditos;
	/**
	 * Cursor de la mira de la pistola
	 */
	private Cursor miraM1911;
	/**
	 * Cursor de la mira de la escopeta
	 */
	private Cursor miraRemington;
	/**
	 * Cursor temporal del cuchillo
	 */
	private Cursor cursorCuchillo;

	/**
	 * Constructor de la clase principal del juego Aquí se inicializan todos los
	 * componentes necesarios para empezar a jugar
	 */
	public InterfazZombieKiller() {
		BorderLayout custom = new BorderLayout();
		setLayout(custom);
		ImageIcon laterales = new ImageIcon(getClass().getResource("/img/Fondo/iconozombie.png"));
		ImageIcon fondo = new ImageIcon(getClass().getResource("/img/Fondo/fondoMenu.png"));

		ImageIcon cursorP = new ImageIcon(getClass().getResource("/img/Fondo/mira1p.png"));
		miraM1911 = Toolkit.getDefaultToolkit().createCustomCursor(cursorP.getImage(), new Point(16, 16), "C");
		cursorP = new ImageIcon(getClass().getResource("/img/Fondo/mira1.png"));
		miraRemington = Toolkit.getDefaultToolkit().createCustomCursor(cursorP.getImage(), new Point(16, 16), "C2");
		cursorP = new ImageIcon(getClass().getResource("/img/Fondo/Cuchillo.png"));
		cursorCuchillo = Toolkit.getDefaultToolkit().createCustomCursor(cursorP.getImage(), new Point(1, 1), "C2");
		setCursor(miraM1911);
		panelCampo = new PanelCamp(this);
		panelMenu = new PanelMenu(this);
		panelComoJugar = new PanelComoJugar(this);
		panelPuntajes = new PanelPuntajes(this);
		panelCreditos = new PanelCreditos(this);

		add(panelMenu, BorderLayout.CENTER);

		campo = new SurvivorCamp();
		try {
			campo.cargarPuntajes();
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(this, "Hubo un error al guardar los últimos puntajes");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "No se han encontrado puntajes anteriores");
		}
		setSize(campo.ANCHO_PANTALLA, campo.ALTO_PANTALLA);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
	}

	/**
	 * Obtiene el estado actual de la partida
	 * 
	 * @return estado
	 */
	public char getEstadoPartida() {
		return campo.getEstadoJuego();
	}

	/**
	 * Inicia una partida desde 0
	 */
	public void iniciarNuevaPartida() {
		if (campo.getEstadoJuego() != SurvivorCamp.SIN_PARTIDA) {
			int respuesta = JOptionPane.showConfirmDialog(this,
					"En este momento se encuentra en una partida, segudo que desea salir?", "Iniciar Nueva Partida",
					JOptionPane.YES_NO_OPTION);
			if (respuesta == JOptionPane.YES_OPTION) {
				campo.setEstadoJuego(SurvivorCamp.SIN_PARTIDA);
				partidaIniciada();
			}
		} else {
			partidaIniciada();
		}
	}

	/**
	 * Método auxiliar que inicializa y actualiza la información en los
	 * componentes visibles
	 */
	private void partidaIniciada() {
		setCursor(cursorCuchillo);
		Puntaje actual = campo.getRaizPuntajes();
		campo = new SurvivorCamp();
		campo.actualizarPuntajes(actual);
		campo.setEstadoJuego(SurvivorCamp.EN_CURSO);
		armaActual = campo.getPersonaje().getPrincipal();
		panelCampo.actualizarMatador(campo.getPersonaje());
		panelCampo.actualizarEquipada(armaActual);
		panelCampo.actualizarChombis(campo.getZombNodoLejano());
		panelCampo.incorporarJefe(null);
		panelMenu.setVisible(false);
		panelCampo.setVisible(true);
		add(panelCampo, BorderLayout.CENTER);
		panelCampo.requestFocusInWindow();
		HiloGeneradorDeZombies generador = new HiloGeneradorDeZombies(this, campo);
		generador.start();
		HiloEnemigo hE = new HiloEnemigo(this, campo.getZombNodoCercano(), campo);
		hE.start();
	}

	/**
	 * pregunta si en el PanelCamp se están cargando las imágenes
	 * 
	 * @return true si aún se están cargando
	 */
	public boolean estaCargando() {
		boolean pintando = false;
		if (panelCampo.getDebugGraphicsOptions() == DebugGraphics.BUFFERED_OPTION)
			pintando = true;
		return pintando;
	}

	/**
	 * obtiene el puntaje/score actual del personaje
	 * 
	 * @return puntaje
	 */
	public int getPuntajeActual() {
		return campo.getPersonaje().getScore();
	}

	/**
	 * Carga la partida guardada y actualiza todos los componentes que la usan
	 */
	public void cargarJuego() {
		try {
			Puntaje actuales = campo.getRaizPuntajes();
			SurvivorCamp partida = campo.cargarPartida();
			campo.setEstadoJuego(SurvivorCamp.SIN_PARTIDA);
			campo = partida;
			campo.actualizarPuntajes(actuales);
			panelCampo.actualizarMatador(campo.getPersonaje());
			panelCampo.actualizarChombis(campo.getZombNodoLejano());
			armaActual = campo.getPersonaje().getPrincipal();
			panelCampo.actualizarEquipada(armaActual);
			panelCampo.actualizarRonda();
			cambiarPuntero();
			panelMenu.setVisible(false);
			panelCampo.setVisible(true);
			campo.setEstadoJuego(campo.EN_CURSO);
			add(panelCampo, BorderLayout.CENTER);
			panelCampo.requestFocusInWindow();
			HiloEnemigo hE = new HiloEnemigo(this, campo.getZombNodoCercano(), campo);
			HiloGeneradorDeZombies generador = new HiloGeneradorDeZombies(this, campo);
			hE.start();
			generador.start();
			iniciarGemi2();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
	}

	/**
	 * Guarda la partida que está en curso
	 */
	public void guardarJuego() {
		try {
			campo.guardarPartida();
			JOptionPane.showMessageDialog(this, "Partida Guardada");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
	}

	/**
	 * repinta el panelCampo para mostrar los zombies en movimiento
	 */
	public void refrescar() {
		panelCampo.repaint();
	}

	public static void main(String[] args) {
		InterfazZombieKiller inter = new InterfazZombieKiller();
		inter.setVisible(true);
		inter.setLocationRelativeTo(null);
	}

	/**
	 * <pre></pre>
	 * 
	 * el juego no se encuentra pausado dispara el arma principal en la posición
	 * pasada por parámetro
	 * 
	 * @param posX
	 * @param posY
	 */
	public void disparar(int posX, int posY) {
		if (campo.leDio(posX, posY)) {
			reproducir("leDio" + armaActual.getClass().getSimpleName());
		} else
			reproducir("disparo" + armaActual.getClass().getSimpleName());
		HiloArma hA = new HiloArma(this, armaActual);
		hA.start();
	}

	/**
	 * inicia el sonido de los zombies
	 */
	public void iniciarGemi2() {
		sonidoFondo = new HiloSonido("zombies");
		sonidoFondo.start();
	}

	/**
	 * termina el sonido de los zombies
	 */
	public void terminarGemi2() {
		if (sonidoFondo != null)
			sonidoFondo.detenerSonido();
	}

	/**
	 * genera un zombie en la partida dada la ronda actual
	 * 
	 * @param nivel
	 */
	public void generarZombie(int nivel) {
		Zombie chombi = campo.generarZombie(nivel);
	}

	/**
	 * Ejecuta los efectos tras ser atacado por un enemigo
	 */
	public void leDaAPersonaje() {
		reproducir("meDio");
		campo.enemigoAtaca();
		panelCampo.zombieAtaco();
	}

	/**
	 * Pausa y despausa el juego
	 */
	public void pausarJuego() {
		char estado = campo.pausarJuego();
		if (estado == SurvivorCamp.PAUSADO) {
			terminarGemi2();
			panelMenu.setVisible(true);
			panelCampo.setVisible(false);
			panelMenu.updateUI();
			panelMenu.requestFocusInWindow();
		} else {
			iniciarGemi2();
			panelCampo.setVisible(true);
			panelMenu.setVisible(false);
			panelCampo.updateUI();
			panelCampo.requestFocusInWindow();
		}
	}

	/**
	 * <pre></pre>
	 * 
	 * existe por lo menos una granada en el bolsillo del personaje Ejecuta los
	 * efectos que trae lanzar una granada
	 */
	public void granadaLanzada() {
		campo.seLanzoGranada();
		HiloArma hA = new HiloArma(this, campo.getPersonaje().getGranadas());
		hA.start();
		reproducir("bomba");
	}

	/**
	 * reabastece la carga del arma principal
	 */
	public void cargarArmaPersonaje() {
		campo.getPersonaje().cargo();
		reproducir("carga" + armaActual.getClass().getSimpleName());
		HiloArma hA = new HiloArma(this, armaActual);
		hA.start();
	}

	/**
	 * reproduce cualquier sonido que se encuentre en la carpeta sonidos
	 * 
	 * @param ruta
	 */
	public void reproducir(String ruta) {
		HiloSonido efecto = new HiloSonido(ruta);
		efecto.start();
	}

	/**
	 * cambia el arma del personaje y actualiza aquí
	 */
	public void cambiarArma() {
		campo.cambiarArma();
		armaActual = campo.getPersonaje().getPrincipal();
		cambiarPuntero();
	}

	/**
	 * cambia el cursor de acuerdo al arma principal
	 */
	public void cambiarPuntero() {
		if (armaActual instanceof Remington)
			setCursor(miraRemington);
		else
			setCursor(miraM1911);
	}

	/**
	 * termina el efecto del disparo con sangre
	 */
	public void terminarEfectoDeSangre() {
		armaActual.setEnsangrentada(false);
		panelCampo.quitarSangreZombie();
	}

	/**
	 * obtiene la ronda en la que se encuentra
	 */
	public byte darRondaActual() {
		return campo.getRondaActual();
	}

	/**
	 * sube la ronda actual, suena la sirena al avanzar
	 * 
	 * @param nivel
	 */
	public void subirDeRonda(int nivel) {
		terminarGemi2();
		reproducir("sirena");
		campo.actualizarRondaActual((byte) nivel);
		campo.setEstadoJuego(SurvivorCamp.INICIANDO_RONDA);
		panelCampo.actualizarRonda();
	}

	/**
	 * <pre>
	 * la posición en el eje Y está por debajo de la que el zombie ataca
	 * </pre>
	 * 
	 * intenta acuchillar
	 * 
	 * @param x
	 * @param y
	 */
	public void acuchillar(int x, int y) {
		if (campo.acuchilla(x, y)) {
			setCursor(cursorCuchillo);
			reproducir("leDioCuchillo");
			HiloArma hA = new HiloArma(this, campo.getPersonaje().getCuchillo());
			hA.start();
		} else if (armaActual.getMunicion() == 0)
			reproducir("sin_balas");
	}

	/**
	 * genera el jefe con su respectivo hilo
	 */
	public void generarBoss() {
		Boss aMatar = campo.generarBoss();
		panelCampo.incorporarJefe(aMatar);
		HiloBoss hB = new HiloBoss(this, aMatar, campo);
		hB.start();
	}

	/**
	 * Muestra el Panel de Cómo jugar / Lo oculta
	 */
	public void mostrarComoJugar() {
		if (panelMenu.isVisible()) {
			panelMenu.setVisible(false);
			panelComoJugar.setVisible(true);
			add(panelComoJugar, BorderLayout.CENTER);
		} else {
			panelComoJugar.setVisible(false);
			panelMenu.setVisible(true);
		}
	}

	/**
	 * Muestra el Panel donde se encuentran los puntjes / lo oculta
	 */
	public void mostrarPuntajes() {
		if (panelMenu.isVisible()) {
			panelPuntajes.actualizarPuntajes(campo.ordenarPuntajePorScore());
			panelMenu.setVisible(false);
			panelPuntajes.setVisible(true);
			add(panelPuntajes, BorderLayout.CENTER);
		} else {
			panelPuntajes.setVisible(false);
			panelMenu.setVisible(true);
		}
	}

	/**
	 * Muestra el Panel donde se encuentran los créditos / lo oculta
	 */
	public void mostrarCreditos() {
		if (panelMenu.isVisible()) {
			panelMenu.setVisible(false);
			panelCreditos.setVisible(true);
			add(panelCreditos, BorderLayout.CENTER);
		} else {
			panelCreditos.setVisible(false);
			panelMenu.setVisible(true);
		}
	}

	/**
	 * obtiene el número de referencia al arma que se muestra en el panelArmas
	 * 
	 * @return número de referencia
	 */
	public int darArmaMostrada() {
		return campo.getArmaMostrada();
	}

	/**
	 * Cambia el arma que se está viendo por el de la derecha
	 * 
	 * @return número de referencia al arma de la derecha
	 */
	public int cambiarArmaVisibleDerecha() {
		return campo.moverArmaVisibleDerecha();
	}

	/**
	 * Cambia el arma que se está viendo por el de la izquierda
	 * 
	 * @return número de referencia al arma de la izquierda
	 */
	public int cambiarArmaVisibleIzquierda() {
		return campo.moverArmaVisibleIzquierda();
	}

	/**
	 * Método llamado cuando el personaje muere para verificar si el jugador
	 * desea seguir o no
	 */
	public void juegoTerminado() {
		boolean seLlamoDeNuevo = false;
		int aceptoGuardarScore = JOptionPane.showConfirmDialog(this,
				"Su puntaje fue: " + campo.getPersonaje().getScore() + ", con " + campo.getPersonaje().getMatanza()
						+ " bajas y en la Ronda " + campo.getRondaActual() + ". Desea guardar su puntaje?",
				"Juego Terminado", JOptionPane.YES_NO_OPTION);
		if (aceptoGuardarScore == JOptionPane.YES_OPTION) {
			String nombrePlayer = JOptionPane.showInputDialog(this, "Escribe tu nombre");
			if (nombrePlayer != null && !nombrePlayer.equals(""))
				try {
					campo.verificarNombre(nombrePlayer);
					campo.aniadirMejoresPuntajes(nombrePlayer);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(this,
							"Error al guardar el puntaje, es posible que haya abierto el juego desde \"Acceso rápido\"");
				} catch (NombreInvalidoException e) {
					JOptionPane.showMessageDialog(this, e.getMessage());
					juegoTerminado();
				}
			else {
				seLlamoDeNuevo = true;
				juegoTerminado();
			}
		}
		if (!seLlamoDeNuevo) {
			int aceptoJugar = JOptionPane.showConfirmDialog(this, "Desea volver a jugar?", "Juego Terminado",
					JOptionPane.YES_NO_OPTION);
			if (aceptoJugar == JOptionPane.YES_OPTION)
				iniciarNuevaPartida();
			else {
				panelCampo.setVisible(false);
				panelMenu.setVisible(true);
			}
		}
		terminarGemi2();
	}

	/**
	 * Método que se ejecuta cuando el Boss muere
	 */
	public void victoria() {
		String nombrePlayer = JOptionPane.showInputDialog(this,
				"Enhorabuena, has pasado todas los niveles de dificultad. su puntaje final es: "
						+ campo.getPersonaje().getScore() + ". Escribe tu nombre");
		if (nombrePlayer != null && !nombrePlayer.equals(""))
		try {
			campo.verificarNombre(nombrePlayer);
			campo.aniadirMejoresPuntajes(nombrePlayer);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this,
					"Error al guardar el puntaje, es posible que haya abierto el juego desde \"Acceso rápido\"");
		} catch (NombreInvalidoException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
			victoria();
		}
		else {
			victoria();
		}
		panelMenu.setVisible(true);
		panelCampo.setVisible(false);
		terminarGemi2();
	}

	/**
	 * Llama al método de ordenar por bajas
	 */
	public void ordenarPorBajas() {
		panelPuntajes.actualizarPuntajes(campo.ordenarPuntajePorBajas());
	}

	/**
	 * Llama al método de ordenar por bajas con tiro a la cabeza
	 */
	public void ordenarPorHeadshot() {
		panelPuntajes.actualizarPuntajes(campo.ordenarPuntajePorTirosALaCabeza());
	}

	/**
	 * busca el mejor puntaje del nombre
	 */
	public void buscarPorNombre() {
		String nombre = JOptionPane.showInputDialog("Ingrese el nombre que desea buscar");
		if (nombre != null) {
			Puntaje buscado = campo.buscarPuntajeDe(nombre);
			panelPuntajes.mostrarPuntajeDe(buscado);
		}
	}

	/**
	 * Llama al método de ordenar por puntaje
	 */
	public void ordenarPorScore() {
		panelPuntajes.actualizarPuntajes(campo.ordenarPuntajePorScore());
	}

}
