package edu.puj.pattern_design.zombie_killer.service.exceptions;

public class DatosErroneosException extends Exception {

    private static final long serialVersionUID = -1454590013618632518L;

    public DatosErroneosException() {
        super("Hay valores no validos en el juego");
    }

    public DatosErroneosException(int excedente) {
        super("El archivo ha excedido el numero de Zombies generados en " + excedente);
    }
}