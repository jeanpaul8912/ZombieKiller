package mundo;

public interface SeMueveEnZigzag {

	/**
	 * hace que varíe la velocidad en Y con respecto a la de X
	 */
	void moverEnDireccion();
	/**
	 * obtiene la direccion o velocidad en pixeles en el eje X
	 * @return direccionX
	 */
	int getDireccionX();
	/**
	 * obtiene la dirección o velocidad en el eje Y
	 * @return direccionY
	 */
	int getDireccionY();
}
