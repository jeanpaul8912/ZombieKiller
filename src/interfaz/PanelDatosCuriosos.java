package interfaz;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.font.FontRenderContext;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PanelDatosCuriosos extends JPanel {

	private JLabel[] titulos;
	private JLabel[] datos;
	private JLabel[] instrucciones;

	public PanelDatosCuriosos() {
		setBackground(Color.black);
		setLayout(new GridLayout(0, 1));

		titulos = new JLabel[2];
		datos = new JLabel[3];
		instrucciones = new JLabel[5];
		titulos[0] = new JLabel("Datos curiosos");
		titulos[1] = new JLabel("Controles");
		datos[0] = new JLabel("*No siempre matar de tiro a la cabeza da más puntos. Que el último tiro sea el que le vuele los sesos!");
		datos[1] = new JLabel("*Puedes acuchillar a un enemigo en posición de ataque, sólo dispara...");
		datos[2] = new JLabel("*El daño de la escopeta varía según la distancia, úsala sabiamente");

		instrucciones[0] = new JLabel("Presiona \"SHIFT\" para ver las estadísticas de la partida");
		instrucciones[1] = new JLabel("Presiona \"C\" para cambiar de arma");
		instrucciones[2] = new JLabel("Presiona \"SPACE\" para lanzar granada");
		instrucciones[3] = new JLabel("Presiona \"Click Izquierdo\" para disparar el arma equipada");
		instrucciones[4] = new JLabel("Presiona \"Click Derecho\" para recargar el arma equipada");
		// instruccion3 = new JLabel("Presiona \"SPACE\" para lanzar granada");

		configurarTipoDeLetra();

		add(titulos[0]);
		for (int i = 0; i < datos.length; i++) {
			add(datos[i]);
		}
		add(titulos[1]);
		for (int i = 0; i < instrucciones.length; i++) {
			add(instrucciones[i]);
		}
	}

	private void configurarTipoDeLetra() {
		Font letra = new Font("Chiller", Font.ITALIC, 30);
		for (int i = 0; i < datos.length; i++) {
			datos[i].setFont(letra);
			datos[i].setForeground(Color.WHITE);
		}
		for (int i = 0; i < instrucciones.length; i++) {
			instrucciones[i].setFont(letra);
			instrucciones[i].setForeground(Color.WHITE);
		}
		for (int i = 0; i < titulos.length; i++) {
			titulos[i].setFont(letra);
			titulos[i].setForeground(Color.WHITE);
			titulos[i].setHorizontalAlignment(SwingConstants.CENTER);
		}
	}
}
