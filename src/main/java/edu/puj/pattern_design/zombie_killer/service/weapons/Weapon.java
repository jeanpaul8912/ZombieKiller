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

    private int backward;

    private int damage;

    private String status;

    public Weapon() {
        status = LISTA;
    }

    public long calculatePause() {
        long descanso = 0;

        if (getStatus().equals(CARGANDO)) {
            descanso = backward;
        }

        return descanso;
    }

}