//John H. Rucker
//Project 2 Extra Credit assignment
//Asteroids Clone
//CSC 2310 4/18/2010
//This program is designed to be a clone/remix/variation of the
//How to play: Destroy the asteriods and not get hit.
//Control Scheme (W A S D) key scheme as seen in most 1st person shooters
//Turn left using the A-key,and turn right using the D-key.
//thrust with the W-key, reverse using the S-key and fire with spacebar.
//Pres P-key to pause game
//While Game is paused you can adjust the game speed.
//Move the scroll bar to the left to slow down the game and right to speed
//up the game.

import processing.core.*;
import processing.xml.*;
import java.applet.*;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.event.FocusEvent;
import java.awt.Image;
import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;
import java.util.zip.*;
import java.util.regex.*;

public class ProjectTwo extends PApplet {
    
//Asteroid class: Controls asteriod movement and position
class Asteroid
{
  int x;
  int y;
  float angle;
  float velocity;
  float diameter;
  PImage asteroid;

  //Constructor
  Asteroid(int X, int Y, float Angle)
  {
    x = X;
    y = Y;
    angle = Angle;
    velocity = 4;
    diameter = 5;
    asteroid = loadImage("asteroid.gif");
  }

  //Constructor
  Asteroid(float X, float Y, float Angle)
  {
    x = (int)X;
    y = (int)Y;
    angle = Angle;
    velocity = 5;
    diameter = 5;
  }

  //Controls movement of Asteroids
  public void move()
  {
    fill(255, 255, 0);
    x += velocity * cos(angle);
    y += velocity * sin(angle);
    image(asteroid, x, y);
  }

  //This method returns a integer with the contents of the variable
  //(x) to whatever object calls it.
  public int getX()
  {
    return x;
  }

  //This method returns a integer with the contents of the variable
  //(y) to whatever object calls it.
  public int getY()
  {
    return y;
  }

  //This method returns a float with the contents of the variable
  //(angle) to whatever object calls it.
  public float getAngle()
  {
    return angle;
  }

  //This method allows the contents of (x) to be changed
  //to store a different integer value, should that be required
  public void setX(int X)
  {
    x = X;
  }

  //This method allows the contents of (y) to be changed
  //to store a different integer value, should that be required
  public void setY(int Y)
  {
    y = Y;
  }

   //This method allows the contents of (angle) to be changed
  //to store a different String value, should that be required
  public void setAngle(float a)
  {
    angle = a;
  }

  //This method returns the value/width of the asteroid
  public int getWidth()
  {
    return asteroid.width;
  }

  //This method returns the value/height of the asteroid
  public int getHeight()
  {
    return asteroid.height;
  }

   //This method allows the contents of (velocity) to be changed
  //to store a different float value, should that be required
  public void setVelocity(float vel)
  {
    velocity = vel;
  }
}

//Bang Class
class Bang
{
  int x,y;
  PImage Bang;
  int counter;

  //Constructor
  Bang(int X, int Y, PImage foo)
  {
    Bang = foo;
    x=X;
    y=Y;
    counter = 0;
  }

  //Method controling the asteroid elposiont
  public void act()
  {
    PImage sprite;
    if(counter < 5)
      sprite = Bang.get(counter * Bang.width/5, counter / 5 * Bang.height/5 ,Bang.width/5, Bang.height/5);
    else
      sprite = Bang.get((counter % 5) * Bang.width/5, counter / 5 * Bang.height/5 ,Bang.width/5, Bang.height/5);

    image(sprite, x - sprite.width/2, y - sprite.height/2);

    counter++;
  }

  //Controls the number of lives
  public boolean isAlive()
  {
    if(counter > 24)
      return false;
    return true;
  }
}

//Extra life class
class LifeUp
{
  int x,y;
  int lifeTime;
  PImage life;

  //Constructor
  LifeUp(int X, int Y,float L)
  {
    x=X;
    y=Y;
    lifeTime = (int)(100000 / L) ;
    life = loadImage("1up.gif");
  }


  public boolean isAlive()
  {
    if(lifeTime < 0) return false;
    return true;
  }

  //Controls the position of the 1up icon
  public void act()
  {
    image(life, x,y);
    lifeTime--;
  }

  //This method returns a integer with the contents of the variable
  //(x) to whatever object calls it.
  public int getX()
  {
    return x;
  }

