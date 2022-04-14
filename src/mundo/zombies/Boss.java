package mundo.zombies;

import mundo.weapons.guns.Remington;

import java.util.Formatter;

import static mundo.constants.WeaponsConstants.REMINGTON_DAMAGE;
import static mundo.constants.ZombiesConstants.DERROTADO;
import static mundo.constants.ZombiesConstants.LENTITUD_BOSS;
import static mundo.constants.ZombiesConstants.POS_ATAQUE;
import static mundo.constants.ZombiesConstants.POS_INICIAL;
import static mundo.constants.ZombiesConstants.SALUD_BOSS;
import static mundo.constants.ZombiesConstants.VOLANDO;

public class Boss extends Enemigo implements SeMueveEnZigzag {


    /**
     * valor numerico entero que representa la direccion o velocidad en el eje X
     */
    private int direccionX;
    /**
     * valor numerico entero que representa la direccion o velocidad en el eje Y
     */
    private int direccionY;
    /**
     * valor que representa la posicion en el eje X del jefe
     */
    private int posHorizontal;

    /**
     * Constructor del jefe al iniciar la ronda 10
     */
    public Boss() {
        super();
        setEstadoActual(VOLANDO);
        setSalud(SALUD_BOSS);
        setLentitud(LENTITUD_BOSS);
    }

    /**
     * Constructor del jefe al cargar la partida si fue guardada en la ronda del
     * jefe
     *
     * @param salud
     */
    public Boss(byte salud) {
        super();
        setEstadoActual(VOLANDO);
        setSalud(salud);
        setLentitud(LENTITUD_BOSS);
        moverEnDireccion();
    }

    public void terminaDeAtacar() {
        setEstadoActual(VOLANDO);
        setPosY(POS_INICIAL);
        moverEnDireccion();
        posHorizontal = posAleatoriaX();
    }

    public void moverEnDireccion() {
        direccionX = (int) (Math.random() * 13) - 6;
        if (direccionX > 0 && direccionX < 6)
            direccionY = 6 - direccionX;
        else if (direccionX <= 0 && direccionX > -6)
            direccionY = 6 + direccionX;
        else
            direccionY = 2;
    }

    @Override
    public boolean comprobarDisparo(int x, int y, int danio) {
        boolean leDio = false;
        int danioResultante = danio;

        if (x > posHorizontal + 108 && x < posHorizontal + 160 && y > getPosY() + 110 && y < getPosY() + 190) {
            if (danio == REMINGTON_DAMAGE) {
                danioResultante = danioResultante - (POS_ATAQUE - getPosY()) / Remington.RANGO;
            }

            setSalud((byte) (getSalud() - danioResultante));
            terminaDeAtacar();

            if (getSalud() <= 0) {
                setEstadoActual(DERROTADO);
                posHorizontal = 365;
                setPosY(POS_INICIAL);
            }

            leDio = true;
        }

        return leDio;
    }

    @Override
    public String getURL(int level) {
        try (Formatter formatter = new Formatter()) {
            return "/img/" + getClass().getSimpleName() + "/" + getEstadoActual() + "/"
                    + formatter.format("%02d", getFrameActual()) + ".png";
        }
    }

    @Override
    public int getPosX() {
        return posHorizontal;
    }

    public void setPosX(int posHorizontal) {
        this.posHorizontal = posHorizontal;
    }

    @Override
    public int getDireccionX() {
        return direccionX;
    }

    public void setDireccionX(int direccionX) {
        this.direccionX = direccionX;
    }

    @Override
    public int getDireccionY() {
        return direccionY;
    }

    public void setDireccionY(int direccionY) {
        this.direccionY = direccionY;
    }

}
