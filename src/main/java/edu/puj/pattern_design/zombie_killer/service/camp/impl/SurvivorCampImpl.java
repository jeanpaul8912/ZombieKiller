package edu.puj.pattern_design.zombie_killer.service.camp.impl;

import edu.puj.pattern_design.zombie_killer.service.camp.SurvivorCamp;
import edu.puj.pattern_design.zombie_killer.service.constants.SurvivorCampConstants;
import edu.puj.pattern_design.zombie_killer.service.exceptions.DatosErroneosException;
import edu.puj.pattern_design.zombie_killer.service.exceptions.InvalidNameException;
import edu.puj.pattern_design.zombie_killer.service.exceptions.SurvivorCampException;
import edu.puj.pattern_design.zombie_killer.service.zombies.Boss;
import edu.puj.pattern_design.zombie_killer.service.zombies.DragZombie;
import edu.puj.pattern_design.zombie_killer.service.zombies.Enemy;
import edu.puj.pattern_design.zombie_killer.service.zombies.WalkerZombie;
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

import static edu.puj.pattern_design.zombie_killer.service.constants.SurvivorCampConstants.ANCHO_PANTALLA;
import static edu.puj.pattern_design.zombie_killer.service.constants.SurvivorCampConstants.EN_CURSO;
import static edu.puj.pattern_design.zombie_killer.service.constants.SurvivorCampConstants.PAUSADO;
import static edu.puj.pattern_design.zombie_killer.service.constants.SurvivorCampConstants.SIN_PARTIDA;
import static edu.puj.pattern_design.zombie_killer.service.constants.SurvivorCampConstants.USER_DIR;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.CARGANDO;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.KNIFE_DAMAGE;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.LEVELS_TO_IMPROVE_GUNS;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.ANCHO_IMAGEN;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.ATACANDO;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.CAMINANDO;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.CREEPING_ZOMBIE;
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
public class SurvivorCampImpl implements Cloneable, Comparator<CharacterScore>, SurvivorCamp {


    private ZombieZigZag zombieFarNode;

    private ZombieZigZag zombieNearNode;

    private Character character;

    private Boss boss;

    private char gameStatus;

    private int currentRound;

    private int zombiesGeneratedCount;

    private int showedWeapon;

    private ArrayList<CharacterScore> bestCharacterScores;

    private CharacterScore rootScores;

    private final Enemy enemyWalker;

    private Enemy enemyDrag;

    public SurvivorCampImpl() {
        enemyWalker = new WalkerZombie();
        character = new Character();
        gameStatus = SIN_PARTIDA;
        currentRound = 0;
        zombieFarNode = new WalkerZombie();
        zombieNearNode = new WalkerZombie();
        zombieFarNode.setSpeed((short) 500);
        zombieFarNode.setInFront(zombieNearNode);
        zombieNearNode.setInBack(zombieFarNode);
        bestCharacterScores = new ArrayList<>();
        enemyDrag = new DragZombie((short) 0, zombieFarNode);
        boss = new Boss();
    }

