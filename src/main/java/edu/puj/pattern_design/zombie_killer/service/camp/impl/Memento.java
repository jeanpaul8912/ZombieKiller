package edu.puj.pattern_design.zombie_killer.service.camp.impl;

import edu.puj.pattern_design.zombie_killer.service.camp.SurvivorCamp;

public class Memento {

	private SurvivorCamp estado;

	public Memento(SurvivorCamp estado) {
		this.estado = estado;
	}

	public SurvivorCamp getEstado() {
		return estado;
	}
}