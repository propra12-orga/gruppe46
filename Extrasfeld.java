/**
 * Klasse fuer Extrasfelder
 * @author philip
 *
 */
public class Extrasfeld extends Feld
{
	public int art;
	
	public Extrasfeld(int art)
	{
		this.art = art;
	}
	public void draw(int x, int y)
	{
		switch(art)
		{
			case 1: {}//Dieses Feld generiert ein zusätzliches Leben um einen Bombentreffer zu überleben}
			case 2: {}//Dieses Feld generiert ein Upgrade, um die Anzahl der Bomben, die man gleichzeitig legen kann, um 1 zu erhöhen
			case 3: {}//Dieses Feld generiert ein Upgrade, wodurch der Spieler dazu befähigt wird, die Bomben linear zu treten
			case 4: {}//Dieses Feld generiert ein Upgrade um (einmalig) eine Super-Bombe anstatt einer normalen Bombe zu legen. Die Super-Bombe hat <spezifikation>
			case 5: {}//Dieses Feld generiert eine temporäre Steuerungsbehinderung für alle feindlichen/anderen Spieler. Alle außer der Spieler, der das Feld betritt, unterliegen einer umgekehrten Steuerung (Ausnahme: Bombe legen)
			case 6: {}//Dieses Feld generiert ein Teleportationsfeld. Der Spieler der auf dieses Feld tritt wird mit sofortiger Wirkung zum entsprechenden Feld teleportiert
			case 7: {}//Dieses Feld generiert ein Upgrade, welches einen temporären Geschwindigkeitsbonus für den Spieler gibt, der es aufsammelt
			case 8: {}//Dieses Feld generiert ein temporäre Spielerbehinderung. Alle gegnerischen/anderen Spieler sind nur noch 50-75% so schnell
			case 9: {}//Dieses Feld generiert bei Kontakt auf dem gesammten Spielfeld Bomben (5-10 Bomben? [Abhängig davon, wie viele freie Felder es gibt]
			case 10: {}//Dieses Feld generiert ein Loch. Der Spieler, der dieses Loch betritt fällt darin hinein und stirbt (egal wie viele Leben dieser noch hatte = Instant Death). Weitere Spieler können dieses Feld gefahrlos überqueren
			case 11: {}//Dieses Feld generiert ein Uprade, welches temporäre Unverwundbarkeit verleiht. Dem Spieler wird kein Bombentreffer angerechnet. Fraglich: Würde er bei ART == 10 sterben?
			case 12: {}//Dieses Feld generiert einen FROST-SCHOCK. Der Spieler der es aufsammelt, darf sich temporär nicht bewegen. Wenn der Frost-Schock vorbei ist, erhält der Spieler seine komplette Bewegungsfreiheit.
		}
		if (art == 1)
		{
			
		}
		if (art == 2)
		{
			
		}
		if (art == 3)
		{
			
		}
		if (art == 4)
		{
			
		}
		if (art == 5)
		{
			
		}
		if (art == 6)
		{
			
		}
		if (art == 7)
		{
			
		}
		if (art == 8)
		{
			
		}
		if (art == 9)
		{
			
		}
		if (art == 10)
		{
			
		}
		if (art == 11)
		{
			
		}
		if (art == 12)
		{
			
		}
		if (art == 13)
		{
			//
		}
		/*
		 * Variable fuer das jeweilige Extra; Aufruf einer getter
		 * Renderer.Tile_   .draw(x * 128 * 0.33f, y * 128 * 0.33f);
		 * Switch fuer Unterschiede 
		 */
	}
}
