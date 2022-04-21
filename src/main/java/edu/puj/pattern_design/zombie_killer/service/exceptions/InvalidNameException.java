package edu.puj.pattern_design.zombie_killer.service.exceptions;

public class InvalidNameException extends Exception {

    private static final long serialVersionUID = -995657264574625679L;

    public InvalidNameException(char caracter) {
        super("El nombre no puede contener numeros ni simbolos \n" + "Caracter invalido: " + caracter);
    }
}