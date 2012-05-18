import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;

public class Optionsmenue extends Frame
{
	private ActionLauscher a;
	private WindowListener l;
	private JLabel schriftzug;
	private JTextField copyright;
	private JButton back;
	private JRadioButton g1, g2, g3, g4;
	private ImageIcon icon;
	private JFrame frame;
	
	private JCheckBox[] boxen;
	private JTextField[] felder;
	private String[] headlines;
	
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
		headlines = new String[4];
		headlines[0] = "Power-Ups";
		headlines[1] = "Anzahl der Leben";
		headlines[2] = "Anzahl an Bomben";
		headlines[3] = "Reichweite d. Bomben";
		felder = new JTextField[4];
		for(int i=0;i<felder.length;i++)
		{
			JTextField t = new JTextField(headlines[i]);
			t.setBounds(125, 140+(i*60), 150, 50);
			t.setBackground(Color.white);
			t.setEditable(false);
			this.add(t);
			felder[i] = t;
		}
		
		//Checkboxen
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
			c.setBounds(300+(i*30), 150, 50, 30);
			this.add(c);
			boxen[i] = c;
		}
		int j = 1;
		int k = 0;
		int l = 0;
		for(int i=2;i<boxen.length;i++)
		{
			JCheckBox c = new JCheckBox(""+j);
			c.setBounds(300+(l*60), 150+(k*60), 50, 30);
			this.add(c);
			j = j+2;
			if(i==5 && i==8)
			{
				j = 1;
				k++;
				l = 0;
			}
			l++;
		}
		
		//RadioButtons
		g1 = new JRadioButton();
		g1.add(boxen[0]);
		g1.add(boxen[1]);
		this.add(g1);
		
		g2 = new JRadioButton();
		g2.add(boxen[2]);
		g2.add(boxen[3]);
		g2.add(boxen[4]);
		this.add(g2);
		
		g3 = new JRadioButton();
		g3.add(boxen[5]);
		g3.add(boxen[6]);
		g3.add(boxen[7]);
		this.add(g3);
		
		g4 = new JRadioButton();
		g4.add(boxen[8]);
		g4.add(boxen[9]);
		g4.add(boxen[10]);
		this.add(g4);
		
		//Button
		back = new JButton("Zurueck");
		back.setBounds(150, 385, 150, 50);
		back.setBackground(Color.white);
		back.addActionListener(a);
		this.add(back);
		
		//TextFeld
		copyright= new JTextField("Copyright by: Simon Thyßen, Marian Martini, Raphael Podoba, Martin von Arkel, Philip Höfges");
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
				
			}
		}
	}
}