    public void updateCurrentRound(int level) {
        this.currentRound = level;
        improveGuns(level);
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

    public Boss generateBoss() {
        boss = (Boss) boss.cloneEnemy();
        return boss;
    }

    public Zombie generateZombie(int level) {
        short newLevel = (short) level;
        Zombie zombie;
        int zombieType = 0;

        if ((newLevel == 3 || newLevel == 4 || newLevel == 8)) {
            zombieType = (int) (Math.random() * 2);
        } else if (newLevel == 6 || newLevel == 9) {
            zombieType = CREEPING_ZOMBIE;
        }

        if (zombieType == CREEPING_ZOMBIE) {
            zombie = (DragZombie) enemyDrag.cloneEnemy();
        } else {
            zombie = (WalkerZombie) enemyWalker.cloneEnemy();
        }

        zombie.inicialize(newLevel, zombieFarNode);
        zombie.introduce(zombieFarNode.getInFront(), zombieFarNode);
        zombiesGeneratedCount++;
        return zombie;
    }

    public char pauseGame() {
        if (gameStatus == PAUSADO) {
            gameStatus = EN_CURSO;
        } else {
            gameStatus = PAUSADO;
        }

        return gameStatus;
    }

    public void loadScores() throws IOException, ClassNotFoundException {
        File directory = new File(System.getProperty(USER_DIR) + SurvivorCampConstants.PARTIDAS_GUARDADAS);
        File scoresFile = new File(directory.getAbsolutePath() + "/puntajes.txt");

        try (ObjectInputStream oIS = new ObjectInputStream(new FileInputStream(scoresFile))) {
            CharacterScore characterScore = (CharacterScore) oIS.readObject();
            updateScores(characterScore);
        }
    }

    public void updateScores(CharacterScore root) {
        rootScores = root;

        if (rootScores != null) {
            bestCharacterScores = new ArrayList<>();
            rootScores.generateInOrderList(bestCharacterScores);
        }
    }

    public SurvivorCampImpl loadGame() throws SurvivorCampException, DatosErroneosException, CloneNotSupportedException {
        File directory = new File(System.getProperty(USER_DIR) + SurvivorCampConstants.PARTIDAS_GUARDADAS);
        File characterFile = new File(directory.getAbsolutePath() + "/personaje.txt");

        try (ObjectInputStream oIS = new ObjectInputStream(new FileInputStream(characterFile))) {
            Character loadedCharacter = (Character) oIS.readObject();
            loadCampData(directory, loadedCharacter);
        } catch (IOException | ClassNotFoundException e) {
            log.error(e.getMessage(), e);
            throw new SurvivorCampException(
                    "No se ha encontrado una partida guardada o es posible que haya abierto el juego desde \"Acceso rapido\"");
        } catch (NumberFormatException e) {
            log.error(e.getMessage(), e);
            throw new DatosErroneosException("En el archivo hay caracteres donde deberian haber números.");
        }

        return (SurvivorCampImpl) clone();
    }

    private void loadCampData(File carpeta, Character character) throws IOException {
        File zombieData = new File(carpeta.getAbsolutePath() + "/zombies.txt");

        try (BufferedReader bR = new BufferedReader(new FileReader(zombieData))) {
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
                            aAgregar = new WalkerZombie(posX, posY, direccionX, direccionY, estadoActual, frameActual, salud,
                                    ronda);
                        } else if (datos.length == 5) {
                            aAgregar = new DragZombie(posX, posY, estadoActual, frameActual, salud, ronda);
                        }
                    }

                    if (aAgregar != null) {
                        if (masCercano != null) {
                            ultimoAgregado.setInBack(aAgregar);
                            aAgregar.setInFront(ultimoAgregado);
                        } else
                            masCercano = aAgregar;
                        ultimoAgregado = aAgregar;
                        if (!aAgregar.getCurrentStatus().equals(MURIENDO)
                                && !aAgregar.getCurrentStatus().equals(MURIENDO_INCENDIADO)) {
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
                currentRound = (byte) ronda;
                zombiesGeneratedCount = character.getKilling() + contadorZombiesEnPantalla;
                this.character = character;
            }
        } catch (DatosErroneosException e) {
            throw new RuntimeException(e);
        }
    }

    private void cargaBossSiExiste(int ronda, byte salud) {
        if (ronda == 10) {
            boss = new Boss(salud);
            zombieNearNode.setInBack(zombieFarNode);
            zombieFarNode.setInFront(zombieNearNode);
        }
    }

    private void enlazaZombiesSiHabian(Zombie masCercano, Zombie ultimoAgregado) {
        if (ultimoAgregado != null) {
            zombieNearNode.setInBack(masCercano);
            masCercano.setInFront(zombieNearNode);
            zombieFarNode.setInFront(ultimoAgregado);
            ultimoAgregado.setInBack(zombieFarNode);
            boss = null;
        }
    }

    private void verificarDatosCaminante(int direccionX, int direccionY) throws DatosErroneosException {
        if (Math.abs(direccionX) + direccionY < 4) {
            throw new DatosErroneosException();
        }
    }

