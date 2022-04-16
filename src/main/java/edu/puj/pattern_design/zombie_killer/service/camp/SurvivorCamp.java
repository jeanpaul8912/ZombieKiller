package edu.puj.pattern_design.zombie_killer.service.camp;

import edu.puj.pattern_design.zombie_killer.service.exceptions.DatosErroneosException;
import edu.puj.pattern_design.zombie_killer.service.exceptions.NombreInvalidoException;
import edu.puj.pattern_design.zombie_killer.service.zombies.Boss;
import edu.puj.pattern_design.zombie_killer.service.zombies.Caminante;
import edu.puj.pattern_design.zombie_killer.service.zombies.Enemy;
import edu.puj.pattern_design.zombie_killer.service.zombies.Rastrero;
import edu.puj.pattern_design.zombie_killer.service.zombies.Zombie;
import edu.puj.pattern_design.zombie_killer.service.zombies.ZombieZigZag;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;

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

import static edu.puj.pattern_design.zombie_killer.service.constants.CampConstants.ANCHO_PANTALLA;
import static edu.puj.pattern_design.zombie_killer.service.constants.CampConstants.EN_CURSO;
import static edu.puj.pattern_design.zombie_killer.service.constants.CampConstants.PAUSADO;
import static edu.puj.pattern_design.zombie_killer.service.constants.CampConstants.SIN_PARTIDA;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.CARGANDO;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.KNIFE_DAMAGE;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.LEVELS_TO_IMPROVE_GUNS;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.ANCHO_IMAGEN;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.ATACANDO;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.CAMINANDO;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.DERROTADO;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.MURIENDO;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.MURIENDO_INCENDIADO;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.NODO;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.NUMERO_ZOMBIES_RONDA;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.POS_ATAQUE;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.POS_INICIAL;

@Getter
@Setter
@Slf4j
public class SurvivorCamp implements Cloneable, Comparator<CharacterScore> {

    public static final int CREEPING_ZOMBIE = 1;

    /**
     * Zombie que no aparece en el juego pero sirve como nodo para modificar la
     * lista facilmente nodo mas cercano al personaje (abajo)
     */
    private ZombieZigZag zombNodoLejano;
    /**
     * Zombie que no aparece en el juego pero sirve como nodo para modificar la
     * lista facilmente nodo mas lejano al personaje (arriba)
     */
    private ZombieZigZag zombNodoCercano;
    /**
     * Personaje en el campo de batalla que esta disparando
     */
    private Character character;
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
    private int rondaActual;
    /**
     * numero de zombies que han salido a dar la cara en el juego
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
    private ArrayList<CharacterScore> mejoresCharacterScores;
    /**
     * raiz del arbol binario de puntajes, tiene los mismos datos del arreglo
     * mejoresPuntajes pero estan ordenados por Score
     */
    private CharacterScore raizPuntajes;

    private final Enemy enemyCaminante;

    private Enemy enemyRastero;