  //This method returns a integer with the contents of the variable
  //(x) to whatever object calls it.
  public int getY()
  {
    return y;
  }
}

//
class HScrollbar
{
  int swidth, sheight;    // width and height of bar
  int xpos, ypos;         // x and y position of bar
  float spos, newspos;    // x position of slider
  int sposMin, sposMax;   // max and min values of slider
  int loose;              // how loose/heavy
  boolean over;           // is the mouse over the slider?
  boolean locked;
  float ratio;

  HScrollbar (int xp, int yp, int sw, int sh, int l) {
    swidth = sw;
    sheight = sh;
    int widthtoheight = sw - sh;
    ratio = (float)sw / (float)widthtoheight;
    xpos = xp;
    ypos = yp-sheight/2;
    spos = xpos + swidth/2 - sheight/2;
    newspos = spos;
    sposMin = xpos;
    sposMax = xpos + swidth - sheight;
    loose = l;
  }

  //Keeps track of mouse location and actions
  public void update() {
    if(over()) {
      over = true;
    } else {
      over = false;
    }
    if(mousePressed && over) {
      locked = true;
    }
    if(!mousePressed) {
      locked = false;
    }
    if(locked) {
      newspos = constrain(mouseX-sheight/2, sposMin, sposMax);
    }
    if(abs(newspos - spos) > 1) {
      spos = spos + (newspos-spos)/loose;
    }
  }

  //keeps the slider in position
  public int constrain(int val, int minv, int maxv) {
    return min(max(val, minv), maxv);
  }

  //Game over--Positions mouse automatically
  public boolean over() {
    if(mouseX > xpos && mouseX < xpos+swidth &&
    mouseY > ypos && mouseY < ypos+sheight) {
      return true;
    } else {
      return false;
    }
  }

  public void display() {
    fill(255);
    rect(xpos, ypos, swidth, sheight);
    if(over || locked) {
      fill(153, 102, 0);
    } else {
      fill(102, 102, 102);
    }
    rect(spos, ypos, sheight, sheight);
  }

  //Convert spos to be values between
  //0 and the total width of the scrollbar
  public float getPos() {
    return spos * ratio;
  }
}

class Weapon
{
  int x;
  int y;
  float angle;
  float velocity;
  float diameter;

  Weapon(int X, int Y, float Angle)
  {
    x=X;
    y=Y;
    angle=Angle;
    velocity = 60;
    diameter = 10;
  }

  //Speed and Diameter of the ships Weapon fire
  Weapon(float X, float Y, float Angle)
  {
    x=(int)X;
    y=(int)Y;
    angle=Angle;
    velocity = 90;
    diameter = 20;
  }

  //Weapon fire movement/position
  public void move()
  {
    fill(255,200,0);
    x+= velocity * cos(angle);
    y+= velocity * sin(angle);
    ellipse(x,y,diameter,diameter);
  }

  //This method returns a integer with the contents of the variable
  //(x) to whatever object calls it.
  public int getX()
  {
    return x;
  }

  //This method returns a integer with the contents of the variable
  //(y) to whatever object calls it.
  public int getY()
  {
    return y;
  }

  //This method returns a floats with the contents of the variable
  //(angle) to whatever object calls it.
  public float getAngle()
  {
    return angle;
  }

