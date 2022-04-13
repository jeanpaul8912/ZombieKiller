package mundo.weapons;

import java.io.Serializable;

public abstract class Weapon implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * cadena de caracteres que representa el estado del arma cargandose
     */
    public static final String CARGANDO = "carga";
    /**
     * cadena de caracteres que representa el estado del arma lista
     */
    public static final String LISTA = "ready";

    /**
     * valor numerico entero que muestra el tiempo de carga en milisegundos
     */
    private int retroceso;
    /**
     * valor numerico entero que representa el danio causado por un arma
     */
    private int danio;
    /**
     * cadena de caracteres que representa el estado del arma
     */
    private String estado;

    /**
     * Constructor abstracto de un arma que pone el estado del arma Lista
     */
    public Weapon() {
        estado = LISTA;
    }

    /**
     * obtiene el tiempo que tarda en cargar el arma
     *
     * @return tiempoCarga
     */
    public int getRetroceso() {
        return retroceso;
    }

    /**
     * cambia el tiempo que tarda en cargar el arma
     *
     * @param retroceso
     */
    protected void setRetroceso(int retroceso) {
        this.retroceso = retroceso;
    }

    /**
     * cambia el danio que causa el arma
     *
     * @param danio
     */
    protected void setDanio(byte danio) {
        this.danio = danio;
    }

    /**
     * obtiene el danio que causa el arma
     *
     * @return danio
     */
    public int getDanio() {
        return danio;
    }

    /**
     * metodo que calcula el tiempo de espera en el hilo del arma con respecto al
     * estado
     *
     * @return tiempo de sleep en milisegundos
     */
    public long calcularDescanso() {
        long descanso = 0;

        if (getEstado().equals(CARGANDO)) {
            descanso = retroceso;
        }

        return descanso;
    }

    /**
     * obtiene el estado del arma presente
     *
     * @return estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * cambia el estado del arma en cuestion
     *
     * @param estado
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

}