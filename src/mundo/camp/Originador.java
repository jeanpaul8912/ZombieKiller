package mundo.camp;

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