    /**
     * Constructor de la clase principal del mundo
     */
    public SurvivorCamp() {
        enemyCaminante = new Caminante();
        character = new Character();
        estadoJuego = SIN_PARTIDA;
        rondaActual = 0;
        zombNodoLejano = new Caminante();
        zombNodoCercano = new Caminante();
        zombNodoLejano.setLentitud((short) 500);
        zombNodoLejano.setAlFrente(zombNodoCercano);
        zombNodoCercano.setAtras(zombNodoLejano);
        mejoresCharacterScores = new ArrayList<>();
        enemyRastero = new Rastrero((short) 0, zombNodoLejano);
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
    public int getRondaActual() {
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
    public void actualizarRondaActual(int rondaActual) {
        this.rondaActual = rondaActual;
        improveGuns(rondaActual);
    }

    private void improveGuns(int rondaActual) {
        if (rondaActual % LEVELS_TO_IMPROVE_GUNS == 0) {
            character.getPrincipalWeapon().improveGun();
        }

        if (rondaActual % (LEVELS_TO_IMPROVE_GUNS * 2) == 0) {
            character.getPrincipalWeapon().improveGun();
            character.getGrenades().improveGun();
        }
    }

    /**
     * obtiene el personaje que esta disparando
     *
     * @return personaje en juego
     */
    public Character getPersonaje() {
        return character;
    }

    /**
     * crea el jefe de la ronda 10
     *
     * @return jefe creado
     */
    public Boss generarBoss() {
        jefe = (Boss) jefe.clonar();
        return jefe;
    }

    /**
     * Genera un zombie respecto al nivel en que se encuentra
     * </pre>
     * la ronda va de 1 a 9
     *
     * @param nivel o ronda en el que se genera
     * @return zombie creado
     */
    public Zombie generarZombie(int nivel) {
        short level = (short) nivel;
        Zombie zombie;
        int tipoZombie = 0;

        if ((level == 3 || level == 4 || level == 8)) {
            tipoZombie = (int) (Math.random() * 2);
        } else if (level == 6 || level == 9) {
            tipoZombie = CREEPING_ZOMBIE;
        }

        if (tipoZombie == CREEPING_ZOMBIE) {
            zombie = (Rastrero) enemyRastero.clonar();
        } else {
            zombie = (Caminante) enemyCaminante.clonar();
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

        while (!actual.getEstadoActual().equals(NODO)) {
            contador++;
            actual = actual.getAtras();
        }

        return contador;
    }

    /**
     * cambia el estado del juego de pausado a en curso o viceversa
     *
     * @return estado final
     */
    public char pausarJuego() {
        if (estadoJuego == PAUSADO) {
            estadoJuego = EN_CURSO;
        } else {
            estadoJuego = PAUSADO;
        }

        return estadoJuego;
    }

    /**
     * obtiene el zombie nodo mas lejano al personaje
     *
     * @return zombie nodo de arriba
     */
    public ZombieZigZag getZombNodoLejano() {
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
     * @throws IOException            en caso de que no se haya guardado algun puntaje
     * @throws ClassNotFoundException en caso de que haya ocurrido un error al guardar los datos
     */
    public void cargarPuntajes() throws IOException, ClassNotFoundException {
        File carpeta = new File(System.getProperty("user.dir") + "/PartidasGuardadas");
        File archivoPuntajes = new File(carpeta.getAbsolutePath() + "/puntajes.txt");
        try (ObjectInputStream oIS = new ObjectInputStream(new FileInputStream(archivoPuntajes))) {
            CharacterScore characterScore = (CharacterScore) oIS.readObject();
            actualizarPuntajes(characterScore);
        }
    }

    /**
     * asigna la raiz del arbol binario y llena el arreglo de mejores puntajes
     *
     * @param raiz
     */
    public void actualizarPuntajes(CharacterScore raiz) {
        raizPuntajes = raiz;
        if (raizPuntajes != null) {
            mejoresCharacterScores = new ArrayList<>();
            raizPuntajes.generarListaInOrden(mejoresCharacterScores);
        }
    }

    /**
     * carga la ultima partida guardada devuelve la partida clonada porque la
     * actual pasa a estar sin juego y asi elimina los hilos en ejecucion
     *
     * @return una partida con las caracteristicas de la nueva partida
     * @throws Exception de cualquier tipo para mostrar en pantalla
     */
    public SurvivorCamp cargarPartida() throws Exception {
        File carpeta = new File(System.getProperty("user.dir") + "/PartidasGuardadas");
        File archivoPersonaje = new File(carpeta.getAbsolutePath() + "/personaje.txt");

        try (ObjectInputStream oIS = new ObjectInputStream(new FileInputStream(archivoPersonaje))) {
            Character loadedCharacter = (Character) oIS.readObject();
            cargarDatosCampo(carpeta, loadedCharacter);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new Exception(
                    "No se ha encontrado una partida guardada o es posible que haya abierto el juego desde \"Acceso rapido\"");
        } catch (DatosErroneosException e) {
            log.error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        } catch (NumberFormatException e) {
            log.error(e.getMessage(), e);
            throw new Exception("En el archivo hay caracteres donde deberian haber numeros");
        }

        return (SurvivorCamp) clone();
    }

    /**
     * carga los datos de los enemigos del archivo de texto plano
     *
     * @param carpeta
     * @param character para asignarselo a la partida si todos los datos son validos
     * @throws Exception si hay informacion invalida
     */
    private void cargarDatosCampo(File carpeta, Character character) throws Exception {
        File datosZombie = new File(carpeta.getAbsolutePath() + "/zombies.txt");

        try (BufferedReader bR = new BufferedReader(new FileReader(datosZombie))) {
            int ronda;

            if (character.getKilling() % NUMERO_ZOMBIES_RONDA == NumberUtils.INTEGER_ZERO) {
                ronda = character.getKilling() / NUMERO_ZOMBIES_RONDA;
            } else {
                ronda = character.getKilling() / NUMERO_ZOMBIES_RONDA + CREEPING_ZOMBIE;
            }

            String lineaActual = bR.readLine();
            int contadorZombiesEnPantalla = 0;
            Zombie masCercano = null;
            Zombie ultimoAgregado = null;

            while (lineaActual != null) {
                if (!lineaActual.startsWith("/") && !lineaActual.equals("")) {
                    String[] datos = lineaActual.split("_");
                    Zombie aAgregar = null;
                    byte salud = Byte.parseByte(datos[0]);

                    if (datos.length > CREEPING_ZOMBIE) {
                        int posX = Integer.parseInt(datos[CREEPING_ZOMBIE]);
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
                        if (!aAgregar.getEstadoActual().equals(MURIENDO)
                                && !aAgregar.getEstadoActual().equals(MURIENDO_INCENDIADO)) {
                            contadorZombiesEnPantalla++;
                        }
                    } else {
                        cargaBossSiExiste(ronda, salud);
                    }
                }

                lineaActual = bR.readLine();
            }

            int zombiesExcedidos = contadorZombiesEnPantalla + (character.getKilling() % NUMERO_ZOMBIES_RONDA)
                    - NUMERO_ZOMBIES_RONDA;

            if (zombiesExcedidos > 0) {
                throw new DatosErroneosException(zombiesExcedidos);
            } else {
                enlazaZombiesSiHabian(masCercano, ultimoAgregado);
                rondaActual = (byte) ronda;
                cantidadZombiesGenerados = character.getKilling() + contadorZombiesEnPantalla;
                this.character = character;
            }
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
        if (Math.abs(direccionX) + direccionY < 4) {
            throw new DatosErroneosException();
        }
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
        if (posX > ANCHO_PANTALLA - ANCHO_IMAGEN || posX < 0 || posY > POS_ATAQUE
                || posY < POS_INICIAL || frameActual > 31
                || (!estadoActual.equals(CAMINANDO) && !estadoActual.equals(MURIENDO_INCENDIADO)
                && !estadoActual.equals(MURIENDO) && !estadoActual.equals(ATACANDO))) {
            throw new DatosErroneosException();
        }
    }

    /**
     * guarda la partida actual
     *
     * @throws IOException en caso de que el jugador abra el ejecutable desde una
     *                     carpeta invalida
     */
    public void guardarPartida() throws IOException {
        File carpeta = new File(System.getProperty("user.dir") + "/PartidasGuardadas");
        File archivoPersonaje = new File(carpeta.getAbsolutePath() + "/personaje.txt");
        if (!carpeta.exists())
            carpeta.mkdirs();
        ObjectOutputStream escritor = new ObjectOutputStream(new FileOutputStream(archivoPersonaje));
        escritor.writeObject(character);
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
     * @param carpeta carpeta en la que se va a guardar el archivo
     * @throws IOException en caso de que ocurra un error inesperado
     */
    private void guardarDatosCampo(File carpeta) throws IOException {
        File datosZombie = new File(carpeta.getAbsolutePath() + "/zombies.txt");
        BufferedWriter bW = new BufferedWriter(new FileWriter(datosZombie));
        String texto = "/salud/posX/posY/estado/frame/dirX/dirY";
        if (jefe != null)
            texto += "\n" + jefe.getHealth();
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
        if (actual.getEstadoActual().equals(NODO))
            return datos;
        datos += "\n" + actual.getHealth() + "_" + actual.getPosX() + "_" + actual.getPosY() + "_"
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
        character.changeWeapon();
    }

    /**
     * el enemigo hace lo que le pertenece despues de terminar su golpe
     *
     * @param enemy el enemigo que acaba de atacar
     */
    public void enemigoTerminaSuGolpe(Enemy enemy) {
        character.setBlooded(false);
        //enemy.terminaDeAtacar();
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

        while (!aAcuchillar.getEstadoActual().equals(NODO) && !seEncontro) {
            if (aAcuchillar.getEstadoActual().equals(ATACANDO)
                    && aAcuchillar.comprobarDisparo(x, y, KNIFE_DAMAGE)) {
                if (aAcuchillar.getEstadoActual().equals(MURIENDO)) {
                    character.increaseScore(40);
                }

                seEncontro = true;
                character.setBlooded(false);
                character.getKnife().setEstado(CARGANDO);
            }

            aAcuchillar = aAcuchillar.getAtras();
        }

        if (jefe != null) {
            if (jefe.getEstadoActual().equals(ATACANDO) && jefe.comprobarDisparo(x, y, KNIFE_DAMAGE)) {
                character.setBlooded(false);
                character.getKnife().setEstado(CARGANDO);
                seEncontro = true;

                if (jefe.getEstadoActual().equals(DERROTADO)) {
                    character.increaseScore(100);
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
            armaMostrada = armaMostrada + CREEPING_ZOMBIE;
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
            armaMostrada = armaMostrada - CREEPING_ZOMBIE;
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
     * @throws IOException en caso de que ocurra un problema al guardar el puntaje
     *                     serializado
     */
    public void aniadirMejoresPuntajes(String nombreJugador) throws IOException {
        CharacterScore characterScore = new CharacterScore(character.getScore(), character.getHeadShots(), character.getKilling(),
                nombreJugador);
        if (raizPuntajes != null)
            raizPuntajes.aniadirPorPuntaje(characterScore);
        else
            raizPuntajes = characterScore;
        mejoresCharacterScores.add(characterScore);
        guardarPuntajes();
    }

    /**
     * guarda el Puntaje raiz en la carpeta
     *
     * @throws IOException en caso de que ocurra un problema al guardar la raiz con las
     *                     nuevas asociaciones
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
    public List<CharacterScore> ordenarPuntajePorTirosALaCabeza() {
        for (int i = 0; i < mejoresCharacterScores.size(); i++) {
            CharacterScore masHeadShot = mejoresCharacterScores.get(i);
            int posACambiar = i;
            for (int j = i; j < mejoresCharacterScores.size() - CREEPING_ZOMBIE; j++) {
                if (masHeadShot.getHeadShoots() - mejoresCharacterScores.get(j + CREEPING_ZOMBIE).getHeadShoots() < 0) {
                    masHeadShot = mejoresCharacterScores.get(j + CREEPING_ZOMBIE);
                    posACambiar = j + CREEPING_ZOMBIE;
                } else if (masHeadShot.getHeadShoots() - mejoresCharacterScores.get(j + CREEPING_ZOMBIE).getHeadShoots() == 0) {
                    if (masHeadShot.compareTo(mejoresCharacterScores.get(j + CREEPING_ZOMBIE)) < 0) {
                        masHeadShot = mejoresCharacterScores.get(j + CREEPING_ZOMBIE);
                        posACambiar = j + CREEPING_ZOMBIE;
                    }
                }
            }

            mejoresCharacterScores.set(posACambiar, mejoresCharacterScores.get(i));
            mejoresCharacterScores.set(i, masHeadShot);
        }

        return mejoresCharacterScores;
    }

    /**
     * ordena el arreglo con corde a la cantidad de kill
     *
     * @return arreglo de puntajes
     */
    public List<CharacterScore> ordenarPuntajePorBajas() {
        for (int i = 0; i < mejoresCharacterScores.size(); i++) {
            CharacterScore masKill = mejoresCharacterScores.get(i);
            int posACambiar = i;

            for (int j = i; j < mejoresCharacterScores.size() - CREEPING_ZOMBIE; j++) {
                if (compare(masKill, mejoresCharacterScores.get(j + CREEPING_ZOMBIE)) < 0) {
                    masKill = mejoresCharacterScores.get(j + CREEPING_ZOMBIE);
                    posACambiar = j + CREEPING_ZOMBIE;
                }
            }

            mejoresCharacterScores.set(posACambiar, mejoresCharacterScores.get(i));
            mejoresCharacterScores.set(i, masKill);
        }

        return mejoresCharacterScores;
    }

    /**
     * crea un arreglo con el arbol binario usando el metodo inOrden
     *
     * @return arreglo de puntajes
     */
    public List<CharacterScore> ordenarPuntajePorScore() {
        List<CharacterScore> ordenados = new ArrayList<>();
        if (raizPuntajes != null)
            raizPuntajes.generarListaInOrden(ordenados);
        return ordenados;
    }

    /**
     * obtiene la raiz del arbol binario de Puntajes
     *
     * @return raizPuntajes
     */
    public CharacterScore getRaizPuntajes() {
        return raizPuntajes;
    }

    @Override
    public int compare(CharacterScore o1, CharacterScore o2) {
        int porBajas = o1.getDowns() - o2.getDowns();
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
    public CharacterScore buscarPuntajeDe(String nombre) {
        mejoresCharacterScores.sort(new CompareScoresByName());
        int inicio = 0;
        int fin = mejoresCharacterScores.size() - CREEPING_ZOMBIE;
        CharacterScore characterScoreBuscado = null;
        int medio = (inicio + fin) / 2;
        while (inicio <= fin && characterScoreBuscado == null) {
            if (mejoresCharacterScores.get(medio).getKillerName().compareToIgnoreCase(nombre) == 0) {
                characterScoreBuscado = mejoresCharacterScores.get(medio);
                boolean hayMas = true;
                medio = medio + CREEPING_ZOMBIE;

                while (medio <= fin && hayMas) {
                    if (mejoresCharacterScores.get(medio).getKillerName().compareToIgnoreCase(nombre) == 0) {
                        characterScoreBuscado = mejoresCharacterScores.get(medio);
                    } else {
                        hayMas = false;
                    }

                    medio = medio + CREEPING_ZOMBIE;
                }
            } else if (mejoresCharacterScores.get(medio).getKillerName().compareToIgnoreCase(nombre) > 0) {
                fin = medio - CREEPING_ZOMBIE;
            } else {
                inicio = medio + CREEPING_ZOMBIE;
            }

            medio = (inicio + fin) / 2;
        }

        return characterScoreBuscado;
    }

    /**
     * Verifica que el nombre pasado por parametro sea completamente alfabetico
     *
     * @param nombrePlayer
     * @throws NombreInvalidoException
     */
    public void verificarNombre(String nombrePlayer) throws NombreInvalidoException {
        for (int i = 0; i < nombrePlayer.length(); i++) {
            char caracter = nombrePlayer.charAt(i);

            if ((caracter > 90 && caracter < 97) || caracter < 65 || caracter > 122) {
                throw new NombreInvalidoException(caracter);
            }
        }
    }

    public char getSinPartida() {
        return SIN_PARTIDA;
    }

}
