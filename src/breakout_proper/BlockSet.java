package breakout_proper;

import java.util.*;

import game_objects.Point2d;

public class BlockSet
{
	private int x_limit;
	private int y_limit;
	
	private int x_cell;
	private int y_cell;
	
	private int row_number;
	private int col_number;

	private Block[][] block_tab;
	
//-----------------------------------------------
	
	public int getXLim() { return x_limit; }
	public int getYLim() { return y_limit; }
	
	public int getXCell() { return x_cell; }
	public int getYCell() { return y_cell; }
	
	public int getRowN() { return row_number; }
	public int getColN() { return col_number; }
	
//------------------------------------------------
	/**
	 * This method returns block's column based on its x-position.
	 * @param x
	 * @return
	 */
	public int getXAddr(double x)
	{
		int col = (int)x/x_cell;
		
		if(col >= col_number) col = col_number-1;
		else if(col < 0) col = 0;
		
		return col;		
	}
	/**
	 * This method returns block's row based on its y-position.
	 * @param y
	 * @return
	 */
	public int getYAddr(double y)
	{
		int row = (int)y/y_cell;
		
		if(row >= row_number) row = row_number-1;
		else if(row < 0) row = 0;
		
		return row;		
	}
	
	public void insertBlock(int c, int r, int dur)
	{
		if(block_tab[c][r] == null) block_tab[c][r] = new Block(x_cell*c, y_cell*r, x_cell, y_cell, dur);
	}
	
	public Block getBlock(int c, int r)
	{
		return block_tab[c][r];
	}
	
	public Block getBlock(double x, double y)
	{
		return block_tab[this.getXAddr(x)][this.getYAddr(y)];
	}
	/**
	 * This method returns list of blocks in a certain area
	 * @param col
	 * @param row
	 * @param xr
	 * @param yr
	 * @return
	 */
	public List<Block> getVicinity(int col, int row, int xr, int yr)
	{
		List<Block> block_list = new ArrayList<Block>();
		
		for(int i=col-xr;i<=col+xr;i++) for(int j=row-yr;j<=row+yr;j++)
		if(i>=0 && i<this.col_number && j>=0 && j<this.row_number &&
		   this.getBlock(i, j) != null &&
		   this.getBlock(i, j).getDurablility() > 0)
			block_list.add(this.getBlock(i, j));
		
		return block_list;
	}
	
	public List<Block> getVicinity(double x, double y, double r)
	{
		int vx = (int)r/x_cell + 1;
		int vy = (int)r/y_cell + 1;
		
		return this.getVicinity(this.getXAddr(x), this.getYAddr(y), vx, vy);
	}
	
	public List<Block> getVicinity(Point2d p, double r)
	{
		int vx = (int)r/x_cell + 1;
		int vy = (int)r/y_cell + 1;

		return this.getVicinity(this.getXAddr(p.x()), this.getYAddr(p.y()), vx, vy);
	}
	
	public List<Block> getBlockList()
	{
		List<Block> ret = new ArrayList<Block>();
		
		for(int i=0; i<this.getColN(); i++) for(int j=0; j<this.getRowN(); j++)
		if(this.getBlock(i, j) != null) 
			ret.add(this.getBlock(i, j));
		
		return Collections.unmodifiableList(ret);
	}
	
	public void removeBlock(int c, int r)
	{
		block_tab[c][r] = null;
	}

//------------------------------------------------
	@Override
	public String toString()
	{
		String lim = "Limits: " + this.x_limit + "\t" + this.y_limit + "\n";
		String cel = "Cells: " + this.x_cell + "\t" + this.y_cell + "\n";
		
		return "BlockSet data:\n" + lim + cel;
	}
	
	public BlockSet(BlockSet bs)
	{
		row_number = bs.row_number;
		col_number = bs.col_number;
		
		x_cell = bs.x_cell;
		y_cell = bs.y_cell;
		
		x_limit = bs.x_limit;
		y_limit = bs.y_limit;
		
		block_tab = new Block[col_number][row_number];
		for(int i=0;i<col_number;i++) for(int j=0;j<row_number;j++)
			if(bs.block_tab[i][j] != null) block_tab[i][j] = new Block(bs.block_tab[i][j]);
	}
	
	public BlockSet(int x, int y, int col, int row)
	{
		row_number = row;
		col_number = col;
		
		x_cell = x/col;
		y_cell = y/row;
		
		x_limit = (x_cell)*col;
		y_limit = (y_cell)*row;
		
		block_tab = new Block[col][row];
	}
}
