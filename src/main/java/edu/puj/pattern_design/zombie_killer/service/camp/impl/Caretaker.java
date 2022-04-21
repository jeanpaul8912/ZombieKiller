package edu.puj.pattern_design.zombie_killer.service.camp.impl;

import java.util.ArrayList;
import java.util.List;

public class Caretaker {

	private List<Memento> estadosGuardados = new ArrayList<Memento>();

	public void addMemento(Memento m) {
		estadosGuardados.add(m);
	}

	public Memento getMemento() {
		
		if(estadosGuardados == null || estadosGuardados.isEmpty()) {
			return null;
		}
		
		int size = estadosGuardados.size();
		return estadosGuardados.get(size - 1);
	}
}