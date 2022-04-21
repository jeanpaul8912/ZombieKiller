package edu.puj.pattern_design.zombie_killer.service.camp.impl;

import edu.puj.pattern_design.zombie_killer.service.camp.SurvivorCamp;

public class Originador {
	
	private SurvivorCamp estado;

	public void setStatus(SurvivorCamp estado) {
		this.estado = estado;
	}

	public SurvivorCamp getStatus() {
		return estado;
	}

	public Memento save() {
		return new Memento(estado);
	}

	public void restore(Memento m) {
		this.estado = m.getEstado();
	}

}