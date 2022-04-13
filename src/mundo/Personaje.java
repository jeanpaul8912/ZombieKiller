package mundo;

import mundo.armas.Arma;
import mundo.armas.blancas.Cuchillo;
import mundo.armas.fuego.ArmaDeFuego;
import mundo.armas.fuego.Granada;

import java.io.Serializable;

public class Personaje implements SerViviente, Serializable {

    private static final long serialVersionUID = -9073363089990894813L;
    /**
     * valor incambiable de la salud total del personaje
     */
    public static final byte SALUD = 4;
    /**
     * valor de la salud del personaje
     */
    private byte salud;
    /**
     * cantidad de bajas realizadas
     */
    private short matanza;
    /**
     * puntaje que lleva
     */
    private int score;
    /**
     * cantidad de bajas con el ultimo disparo en la cabeza
     */
    private int headShots;
    /**
     * Arma que el personaje usa en pantalla
     */
    private ArmaDeFuego armaPrincipal;
    /**
     * Arma que esta guardada
     */
    private ArmaDeFuego armaSecundaria;
    /**
     * granadas que posee el jugador
     */
    private ArmaDeFuego granadas;
    /**
     * cuchillo del personaje, es usado cuando un zombie ataca y no tiene la
     * posibilidad de usar otra arma
     */
    private Arma cuchillo;
    /**
     * estado temporal que indica que fue herido
     */
    private boolean ensangrentado;

    /**
     * Constructor del personaje cada vez que se inicia una partida los valores no
     * inicializados tienen por defecto 0
     */
    public Personaje() {
        salud = SALUD;
        WeaponFactory armaFactory = new WeaponFactoryImpl();
        granadas = (Granada) armaFactory.createWeapon("granada");
        armaPrincipal = (ArmaDeFuego) armaFactory.createWeapon("m1911");
        armaSecundaria = (ArmaDeFuego) armaFactory.createWeapon("remington");
        cuchillo = (Cuchillo) armaFactory.createWeapon("cuchillo");
    }

    /**
     * obtiene el cuchillo del personaje
     *
     * @return cuchillo
     */
    public Arma getCuchillo() {
        return cuchillo;
    }

    /**
     * obtiene las granadas para verificar su estado y cantidad
     *
     * @return granadas
     */
    public ArmaDeFuego getGranadas() {
        return granadas;
    }

    /**
     * pregunta si el personaje se encuentra herido
     *
     * @return true si acaba de ser araniado
     */
    public boolean isEnsangrentado() {
        return ensangrentado;
    }

    /**
     * cambia el estado de herida (solo sirve para mostrar temporalmente el danio
     * causado)
     *
     * @param ensangrentado
     */
    public void setEnsangrentado(boolean ensangrentado) {
        this.ensangrentado = ensangrentado;
    }

    /**
     * obtiene el arma que se muestra en pantalla
     *
     * @return
     */
    public ArmaDeFuego getPrincipal() {
        return armaPrincipal;
    }

    @Override
    public byte getSalud() {
        return salud;
    }

    @Override
    public void setSalud(byte salud) {
        this.salud = salud;
    }

    /**
     * retorna la cantidad de bajas que ha realizado el personaje
     */
    public short getMatanza() {
        return matanza;
    }

    /**
     * obtiene el puntaje que lleva
     *
     * @return score
     */
    public int getScore() {
        return score;
    }

    public void setMatanza(short matanza) {
        this.matanza = matanza;
    }

    public void setScore(int score) {
        this.score = score;
    }

    /**
     * sube el puntaje que lleva el personaje
     *
     * @param puntos
     */
    public void aumentarScore(int puntos) {
        score = score + puntos;
        matanza = (short) (matanza + 1);
    }

    /**
     * se encarga de restaurar la municion del arma principal al maximo
     */
    public void cargo() {
        armaPrincipal.reload();
        armaPrincipal.setEstado(Arma.CARGANDO);
    }

    /**
     * cambia del arma principal a la secundaria
     */
    public void cambiarArma() {
        ArmaDeFuego temporal = armaPrincipal;
        armaPrincipal = armaSecundaria;
        armaSecundaria = temporal;
    }

    /**
     * aumenta la cantidad de bajas con tiro a la cabeza
     */
    public void aumentarTirosALaCabeza() {
        headShots++;
    }

    /**
     * obtiene la cantidad de bajas con tiros a la cabeza
     *
     * @return
     */
    public int getHeadShots() {
        return headShots;
    }
}
