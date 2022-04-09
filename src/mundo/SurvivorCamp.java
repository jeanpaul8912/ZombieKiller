package mundo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SurvivorCamp implements Cloneable, Comparator<Puntaje> {

	/**
	 * entero incambiable que representa los pixeles del ancho del juego
	 */
	public static final int ANCHO_PANTALLA = 1000;
	/**
	 * entero incambiable que representa los pixeles del alto del juego
	 */
	public static final int ALTO_PANTALLA = 720;
	/**
	 * entero incambiable que representa el numero de zombies que aparecen en
	 * una ronda
	 */
	public static final int NUMERO_ZOMBIES_RONDA = 16;
	/**
	 * char incambiable que representa el estado del juego pausado
	 */
	public static final char PAUSADO = 'P';
	/**
	 * char incambiable que representa el estado del juego en curso
	 */
	public static final char EN_CURSO = 'J';
	/**
	 * char incambiable que representa el estado del juego sin una partida
	 * iniciada
	 */
	public static final char SIN_PARTIDA = 'N';
	/**
	 * char incambiable que representa el estado del juego iniciando la ronda
	 */
	public static final char INICIANDO_RONDA = 'I';

	/**
	 * Zombie que no aparece en el juego pero sirve como nodo para modificar la
	 * lista facilmente nodo mas cercano al personaje (abajo)
	 */
	private Zombie zombNodoLejano;
	/**
	 * Zombie que no aparece en el juego pero sirve como nodo para modificar la
	 * lista facilmente nodo mas lejano al personaje (arriba)
	 */
	private Zombie zombNodoCercano;
	/**
	 * Personaje en el campo de batalla que esta disparando
	 */
	private Personaje personaje;
	/**
	 * Enemigo final, aparece en la ronda 10
	 */
	private Boss jefe;
	/**
	 * estado del juego, puede ser pausado, en curso, sin partida o iniciando
	 * ronda
	 */
	private char estadoJuego;
	/**
	 * ronda en la que se encuentra la partida actual, varia desde 1 a 10, en la
	 * 10 solo puede estar el jefe
	 */
	private byte rondaActual;
	/**
	 * numero de zombies que han salido a dar la cara en todo el juego
	 */
	private int cantidadZombiesGenerados;
	/**
	 * numero que representa el arma que se esta mostrando en las
	 * especificaciones del arma (panelArmas)
	 */
	private int armaMostrada;
	/**
	 * arreglo de puntajes obtenidos por lo jugadores
	 */
	private ArrayList<Puntaje> mejoresPuntajes;
	/**
	 * raiz del arbol binario de puntajes, tiene los mismos datos del arreglo
	 * mejoresPuntajes pero estan ordenados por Score
	 */
	private Puntaje raizPuntajes;
	
	private Enemigo enemigoCaminante;
	private Enemigo enemigoRastero;

	/**
	 * Constructor de la clase principal del mundo
	 */
	public SurvivorCamp() {
		personaje = new Personaje();
		// aEliminar = new ArrayList<Zombie>();
		estadoJuego = SIN_PARTIDA;
		rondaActual = 0;
		// son caminantes auxiliares, solo necesito las instancias del anterior
		// y el de al frente
		zombNodoLejano = new Caminante();
		zombNodoCercano = new Caminante();
		zombNodoLejano.setLentitud((short) 500);
		zombNodoLejano.setAlFrente(zombNodoCercano);
		zombNodoCercano.setAtras(zombNodoLejano);
		mejoresPuntajes = new ArrayList<>();
		
		enemigoCaminante = new Caminante();
		enemigoRastero = new Rastrero((short)0,zombNodoLejano);
		jefe = new Boss();
	}

	/**
	 * obtiene el estado actual del juego
	 * 
	 * @return estado del juego
	 */
	public char getEstadoJuego() {
		return estadoJuego;
	}

	/**
	 * cambia el estado del juego
	 * 
	 * @param estadoJuego
	 */
	public void setEstadoJuego(char estadoJuego) {
		this.estadoJuego = estadoJuego;
	}

	/**
	 * obtiene la ronda en el instante en que se llama el metodo, 0 si el estado
	 * es sin partida
	 * 
	 * @return ronda actual
	 */
	public byte getRondaActual() {
		return rondaActual;
	}

	/**
	 * obtiene el jefe de la partida, null si no existe
	 * 
	 * @return jefe
	 */
	public Boss getJefe() {
		return jefe;
	}

	/**
	 * cambia la ronda en la que se encuentra, en general sube una ronda, pero
	 * si se carga una partida puede variar
	 */
	public void actualizarRondaActual(byte rondaActual) {
		this.rondaActual = rondaActual;
	}

	/**
	 * obtiene el personaje que esta disparando
	 * 
	 * @return personaje en juego
	 */
	public Personaje getPersonaje() {
		return personaje;
	}

	/**
	 * cambia el personaje que esta disparando, ocurre cuando se carga una
	 * partida
	 */
	public void setPersonaje(Personaje personaje) {
		this.personaje = personaje;
	}

	/**
	 * crea el jefe de la ronda 10
	 * 
	 * @return jefe creado
	 */
	public Boss generarBoss() {
		return	(Boss) jefe.clonar();
	}

	/**
	 * Genera un zombie respecto al nivel en que se encuentra
	 * </pre>
	 * la ronda va de 1 a 9
	 * 
	 * @param nivel
	 *            o ronda en el que se genera
	 * @return zombie creado
	 */
	public Zombie generarZombie(int nivel) {
		short level = (short) nivel;
		Zombie zombie = null;
		int tipoZombie = 0;
		
		if ((level == 3 || level == 4 || level == 8)) {
			tipoZombie = (int) (Math.random() * 2);
		} else if (level == 6 || level == 9) {
			tipoZombie = 1;
		}

		if (tipoZombie == 1) {		
			zombie = (Rastrero) enemigoRastero.clonar();				
		} else {
			zombie = (Caminante) enemigoCaminante.clonar();						
		}
			
		zombie.inicializar(level, zombNodoLejano);
		zombie.introducirse(zombNodoLejano.getAlFrente(), zombNodoLejano);
		cantidadZombiesGenerados++;
		return zombie;
	}

	/**
	 * verifica el numero de zombies que se encuentra en la partida
	 * 
	 * @return numero de zombies
	 */
	public int contarZombies() {
		Zombie actual = zombNodoCercano.getAtras();
		int contador = 0;
		
		while (!actual.getEstadoActual().equals(Zombie.NODO)) {
			contador++;
			actual = actual.getAtras();
		}
		return contador;
	}

	/**
	 * verifica por cada zombie la posicion del disparo hasta que lo encuentre
	 * 
	 * @param posX
	 *            del disparo
	 * @param posY
	 *            del disparo
	 * @return true si le dio a alguno, false si fallo el disparo
	 */
	public boolean leDio(int posX, int posY) {
		personaje.ataco();
		boolean leDio = false;
		Zombie actual = zombNodoCercano.getAtras();
		
		while (!actual.getEstadoActual().equals(Zombie.NODO) && !leDio) {
			if (actual.comprobarDisparo(posX, posY, personaje.getPrincipal().getDanio())) {
				leDio = true;
				personaje.getPrincipal().setEnsangrentada(true);
				if (actual.getSalud() <= 0) {
					personaje.aumentarScore(10 + actual.getSalud() * (-10));
					if (actual.getEstadoActual().equals(Zombie.MURIENDO_HEADSHOT))
						personaje.aumentarTirosALaCabeza();
				}

				personaje.setEnsangrentado(false);
			}
			actual = actual.getAtras();
		}
		if (jefe != null)
			if (jefe.comprobarDisparo(posX, posY, personaje.getPrincipal().getDanio())) {
				personaje.getPrincipal().setEnsangrentada(true);
				personaje.setEnsangrentado(false);
				leDio = true;
				if (jefe.getEstadoActual().equals(Boss.DERROTADO)) {
					personaje.aumentarScore(20 + jefe.getSalud() * (-20));
					estadoJuego = SIN_PARTIDA;
				}
			}
		return leDio;
	}

	/**
	 * cambia los estados del personaje con corde a que recibe un araniazo
	 * zombie, termina el juego si muere
	 */
	public void enemigoAtaca() {
		personaje.setEnsangrentado(true);
		personaje.setSalud((byte) (personaje.getSalud() - 1));
		if (personaje.getSalud() <= 0) {
			estadoJuego = SIN_PARTIDA;
		}
	}

	/**
	 * cambia el estado del juego de pausado a en curso o viceversa
	 * 
	 * @return estado final
	 */
	public char pausarJuego() {
		if (estadoJuego == PAUSADO)
			estadoJuego = EN_CURSO;
		else
			estadoJuego = PAUSADO;
		return estadoJuego;
	}

	/**
	 * Se eliminan todos los zombies que hay en la pantalla, cada uno brinda 50
	 * puntos al personaje
	 */
	public void seLanzoGranada() {
		Zombie actual = zombNodoCercano.getAtras();
		personaje.setEnsangrentado(false);
		while (!actual.getEstadoActual().equals(Zombie.NODO)) {
			if (actual.recibeGranada())
				personaje.aumentarScore(50);
			actual = actual.getAtras();
		}
		personaje.lanzoGranada();
	}

	/**
	 * obtiene el zombie nodo mas lejano al personaje
	 * 
	 * @return zombie nodo de arriba
	 */
	public Zombie getZombNodoLejano() {
		return zombNodoLejano;
	}

	/**
	 * obtiene el zombie nodo mas cercano al personaje
	 * 
	 * @return zombie nodo de abajo
	 */
	public Zombie getZombNodoCercano() {
		return zombNodoCercano;
	}

	/**
	 * carga el Puntaje que se guarda en forma de raiz de AB
	 * 
	 * @throws IOException
	 *             en caso de que no se haya guardado algun puntaje
	 * @throws ClassNotFoundException
	 *             en caso de que haya ocurrido un error al guardar los datos
	 */
	public void cargarPuntajes() throws IOException, ClassNotFoundException {
		File carpeta = new File(System.getProperty("user.dir") + "/PartidasGuardadas");
		File archivoPuntajes = new File(carpeta.getAbsolutePath() + "/puntajes.txt");
		try (ObjectInputStream oIS = new ObjectInputStream(new FileInputStream(archivoPuntajes))) {
			Puntaje puntaje = (Puntaje) oIS.readObject();
			actualizarPuntajes(puntaje);
		}
	}

	/**
	 * asigna la raiz del arbol binario y llena el arreglo de mejores puntajes
	 * 
	 * @param raiz
	 */
	public void actualizarPuntajes(Puntaje raiz) {
		raizPuntajes = raiz;
		if (raizPuntajes != null) {
			mejoresPuntajes = new ArrayList<>();
			raizPuntajes.generarListaInOrden(mejoresPuntajes);
		}
	}

	/**
	 * carga la ultima partida guardada devuelve la partida clonada porque la
	 * actual pasa a estar sin juego y asi elimina los hilos en ejecucion
	 * 
	 * @return una partida con las caracteristicas de la nueva partida
	 * @throws Exception
	 *             de cualquier tipo para mostrar en pantalla
	 */
	public SurvivorCamp cargarPartida() throws Exception {
		File carpeta = new File(System.getProperty("user.dir") + "/PartidasGuardadas");
		File archivoPersonaje = new File(carpeta.getAbsolutePath() + "/personaje.txt");
		try {
			ObjectInputStream oIS = new ObjectInputStream(new FileInputStream(archivoPersonaje));
			Personaje personaje = (Personaje) oIS.readObject();
			oIS.close();
			cargarDatosCampo(carpeta, personaje);
		} catch (IOException e) {
			throw new Exception(
					"No se ha encontrado una partida guardada o es posible que haya abierto el juego desde \"Acceso rapido\"");
		} catch (DatosErroneosException e) {
			throw new Exception(e.getMessage());
		} catch (NumberFormatException e) {
			throw new Exception("En el archivo hay caracteres donde deberian haber numeros");
		}
		return (SurvivorCamp) clone();
	}

	/**
	 * carga los datos de los enemigos del archivo de texto plano
	 * 
	 * @param carpeta
	 * @param personaje
	 *            para asignarselo a la partida si todos los datos son validos
	 * @throws Exception
	 *             si hay informacion invalida
	 */
	private void cargarDatosCampo(File carpeta, Personaje personaje) throws Exception {
		File datosZombie = new File(carpeta.getAbsolutePath() + "/zombies.txt");
		BufferedReader bR = new BufferedReader(new FileReader(datosZombie));
		int ronda = 0;
		if (personaje.getMatanza() % NUMERO_ZOMBIES_RONDA == 0)
			ronda = personaje.getMatanza() / NUMERO_ZOMBIES_RONDA;
		else
			ronda = personaje.getMatanza() / NUMERO_ZOMBIES_RONDA + 1;
		String lineaActual = bR.readLine();
		int contadorZombiesEnPantalla = 0;
		Zombie masCercano = null;
		Zombie ultimoAgregado = null;

		while (lineaActual != null) {
			if (!lineaActual.startsWith("/") && !lineaActual.equals("")) {
				String[] datos = lineaActual.split("_");
				Zombie aAgregar = null;
				byte salud = Byte.parseByte(datos[0]);
				
				if (datos.length > 1) {
					
					int posX = Integer.parseInt(datos[1]);
					int posY = Integer.parseInt(datos[2]);
					String estadoActual = datos[3];
					byte frameActual = Byte.parseByte(datos[4]);
					verificarDatosZombie(posX, posY, estadoActual, frameActual);
					
					if (datos.length == 7) {
						int direccionX = Integer.parseInt(datos[5]);
						int direccionY = Integer.parseInt(datos[6]);
						verificarDatosCaminante(direccionX, direccionY);
						aAgregar = new Caminante(posX, posY, direccionX, direccionY, estadoActual, frameActual, salud,
								ronda);
					} else if (datos.length == 5) {
						aAgregar = new Rastrero(posX, posY, estadoActual, frameActual, salud, ronda);
					}
				}
				
				if (aAgregar != null) {
					if (masCercano != null) {
						ultimoAgregado.setAtras(aAgregar);
						aAgregar.setAlFrente(ultimoAgregado);
					} else
						masCercano = aAgregar;
					ultimoAgregado = aAgregar;
					if (!aAgregar.getEstadoActual().equals(Zombie.MURIENDO)
							&& !aAgregar.getEstadoActual().equals(Zombie.MURIENDO_INCENDIADO))
						contadorZombiesEnPantalla++;
				} else
					cargaBossSiExiste(ronda, salud);
			}
			lineaActual = bR.readLine();
		}
		bR.close();
		int zombiesExcedidos = contadorZombiesEnPantalla + (personaje.getMatanza() % NUMERO_ZOMBIES_RONDA)
				- NUMERO_ZOMBIES_RONDA;
		if (zombiesExcedidos > 0)
			throw new DatosErroneosException(zombiesExcedidos);
		else {
			enlazaZombiesSiHabian(masCercano, ultimoAgregado);
			rondaActual = (byte) ronda;
			cantidadZombiesGenerados = personaje.getMatanza() + contadorZombiesEnPantalla;
			this.personaje = personaje;
		}
	}

	/**
	 * metodo auxiliar para generar un boss con respecto a la salud que le entra
	 * por parametro
	 * 
	 * @param ronda
	 * @param salud
	 */
	private void cargaBossSiExiste(int ronda, byte salud) {
		if (ronda == 10) {
			jefe = new Boss(salud);
			zombNodoCercano.setAtras(zombNodoLejano);
			zombNodoLejano.setAlFrente(zombNodoCercano);
		}
	}

	/**
	 * conecta los zombies cargados a los nodos para que sean parte del juego
	 * 
	 * @param masCercano
	 * @param ultimoAgregado
	 */
	private void enlazaZombiesSiHabian(Zombie masCercano, Zombie ultimoAgregado) {
		if (ultimoAgregado != null) {
			zombNodoCercano.setAtras(masCercano);
			masCercano.setAlFrente(zombNodoCercano);
			zombNodoLejano.setAlFrente(ultimoAgregado);
			ultimoAgregado.setAtras(zombNodoLejano);
			jefe = null;
		}
	}

	/**
	 * metodo auxiliar que verifica las direcciones a las que se mueven los
	 * caminantes la suma de sus direcciones no puede ser menor a 4
	 * 
	 * @param direccionX
	 * @param direccionY
	 * @throws DatosErroneosException
	 */
	private void verificarDatosCaminante(int direccionX, int direccionY) throws DatosErroneosException {
		if (Math.abs(direccionX) + direccionY < 4)
			throw new DatosErroneosException();
	}

	/**
	 * verifica que los datos generales de loz zombies estan dentro de los
	 * limites del juego
	 * 
	 * @param posX
	 * @param posY
	 * @param estadoActual
	 * @param frameActual
	 * @throws DatosErroneosException
	 */
	private void verificarDatosZombie(int posX, int posY, String estadoActual, byte frameActual)
			throws DatosErroneosException {
		if (posX > ANCHO_PANTALLA - Zombie.ANCHO_IMAGEN || posX < 0 || posY > Zombie.POS_ATAQUE
				|| posY < Zombie.POS_INICIAL || frameActual > 31
				|| (!estadoActual.equals(Zombie.CAMINANDO) && !estadoActual.equals(Zombie.MURIENDO_INCENDIADO)
						&& !estadoActual.equals(Zombie.MURIENDO) && !estadoActual.equals(Zombie.ATACANDO)))
			throw new DatosErroneosException();
	}

	/**
	 * guarda la partida actual
	 * 
	 * @throws IOException
	 *             en caso de que el jugador abra el ejecutable desde una
	 *             carpeta invalida
	 */
	public void guardarPartida() throws IOException {
		File carpeta = new File(System.getProperty("user.dir") + "/PartidasGuardadas");
		File archivoPersonaje = new File(carpeta.getAbsolutePath() + "/personaje.txt");
		if (!carpeta.exists())
			carpeta.mkdirs();
		ObjectOutputStream escritor = new ObjectOutputStream(new FileOutputStream(archivoPersonaje));
		escritor.writeObject(personaje);
		escritor.close();
		try {
			guardarDatosCampo(carpeta);
		} catch (IOException e) {
			throw new IOException(
					"Error al guardar el archivo, es posible que haya abierto el juego desde \"Acceso rapido\"");
		}
	}

	/**
	 * escribe los datos de los enemigos
	 * 
	 * @param carpeta
	 *            carpeta en la que se va a guardar el archivo
	 * @throws IOException
	 *             en caso de que ocurra un error inesperado
	 */
	private void guardarDatosCampo(File carpeta) throws IOException {
		File datosZombie = new File(carpeta.getAbsolutePath() + "/zombies.txt");
		BufferedWriter bW = new BufferedWriter(new FileWriter(datosZombie));
		String texto = "/salud/posX/posY/estado/frame/dirX/dirY";
		if (jefe != null)
			texto += "\n" + jefe.getSalud();
		else
			texto = escribirDatosZombie(texto, zombNodoCercano.getAtras());

		bW.write(texto);
		bW.close();
	}

	/**
	 * escribe los datos de los zombies de manera recursiva
	 * 
	 * @param datos
	 * @param actual
	 * @return el texto con la informacion de los zombies
	 */
	private String escribirDatosZombie(String datos, Zombie actual) {
		if (actual.getEstadoActual().equals(Zombie.NODO))
			return datos;
		datos += "\n" + actual.getSalud() + "_" + actual.getPosX() + "_" + actual.getPosY() + "_"
				+ actual.getEstadoActual() + "_" + actual.getFrameActual();
		if (actual instanceof Caminante) {
			datos += "_" + ((Caminante) actual).getDireccionX();
			datos += "_" + ((Caminante) actual).getDireccionY();
		}
		return escribirDatosZombie(datos, actual.getAtras());
	}

	/**
	 * cambia el arma del personaje, en esta version solo tiene 2 armas em total
	 */
	public void cambiarArma() {
		personaje.cambiarArma();
	}

	/**
	 * el enemigo hace lo que le pertenece despues de terminar su golpe
	 * 
	 * @param enemy
	 *            el enemigo que acaba de atacar
	 */
	public void enemigoTerminaSuGolpe(Enemigo enemy) {
		personaje.setEnsangrentado(false);
		enemy.terminaDeAtacar();
	}

	/**
	 * verifica las posiciones de los zombies cercanos y su estado para saber si
	 * puede acuchillar
	 * 
	 * @param x
	 * @param y
	 * @return true si logra achuchillar a alguno
	 */
	public boolean acuchilla(int x, int y) {
		Zombie aAcuchillar = zombNodoCercano.getAtras();
		boolean seEncontro = false;
		
		while (!aAcuchillar.getEstadoActual().equals(Zombie.NODO) && !seEncontro) {
			if (aAcuchillar.getEstadoActual().equals(Zombie.ATACANDO)
					&& aAcuchillar.comprobarDisparo(x, y, Cuchillo.DANIO)) {
				if (aAcuchillar.getEstadoActual().equals(Zombie.MURIENDO))
					personaje.aumentarScore(40);
				seEncontro = true;
				personaje.setEnsangrentado(false);
				personaje.getCuchillo().setEstado(Arma.CARGANDO);
			}
			aAcuchillar = aAcuchillar.getAtras();
		}
		if (jefe != null) {
			if (jefe.getEstadoActual().equals(Enemigo.ATACANDO) && jefe.comprobarDisparo(x, y, Cuchillo.DANIO)) {
				personaje.setEnsangrentado(false);
				personaje.getCuchillo().setEstado(Arma.CARGANDO);
				seEncontro = true;
				if (jefe.getEstadoActual().equals(Boss.DERROTADO)) {
					personaje.aumentarScore(100);
					estadoJuego = SIN_PARTIDA;
				}
			}
		}
		return seEncontro;
	}

	/**
	 * obtiene la cantidad de zombies que han salido en toda la partida
	 * 
	 * @return cantidad de zombies generados
	 */
	public int getCantidadZombiesGenerados() {
		return cantidadZombiesGenerados;
	}

	/**
	 * devuelve el numero de referencia al arma que se encuentra a la derecha de
	 * la actual
	 * 
	 * @return numero del arma Mostrada
	 */
	public int moverArmaVisibleDerecha() {
		if (armaMostrada == 3)
			armaMostrada = 0;
		else
			armaMostrada = armaMostrada + 1;
		return armaMostrada;
	}

	/**
	 * devuelve el numero de referencia al arma que se encuentra a la izquierda
	 * de la actual
	 * 
	 * @return numero del arma Mostrada
	 */
	public int moverArmaVisibleIzquierda() {
		if (armaMostrada == 0)
			armaMostrada = 3;
		else
			armaMostrada = armaMostrada - 1;
		return armaMostrada;
	}

	/**
	 * obtiene el arma que se muestra actualmente en el panelArmas
	 * 
	 * @return numero del arma mostrada
	 */
	public int getArmaMostrada() {
		return armaMostrada;
	}

	/**
	 * aniade un puntaje obtenido por el jugador
	 * 
	 * @param nombreJugador
	 * @throws IOException
	 *             en caso de que ocurra un problema al guardar el puntaje
	 *             serializado
	 */
	public void aniadirMejoresPuntajes(String nombreJugador) throws IOException {
		Puntaje score = new Puntaje(personaje.getScore(), personaje.getHeadShots(), personaje.getMatanza(),
				nombreJugador);
		if (raizPuntajes != null)
			raizPuntajes.aniadirPorPuntaje(score);
		else
			raizPuntajes = score;
		mejoresPuntajes.add(score);
		guardarPuntajes();
	}

	/**
	 * guarda el Puntaje raiz en la carpeta
	 * 
	 * @throws IOException
	 *             en caso de que ocurra un problema al guardar la raiz con las
	 *             nuevas asociaciones
	 */
	private void guardarPuntajes() throws IOException {
		File carpeta = new File(System.getProperty("user.dir") + "/PartidasGuardadas");
		File archivoPuntajes = new File(carpeta.getAbsolutePath() + "/puntajes.txt");
		if (!carpeta.exists())
			carpeta.mkdirs();
		ObjectOutputStream escritor = new ObjectOutputStream(new FileOutputStream(archivoPuntajes));
		escritor.writeObject(raizPuntajes);
		escritor.close();
	}

	/**
	 * ordena el arreglo con corde a la cantidad de kill con tiros a la cabeza
	 * 
	 * @return arreglo de puntajes
	 */
	public ArrayList<Puntaje> ordenarPuntajePorTirosALaCabeza() {
		for (int i = 0; i < mejoresPuntajes.size(); i++) {
			Puntaje masHeadShot = mejoresPuntajes.get(i);
			int posACambiar = i;
			for (int j = i; j < mejoresPuntajes.size() - 1; j++) {
				if (masHeadShot.getTirosALaCabeza() - mejoresPuntajes.get(j + 1).getTirosALaCabeza() < 0) {
					masHeadShot = mejoresPuntajes.get(j + 1);
					posACambiar = j + 1;
				} else if (masHeadShot.getTirosALaCabeza() - mejoresPuntajes.get(j + 1).getTirosALaCabeza() == 0) {
					if (masHeadShot.compareTo(mejoresPuntajes.get(j + 1)) < 0) {
						masHeadShot = mejoresPuntajes.get(j + 1);
						posACambiar = j + 1;
					}
				}
			}
			mejoresPuntajes.set(posACambiar, mejoresPuntajes.get(i));
			mejoresPuntajes.set(i, masHeadShot);
		}
		return mejoresPuntajes;
	}

	/**
	 * ordena el arreglo con corde a la cantidad de kill
	 * 
	 * @return arreglo de puntajes
	 */
	public ArrayList<Puntaje> ordenarPuntajePorBajas() {
		for (int i = 0; i < mejoresPuntajes.size(); i++) {
			Puntaje masKill = mejoresPuntajes.get(i);
			int posACambiar = i;
			for (int j = i; j < mejoresPuntajes.size() - 1; j++) {
				if (compare(masKill, mejoresPuntajes.get(j + 1)) < 0) {
					masKill = mejoresPuntajes.get(j + 1);
					posACambiar = j + 1;
				}
			}
			mejoresPuntajes.set(posACambiar, mejoresPuntajes.get(i));
			mejoresPuntajes.set(i, masKill);
		}
		return mejoresPuntajes;
	}

	/**
	 * crea un arreglo con el arbol binario usando el metodo inOrden
	 * 
	 * @return arreglo de puntajes
	 */
	public List<Puntaje> ordenarPuntajePorScore() {
		List<Puntaje> ordenados = new ArrayList<>();
		if (raizPuntajes != null)
			raizPuntajes.generarListaInOrden(ordenados);
		return ordenados;
	}

	/**
	 * obtiene la raiz del arbol binario de Puntajes
	 * 
	 * @return raizPuntajes
	 */
	public Puntaje getRaizPuntajes() {
		return raizPuntajes;
	}

	@Override
	public int compare(Puntaje o1, Puntaje o2) {
		int porBajas = o1.getBajas() - o2.getBajas();
		if (porBajas != 0)
			return porBajas;
		return o1.compareTo(o2);
	}

	/**
	 * busca el puntaje del nombre ingresado por parametro con busqueda binaria
	 * 
	 * @param nombre
	 * @return mejor puntaje del nombre buscado
	 */
	public Puntaje buscarPuntajeDe(String nombre) {
		mejoresPuntajes.sort(new ComparadorPuntajePorNombre());
		int inicio = 0;
		int fin = mejoresPuntajes.size() - 1;
		Puntaje puntajeBuscado = null;
		int medio = (inicio + fin) / 2;
		while (inicio <= fin && puntajeBuscado == null) {
			if (mejoresPuntajes.get(medio).getNombreKiller().compareToIgnoreCase(nombre) == 0) {
				puntajeBuscado = mejoresPuntajes.get(medio);
				boolean hayMas = true;
				medio = medio + 1;
				while (medio <= fin && hayMas) {
					if (mejoresPuntajes.get(medio).getNombreKiller().compareToIgnoreCase(nombre) == 0)
						puntajeBuscado = mejoresPuntajes.get(medio);
					else
						hayMas = false;
					medio = medio + 1;
				}
			} else if (mejoresPuntajes.get(medio).getNombreKiller().compareToIgnoreCase(nombre) > 0)
				fin = medio - 1;
			else
				inicio = medio + 1;
			medio = (inicio + fin) / 2;
		}
		return puntajeBuscado;
	}

	/**
	 * Verifica que el nombre pasado por parametro sea completamente alfabetico
	 * @param nombrePlayer
	 * @throws NombreInvalidoException
	 */
	public void verificarNombre(String nombrePlayer) throws NombreInvalidoException{
		for (int i = 0; i < nombrePlayer.length(); i++) {
			char caracter = nombrePlayer.charAt(i);
			if((caracter > 90 && caracter < 97) || caracter < 65 || caracter > 122)
			throw new NombreInvalidoException(caracter);
		}
	}
}
