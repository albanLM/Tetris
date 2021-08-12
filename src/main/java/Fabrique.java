import java.awt.Color;
import java.util.Random;

public class Fabrique 
{
	public static final int nbPiece = 7;
	
	public Fabrique()
	{
	}
	
	public static Piece getRandomPiece()
	{		
		Piece piece = null;
				
		Random random = new Random();
		int max = nbPiece - 1;
		int min = 0;
		int nbRandom = random.nextInt(max - min + 1) + min;
		
		min = 0;
		max = Plateau.cellInWidth - 4;
		int posRandom = random.nextInt(max - min + 1) + min;
		
		switch(nbRandom)
		{
			case 0 :
				piece = createO(posRandom);	
				break;
			case 1 :
				piece = createI(posRandom);
				break;
			case 2 :
				piece = createS(posRandom);	
				break;
			case 3 :
				piece = createZ(posRandom);
				break;
			case 4 :
				piece = createL(posRandom);	
				break;
			case 5 :
				piece = createJ(posRandom);
				break;
			case 6 :
				piece = createT(posRandom);	
				break;
		}
		
		return piece;
	}

	private static Piece createO(int posRandom) 
	{
		Cellule[] temp = new Cellule[Piece.nbCell];
		
		temp[0] = new Cellule(posRandom, 0);
		temp[1] = new Cellule(temp[0], posRandom + 1, 0);
		temp[2] = new Cellule(temp[0], posRandom + 1, 1);
		temp[3] = new Cellule(temp[0], posRandom, 1);
		
		return new Piece(temp, 1, new Color(245, 245, 0), 'o');
	}
	
	private static Piece createI(int posRandom) 
	{
		Cellule[] temp = new Cellule[Piece.nbCell];
		
		temp[0] = new Cellule(posRandom + 1, 0);
		temp[1] = new Cellule(temp[0], posRandom, 0);
		temp[2] = new Cellule(temp[0], posRandom + 2, 0);
		temp[3] = new Cellule(temp[0], posRandom + 3, 0);
		
		return new Piece(temp, 2, Color.CYAN, 'i');
	}
	
	private static Piece createS(int posRandom) 
	{
		Cellule[] temp = new Cellule[Piece.nbCell];
		
		temp[0] = new Cellule(posRandom + 1, 0);
		temp[1] = new Cellule(temp[0], posRandom + 2, 0);
		temp[2] = new Cellule(temp[0], posRandom, 1);
		temp[3] = new Cellule(temp[0], posRandom + 1, 1);
		
		return new Piece(temp, 2, Color.GREEN, 's');
	}
	
	private static Piece createZ(int posRandom) 
	{
		Cellule[] temp = new Cellule[Piece.nbCell];
		
		temp[0] = new Cellule(posRandom + 1, 0);
		temp[1] = new Cellule(temp[0], posRandom, 0);
		temp[2] = new Cellule(temp[0], posRandom + 1, 1);
		temp[3] = new Cellule(temp[0], posRandom + 2, 1);
		
		return new Piece(temp, 2, Color.RED, 'z');
	}
	
	private static Piece createL(int posRandom) 
	{
		Cellule[] temp = new Cellule[Piece.nbCell];
		
		temp[0] = new Cellule(posRandom + 1, 0);
		temp[1] = new Cellule(temp[0], posRandom, 1);
		temp[2] = new Cellule(temp[0], posRandom, 0);
		temp[3] = new Cellule(temp[0], posRandom + 2, 0);
		
		return new Piece(temp, 3, new Color(255, 128, 0), 'l');
	}
	
	private static Piece createJ(int posRandom) 
	{
		Cellule[] temp = new Cellule[Piece.nbCell];
		
		temp[0] = new Cellule(posRandom + 1, 0);
		temp[1] = new Cellule(temp[0], posRandom + 2, 1);
		temp[2] = new Cellule(temp[0], posRandom, 0);
		temp[3] = new Cellule(temp[0], posRandom + 2, 0);
		
		return new Piece(temp, 3, Color.BLUE, 'j');
	}
	
	private static Piece createT(int posRandom) 
	{
		Cellule[] temp = new Cellule[Piece.nbCell];
		
		temp[0] = new Cellule(posRandom + 1, 0);
		temp[1] = new Cellule(temp[0], posRandom, 0);
		temp[2] = new Cellule(temp[0], posRandom + 2, 0);
		temp[3] = new Cellule(temp[0], posRandom + 1, 1);
		
		return new Piece(temp, 3, new Color(199, 66, 225), 't');
	}
	
	public static boolean[][] getAffichageTab(char type)
	{
		final int nbCell = Piece.nbCell;
		boolean[][] temp = new boolean[nbCell][nbCell];
		
		for(int i = 0; i < nbCell; i++)
		{
			for(int j = 0; j < nbCell; j++)
			{
				temp[i][j] = false;
			}
		}
		
		switch(type)
		{
			case 'o':
				temp[0][0] = true;
				temp[0][1] = true;
				temp[1][0] = true;
				temp[1][1] = true;
				break;
			case 'i':
				temp[0][0] = true;
				temp[1][0] = true;
				temp[2][0] = true;
				temp[3][0] = true;
				break;
			case 's':
				temp[0][1] = true;
				temp[1][1] = true;
				temp[1][0] = true;
				temp[2][0] = true;
				break;
			case 'z':
				temp[0][0] = true;
				temp[1][0] = true;
				temp[1][1] = true;
				temp[2][1] = true;
				break;
			case 'l':
				temp[0][0] = true;
				temp[0][1] = true;
				temp[1][0] = true;
				temp[2][0] = true;
				break;
			case 'j':
				temp[0][0] = true;
				temp[1][0] = true;
				temp[2][0] = true;
				temp[2][1] = true;
				break;
			case 't':
				temp[0][0] = true;
				temp[1][0] = true;
				temp[2][0] = true;
				temp[1][1] = true;
				break;
		}
		
		return temp;
	}
}
