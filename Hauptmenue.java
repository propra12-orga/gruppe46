import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class Hauptmenue extends Frame
{
	private WindowListener l;
	private ActionLauscher a;
	private JTextField copyright;
	private JLabel schriftzug;
	private ImageIcon icon;
	private JFrame frame;
	private JButton einzel, mehr, options, finish;
	
	public Hauptmenue(String titel)
	{
		//Standard-Anweisung GUI
		super(titel);
		this.setResizable(true);
		this.setLayout(null);
		this.setBackground(Color.black);
		this.setBounds(100, 100, 500, 500);
		
		//Listener
		l = new WindowListener();
		this.addWindowListener(l);
		a = new ActionLauscher();
		
		//Buttons
		einzel = new JButton("Einzelspieler");
		einzel.setBounds(155, 140, 150, 50);
		einzel.setBackground(Color.white);
		einzel.addActionListener(a);
		this.add(einzel);
		
		mehr = new JButton("Mehrspieler");
		mehr.setBounds(155, 200, 150, 50);
		mehr.setBackground(Color.white);
		mehr.addActionListener(a);
		this.add(mehr);
		
		options = new JButton("Optionen");
		options.setBounds(155, 260, 150, 50);
		options.setBackground(Color.white);
		options.addActionListener(a);
		this.add(options);
		
		finish = new JButton("Spiel beenden");
		finish.setBounds(155, 320, 150, 50);
		finish.setBackground(Color.white);
		finish.addActionListener(a);
		this.add(finish);
		
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
			if(e.getActionCommand().equals("Einzelspieler"))
			{
				//Ab hier ersetzen durch Spielbildschirm
				main.schliesseHaupt();
				main.setWert(1);
				main.main2(string);
			}
			if(e.getActionCommand().equals("Mehrspieler"))
			{
				//Start Spiel für Mehrspieler(KI oder Netzwerk)
			}
			if(e.getActionCommand().equals("Optionen"))
			{
				main.schliesseHaupt();
				main.setWert(2);
				main.main2(string);
			}
			if(e.getActionCommand().equals("Spiel beenden"))
			{
				System.exit(0);
			}
		}
	}
}
