package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import mundo.Cuchillo;
import mundo.Granada;
import mundo.M1911;
import mundo.Remington;

public class PanelArmas extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
	private JLabel [] labArmas;
	private PanelAtributos [] panelAtributos;
	private int aMostrar=0;

	public PanelArmas (InterfazZombieKiller interfazZombieKiller) {
		setBackground(Color.BLACK);
		setLayout(new BorderLayout());
		labArmas = new JLabel[4];
		panelAtributos = new PanelAtributos[4];	
		JLabel infoImagen = new JLabel("Click en la imagen para conocer más armas.");
		infoImagen.setFont(new Font("Agency FB", Font.BOLD, 20));
		infoImagen.setForeground(Color.WHITE);
		infoImagen.setBorder(new EmptyBorder(0,80,0,0));
		
		Image perfil;
		Icon iconoEscalado;
		perfil = new ImageIcon(getClass().getResource("/img/Fondo/perfilRemington.png")).getImage().getScaledInstance(300, 300, 300);
	    iconoEscalado = new ImageIcon(perfil);
		labArmas[0] = new JLabel (iconoEscalado);
		labArmas[0].setToolTipText("Fusiles de cerrojo fabricados para emplear munición de diversos calibres.");
		perfil = new ImageIcon(getClass().getResource("/img/Fondo/perfilM1911.png")).getImage().getScaledInstance(300, 200, 300);
	    iconoEscalado = new ImageIcon(perfil);
	    labArmas[1] = new JLabel (iconoEscalado);
	    labArmas[1].setToolTipText("Arma de fuego de repetición que se caracteriza por llevar la munición en un tambor.");
	    perfil = new ImageIcon(getClass().getResource("/img/Fondo/perfilGranada.png")).getImage().getScaledInstance(300, 200, 300);
	    iconoEscalado = new ImageIcon(perfil);
	    labArmas[2] = new JLabel (iconoEscalado);
	    labArmas[2].setToolTipText("Proyectil pequeño que contiene explosivos o gas en su interior y que se lanza a mano.");
	    perfil = new ImageIcon(getClass().getResource("/img/Fondo/perfilCuchillo.png")).getImage().getScaledInstance(300, 200, 300);
	    iconoEscalado = new ImageIcon(perfil);
	    labArmas[3] = new JLabel (iconoEscalado);
	    labArmas[3].setToolTipText("Utensilio para cortar tiene una hoja de metal alargada y afilada por un solo lado.");
	    
		inicializarAtributos();
		add(infoImagen,BorderLayout.NORTH);
		add(labArmas[1], BorderLayout.CENTER);
		add(panelAtributos[1], BorderLayout.SOUTH);
		add(labArmas[2], BorderLayout.CENTER);
		add(panelAtributos[2], BorderLayout.SOUTH);
		add(labArmas[3], BorderLayout.CENTER);
		add(panelAtributos[3], BorderLayout.SOUTH);
		add(labArmas[0], BorderLayout.CENTER);
		add(panelAtributos[0], BorderLayout.SOUTH);

		
		for(int i=0; i < labArmas.length; i++) {
			labArmas[i].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				verArma();
			}
			});
		}
		}
	
	public void inicializarAtributos () {
		String[] atributos = new String[3];
		int[] valores = new int [3];
		atributos[0] = "Danio";
		atributos[1] = "Retroceso";
		atributos[2] = "Tiempo de Carga";
		
		valores[0] = Remington.DANIO;
		valores[1] = Remington.RETROCESO;
		valores[2] = Remington.TIEMPO_CARGA;
		
		panelAtributos[0] = new PanelAtributos(atributos, valores);
		
		atributos = new String[3];
		valores = new int [3];
		atributos[0] = "Danio";
		atributos[1] = "Retroceso";
		atributos[2] = "Tiempo de Carga";
		
		valores[0] = M1911.DANIO;
		valores[1] = M1911.RETROCESO;
		valores[2] = M1911.TIEMPO_CARGA;
		
		panelAtributos[1] = new PanelAtributos(atributos, valores);
		
		atributos = new String[1];
		valores = new int [1];
		atributos[0] = "Danio";
		
		valores[0] = Granada.DANIO;
		
		panelAtributos[2] = new PanelAtributos(atributos, valores);
		
		atributos = new String[1];
		valores = new int [1];
		atributos[0] = "Danio";
		
		valores[0] = Cuchillo.DANIO;
		
		panelAtributos[3] = new PanelAtributos(atributos, valores);

	}


	private void verArma() {
		
		labArmas[aMostrar].setVisible(false);
		panelAtributos[aMostrar].setVisible(false);
		if(aMostrar==3) {
			aMostrar=-1;
		}
		aMostrar++;
		labArmas[aMostrar].setVisible(true);
		panelAtributos[aMostrar].setVisible(true);
		add(labArmas[aMostrar], BorderLayout.CENTER);
		add(panelAtributos[aMostrar], BorderLayout.SOUTH);	
	}
	

	public int getaMostrar() {
		return aMostrar;
	}

	public void setaMostrar(int aMostrar) {
		this.aMostrar = aMostrar;
	}

}
