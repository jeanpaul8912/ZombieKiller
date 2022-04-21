package edu.puj.pattern_design.zombie_killer.service.camp.impl;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class CharacterScore implements Serializable, Comparable<CharacterScore> {

    private static final long serialVersionUID = -2214438786931470407L;

    /**
     * valor entero que representa el score del personaje en la partida finalizada
     */
    private final int score;
    /**
     * valor entero que representa la cantidad de bajas con tiro a la cabeza del
     * personaje
     */
    private final int headShoots;
    /**
     * valor entero que representa la cantidad de bajas del personaje en la partida
     * finalizada
     */
    private final int downs;
    /**
     * valor entero que representa el nombre del personaje en la partida terminada
     */
    private final String killerName;
    /**
     * Puntaje mayor que el presente, esta referencia para ordenar en forma de arbol
     * binario
     */
    private CharacterScore highest;
    /**
     * Puntaje menor que el presente
     */
    private CharacterScore lowest;

    public CharacterScore(int puntaje, int headShoots, int downs, String killerName) {
        this.score = puntaje;
        this.headShoots = headShoots;
        this.downs = downs;
        this.killerName = killerName;
    }

    /**
     * metodo que se encarga de agregar un puntaje en el arbol binario con el
     * score(puntaje en numeros) como criterio de ordenamiento
     *
     * @param characterScore
     */
    public void aniadirPorPuntaje(CharacterScore characterScore) {
        if (score < characterScore.getScore()) {
            if (highest != null) {
                highest.aniadirPorPuntaje(characterScore);
            } else {
                highest = characterScore;
            }
        } else if (characterScore.getScore() == score) {
            if (this.compareTo(characterScore) < 0) {
                if (highest != null) {
                    highest.aniadirPorPuntaje(characterScore);
                } else {
                    highest = characterScore;
                }
            } else {
                if (lowest != null) {
                    lowest.aniadirPorPuntaje(characterScore);
                } else {
                    lowest = characterScore;
                }
            }
        } else {
            if (lowest != null) {
                lowest.aniadirPorPuntaje(characterScore);
            } else {
                lowest = characterScore;
            }
        }
    }

    public void generateInOrderList(List<CharacterScore> lista) {
        if (highest != null) {
            highest.generateInOrderList(lista);
        }

        lista.add(this);

        if (lowest != null) {
            lowest.generateInOrderList(lista);
        }
    }

    @Override
    public int compareTo(CharacterScore o) {
        int porScore = score - o.score;

        if (porScore != 0) {
            return porScore;
        }

        int porBajas = downs - o.downs;

        if (porBajas != 0) {
            return porBajas;
        }

        return headShoots - o.headShoots;
    }
}
