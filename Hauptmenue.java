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
	private JButton[] buttons;
	private String[] headlines;
	
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
		buttons = new JButton[4];
		headlines = new String[4];
		headlines[0] = "Einzelspieler";
		headlines[1] = "Mehrspieler";
		headlines[2] = "Optionen";
		headlines[3] = "Spiel beenden";
		
		for(int i=0;i<buttons.length;i++)
		{
			JButton b = new JButton(headlines[i]);
			b.setBackground(Color.white);
			b.setBounds(155, 140+(i*60), 150, 50);
			b.addActionListener(a);
			this.add(b);
			buttons[i] = b;
		}
		
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
			if(e.getActionCommand().equals("Einzelspieler"))
			{
				
			}
			if(e.getActionCommand().equals("Mehrspieler"))
			{
				//Start Spiel für Mehrspieler(KI oder Netzwerk)
			}
			if(e.getActionCommand().equals("Optionen"))
			{
				
			}
			if(e.getActionCommand().equals("Spiel beenden"))
			{
				System.exit(0);
			}
		}
	}
}
