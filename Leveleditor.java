import java.io.*;
import java.util.*;

public class Leveleditor {

	public static Feld[][] einlesen(Feld[][] spielfeld) throws IOException{
		
		for (int i=0;i<13;i++){
			for (int j=0;j<15;j++){
				System.out.println("Position X:"+ (j+1) +" Y:"+ (i+1) + ". Welche Art von Feld soll an dieser Position sein? ('S'= nicht zerstoerbarer Stein, 'L'= leeres Feld)");
				BufferedReader eing = new BufferedReader(new InputStreamReader(System.in));
				String zeile = eing.readLine();
				char auswahl = zeile.charAt(0);
				System.out.println(auswahl);
				switch(auswahl){
					case 'S': spielfeld[j][i]=new Steinfeld();
								break;
					case 'L': spielfeld[j][i]=new Leerfeld();
								break;
					default: System.out.println("Fehlerhafte Eingabe. Bitte Programm erneut starten.");
								break;
								}
			}
		}
		return spielfeld;
	}
	
	
	//Main-Methode
	public static void main(String[] args) throws IOException{
	BufferedReader eingabe = new BufferedReader(new InputStreamReader(System.in));
	
	System.out.println("Wie soll das neue Level heissen?");
	String name= eingabe.readLine();
	
	Feld[][] spielfeld = new Feld[15][13];
	
	try{
	ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(name + ".map",true));
	Feld[][] inhalt = einlesen(spielfeld);
	o.writeObject(inhalt);
	o.close();
	} catch (Exception e){}
	System.out.println("Level erfolgreich erstellt!");

}
}
