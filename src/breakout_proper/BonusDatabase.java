package breakout_proper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BonusDatabase 
{
	private Random rand = new Random();
	private int chance_sum;
	
	private class FunctionChance
	{
		Bonus.Function func;
		int chance;
		
		FunctionChance(int c, Bonus.Function f) // consider string identifier
		{
			chance = c;
			func = f;
		}
	}
	
	private List<FunctionChance> bonus_tab;
	
	//-----------------------------------------
	
	public int size() { return bonus_tab.size(); }
	
	//-----------------------------------------
	/*
	public int getIndex(Bonus.Function bf)
	{
		for(int i=0;i<bonus_tab.size();i++)
		{
			if( bonus_tab.get(i).func == bf) return i;
		}
		
		return 0; //throw?
	}*/
	
	public void insertBonus(int c, Bonus.Function bf)
	{
		bonus_tab.add(new FunctionChance(c,bf));
		chance_sum += c;
	}
	
	public void removeBonus(int n)
	{
		chance_sum -= bonus_tab.get(n).chance;
		bonus_tab.remove( bonus_tab.get(n) );
	}
	/**
	 * This method returns random bonus function from its database.
	 * Chances are determined by a number that has been inserted with the function.
	 * There is may be some chance for that funtion to return null.
	 * @return
	 */
	public Bonus.Function gamble()
	{
		int rnd = rand.nextInt(chance_sum+1);

		for(FunctionChance bf : bonus_tab)
		{
			rnd -= bf.chance;
			if(rnd < 0) return bf.func;
		}
		
		return null;
	}
	
	//-----------------------------------------

	public BonusDatabase(int nothing_chance)
	{
		bonus_tab = new ArrayList<FunctionChance>();
		
		chance_sum = nothing_chance;
	}
}
