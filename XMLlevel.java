import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Erstellt ein Level als .xml
 * 
 *
 */
public class XMLlevel {

	
	public static void main(String[] args) throws FileNotFoundException, XMLStreamException {
		BufferedReader eingabe = new BufferedReader(new InputStreamReader(System.in));		
		System.out.println("Wie soll das neue Level heissen?");
		String name="";
		int breit=0;
		int hoch=0;
		int position[] = new int[4];
		try {
			name = eingabe.readLine();
			System.out.println("Wie breit soll das Level " + name +" werden?");
			breit = Integer.parseInt(eingabe.readLine());
			System.out.println("Wie hoch soll das Level " + name + " werden?");
			hoch = Integer.parseInt(eingabe.readLine());
			System.out.println("An welchen Positionen sollen die Spieler starten?");
			for (int i=0; i<4; i++){
			System.out.print("x: ");
			position[i] = Integer.parseInt(eingabe.readLine());
			System.out.println();
			i++;
			System.out.print("y: ");
			position[i] = Integer.parseInt(eingabe.readLine());
			}//for
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}

		Feld[][] spielfeld = new Feld[breit][hoch];	
		
		
		//erstellen der xml-Datei
		XMLOutputFactory factory = XMLOutputFactory.newInstance();
		XMLStreamWriter schreiber = factory.createXMLStreamWriter( new FileOutputStream(name));
		
		
		//schreiben der xml-Datei
		//zunaechst die Groesse des Spielfeldes
		schreiber.writeStartDocument();
		schreiber.writeStartElement("Level");
		schreiber.writeStartElement("Groesse");
		schreiber.writeAttribute("Breite", String.valueOf(breit));
		schreiber.writeAttribute("Hoehe", String.valueOf(hoch));
		schreiber.writeEndElement();
		
		//hier die Beschaffenheit der einzelnen Felder
		schreiber.writeStartElement("Spielfeld");
		for (int i=0;i<hoch;i++){
			schreiber.writeStartElement("Zeile"+String.valueOf(i));
			for (int j=0;j<breit;j++){
				System.out.println("Position X:"+ (j+1) +" Y:"+ (i+1) + ". Welche Art von Feld soll an dieser Position sein? ('S'= nicht zerstoerbarer Stein, 'L'= leeres Feld, 'M'= Mauer)");
				BufferedReader eing = new BufferedReader(new InputStreamReader(System.in));
				String zeile="";
				try {
					zeile = eing.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				char auswahl = zeile.charAt(0);
				//System.out.println(auswahl);
				switch(auswahl){
					case 'S': schreiber.writeAttribute("X"+String.valueOf(j),"S");
								break;
					case 'L': schreiber.writeAttribute("X"+String.valueOf(j),"L");
								break;
					case 'M': schreiber.writeAttribute("X"+String.valueOf(j), "M");
								break;
					default: System.out.println("Fehlerhafte Eingabe. Bitte Programm erneut starten.");
								System.exit(1);
								break;
								}
			}
			schreiber.writeEndElement();
		}//end for(i)
		schreiber.writeEndElement();
		
		//festlegen der Startpositionen
		schreiber.writeStartElement("Startpositionen");
		for (int i=0; i<4; i++){
			schreiber.writeAttribute("pos"+i, String.valueOf(position[i]));
		}
		schreiber.writeEndElement();
		schreiber.writeEndElement();
		schreiber.writeEndDocument();
		schreiber.close();
		System.out.println("Level erfolgreich erstellt!");

	}

}
