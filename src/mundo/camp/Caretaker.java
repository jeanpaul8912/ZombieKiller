package mundo.camp;

import java.util.ArrayList;
import java.util.List;

public class Caretaker {

	private List<Memento> estadosGuardados = new ArrayList<Memento>();

	public void addMemento(Memento m) {
		estadosGuardados.add(m);
	}

	public Memento getMemento(int index) {
		return estadosGuardados.get(index);
	}
}