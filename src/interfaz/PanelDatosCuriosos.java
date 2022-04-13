package interfaz;

import javax.swing.*;
import java.awt.*;

public class PanelDatosCuriosos extends JPanel {

	private final JLabel[] titulos;
	private final JLabel[] datos;
	private final JLabel[] instrucciones;

	public PanelDatosCuriosos() {
		setBackground(Color.black);
		setLayout(new GridLayout(0, 1));

		titulos = new JLabel[2];
		datos = new JLabel[3];
		instrucciones = new JLabel[5];
		titulos[0] = new JLabel("Datos curiosos");
		titulos[1] = new JLabel("Controles");
		datos[0] = new JLabel(
				"*No siempre matar de tiro a la cabeza da mas puntos. Que el altimo tiro sea el que le vuele los sesos!");
		datos[1] = new JLabel("*Puedes acuchillar a un enemigo en posicion de ataque, solo dispara...");
		datos[2] = new JLabel("*El danio de la escopeta varia segun la distancia, usala sabiamente");

		instrucciones[0] = new JLabel("Presiona \"SHIFT\" para ver las estadisticas de la partida");
		instrucciones[1] = new JLabel("Presiona \"C\" para cambiar de arma");
		instrucciones[2] = new JLabel("Presiona \"SPACE\" para lanzar granada");
		instrucciones[3] = new JLabel("Presiona \"Click Izquierdo\" para disparar el arma equipada");
		instrucciones[4] = new JLabel("Presiona \"Click Derecho\" para recargar el arma equipada");
		// instruccion3 = new JLabel("Presiona \"SPACE\" para lanzar granada");

		configurarTipoDeLetra();

		add(titulos[0]);
		for (JLabel element : datos) {
			add(element);
		}
		add(titulos[1]);
		for (JLabel instruccione : instrucciones) {
			add(instruccione);
		}
	}

	private void configurarTipoDeLetra() {
		Font letra = new Font("Chiller", Font.ITALIC, 30);
		for (JLabel element : datos) {
			element.setFont(letra);
			element.setForeground(Color.WHITE);
		}
		for (JLabel instruccione : instrucciones) {
			instruccione.setFont(letra);
			instruccione.setForeground(Color.WHITE);
		}
		for (JLabel element : titulos) {
			element.setFont(letra);
			element.setForeground(Color.WHITE);
			element.setHorizontalAlignment(SwingConstants.CENTER);
		}
	}
}
