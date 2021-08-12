import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Plateau extends JPanel implements KeyListener, ActionListener
{
	// Paramètres Plateau
	private JFrame fenetre;
	private boolean[][] tabCellule;
	private Color[][] tabColor;
	private boolean enJeu;
	public static final int cellSize = 30;
	public static final int cellInWidth = 10;
	public static final int cellInHeight = 22;
	
	// Paramètres Timer
	private Timer timer;
	private int interval;
	public static final int intervalMax = 1000;
	public static final int intervalMin = 200;
	
	// Paramètres Piece
	private Piece piece, pieceSuivante;
	
	// Paramètre Score
	private Score score;
	
	private Font customFont;
	
	public Plateau() 
	{
		init();		
		
		// Initialisation de la fenêtre graphique
		loadFont();
		fenetre = new JFrame();
		fenetre.setTitle("Tetris");
	    fenetre.setSize((cellInWidth + 10) * cellSize, (cellInHeight + 1) * cellSize);
	    fenetre.setLocationRelativeTo(null);
	    fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    fenetre.setResizable(false);
	    fenetre.setContentPane(this);
	    fenetre.setVisible(true);
	    fenetre.addKeyListener(this);		
	}
	
	public void init()
	{        
		// Initialisation Timer
		interval = intervalMax;
		timer = new Timer(interval, this);
		timer.start();
		
		// Initialisation variables et objets
		enJeu = true;
		tabCellule = new boolean[cellInWidth][cellInHeight];
		tabColor = new Color[cellInWidth][cellInHeight];
		piece = Fabrique.getRandomPiece();
		pieceSuivante = Fabrique.getRandomPiece();
		score = new Score();
		
		for(int i =  0; i < cellInWidth; i++)
		{
			for(int j =  0; j < cellInHeight; j++)
			{
				tabCellule[i][j] = false;
				tabColor[i][j] = Color.BLACK;
			}
		}	
		
		// Insertion de piece dans plateau 
		// Pour que la pièce s'affiche au début d'une nouvelle partie
		inserer(piece);
	}
	
	public void loadFont()
	{
		try {
		    //create the font to use. Specify the size!
			URI font =  getClass().getClassLoader().getResource("Tetrix.ttf").toURI();
		    customFont = Font.createFont(Font.TRUETYPE_FONT, new File(font)).deriveFont(12f);
		    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    //register the font
		    ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(font)));
		} catch (IOException e) {
		    e.printStackTrace();
		} catch(FontFormatException e) {
		    e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	public void paintComponent(Graphics g)
	{	
		g.setFont(customFont);
		
		// Affichage pan de gauche
		for(int i = 0; i < cellInWidth; i++)
	    {
	    	for(int j = 0; j < cellInHeight; j++)
	    	{
	    		// Affichage des cellules
    			g.setColor(tabColor[i][j]);
				g.fillRect(i * cellSize, j * cellSize, cellSize, cellSize);
				
	    		// Affichage grille de gauche
	    		g.setColor(Color.BLACK);
	    		g.drawRect(i * cellSize, j * cellSize, cellSize, cellSize);
	    	}
	    }
		
		// Affichage pan de droite
		g.setColor(Color.BLACK);
		g.fillRect(cellInWidth * cellSize, 0, (cellInWidth + 10) * cellSize - 1, cellInHeight * cellSize - 1);
		g.setColor(Color.WHITE);
		g.drawRect(0, 0, (cellInWidth) * cellSize, cellInHeight * cellSize);
		g.drawRect(1, 1, (cellInWidth) * cellSize - 1, cellInHeight * cellSize - 1);
		g.drawRect((cellInWidth) * cellSize, 0, (cellInWidth + 10) * cellSize, cellInHeight * cellSize);
		g.drawRect((cellInWidth) * cellSize + 1, 1, (cellInWidth) * cellSize - 1, cellInHeight * cellSize - 1);
		
		// Afficher le score
		g.setColor(Color.WHITE);
		g.drawString("Score : " + score.getScore(), (cellInWidth + 1) * cellSize, cellSize);
		
		// Afficher piece suivante
		boolean[][] temp  = pieceSuivante.getAffichageTab();
		g.drawString("Pi�ce suivante :", (cellInWidth + 1) * cellSize, 3 * cellSize);
		for(int i = 0; i < Piece.nbCell; i++)
	    {
	    	for(int j = 0; j < Piece.nbCell; j++)
	    	{
	    		if(temp[i][j])
	    		{	    			
	    			g.setColor(pieceSuivante.getColor());
					g.fillRect((cellInWidth + 1 + i) * cellSize, (j + 4) * cellSize, cellSize, cellSize);
					g.setColor(Color.BLACK);
		    		g.drawRect((cellInWidth + 1 + i) * cellSize, (j + 4) * cellSize, cellSize, cellSize);
	    		}	    		
	    	}
	    }
		
		g.setColor(Color.WHITE);
		g.drawString("Aller � droite : Fl�che Droite", (cellInWidth + 1) * cellSize, 8 * cellSize);
		g.drawString("Aller � gauche : Fl�che Gauche", (cellInWidth + 1) * cellSize, 9 * cellSize);
		g.drawString("Descendre :      Fl�che Bas", (cellInWidth + 1) * cellSize, 10 * cellSize);
		g.drawString("Tourner :        Fl�che Haut", (cellInWidth + 1) * cellSize, 11 * cellSize);
		
		if(!enJeu)
		{
			g.setColor(Color.WHITE);
			g.drawString("Nouvelle Partie : Entr�e", (cellInWidth + 1) * cellSize, 13 * cellSize);
		}
	}

	public boolean accepter(Piece piece)
	{
		boolean[][] tabCellPiece = piece.getPosTab();
		
		for(int i = 0; i < cellInWidth; i++)
		{
			for(int j = 0; j < cellInHeight; j++)
			{
				// Si la piece testée occupe une cellule déjà prise, retourner faux
				if(tabCellule[i][j] && tabCellPiece[i][j])
				{
					return false;
				}
			}
		}
		
		return true;
	}
	
	public boolean enBas(Piece piece)
	{
		boolean[][] tabCellPiece = piece.getPosTab();
		
		for(int i = 0; i < cellInWidth; i++)
		{
			for(int j = 0; j < cellInHeight - 1; j++)
			{
				// Si une cellule occupée se trouve en dessous de la piece, retourner vrai, la piece ne peux plus descendre
				if(tabCellule[i][j+1] && tabCellPiece[i][j])
				{
					return true;
				}
			}
		}
		
		// Si la piece est au sol, retourner vrai
		for(int i = 0; i < cellInWidth; i++)
		{
			if(tabCellPiece[i][cellInHeight - 1])
			{
				return true;
			}
		}
		
		return false;
	}
	
	public void ajouter(Piece piece)
	{
		if(this.piece != null)
		{
			retirer(this.piece);
		}

		if(accepter(piece))
		{
			if(enBas(piece))
			{	
				inserer(piece);
				
				this.piece = null;
				
				verifLigne();
				
				ajouter(pieceSuivante);
				pieceSuivante = Fabrique.getRandomPiece();
			}
			else
			{
				this.piece = piece;
				
				inserer(piece);
			}
		}
		else
		{
			if(this.piece != null)
			{
				ajouter(this.piece);
			}
			else
			{
				enJeu = false;
				timer.stop();
			}
		}		
		
		repaint();
	}
	
	public void retirer(Piece piece)
	{
		boolean[][] tabCellPiece = piece.getPosTab();
		
		for(int i = 0; i < cellInWidth; i++)
		{
			for(int j = 0; j < cellInHeight; j++)
			{
				if(tabCellPiece[i][j])
				{
					tabCellule[i][j] = false;
					tabColor[i][j] = Color.BLACK;
				}
			}
		}
	}
	
	public void inserer(Piece piece)
	{
		boolean[][] tabCellPiece = piece.getPosTab();
		
		for(int i = 0; i < cellInWidth; i++)
		{
			for(int j = 0; j < cellInHeight; j++)
			{
				if(tabCellPiece[i][j])
				{							
					tabCellule[i][j] = true;
					tabColor[i][j] = piece.getColor();
				}
			}
		}
	}
	
	public void verifLigne()
	{
		boolean test;
		
		for(int i = 0; i < cellInHeight; i++)
		{
			test = true;
			
			for(int j = 0; j < cellInWidth; j++)
			{
				if(!tabCellule[j][i])
				{
					test = false;
				}
			}
			
			if(test)
			{
				suppLigne(i);
				score.add();
				if(interval - 50 > 150)
				{
					interval -= 50;
					timer.setDelay(interval);
				}
			}
		}
	}
	
	public void suppLigne(int k)
	{
		for(int i = 0; i < cellInWidth; i++)
		{
			for(int j = k; j > 0; j--)
			{
				tabCellule[i][j] = tabCellule[i][j-1];
				tabColor[i][j] = tabColor[i][j-1];
			}
		}
	}
	
	public void keyTyped(KeyEvent e) 
	{		
	}

	public void keyPressed(KeyEvent e) 
	{
		if(this.enJeu)
		{
			if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			{
				ajouter(this.piece.mouvDroite());
			}
			if(e.getKeyCode() == KeyEvent.VK_LEFT)
			{
				ajouter(this.piece.mouvGauche());
			}
			if(e.getKeyCode() == KeyEvent.VK_DOWN)
			{
				ajouter(this.piece.mouvBas());
			}
			if(e.getKeyCode() == KeyEvent.VK_UP)
			{
				ajouter(this.piece.tourne());
			}
		}	
		else
		{
			if(e.getKeyCode() == KeyEvent.VK_ENTER)
			{
				init();
			}
		}
	}
	
	public void keyReleased(KeyEvent e)
	{
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		ajouter(this.piece.mouvBas());
	}
	
	public static void main(String[] args) 
	{		
		new Plateau();
	}	
}
