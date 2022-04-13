package interfaz.panels;

import mundo.zombies.Boss;
import mundo.zombies.Zombie;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

import static mundo.constants.ZombieKillerConstants.GRENADE_DAMAGE;
import static mundo.constants.ZombieKillerConstants.REMINGTON_INITIAL_BACKWARD;
import static mundo.constants.ZombieKillerConstants.REMINGTON_INITIAL_RECHARGE_TIME;

public class PanelAtributos extends JPanel {

	private static final long serialVersionUID = 1L;

	// La cantidad de atributos y valores debe ser la misma
	public PanelAtributos(String[] atributos, int[] valores) {
		setBackground(Color.BLACK);
		setLayout(new GridLayout(valores.length, 2, 20, 20));
		this.setBorder(new EmptyBorder(50,30,20,220));
		JLabel[] labAtributos = new JLabel[atributos.length];
		JProgressBar[] barValores = new JProgressBar[valores.length];

		Font letra = new Font("Agency FB", Font.BOLD, 24);
		for (int i = 0; i < valores.length; i++) {

			labAtributos[i] = new JLabel(atributos[i]);
			labAtributos[i].setHorizontalAlignment(SwingConstants.RIGHT);
			labAtributos[i].setFont(letra);
			labAtributos[i].setForeground(Color.white);
			add(labAtributos[i]);

			barValores[i] = new JProgressBar();
			barValores[i].setForeground(Color.RED);
			barValores[i].setBackground(Color.WHITE);
			add(barValores[i]);

			switch (atributos[i]) {
				case "Danio":
					barValores[i].setMaximum(GRENADE_DAMAGE);
					break;
				case "Salud":
					barValores[i].setMaximum(Boss.SALUD);
					break;
				case "Lentitud":
					barValores[i].setMaximum(Zombie.LENTITUD1);
					break;
				case "Retroceso":
					barValores[i].setMaximum(REMINGTON_INITIAL_BACKWARD + 100);
					break;
				case "Tiempo de Carga":
					barValores[i].setMaximum(REMINGTON_INITIAL_RECHARGE_TIME + 200);
					break;
			}

			barValores[i].setValue(valores[i]);
		}
	}
}
