public class Cellule
{
	private int intX, intY;
	
	public Cellule(int posX, int posY)
	{
		this.intX = posX;
		this.intY = posY;
	}
	
	public Cellule(Cellule cell, int posX, int posY)
	{
		this.intX = posX;
		this.intY = posY;
	}
	
	public void mouvBas() 
	{
		this.intY++;
	}

	public void mouvDroite() 
	{
		this.intX++;
	}

	public void mouvGauche() 
	{
		this.intX--;
	}
	
	public int getPosX()
	{
		return this.intX;
	}
	
	public int getPosY()
	{
		return this.intY;
	}

	public void tourne(Cellule cellule)
	{
		int diffX = (this.intX - cellule.intX);
		int diffY = (this.intY - cellule.intY);
		this.intY = cellule.intY - diffX;
		this.intX = cellule.intX + diffY;
	}
	
	public void tourneInverse(Cellule cellule)
	{
		int diffX = (this.intX - cellule.intX);
		int diffY = (this.intY - cellule.intY);
		this.intY = cellule.intY + diffX;
		this.intX = cellule.intX - diffY;
	}
}
