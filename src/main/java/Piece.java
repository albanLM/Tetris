import java.awt.Color;

public class Piece
{
	public static final int nbCell = 4;
	private Cellule[] cellules = new Cellule[nbCell];
	private boolean[][] tab_pos;
	private Color color;
	private int nbRotation;
	private boolean estTourne;
	private char type;
	
	public Piece(Cellule[] cellules, int nbRotation, Color color, char type)
	{
		this.color = color;
		this.type = type;
		this.estTourne = false;
		this.cellules = cellules;
		this.nbRotation = nbRotation;
		this.tab_pos = new boolean[Plateau.cellInWidth][Plateau.cellInHeight];
		
		for(int i = 0; i < nbCell; i++)
		{
			int a = this.cellules[i].getPosX();
			int b = this.cellules[i].getPosY();
			tab_pos[a][b] = true;
		}
	}
	
	public Piece(Cellule[] cellules, int nbRotation, boolean estTourne, Color color, char type)
	{
		this.type = type;
		this.color = color;
		this.estTourne = estTourne;
		this.cellules = cellules;
		this.nbRotation = nbRotation;
		this.tab_pos = new boolean[Plateau.cellInWidth][Plateau.cellInHeight];
		
		for(int i = 0; i < nbCell; i++)
		{
			int a = this.cellules[i].getPosX();
			int b = this.cellules[i].getPosY();
			tab_pos[a][b] = true;
		}
	}
	
	public Piece mouvBas()
	{
		Cellule[] temp = new Cellule[nbCell];
		
		for(int i = 0; i < nbCell; i++)
		{
			temp[i] = new Cellule(cellules[i].getPosX(), cellules[i].getPosY());
			temp[i].mouvBas();
			
			if(temp[i].getPosY() >= Plateau.cellInHeight)
			{
				System.out.println("passe pas");
				return this;
			}
		}
		
		return new Piece(temp, this.nbRotation, this.estTourne, this.color, this.type);
	}
	
	public Piece mouvDroite()
	{
		Cellule[] temp = new Cellule[nbCell];
		
		for(int i = 0; i < nbCell; i++)
		{
			temp[i] = new Cellule(cellules[i].getPosX(), cellules[i].getPosY());
			temp[i].mouvDroite();
			
			if(temp[i].getPosX() >= Plateau.cellInWidth)
			{
				return this;
			}
		}
		
		return new Piece(temp, this.nbRotation, this.estTourne, this.color, this.type);
	}
	
	public Piece mouvGauche()
	{
		Cellule[] temp = new Cellule[nbCell];
		
		for(int i = 0; i < nbCell; i++)
		{
			temp[i] = new Cellule(cellules[i].getPosX(), cellules[i].getPosY());
			temp[i].mouvGauche();
			
			if(temp[i].getPosX() < 0)
			{
				return this;
			}
		}
		
		return new Piece(temp, this.nbRotation, this.estTourne, this.color, this.type);
	}
	
	public Piece tourne()
	{
		Cellule[] temp = new Cellule[nbCell];
		
		temp[0] = new Cellule(cellules[0].getPosX(), cellules[0].getPosY());
		
		switch(this.nbRotation)
		{
		case 1:
			return this;
			
		case 2:
			if(estTourne)
			{
				for(int i = 1; i < nbCell; i++)
				{
					temp[i] = new Cellule(cellules[i].getPosX(), cellules[i].getPosY());
					temp[i].tourneInverse(temp[0]);
					
					if(temp[i].getPosX() < 0 || temp[i].getPosX() >= Plateau.cellInWidth || temp[i].getPosY() < 0 || temp[i].getPosY() >= Plateau.cellInHeight)
					{
						return this;
					}
				}
				estTourne = false;
				return new Piece(temp, this.nbRotation, this.estTourne, this.color, this.type);
			}			
			estTourne = true;
			
		case 3:	
		default:
			for(int i = 1; i < nbCell; i++)
			{
				temp[i] = new Cellule(cellules[i].getPosX(), cellules[i].getPosY());
				temp[i].tourne(temp[0]);
				
				if(temp[i].getPosX() < 0 || temp[i].getPosX() >= Plateau.cellInWidth || temp[i].getPosY() < 0 || temp[i].getPosY() >= Plateau.cellInHeight)
				{
					return this;
				}
			}
			return new Piece(temp, this.nbRotation, this.estTourne, this.color, this.type);
		}
	}
	
	public boolean[][] getPosTab()
	{
		return tab_pos;
	}
	
	public Color getColor()
	{
		return this.color;
	}

	public boolean[][] getAffichageTab() 
	{
		return Fabrique.getAffichageTab(this.type);
	}
}
