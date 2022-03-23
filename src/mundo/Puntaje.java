package mundo;

import java.io.Serializable;
import java.util.ArrayList;

public class Puntaje implements Serializable, Comparable<Puntaje> {

	/**
	 * valor entero que representa el score del personaje en la partida finalizada
	 */
	private int puntaje;
	/**
	 * valor entero que representa la cantidad de bajas con tiro a la cabeza del personaje
	 */
	private int tirosALaCabeza;
	/**
	 * valor entero que representa la cantidad de bajas del personaje en la partida finalizada
	 */
	private int bajas;
	/**
	 * valor entero que representa el nombre del personaje en la partida terminada
	 */
	private String nombreKiller;
	/**
	 * Puntaje mayor que el presente, esta referencia para ordenar en forma de arbol binario
	 */
	private Puntaje mayor;
	/**
	 * Puntaje menor que el presente
	 */
	private Puntaje menor;

	/**
	 * Constructor del Puntaje con los datos finales de la partida
	 * @param puntaje
	 * @param tirosALaCabeza
	 * @param bajas
	 * @param nombreKiller
	 */
	public Puntaje(int puntaje, int tirosALaCabeza, int bajas, String nombreKiller) {
		this.puntaje = puntaje;
		this.tirosALaCabeza = tirosALaCabeza;
		this.bajas = bajas;
		this.nombreKiller = nombreKiller;
	}

	/**
	 * valor entero que representa el score del puntaje guardado
	 * @return puntaje
	 */
	public int getPuntaje() {
		return puntaje;
	}
	/**
	 * valor entero que representa las bajas con tiros a la cabeza del puntaje guardado
	 * @return tirosAlaCabeza
	 */
	public int getTirosALaCabeza() {
		return tirosALaCabeza;
	}

	/**
	 * valor entero que representa las bajas del puntaje guardado
	 * @return cantidad de bajas
	 */
	public int getBajas() {
		return bajas;
	}

	/**
	 * cadena de caracteres que representa el nombre del puntaje guardado
	 * @return nombre del Matador
	 */
	public String getNombreKiller() {
		return nombreKiller;
	}

	/**
	 * metodo que se encarga de agregar un puntaje en el arbol binario con el score(puntaje en numeros) como criterio de ordenamiento
	 * @param score
	 */
	public void aniadirPorPuntaje(Puntaje score) {
		if (puntaje < score.getPuntaje()) {
			
			if (mayor != null)
				mayor.aniadirPorPuntaje(score);
			else
				mayor = score;
		} else if (score.getPuntaje() == puntaje) {
			
			if (this.compareTo(score) < 0) {
				if (mayor != null)
					mayor.aniadirPorPuntaje(score);
				else
					mayor = score;
			} else {
				if (menor != null)
					menor.aniadirPorPuntaje(score);
				else
					menor = score;
			}
		} else {
			
			if (menor != null)
				menor.aniadirPorPuntaje(score);
			else
				menor = score;
		}
	}

	/**
	 * obtiene el puntaje directamente menor al presente
	 * @return menor
	 */
	public Puntaje getMenor() {
		return menor;
	}

	/**
	 * obtiene el puntaje mayor con referencia el presente
	 * @return mayor
	 */
	public Puntaje getMayor() {
		return mayor;
	}

	/**
	 * agrega elementos a la lista pasada por parametro de manera ordenada en forma ascendente
	 * @param lista
	 */
	public void generarListaInOrden(ArrayList lista) {
		if (mayor != null)
			mayor.generarListaInOrden(lista);
		lista.add(this);
		if (menor != null)
			menor.generarListaInOrden(lista);
	}
	
	@Override
	public int compareTo(Puntaje o) {
		int porScore = puntaje - o.puntaje;
		if (porScore != 0)
			return porScore;
		int porBajas = bajas - o.bajas;
		if (porBajas != 0)
			return porBajas;
		return tirosALaCabeza - o.tirosALaCabeza;

	}
}
