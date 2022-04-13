package interfaz;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.io.IOException;
import javax.swing.DebugGraphics;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import facade.ThreadsFacade;
import mundo.ArmaDeFuego;
import mundo.Boss;
import mundo.Caminante;
import mundo.Cuchillo;
import mundo.Granada;
import mundo.NombreInvalidoException;
import mundo.Puntaje;
import mundo.Remington;
import mundo.SurvivorCamp;
import mundo.Zombie;
import mundo.attackStrategies.AttackStrategyContext;
import mundo.attackStrategies.BossAttackStrategy;
import mundo.attackStrategies.CaminanteAttackStrategy;

public class InterfazZombieKiller extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Campo de juego que contiene a todo el mundo
	 */
	private SurvivorCamp campo;
	/**
	 * Arma que el jugador tiene equipada
	 */
	private ArmaDeFuego armaActual;
	/**
	 * Panel del menu principal cualquier boton muestra otro panel
	 * representatitvo a el
	 */
	private PanelMenu panelMenu;
	/**
	 * Panel del campo de juego
	 */
	private PanelCamp panelCampo;
	/**
	 * Panel que muestra las instrucciones de juego Muestra las estadisticas de
	 * las armas
	 */
	private PanelComoJugar panelComoJugar;
	/**
	 * Panel que muestra los puntajes de los jugadores
	 */
	private PanelPuntajes panelPuntajes;
	/**
	 * Panel que muestra los creditos de las personas que participaron
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
	
	private Boss boss;
	
	private Granada granada;
	
	private Cuchillo cuchillo;

	private ThreadsFacade facade;
	
	private AttackStrategyContext attackStrategy;


	/**
	 * Constructor de la clase principal del juego Aqui se inicializan todos los
	 * componentes necesarios para empezar a jugar
	 */
	public InterfazZombieKiller() {
		long start = System.currentTimeMillis();
		
		BorderLayout custom = new BorderLayout();
		setLayout(custom);
		ImageIcon laterales = new ImageIcon(getClass().getResource("/img/Fondo/iconozombie.png"));
		ImageIcon fondo = new ImageIcon(getClass().getResource("/img/Fondo/fondoMenu.png"));

		miraM1911 = CursorObjectPool.getCursor("/img/Fondo/mira1p.png");
		setCursor(miraM1911);
		panelMenu = new PanelMenu(this);
		add(panelMenu, BorderLayout.CENTER);
		setSize(SurvivorCamp.ANCHO_PANTALLA, SurvivorCamp.ALTO_PANTALLA);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);
		
		long finish = System.currentTimeMillis();
		double elapsed = (finish - start) / 1000.0;
		System.out.println(String.format("Elapsed: %1$f, Start = %2$d, Finish = %3$d", elapsed, start, finish));
		facade = new ThreadsFacade(this);
	}

	/**
	 * Obtiene el estado actual de la partida
	 * 
	 * @return estado
	 */
	public char getEstadoPartida() {
		if (campo == null) {
			return SurvivorCamp.SIN_PARTIDA;
		}
		
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
	 * Metodo auxiliar que inicializa y actualiza la informacion en los
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
		facade.initializeEnemyThreads();
	}

	/**
	 * pregunta si en el PanelCamp se estan cargando las imagenes
	 * 
	 * @return true si aun se estan cargando
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
			panelCampo.requestFocusInWindow();
			facade.initializeEnemyThreads();
			iniciarGemi2();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
	}

	/**
	 * Guarda la partida que esta en curso
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

	/**
	 * <pre></pre>
	 * 
	 * el juego no se encuentra pausado dispara el arma principal en la posicion
	 * pasada por parametro
	 * 
	 * @param posX
	 * @param posY
	 */
	/*public void disparar(int posX, int posY) {
		StrategyContext attackStrategy = new StrategyContext(new AttackShoot(posX, posY));
		
		if (attackStrategy.executeAttack(campo)) {
			reproducir("leDio" + armaActual.getClass().getSimpleName());
		} else
			reproducir("disparo" + armaActual.getClass().getSimpleName());
		panelCampo.incorporarJefe(boss);
		facade.initializeWeaponsThread("armaDeFuego");
	}*/

	/**
	 * inicia el sonido de los zombies
	 */
	public void iniciarGemi2() {
		facade.initializeZombieSoundThread("zombies");
	}

	/**
	 * termina el sonido de los zombies
	 */
	public void terminarGemi2() {
		facade.soundStop();
	}

	/**
	 * genera un zombie en la partida dada la ronda actual
	 * 
	 * @param nivel
	 */
	public void generarZombie(int nivel) {
		Zombie chombi = campo.generarZombie(nivel);
		if (chombi instanceof Caminante) {
			attackStrategy = new AttackStrategyContext(new CaminanteAttackStrategy());
			CaminanteAttackStrategy caminanteAttackStrategy = new CaminanteAttackStrategy();
			caminanteAttackStrategy.moverEnDireccion(chombi);
		}
	}

	/**
	 * Ejecuta los efectos tras ser atacado por un enemigo
	 */
	public void leDaAPersonaje() {
		
		reproducir("meDio");
		//campo.enemigoAtaca();
		attackStrategy = new AttackStrategyContext(new BossAttackStrategy());				
		attackStrategy.enemigoAtaca(campo);
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
	 * reabastece la carga del arma principal
	 */
	public void cargarArmaPersonaje() {
		campo.getPersonaje().cargo();
		reproducir("carga" + armaActual.getClass().getSimpleName());
		facade.initializeWeaponsThread("armaDeFuego");
	}

	/**
	 * reproduce cualquier sonido que se encuentre en la carpeta sonidos
	 * 
	 * @param ruta
	 */
	public void reproducir(String ruta) {
		facade.initializeGeneralSoundThread(ruta);
	}

	/**
	 * cambia el arma del personaje y actualiza aqui
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
	 * la posicion en el eje Y esta por debajo de la que el zombie ataca
	 * </pre>
	 * 
	 * intenta acuchillar
	 * 
	 * @param x
	 * @param y
	 */
	/*public void acuchillar(int x, int y) {
		setCuchillo(campo.getPersonaje().getCuchillo());
		if (campo.acuchilla(x, y)) {
			setCursor(cursorCuchillo);
			reproducir("leDioCuchillo");
			facade.initializeWeaponsThread("cuchillo");
		} else if (armaActual.getMunicion() == 0)
			reproducir("sin_balas");
	}/*

	/**
	 * genera el jefe con su respectivo hilo
	 */
	public void generarBoss() {
		boss = campo.generarBoss();
		panelCampo.incorporarJefe(boss);
		BossAttackStrategy bossAttackStrategy = new BossAttackStrategy();
		attackStrategy = new AttackStrategyContext(bossAttackStrategy);
		bossAttackStrategy.moverEnDireccion(boss);
		facade.initializeBossThread();
	}

	/**
	 * Muestra el Panel de Como jugar / Lo oculta
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
	 * Muestra el Panel donde se encuentran los creditos / lo oculta
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
	 * obtiene el numero de referencia al arma que se muestra en el panelArmas
	 * 
	 * @return numero de referencia
	 */
	public int darArmaMostrada() {
		return campo.getArmaMostrada();
	}

	/**
	 * Cambia el arma que se esta viendo por el de la derecha
	 * 
	 * @return numero de referencia al arma de la derecha
	 */
	public int cambiarArmaVisibleDerecha() {
		return campo.moverArmaVisibleDerecha();
	}

	/**
	 * Cambia el arma que se esta viendo por el de la izquierda
	 * 
	 * @return numero de referencia al arma de la izquierda
	 */
	public int cambiarArmaVisibleIzquierda() {
		return campo.moverArmaVisibleIzquierda();
	}

	/**
	 * Metodo llamado cuando el personaje muere para verificar si el jugador
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
							"Error al guardar el puntaje, es posible que haya abierto el juego desde \"Acceso rapido\"");
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
	 * Metodo que se ejecuta cuando el Boss muere
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
					"Error al guardar el puntaje, es posible que haya abierto el juego desde \"Acceso rapido\"");
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
	 * Llama al metodo de ordenar por bajas
	 */
	public void ordenarPorBajas() {
		panelPuntajes.actualizarPuntajes(campo.ordenarPuntajePorBajas());
	}

	/**
	 * Llama al metodo de ordenar por bajas con tiro a la cabeza
	 */
	public void ordenarPorHeadshot() {
		panelPuntajes.actualizarPuntajes(campo.ordenarPuntajePorTirosALaCabeza());
	}

	/**
	 * busca el mejor puntaje del nombre
	 */
	public void buscarPorNombre(String nombreBuscado) {
		if (nombreBuscado != null) {
			Puntaje buscado = campo.buscarPuntajeDe(nombreBuscado);
			panelPuntajes.mostrarPuntajeDe(buscado);
		}
	}

	/**
	 * Llama al metodo de ordenar por puntaje
	 */
	public void ordenarPorScore() {
		panelPuntajes.actualizarPuntajes(campo.ordenarPuntajePorScore());
	}
	
	public PanelCamp getPanelCampo() {
		return panelCampo;
	}

	public void setPanelCampo(PanelCamp panelCampo) {
		this.panelCampo = panelCampo;
	}

	public PanelComoJugar getPanelComoJugar() {
		return panelComoJugar;
	}

	public void setPanelComoJugar(PanelComoJugar panelComoJugar) {
		this.panelComoJugar = panelComoJugar;
	}

	public PanelMenu getPanelMenu() {
		return panelMenu;
	}

	public void setPanelMenu(PanelMenu panelMenu) {
		this.panelMenu = panelMenu;
	}

	public PanelPuntajes getPanelPuntajes() {
		return panelPuntajes;
	}

	public void setPanelPuntajes(PanelPuntajes panelPuntajes) {
		this.panelPuntajes = panelPuntajes;
	}

	public PanelCreditos getPanelCreditos() {
		return panelCreditos;
	}

	public void setPanelCreditos(PanelCreditos panelCreditos) {
		this.panelCreditos = panelCreditos;
	}

	public SurvivorCamp getCampo() {
		return campo;
	}

	public void setCampo(SurvivorCamp campo) {
		this.campo = campo;
	}

	public Cursor getMiraM1911() {
		return miraM1911;
	}

	public void setMiraM1911(Cursor miraM1911) {
		this.miraM1911 = miraM1911;
	}

	public Cursor getMiraRemington() {
		return miraRemington;
	}

	public void setMiraRemington(Cursor miraRemington) {
		this.miraRemington = miraRemington;
	}

	public Cursor getCursorCuchillo() {
		return cursorCuchillo;
	}

	public void setCursorCuchillo(Cursor cursorCuchillo) {
		this.cursorCuchillo = cursorCuchillo;
	}
	public ArmaDeFuego getArmaActual() {
		return armaActual;
	}

	public void setArmaActual(ArmaDeFuego armaActual) {
		this.armaActual = armaActual;
	}

	public Boss getBoss() {
		return boss;
	}

	public void setBoss(Boss boss) {
		this.boss = boss;
	}
	
	public Granada getGranada() {
		return granada;
	}

	public void setGranada(Granada granada) {
		this.granada = granada;
	} 
	
	public Cuchillo getCuchillo() {
		return cuchillo;
	}

	public void setCuchillo(Cuchillo cuchillo) {
		this.cuchillo = cuchillo;
	}
	public ThreadsFacade getFacade() {
		return facade;
	}

	public void setFacade(ThreadsFacade facade) {
		this.facade = facade;
	}
}
