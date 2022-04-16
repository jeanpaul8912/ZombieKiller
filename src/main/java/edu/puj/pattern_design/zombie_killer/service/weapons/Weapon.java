package edu.puj.pattern_design.zombie_killer.service.weapons;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.CARGANDO;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.LISTA;

@Getter
@Setter
public abstract class Weapon implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * valor numerico entero que muestra el tiempo de carga en milisegundos
     */
    private int retroceso;

    /**
     * valor numerico entero que representa el danio causado por un arma
     */
    private int damage;

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

}