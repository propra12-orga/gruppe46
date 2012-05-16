import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Spielmenue extends Frame
{
	private WindowListener l;
	private ActionLauscher a;
	private JButton restart, mainmenue, options, back;
	private JTextField copyright;
	private JLabel schriftzug;
	private JFrame frame;
	private ImageIcon icon;
	
	public Spielmenue(String titel)
	{
		//Standard-Anweisung GUI
		super(titel);
		this.setResizable(true);
		this.setLayout(null);
		this.setBackground(Color.black);
		this.setBounds(0, 0, 500, 500);
		
		//Listener
		l = new WindowListener();
		this.addWindowListener(l);
		a = new ActionLauscher();
		
		//Buttons
		back = new JButton("Fortsetzen");
		back.setBounds(155, 140, 150, 50);
		back.setBackground(Color.white);
		back.addActionListener(a);
		this.add(back);
		
		restart = new JButton("Spiel neu starten");
		restart.setBounds(155, 200, 150, 50);
		restart.setBackground(Color.white);
		restart.addActionListener(a);
		this.add(restart);
		
		options = new JButton("Optionen");
		options.setBounds(155, 260, 150, 50);
		options.setBackground(Color.white);
		options.addActionListener(a);
		this.add(options);
		
		mainmenue = new JButton("Hauptmenue");
		mainmenue.setBounds(155, 320, 150, 50);
		mainmenue.setBackground(Color.white);
		mainmenue.addActionListener(a);
		this.add(mainmenue);
		
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
			if(e.getActionCommand().equals("Fortsetzen"))
			{
				main.schliesseSpiel();
			}
			if(e.getActionCommand().equals("Spiel neu starten"))
			{
				main.schliesseSpiel();
				main.setWert(1);
				main.main2(string);	
			}
			if(e.getActionCommand().equals("Optionen"))
			{
				main.schliesseSpiel();
				main.setWert(2);
				main.main2(string);
			}
			if(e.getActionCommand().equals("Hauptmenue"))
			{
				main.schliesseSpiel();
				main.setWert(3);
				main.main2(string);
				//Dieses Fenter schließen, Hauptmenue oeffnen.
			}
		}
	}
}