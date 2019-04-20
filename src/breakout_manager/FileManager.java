package breakout_manager;

import breakout_proper.*;
import java.io.*;
import java.util.*;

public final class FileManager 
{
	static private class MapInfo implements java.io.Serializable
	{
		private static final long serialVersionUID = 5254129445516869165L;
		/*
		 */
		int x_limit, y_limit;
		int col_number, row_number;
		BlockInfo[][] binfo;
		
		static private class BlockInfo implements java.io.Serializable
		{
			private static final long serialVersionUID = -6689670823229674672L;
			/*
			 */
			int durablility;
			boolean destructible;
			
			BlockInfo(int dur, boolean des) { 
				durablility = dur;
				destructible = des;
				}
		}
		
		BlockSet convert()
		{
			BlockSet ret = new BlockSet(x_limit, y_limit, col_number, row_number);
			
			for(int i=0;i<col_number;i++) for(int j=0;j<row_number;j++) 
				if(binfo[i][j] != null) 
				{
					ret.insertBlock(i, j, binfo[i][j].durablility);
					if(!binfo[i][j].destructible) ret.getBlock(i, j).setIndestructible();
				}
			
			return ret;
		}
		
		MapInfo(BlockSet bs)
		{
			x_limit = bs.getXLim();
			y_limit = bs.getYLim();
			
			col_number = bs.getColN();
			row_number = bs.getRowN();
			
			binfo = new BlockInfo[col_number][row_number];
			
			for(int i=0;i<col_number;i++) for(int j=0;j<row_number;j++) 
				if(bs.getBlock(i, j) != null)
					binfo[i][j] = new BlockInfo(bs.getBlock(i, j).getDurablility(), bs.getBlock(i, j).isDestructible());
		}
	}
	
	static public void saveMap(String name, BlockSet map)
	{
		try 
		{
			FileOutputStream fileOut = new FileOutputStream(name + ".brk");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			
			out.writeObject(new MapInfo(map));
			
			out.close();
			fileOut.close();
		}
		catch(IOException i) 
		{
			i.printStackTrace();
		}		
	}
	
	static public BlockSet loadMap(String name)
	{
		MapInfo ret;
		
		try 
		{
			FileInputStream fileIn = new FileInputStream(name + ".brk");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			
			ret = (MapInfo)in.readObject();
			
			in.close();
			fileIn.close();
		}
		catch(IOException i) 
		{
			i.printStackTrace();
			return null;
		}
		catch(ClassNotFoundException c) 
		{
			c.printStackTrace();
			return null;
		}	
		
		return ret.convert();
	}	
	/**
	 * This method returns filenames of every map file in a directory.
	 * @return
	 */
	static public List<String> mapNames()
	{
		List<String> ret = new ArrayList<String>();
		File folder = new File(".");
		File[] listOfFiles = folder.listFiles();
		
		for (int i = 0; i < listOfFiles.length; i++) if (listOfFiles[i].isFile() && listOfFiles[i].getName().endsWith(".brk")) 
			ret.add( listOfFiles[i].getName().substring(0, listOfFiles[i].getName().length() - 4) );

		return ret;
	}
	
	private FileManager() {}
	
	public static void main(String[] args)
	{
		/*BlockSet bs = new BlockSet(712, 512, 20, 30);
		
			for(int i=0; i<bs.getColN(); i++) for(int j=0; j<10; j++) 
			{
				if( i - j != 10 && j + i != 10 && i+1 - j != 10 && j+1 + i != 10) continue;
				
				bs.insertBlock(i, j, 3);
			}

			for(int i=0; i<bs.getColN(); i++) for(int j=10; j<11; j++) 
			{
				bs.insertBlock(i, j, 3); //TBC
			}
	
			for(int i=0; i<bs.getColN(); i++) for(int j=11; j<12; j++) 
			{
				bs.insertBlock(i, j, 2); //TBC
			}
			
			for(int i=0; i<bs.getColN(); i++) for(int j=12; j<13; j++) 
			{
				bs.insertBlock(i, j, 1); //TBC
			}*/
			
		//saveMap("Triangle", bs);
		
		BlockSet bs = new BlockSet(712, 712, 20, 30);
		
		for(int i=2; i<bs.getColN()-2; i++) for(int j=2; j<15; j++) 
		{
			if((j%3 == 0 || j%3-1 == 0) && (i%3 == 0 || i%3-1 == 0)) continue;
			//if(j%3 == 0 || j%3-1 == 0) continue;
			
			if(j%2 == 0 ) bs.insertBlock(i, j, 4); //TBC
			else bs.insertBlock(i, j, 2); //TBC
		}

		//saveMap("Grid", bs);
		/*
		File f = new File("Grid.brk");
		if(f.exists() && !f.isDirectory()) { 
		    System.out.println("Its here!");
		}*/
		
		
		//saveMap("testsmall", bs);
		
		//BlockSet tst = loadMap("test22");
		/*
		Set<Vector2d> bounce_vectors = new HashSet<Vector2d>();
		
		bounce_vectors.add(new Vector2d(1,0));
		bounce_vectors.add(new Vector2d(1,0));
		
		System.out.println(bounce_vectors.size());
		for(Vector2d vec : bounce_vectors) System.out.println(vec);*/

	}
}
