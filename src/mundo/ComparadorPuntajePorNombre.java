package mundo;

import java.util.Comparator;

public class ComparadorPuntajePorNombre implements Comparator<Puntaje>{

	@Override
	public int compare(Puntaje o1, Puntaje o2) {
		int porNombre = o1.getNombreKiller().compareToIgnoreCase(o2.getNombreKiller());
		if(porNombre != 0)
		return porNombre;
		return o1.compareTo(o2);
	}
}