    private void verificarDatosZombie(int posX, int posY, String estadoActual, byte frameActual)
            throws DatosErroneosException {
        if (posX > ANCHO_PANTALLA - ANCHO_IMAGEN || posX < 0 || posY > POS_ATAQUE
                || posY < POS_INICIAL || frameActual > 31
                || (!estadoActual.equals(CAMINANDO) && !estadoActual.equals(MURIENDO_INCENDIADO)
                && !estadoActual.equals(MURIENDO) && !estadoActual.equals(ATACANDO))) {
            throw new DatosErroneosException();
        }
    }

    public void saveGame() throws IOException {
        File carpeta = new File(System.getProperty(USER_DIR) + SurvivorCampConstants.PARTIDAS_GUARDADAS);
        File archivoPersonaje = new File(carpeta.getAbsolutePath() + "/personaje.txt");

        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }

        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(archivoPersonaje))) {
            objectOutputStream.writeObject(character);
            guardarDatosCampo(carpeta);
        } catch (IOException e) {
            throw new IOException(
                    "Error al guardar el archivo, es posible que haya abierto el juego desde \"Acceso rapido\".");
        }
    }

    private void guardarDatosCampo(File carpeta) throws IOException {
        File datosZombie = new File(carpeta.getAbsolutePath() + "/zombies.txt");

        try (BufferedWriter bW = new BufferedWriter(new FileWriter(datosZombie))) {
            String texto = "/salud/posX/posY/estado/frame/dirX/dirY";

            if (boss != null) {
                texto += "\n" + boss.getHealth();
            } else {
                texto = escribirDatosZombie(texto, zombieNearNode.getInBack());
            }

            bW.write(texto);
        }
    }

    private String escribirDatosZombie(String datos, Zombie actual) {
        if (actual.getCurrentStatus().equals(NODO)) {
            return datos;
        }

        datos += "\n" + actual.getHealth() + "_" + actual.getPosX() + "_" + actual.getPosY() + "_"
                + actual.getCurrentStatus() + "_" + actual.getCurrentFrame();

        if (actual instanceof WalkerZombie) {
            datos += "_" + actual.getDirectionX();
            datos += "_" + actual.getDirectionY();
        }

        return escribirDatosZombie(datos, actual.getInBack());
    }

    public void changeWeapon() {
        character.changeWeapon();
    }

    public boolean acuchilla(int x, int y) {
        Zombie aAcuchillar = zombieNearNode.getInBack();
        boolean seEncontro = false;

        while (!aAcuchillar.getCurrentStatus().equals(NODO) && !seEncontro) {
            if (aAcuchillar.getCurrentStatus().equals(ATACANDO)
                    && aAcuchillar.checkShoot(x, y, KNIFE_DAMAGE)) {
                if (aAcuchillar.getCurrentStatus().equals(MURIENDO)) {
                    character.increaseScore(40);
                }

                seEncontro = true;
                character.setBlooded(false);
                character.getKnife().setStatus(CARGANDO);
            }

            aAcuchillar = aAcuchillar.getInBack();
        }

        if (boss != null) {
            if (boss.getCurrentStatus().equals(ATACANDO) && boss.checkShoot(x, y, KNIFE_DAMAGE)) {
                character.setBlooded(false);
                character.getKnife().setStatus(CARGANDO);
                seEncontro = true;

                if (boss.getCurrentStatus().equals(DERROTADO)) {
                    character.increaseScore(100);
                    gameStatus = SIN_PARTIDA;
                }
            }
        }

        return seEncontro;
    }

    public int getZombiesGeneratedCount() {
        return zombiesGeneratedCount;
    }

    public void addToBestScores(String playerName) throws IOException {
        CharacterScore characterScore = new CharacterScore(character.getScore(), character.getHeadShots(), character.getKilling(),
                playerName);
        if (rootScores != null) {
            rootScores.aniadirPorPuntaje(characterScore);
        } else {
            rootScores = characterScore;
        }

        bestCharacterScores.add(characterScore);
        saveScores();
    }

    private void saveScores() throws IOException {
        File carpeta = new File(System.getProperty(USER_DIR) + SurvivorCampConstants.PARTIDAS_GUARDADAS);
        File archivoPuntajes = new File(carpeta.getAbsolutePath() + "/puntajes.txt");

        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }

        try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(archivoPuntajes))) {
            writer.writeObject(rootScores);
        }
    }

    public List<CharacterScore> sortScoresByHeadShots() {
        for (int i = 0; i < bestCharacterScores.size(); i++) {
            CharacterScore masHeadShot = bestCharacterScores.get(i);
            int posACambiar = i;

            for (int j = i; j < bestCharacterScores.size() - CREEPING_ZOMBIE; j++) {
                if (masHeadShot.getHeadShoots() - bestCharacterScores.get(j + CREEPING_ZOMBIE).getHeadShoots() < 0) {
                    masHeadShot = bestCharacterScores.get(j + CREEPING_ZOMBIE);
                    posACambiar = j + CREEPING_ZOMBIE;
                } else if (masHeadShot.getHeadShoots() - bestCharacterScores.get(j + CREEPING_ZOMBIE).getHeadShoots() == 0) {
                    if (masHeadShot.compareTo(bestCharacterScores.get(j + CREEPING_ZOMBIE)) < 0) {
                        masHeadShot = bestCharacterScores.get(j + CREEPING_ZOMBIE);
                        posACambiar = j + CREEPING_ZOMBIE;
                    }
                }
            }

            bestCharacterScores.set(posACambiar, bestCharacterScores.get(i));
            bestCharacterScores.set(i, masHeadShot);
        }

        return bestCharacterScores;
    }

    public List<CharacterScore> sortScoresByDeadZombies() {
        for (int i = 0; i < bestCharacterScores.size(); i++) {
            CharacterScore masKill = bestCharacterScores.get(i);
            int posACambiar = i;

            for (int j = i; j < bestCharacterScores.size() - CREEPING_ZOMBIE; j++) {
                if (compare(masKill, bestCharacterScores.get(j + CREEPING_ZOMBIE)) < 0) {
                    masKill = bestCharacterScores.get(j + CREEPING_ZOMBIE);
                    posACambiar = j + CREEPING_ZOMBIE;
                }
            }

            bestCharacterScores.set(posACambiar, bestCharacterScores.get(i));
            bestCharacterScores.set(i, masKill);
        }

        return bestCharacterScores;
    }

    public List<CharacterScore> sortScoresByScores() {
        List<CharacterScore> ordenados = new ArrayList<>();
        if (rootScores != null)
            rootScores.generateInOrderList(ordenados);
        return ordenados;
    }

    @Override
    public int compare(CharacterScore o1, CharacterScore o2) {
        int porBajas = o1.getDowns() - o2.getDowns();

        if (porBajas != 0) {
            return porBajas;
        }

        return o1.compareTo(o2);
    }

    public CharacterScore searchByScoreOfPlayerName(String playerName) {
        bestCharacterScores.sort(new CompareScoresByName());
        int inicio = 0;
        int fin = bestCharacterScores.size() - CREEPING_ZOMBIE;
        CharacterScore characterScoreBuscado = null;
        int medio = (inicio + fin) / 2;
        while (inicio <= fin && characterScoreBuscado == null) {
            if (bestCharacterScores.get(medio).getKillerName().compareToIgnoreCase(playerName) == 0) {
                characterScoreBuscado = bestCharacterScores.get(medio);
                boolean hayMas = true;
                medio = medio + CREEPING_ZOMBIE;

                while (medio <= fin && hayMas) {
                    if (bestCharacterScores.get(medio).getKillerName().compareToIgnoreCase(playerName) == 0) {
                        characterScoreBuscado = bestCharacterScores.get(medio);
                    } else {
                        hayMas = false;
                    }

                    medio = medio + CREEPING_ZOMBIE;
                }
            } else if (bestCharacterScores.get(medio).getKillerName().compareToIgnoreCase(playerName) > 0) {
                fin = medio - CREEPING_ZOMBIE;
            } else {
                inicio = medio + CREEPING_ZOMBIE;
            }

            medio = (inicio + fin) / 2;
        }

        return characterScoreBuscado;
    }

    public void verifyName(String playerName) throws InvalidNameException {
        for (int i = 0; i < playerName.length(); i++) {
            char caracter = playerName.charAt(i);

            if ((caracter > 90 && caracter < 97) || caracter < 65 || caracter > 122) {
                throw new InvalidNameException(caracter);
            }
        }
    }

}
