import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Game extends JFrame implements ActionListener, KeyListener{

	Display jpan = new Display();
	LinkedList<Integer> list = new LinkedList<Integer>();
	public static int sizeOfTiles;
	Thread t;
	boolean first;
	int counter = 0;
	ArrayList<String> directionList;
	boolean isValidDirection = false;
	String validDirection = "";
	
	static Game game;

	public static int[][] field;

	public Game(int sizeOfMaze, int tileSize) throws InterruptedException
	{
		super("Basic Swing App");

		field = new int[sizeOfMaze][sizeOfMaze];

		sizeOfTiles = tileSize;

		this.pack();
		this.add(jpan);
		this.setTitle("Maze");
		this.setSize(tileSize*sizeOfMaze,tileSize*(sizeOfMaze+1));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);

		this.addKeyListener(this);
		//setFocusable(true);
		requestFocus();
		
		this.setVisible(true);
		initializeGame();
		jpan.repaint();
	}

	public void initializeGame() throws InterruptedException
	{
		directionList = new ArrayList<String>(4);
		directionList.add("left");
		directionList.add("right");
		directionList.add("up");
		directionList.add("down");

		//		int counter = 0;
		//		for(int y=0; y<field.length; y++)
		//		{
		//			for(int x=0; x<field[0].length; x++)
		//			{
		//				if(counter%2 == 1)
		//					field[x][y] = 1;
		//				counter++;
		//			}
		//			counter++;
		//		}
		for(int i=0; i<=20; i++)
			list.add(1);
		//field[field.length/2][field.length/2] = 3;

		//		t = new Thread( new Runnable(){
		//			public void run(){
		//
		//				int acceleration =25;
		//
		//					try {
		//						Thread.sleep(acceleration);
		//					} catch(InterruptedException ex) {
		//						Thread.currentThread().interrupt();
		//					}
		//					repaint();
		//				}
		//		});
		//
		//		t.start();
		first =true;
		//openSection(field.length/2, field.length/2);

		//repaint();

		//Thread.sleep(5000);

		//walkMaze(field.length/2, field.length/2);

		//repaint();
	}

	public void resetMaze() throws InterruptedException
	{
		for(int y=0; y<field.length; y++)
			for(int x=0; x<field[0].length; x++)
				field[x][y] = 0;
		
		mazeMaker(0,0);
	}
	
	public void determineSpace(int x, int y) throws InterruptedException
	{	
		if(!isValid(x) || !isValid(y))
			return;

		if(field[x][y] == 0)
		{
			long start = System.nanoTime();
			while(start + 1 >= System.nanoTime());			if(first)
			{
				field[x][y] = 3;
				first = false;
				//System.out.println("NEW BLUE x = "+x+", y = "+y);
			}
			else
			{
				int rand = (Math.random() <= 0.6) ? 1 : 2;
				field[x][y] = rand;

				String color = (rand == 1) ? "WHITE" : "RED";
				//System.out.println("NEW "+color+" x = "+x+", y = "+y);
			}
			repaint();
		}
	}

	public boolean isValid(int num)
	{
		if(num < field.length && num >= 0)
			return true;
		else
			return false;
	}

	public void walkMaze(int x, int y) throws InterruptedException
	{
		if(!isValid(x) || !isValid(y))
		{
			//System.out.println("OUT OF BOUNDS x = "+x+", y = "+y);
			return;
		}

		if(field[x][y]>3)
			return;

		if(field[x][y] == 2)
		{
			//Thread.sleep(5);
			field[x][y] = 5; //red wall
			//repaint();
			return;
		}
		else if(field[x][y] == 3) //blue start
		{
			Thread.sleep(5);
			field[x][y] = 6;
			repaint();
		}
		else
		{
			Thread.sleep(5);
			field[x][y] = 6; //white path
			repaint();
		}

		for(int j=-1; j<2; j++)
		{
			for(int i=-1; i<2; i++)
			{
				{
					if(j != i && (j != 1 || i != -1) && (i != 1 || j != -1))
						walkMaze(x+i,y+j);
				}
			}
		}
	}


	public void openSection(int x, int y) throws InterruptedException
	{
		//System.out.println("Start");

		if(counter > 100)
			return;

		if(!isValid(x) || !isValid(y))
		{
			//System.out.println("OUT OF BOUNDS x = "+x+", y = "+y);
			return;
		}

		if(field[x][y] != 0)
		{
			//System.out.println("OLD x = "+x+", y = "+y);
			return;
		}

		determineSpace(x, y);

		if(field[x][y] == 2)
		{
			//System.out.println("WALL x = "+x+", y = "+y);
			return;
		}

		for(int j=-1; j<2; j++)
		{
			for(int i=-1; i<2; i++)
			{
				{
					//if(j != i && (j != 1 || i != -1) && (i != 1 || j != -1))
					openSection(x+i,y+j);
				}
			}
		}
	}

	public void mazeMaker(int x, int y) throws InterruptedException
	{		
		field[x][y] = 3;
		repaint();
		Thread.sleep(1);
		field[x][y] = 1;
		repaint();
		ArrayList<String> temp = (ArrayList<String>) directionList.clone();
		Collections.shuffle(temp);
		
		if(isValidDirection)
		{
			for(int i=0; i<temp.size(); i++)
			{
				if(temp.get(i) == validDirection)
				{
					temp.remove(i);
					temp.add(0, validDirection);
					break;
				}
			}
		}

		for(int i=0; i<4; i++) //go in a random direction
			moveDirection(x,y, temp.get(i));
		
		return;
	}

	public void moveDirection(int x, int y, String direction) throws InterruptedException
	{
		if(direction.equals("down"))
		{
			if(canMoveDirection(x, y, direction))
			{
				if(!isValidDirection)
					isValidDirection = true;
				else
					isValidDirection = false;
				validDirection = direction;
				mazeMaker(x,y+1);
			}
			else
				isValidDirection = false;
		}
		else if(direction.equals("up"))
		{
			if(canMoveDirection(x, y, direction))
			{
				if(!isValidDirection)
					isValidDirection = true;
				else
					isValidDirection = false;
				validDirection = direction;
				mazeMaker(x,y-1);
			}
			else
				isValidDirection = false;
		}
		else if(direction.equals("left"))
		{
			if(canMoveDirection(x, y, direction))
			{
				if(!isValidDirection)
					isValidDirection = true;
				else
					isValidDirection = false;
				validDirection = direction;
				mazeMaker(x-1,y);
			}
			else
				isValidDirection = false;
		}
		else if(direction.equals("right"))
		{
			if(canMoveDirection(x, y, direction))
			{
				if(!isValidDirection)
					isValidDirection = true;
				else
					isValidDirection = false;
				validDirection = direction;
				mazeMaker(x+1,y);
			}
			else
				isValidDirection = false;
		}
	}
	
	public boolean canMoveDirection(int x, int y, String direction) throws InterruptedException
	{		
		if(direction.equals("down"))
		{
			if(isValid(y+1) && isValid(y+2) && field[x][y+1] == 0 && field[x][y+2] == 0 
					&& !touchingSideWalls(x,y+1,true) && !touchingSideWalls(x,y+2,true))
			return true;
		}
		else if(direction.equals("up"))
		{
			if(isValid(y-1) && isValid(y-2) && field[x][y-1] == 0 && field[x][y-2] == 0 
					&& !touchingSideWalls(x,y-1,true) && !touchingSideWalls(x,y-2,true))
				return true;
		}
		else if(direction.equals("left"))
		{
			if(isValid(x-1) && isValid(x-2) && field[x-1][y] == 0 && field[x-2][y] == 0 
					&& !touchingSideWalls(x-1,y,false) && !touchingSideWalls(x-2,y,false))
				return true;
		}
		else if(direction.equals("right"))
		{
			if(isValid(x+1) && isValid(x+2) && field[x+1][y] == 0 && field[x+2][y] == 0 
					&& !touchingSideWalls(x+1,y,false) && !touchingSideWalls(x+2,y,false))
				return true;
		}
		return false;
	}

	public boolean touchingSideWalls(int x, int y, boolean isVertical)
	{		
		for(int i=-1; i<=1; i++)
		{
			if(i!=0)
			{
				if(isVertical)
				{
					if(isValid(x+i) && field[x+i][y] == 1)
						return true;
				}
				else
				{
					if(isValid(y+i) && field[x][y+i] == 1)
						return true;
				}
			}
		}
		return false;
	}

	public static void main(String[] args) throws InterruptedException 
	{
		//maze dimention, pixel size
		game = new Game(225, 3);

		game.mazeMaker(1,1);
		//game.resetMaze();
		game.repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
//		int keycode = e.getKeyCode();
//		
//		if(keycode == 82)
//		{
//			game.resetMaze();
//		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
