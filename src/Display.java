import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;


public class Display extends JPanel implements ActionListener, MouseListener, KeyListener, MouseMotionListener{

	Font borderFont = new Font("Helvitica", Font.BOLD, 20);
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		this.setBackground(Color.CYAN);
		paintMaze(g);
	}
	
	public void paintMaze(Graphics g)
	{
		int size = Game.sizeOfTiles;
		g.setFont(borderFont);
		g.setColor(Color.red);
		
		for(int i=0; i<Game.field.length; i++)
		{
			g.drawString(""+i, size*(Game.field.length), i*size+size);
			g.drawString(""+i, i*size, size*(Game.field.length+1));

		}
		
		for(int x=0; x<Game.field.length; x++)
		{
			for(int y=0; y<Game.field[0].length; y++)
			{
				if(Game.field[x][y] == 1)
					g.setColor(Color.WHITE);
				else if(Game.field[x][y] == 4)
					g.setColor(Color.YELLOW);
				else if(Game.field[x][y] == 2)
					g.setColor(Color.RED);
				else if(Game.field[x][y] == 5)
					g.setColor(Color.RED);
				else if(Game.field[x][y] == 3 || Game.field[x][y] == 6)
					g.setColor(Color.BLUE);
				else
					g.setColor(Color.BLACK);
				g.fillRect(x*size, y*size, size, size);
//				g.setColor(Color.GREEN);
//				g.drawRect(x*size, y*size, size, size);

			}
		}
	}
	
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
