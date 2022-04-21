package edu.puj.pattern_design.zombie_killer.service.camp.impl;

import edu.puj.pattern_design.zombie_killer.service.camp.SurvivorCamp;
import edu.puj.pattern_design.zombie_killer.service.camp.impl.Memento;

public class Originador {
	
	private SurvivorCamp estado;

	public void setEstado(SurvivorCamp estado) {
		this.estado = estado;
	}

	public SurvivorCamp getEstado() {
		return estado;
	}

	public Memento guardar() {
		return new Memento(estado);
	}

	public void restaurar(Memento m) {
		this.estado = m.getEstado();
	}

}