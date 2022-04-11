package mundo;

public interface SerViviente {
	/**
	 * todo ser viviente en el juego realiza un ataque caracteristico
	 * @return
	 */
	//void ataco();
	/**
	 * cambia la salud del ser viviente
	 * @param nuevaSalud
	 */
	void setSalud(byte nuevaSalud);
	/**
	 * todo ser viviente contiene salud, representado por un valor numerico
	 * @return
	 */
	byte getSalud();
}
