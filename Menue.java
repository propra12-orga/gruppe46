import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Menue extends Frame
{
	private WindowListener l;
	private ActionLauscher a;
	private JLabel schriftzug;
	private ImageIcon icon;
	private JFrame frame;
	
	private JButton[] hauptButtons;
	private JButton[] spielButtons;
	private JButton optionButton;
	private String[] headlines;
	private JTextField[] felder;
	private JCheckBox[] boxen;
	private JRadioButton g1, g2, g3, g4;
	
	public Menue(String titel)
	{
		super(titel);
		this.setResizable(true);
		this.setLayout(null);
		this.setBackground(Color.black);
		this.setBounds(100, 100, 612, 792);
		
		//Listener
		a = new ActionLauscher();
		l = new WindowListener();
		this.addWindowListener(l);
		
		//String-Array für Titel der Komponenten.
		headlines = new String[13];
		headlines[0] = "Einzelspieler";
		headlines[1] = "Mehrspieler";
		headlines[2] = "Optionen";
		headlines[3] = "Spiel beenden";
		headlines[4] = "Fortsetzen";
		headlines[5] = "Spiel neu starten";
		headlines[6] = "Optionen";
		headlines[7] = "Hauptmenue";
		headlines[8] = "Zureuck";
		headlines[9] = "Power-Ups";
		headlines[10] = "Anzahl der Leben";
		headlines[11] = "Anzahl an Bomben";
		headlines[12] = "Reichweite d. Bomben";
		
		//Buttons
		hauptButtons = new JButton[4];
		for(int i=0;i<hauptButtons.length;i++)
		{
			JButton b = new JButton(headlines[i]);
			b.setBackground(Color.white);
			b.setBounds(245, 160+(i*60), 150, 50);
			b.addActionListener(a);
			this.add(b);
			hauptButtons[i] = b;
		}
		spielButtons = new JButton[4];
		for(int i=0;i<spielButtons.length;i++)
		{
			JButton b = new JButton(headlines[i+4]);
			b.setBackground(Color.white);
			b.setBounds(245, 160+(i*60), 150, 50);
			b.addActionListener(a);
			b.setVisible(false);
			this.add(b);
			spielButtons[i] = b;
		}
		optionButton = new JButton("Zurueck");
		optionButton.setBackground(Color.white);
		optionButton.setBounds(245, 435, 150, 50);
		optionButton.addActionListener(a);
		optionButton.setVisible(false);
		this.add(optionButton);
		
		//TextField
		felder = new JTextField[5];
		for(int i=0;i<felder.length-1;i++)
		{
			JTextField t = new JTextField(headlines[i+9]);
			t.setBounds(125, 170+(i*60), 150, 50);
			t.setBackground(Color.white);
			t.setEditable(false);
			t.setVisible(false);
			this.add(t);
			felder[i] = t;
		}
		felder[4]= new JTextField("Copyright by: Simon Thyßen, Marian Martini, Raphael Podoba, Martin von Arkel, Philip Höfges");
		felder[4].setBounds(10, 742, 500, 50);
		felder[4].setForeground(Color.white);
		felder[4].setEditable(false);
		felder[4].setFont(new Font("Arial",Font.PLAIN , 10));
		felder[4].setBackground(this.getBackground());
		this.add(felder[4]);
		
		//CheckBoxen
		boxen = new JCheckBox[11];
		for(int i=0;i<2;i++)
		{
			String s;
			if(i==0)
			{
				s = "Ja";
			}
			else
			{
				s = "Nein";
			}
			JCheckBox c = new JCheckBox(s);
			this.add(c);
			c.setVisible(false);
			boxen[i] = c;
		}
		int j = 1;
		for(int i=2;i<boxen.length;i++)
		{
			JCheckBox c = new JCheckBox(""+j);
			this.add(c);
			j = j+2;
			if(i==5 && i==8)
			{
				j = 1;
			}
			c.setVisible(false);
			boxen[i] = c;
		}
		
		//RadioButtons
		g1 = new JRadioButton();
		g1.add(boxen[0]);
		g1.add(boxen[1]);
		g1.setBounds(300, 175, 50, 30);
		g1.setVisible(false);
		this.add(g1);
		
		g2 = new JRadioButton();
		g2.add(boxen[2]);
		g2.add(boxen[3]);
		g2.add(boxen[4]);
		g2.setBounds(300, 235, 50, 30);
		g2.setVisible(false);
		this.add(g2);
		
		g3 = new JRadioButton();
		g3.add(boxen[5]);
		g3.add(boxen[6]);
		g3.add(boxen[7]);
		g3.setBounds(300, 295, 50, 30);
		g3.setVisible(false);
		this.add(g3);
		
		g4 = new JRadioButton();
		g4.add(boxen[8]);
		g4.add(boxen[9]);
		g4.add(boxen[10]);
		g4.setBounds(300, 355, 50, 30);
		g4.setVisible(false);
		this.add(g4);
		
		//Image/Banner
		schriftzug = new JLabel();
		schriftzug.setBounds(0, 0, 500, 500);
		this.add(schriftzug);
		//Hier Pfad eurem PC anpassen.
		icon = new ImageIcon("/home/philip/ProPra/Spielmenue/src/Bomberman_Vector_by_Haite_S_Regna.png");
		frame = new JFrame();
		frame.getContentPane().add(schriftzug);
		frame.setBounds(0, 0, 612, 792);
		schriftzug.setIcon(icon);
		frame.pack();
		this.add(schriftzug);
		
		this.setVisible(true);
	}
	
	private class ActionLauscher implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getActionCommand().equals("Einzelspieler"))
			{
				for(int i=0;i<hauptButtons.length;i++)
				{
					hauptButtons[i].setVisible(false);
					spielButtons[i].setVisible(true);
				}
			}
			if(e.getActionCommand().equals("Mehrspieler"))
			{
				//Mehrspielermenue.
			}
			if(e.getActionCommand().equals("Optionen"))
			{
				for(int i=0;i<spielButtons.length;i++)
				{
					spielButtons[i].setVisible(false);
					hauptButtons[i].setVisible(false);
					optionButton.setVisible(true);
					boxen[i].setVisible(true);
					felder[i].setVisible(true);
					g1.setVisible(true);
					g2.setVisible(true);
					g3.setVisible(true);
					g4.setVisible(true);
				}
			}
			if(e.getActionCommand().equals("Spiel beenden"))
			{
				System.exit(0);
			}
			if(e.getActionCommand().equals("Fortsetzen"))
			{
				
			}
			if(e.getActionCommand().equals("Neustarten"))
			{
				//aktuelle Spiel beenden, neue beginnen
			}
			if(e.getActionCommand().equals("Hauptmenue"))
			{
				for(int i=0;i<spielButtons.length;i++)
				{
					spielButtons[i].setVisible(false);
					hauptButtons[i].setVisible(true);
				}
			}
			if(e.getActionCommand().equals("Zurueck"))
			{
				g1.setVisible(false);
				g2.setVisible(false);
				g3.setVisible(false);
				g4.setVisible(false);
				optionButton.setVisible(false);
				for(int i=0;i<hauptButtons.length;i++)
				{
					felder[i].setVisible(false);
					hauptButtons[i].setVisible(true);
				}
			}
		}
	}
}
