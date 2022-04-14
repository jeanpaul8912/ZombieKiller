package mundo.camp;

import mundo.weapons.Weapon;
import mundo.weapons.WeaponFactory;
import mundo.weapons.WeaponFactoryImpl;
import mundo.weapons.guns.Grenade;
import mundo.weapons.guns.GunWeapon;
import mundo.zombies.SerViviente;

import java.io.Serializable;

public class Personaje implements SerViviente, Serializable {

    private static final long serialVersionUID = -9073363089990894813L;
    /**
     * valor incambiable de la salud total del personaje
     */
    public static final byte SALUD = 100;
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
    private GunWeapon armaPrincipal;
    /**
     * Arma que esta guardada
     */
    private GunWeapon armaSecundaria;
    /**
     * granadas que posee el jugador
     */
    private final GunWeapon granadas;
    /**
     * cuchillo del personaje, es usado cuando un zombie ataca y no tiene la
     * posibilidad de usar otra arma
     */
    private final Weapon cuchillo;
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
        granadas = (Grenade) armaFactory.createWeapon("granada");
        armaPrincipal = (GunWeapon) armaFactory.createWeapon("m1911");
        armaSecundaria = (GunWeapon) armaFactory.createWeapon("remington");
        cuchillo = armaFactory.createWeapon("cuchillo");
    }

    /**
     * obtiene el cuchillo del personaje
     *
     * @return cuchillo
     */
    public Weapon getCuchillo() {
        return cuchillo;
    }

    /**
     * obtiene las granadas para verificar su estado y cantidad
     *
     * @return granadas
     */
    public GunWeapon getGranadas() {
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
    public GunWeapon getPrincipal() {
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
        armaPrincipal.setEstado(Weapon.CARGANDO);
    }

    /**
     * cambia del arma principal a la secundaria
     */
    public void cambiarArma() {
        GunWeapon temporal = armaPrincipal;
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
