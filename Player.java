import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.*;
import org.lwjgl.opengl.*;
import static org.lwjgl.opengl.GL11.*;


public class Player{
 private String name;
 private boolean alive;
 private int posx, posy;
 
 
 public Player (String name, int x, int y){
	this.name=name;
	this.posx=x;
	this.posy=y;
	this.alive=true;
 }

 public boolean isAlive(){
	 return alive;
 }
 
 public int getx(){
	 return posx;
 }
 
 public int gety(){
	 return posy;
 }
 
 public void die(){
	 alive=false;
 }
 
 public void move(int addx, int addy){
	 posx+=addx;
	 posy+=addy;														//taste fürs bombe legen fehlt
 }
 
 public void print(){
	 /*glBegin(GL_QUADS);
	 	glVertex2i(10*(posx-1),10*(posy-1));
	 	glVertex2i(10*posx, 10*(posy-1));
	 	glVertex2i(10*posx,10*posy);
	 	glVertex2i(10*(posx-1),10*posy);
	 glEnd();*/ // Zum Test: Bomberman=weißer Kasten
 }
 
}
 