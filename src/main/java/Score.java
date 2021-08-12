public class Score
{
	private int score;
	private int ligneComp;
	
	public Score()
	{
		ligneComp = 0;
		score = 0;
	}
	
	public void setScore(int score)
	{
		this.score = score;
	}
	
	public int getScore()
	{
		return this.score;
	}
	
	public void add()
	{
		if(ligneComp < 20)
			ligneComp++;
		
		// Score incrémenté de 10 * le nombre de ligne complété, jusqu'à 20;
		this.score +=  10 * this.ligneComp;
	}
}
