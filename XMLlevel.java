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
		try {
			name = eingabe.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Feld[][] spielfeld = new Feld[15][13];
		
		
		//erstellen der xml-Datei
		XMLOutputFactory factory = XMLOutputFactory.newInstance();
		XMLStreamWriter schreiber = factory.createXMLStreamWriter( new FileOutputStream(name));
		
		
		//schreiben der xml-Datei
		schreiber.writeStartDocument();
		schreiber.writeStartElement("Spielfeld");
		for (int i=0;i<13;i++){
			schreiber.writeStartElement("Zeile"+String.valueOf(i));
			for (int j=0;j<15;j++){
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
					case 'S': schreiber.writeAttribute("X"+String.valueOf(j),"Steinfeld");
								break;
					case 'L': schreiber.writeAttribute("X"+String.valueOf(j),"Leerfeld");
								break;
					case 'M': schreiber.writeAttribute("X"+String.valueOf(j), "Mauerfeld");
								break;
					default: System.out.println("Fehlerhafte Eingabe. Bitte Programm erneut starten.");
								System.exit(1);
								break;
								}
			}
			schreiber.writeEndElement();
		}//end for(i)
		schreiber.writeEndElement();
		schreiber.writeEndDocument();
		schreiber.close();
		System.out.println("Level erfolgreich erstellt!");

	}

}
