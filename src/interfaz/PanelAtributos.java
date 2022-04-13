package interfaz;

import mundo.zombies.Boss;
import mundo.zombies.Zombie;

import javax.swing.*;
import java.awt.*;

import static mundo.constants.ZombieKillerConstants.GRENADE_DAMAGE;
import static mundo.constants.ZombieKillerConstants.REMINGTON_INITIAL_BACKWARD;
import static mundo.constants.ZombieKillerConstants.REMINGTON_INITIAL_RECHARGE_TIME;

public class PanelAtributos extends JPanel {

	// La cantidad de atributos y valores debe ser la misma
	public PanelAtributos(String[] atributos, int[] valores) {
		setBackground(Color.BLACK);
		setLayout(new GridLayout(valores.length, 2));

		JLabel[] labAtributos = new JLabel[atributos.length];
		JProgressBar[] barValores = new JProgressBar[valores.length];

		Font letra = new Font("Chiller", Font.ITALIC, 34);
		for (int i = 0; i < valores.length; i++) {

			labAtributos[i] = new JLabel(atributos[i]);
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
