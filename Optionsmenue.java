import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;

public class Optionsmenue extends Frame
{
	private JTextField powerups, leben, bomben, reichweite;
	private ActionLauscher a;
	private WindowListener l;
	private JLabel schriftzug;
	private JTextField copyright;
	private JButton back;
	private JRadioButton g1, g2, g3, g4;
	private JCheckBox ups, ups2;
	private JCheckBox lives, lives2, lives3;
	private JCheckBox bombs, bombs2, bombs3;
	private JCheckBox range, range2, range3;
	private ImageIcon icon;
	private JFrame frame;
	
	public Optionsmenue(String titel)
	{
		super(titel);
		this.setResizable(true);
		this.setLayout(null);
		this.setBackground(Color.black);
		this.setBounds(100, 100, 500, 500);		
		
		//Listener
		a = new ActionLauscher();
		l = new WindowListener();
		this.addWindowListener(l);
		
		//TextField
		powerups = new JTextField("Power-Ups");
		powerups.setBounds(125, 140, 150, 50);
		powerups.setBackground(Color.white);
		powerups.setEditable(false);
		this.add(powerups);
		
		leben = new JTextField("Anzahl an Leben");
		leben.setBounds(125, 200, 150, 50);
		leben.setBackground(Color.white);
		leben.setEditable(false);
		this.add(leben);
		
		bomben = new JTextField("Anzahl an Bomben");
		bomben.setBounds(125, 260, 150, 50);
		bomben.setBackground(Color.white);
		bomben.setEditable(false);
		this.add(bomben);
		
		reichweite = new JTextField("Reichweite d. Bombens");
		reichweite.setBounds(125, 320, 150, 50);
		reichweite.setBackground(Color.white);
		reichweite.setEditable(false);
		this.add(reichweite);
		
		//Checkboxen
		ups = new JCheckBox("Ja");
		this.add(ups);
		
		ups2 = new JCheckBox("Nein");
		this.add(ups2);
		
		lives = new JCheckBox("1");
		this.add(lives);
		
		lives2 = new JCheckBox("3");
		this.add(lives2);
		
		lives3 = new JCheckBox("5");
		this.add(lives3);
		
		bombs = new JCheckBox("1");
		this.add(bombs);
			
		bombs2 = new JCheckBox("3");
		this.add(bombs2); 
			
		bombs3 = new JCheckBox("5");
		this.add(bombs3);
				
		range = new JCheckBox("1");
		this.add(range); 
			
		range2 = new JCheckBox("3");
		this.add(range2);
				
		range3 = new JCheckBox("5");
		this.add(range3);
		
		//RadioButtons
		g1 = new JRadioButton();
		g1.add(ups);
		g1.add(ups2);
		g1.setBounds(300, 150, 100, 30);
		this.add(g1);
		
		g2 = new JRadioButton();
		g2.add(lives);
		g2.add(lives2);
		g2.add(lives3);
		g2.setBounds(300, 210, 50, 30);
		this.add(g2);
		
		g3 = new JRadioButton();
		g3.add(bombs);
		g3.add(bombs2);
		g3.add(bombs3);
		g3.setBounds(300, 270, 50, 30);
		this.add(g3);
		
		g4 = new JRadioButton();
		g4.add(range);
		g4.add(range2);
		g4.add(range3);
		g4.setBounds(300, 330, 50, 30);
		this.add(g4);
		
		//Button
		back = new JButton("Zurueck");
		back.setBounds(150, 385, 150, 50);
		back.setBackground(Color.white);
		back.addActionListener(a);
		this.add(back);
		
		//TextFeld
		copyright= new JTextField("Copyright by: Simon Thyßen, Marian Martini, Raphael Poboda, Martin von Arkel, Philip Höfges");
		copyright.setBounds(10, 450, 500, 50);
		copyright.setForeground(Color.white);
		copyright.setEditable(false);
		copyright.setFont(new Font("Arial",Font.PLAIN , 10));
		copyright.setBackground(this.getBackground());
		this.add(copyright);
		
		//Image/Banner
		schriftzug = new JLabel();
		schriftzug.setBounds(0, 0, 500, 500);
		this.add(schriftzug);
		icon = new ImageIcon("/home/philip/ProPra/Spielmenue/src/Bomberman_Vector_by_Haite_S_Regna.png");
		frame = new JFrame();
		frame.getContentPane().add(schriftzug);
		frame.setBounds(0, 0, 500, 500);
		schriftzug.setIcon(icon);
		frame.pack();
		this.add(schriftzug);
		
		this.setVisible(true);
	}
	
	private class ActionLauscher implements ActionListener
	{
		Main main = new Main();
		String[] string = new String[1];
		public void actionPerformed(ActionEvent e)
		{
			if(e.getActionCommand().equals("Zurueck"))
			{
				main.schließeOption();
				main.setWert(3);
				main.main2(string);
			}
		}
	}
}