  //This method returns a floats with the contents of the variable
  //(velocity) to whatever object calls it.
  public void setVelocity(float vel)
  {
    velocity = vel;
  }
}

//Keyboard button functions
boolean left = false;
boolean right = false;
boolean up = false;
boolean down = false;
boolean firstTime = true;
boolean paused = false;
float x;
float y;

//Images loaded for the game
PImage sprite;
PImage asteroid;
PFont f;
PImage backing;
PImage LifeUpSprite;
PImage explosion;
float step;
float angle = radians(45);

//Gameplay details
boolean removal;
int flash;
long score;
int lives;
int invincible;
boolean shoot;
ArrayList shots;
ArrayList asteroids;
ArrayList LifeUps;
ArrayList Bangs;

//Controls the speed of the game
HScrollbar speedBar;

//Frames per sec
long[] fps;

public void setup()
{
  //Sets the size from the Fram playing field
  size(1280, 768);
  smooth();

  //frame.setTitle("Andromeda");
  speedBar = new HScrollbar(width/2 - 100, 2 * height/3, 200, 20, 16);
  x = width/2;
  y = height/2;
  step = 0;

  //Varibles to hold images to be used
  sprite = loadImage("spaceship1.gif");
  backing = loadImage("space.jpg");
  asteroid = loadImage("asteroid.gif");
  LifeUpSprite = loadImage("1up.gif");
  explosion = loadImage("explosion17.png");
  backing.resize(width, height);

  //Game framerate counter
  frameRate(30);
  f = loadFont("AlBayan-20.vlw");
  asteroids = new ArrayList();

  for(int i = 0; i < 40; i++)
  {
    asteroids.add(new Asteroid((int)random(width),(int)random(height),random(5)));
  }

  lives = 10;
  invincible = 100;
  flash = 10;
  shoot = false;
  shots = new ArrayList();
  LifeUps = new ArrayList();
  fps = new long[30];
  Bangs = new ArrayList();

  }

public void draw()
{
  int asdf;
  if(step < 0) asdf = -1;
  else asdf = 1;
  step = asdf * speedBar.getPos() / 80;

  //Sets the displayed text on the main menu
  if(firstTime)
  {
    background(10);
    fill(255);
    textAlign(CENTER);
    text("Ready to Game", width/2, height/2);
    text("Asteroids Remix by John Rucker CSC 2310 Project 2", width/2, height/2 + 20);
    text("Control ship with W for up, S for down, A for left, and D for right", width/2, height/2 + 40);
    text("Press any key to start Game, Press P to pause.", width/2, height/2 + 60);
  }
  else
  {
    if(lives > 0)
    {
      if(!paused && focused)
      {
        background(backing);
        if(random(500) < 1)
        {
          //Calls the LifeUp class
          LifeUps.add(new LifeUp((int)random(width-LifeUpSprite.width), (int)random(height-LifeUpSprite.height),speedBar.getPos()));
        }
        for(int i =0;i<LifeUps.size();i++)
        {
          LifeUp tempLife = (LifeUp)LifeUps.get(i);
          if(!tempLife.isAlive())
            LifeUps.remove(i);
          if(sqrt(sq(x-(tempLife.getX()+LifeUpSprite.height/2)) + sq(y-(tempLife.getY() + LifeUpSprite.width/2))) < sprite.height/2 + 3)
          {
            LifeUps.remove(i);
            lives++;
          }
          tempLife.act();
        }
        if(up || down)
        {
          y+= PApplet.parseInt(-1 * step * sin(angle));
          x+= PApplet.parseInt(-1 * step * cos(angle));
        }
        //Controls the speed of the game
        //Slows down gameplay
        if(left)
        {
          angle -= speedBar.getPos()/4000;
        }
        //Speed up gameplay
        if(right)
        {
          angle += speedBar.getPos()/4000;
        }

        stroke(100);
        fill(100);

        //Randomize asteroid position
        for(int j = 0; j < asteroids.size(); j++)
        {
          Asteroid a = (Asteroid)asteroids.get(j);
          a.setVelocity(speedBar.getPos() / 100);
          if(a.getY() >= height || a.getY() <= 0-a.getHeight() || a.getX() >= width || a.getX() <= 0-getWidth())
          {
            int foo = (int)random(4);
            switch(foo)
            {
              case(0):
              a.setX((int)random(width));
              a.setY(0);
              a.setAngle(random(PI));
              break;
              case(1):
              a.setX((int)random(width));
              a.setY(height);
              a.setAngle(random(PI)+PI);
              break;
              case(2):
              a.setY((int)random(height));
              a.setX(0);
              a.setAngle(random(PI)-PI/2);
              break;
              case(3):
              a.setY((int)random(height));
              a.setX(width);
              a.setAngle(random(PI)+PI/2);
              break;
            }
          }

          a.move();

          //Prints the  X and Y axis location of the ship an
          println(x + "," + y + "," + shots.size() + "," + asteroids.size());

          if(sqrt(sq(x-(a.getX() + asteroid.height/2)) + sq(y - (a.getY() + asteroid.width/2))) < sprite.height/2)
          {
            if(invincible <= 0)
            {
              lives--;
              invincible = 100;
              flash = 10;
            }
          }
        }

        //Removes exlpoded asteroid and weapon fire
        for( int i = 0; i < Bangs.size(); i++)
        {
          Bang explodedObject = (Bang) Bangs.get(i);
          if(explodedObject.isAlive())
          {
            explodedObject.act();
          }
          else
          {
            Bangs.remove(i);
          }
        }
        if(shoot)
        {
          shots.add(new Weapon(x,y,angle+random(.3f)-.15f));
        }
        for(int i = 0; i < shots.size(); i++)
        {
          Weapon bill = (Weapon) shots.get(i);
          bill.setVelocity(speedBar.getPos() / 50);
          bill.move();
          if(bill.getX() < 0 || bill.getX() > width ||
            bill.getY() < 0 || bill.getY() > height)
          {
            shots.remove(i);
          }
          for(int j = 0; j<asteroids.size() ;j++)
          {
            Asteroid a = (Asteroid)asteroids.get(j);
            if(abs(bill.getX()-(a.getX()+asteroid.width/2)) < 10 && abs(bill.getY() - (a.getY()+asteroid.height/2)) < 10)
            {
              shots.remove(i);
              int foo = (int)random(4);
              Bangs.add(new Bang(a.getX() + asteroid.width/2, a.getY()+asteroid.height/2, explosion));
              switch(foo)
              {
                case(0):
                a.setX((int)random(width));
                a.setY(0);
                a.setAngle(random(PI));
                break;
                case(1):
                a.setX((int)random(width));
                a.setY(height);
                a.setAngle(random(PI)+PI);
                break;
                case(2):
                a.setY((int)random(height));
                a.setX(0);
                a.setAngle(random(PI)-PI/2);
                break;
                case(3):
                a.setY((int)random(height));
                a.setX(width);
                a.setAngle(random(PI)+PI/2);
                break;
              }
            }
          }
        }
        if(invincible > 0)
        {
          invincible--;
          tint(255,128);
        }
        else
        {
          tint(255,255);
        }
        if(flash>0)
        {
          fill(0, flash*10);
          rectMode(CORNER);
          rect(0, 0, width, height);
          flash--;
        }

        //Controls the position of the number of lives and score
        textFont(f);
        fill(255);
        textAlign(RIGHT);
        score++;
        text(lives + " lives", width - 3, 20);
        text(score + " points", width - 3, 50);

        //Frams per sec counter
        for(int i = 0; i < fps.length-1; i++)
        {
          fps[i] = fps[i+1];
        }
        fps[fps.length-1] = millis();
        text(fps.length * 1000 / (fps[fps.length - 1] - fps[0]) + " fps", width - 3,80);

        translate(x,y);
        rotate(angle);
        x = constrain(x, 0, width);
        y = constrain(y,0,height);
        image(sprite,-sprite.width/2,-sprite.height/2);
      }
      else // not paused and/or focused
      {
        if(!focused) paused = true;
        background(55,200,240);
        textFont(f);
        fill(0);
        textAlign(CENTER);
        text("Press  P  to resume",width/2,height/2);
        text("Speed:",width/2,2*height/3 - 20);
        speedBar.update();
        speedBar.display();
      }
    }
    else //lives == 0
    {
      background(40,150,200);
      textFont(f);
      fill(255);
      textAlign(CENTER);
      text("GAME OVER", width/2,height/2-100);
      text(score + " points",width/2,height/2); //Position of score
    }
  }
}

//Keeps track of the keys actions when being pressed
public void keyTyped()
{
  firstTime = false;
  switch(key)
  {
    case('w'):
    up = true;
    step = -6;
    break;
    case('s'):
    down = true;
    step = 6;
    break;
    case('a'):
    left = true;
    break;
    case('d'):
    right = true;
    break;
    case('r'):
    loop();
    lives = 10;
    score = 0;
    x = width/2 - sprite.width/2;
    y = width/2 - sprite.width/2;
    break;
    case('p'):
    paused = !paused;
    break;
    case(32):
    shoot = true;
    break;
  }
}

//Keeps track of the keys actions while not being pressed
public void keyReleased()
{
  switch(key)
  {
    case('w'):
    up = false;
    break;
    case('s'):
    down = false;
    break;
    case('a'):
    left = false;
    break;
    case('d'):
    right = false;
    break;
    case(32):
    shoot = false;
    break;
  }
}

  static public void main(String args[]) {
    PApplet.main(new String[] { "--bgcolor=#D4D0C8", "ProjectTwo" });
  }
}
