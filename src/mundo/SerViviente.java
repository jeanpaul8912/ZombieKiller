package mundo;

public interface SerViviente {
	/**
	 * todo ser viviente en el juego realiza un ataque característico
	 * @return
	 */
	String ataco();
	/**
	 * cambia la salud del ser viviente
	 * @param nuevaSalud
	 */
	void setSalud(byte nuevaSalud);
	/**
	 * todo ser viviente contiene salud, representado por un valor numérico
	 * @return
	 */
	byte getSalud();
}
