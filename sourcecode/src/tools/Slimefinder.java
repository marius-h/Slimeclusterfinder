package tools;
import java.awt.Point;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Slimefinder {
	
	Logging log;
	long seed;
	
	/** Returns true if chunk is a slime chunk
	 */
	public boolean isSlime(int xPos, int zPos) {
		Random rnd = new Random(seed + 
				(long) (xPos * xPos * 0x4c1906) + 
				(long) (xPos * 0x5ac0db) + 
				(long) (zPos * zPos) * 0x4307a7L + 
				(long) (zPos * 0x5f24f) ^ 0x3ad8025f);
		return rnd.nextInt(10) == 0;
	}
	
	/** Check maximum area around a centerpoint. Returns the amount of slimechunks in that area.
	*/ 
	public int spawningArea(int xCenter, int zCenter) {		
		int slimechunks = 0;
		// check this area for slimechunks
		for (int x = xCenter - 7; x < xCenter + 7; x++) {
			for (int z = zCenter - 7; z < zCenter + 7; z++) {
				if (Math.pow(x - xCenter, 2) + Math.pow(z - zCenter, 2) <= 60 && isSlime(x, z))
					slimechunks++;
			}
		}
		return slimechunks;
	}

	public void findFormations(long seed, int range, int min, int max, JPanel panel, int x, int z) {		
		log = new Logging(seed, range, min, max, x, z);
		
		double start_time = System.currentTimeMillis();
		
		for (int zCenter = -range+z; zCenter < range+z; zCenter++) {
			for (int xCenter = -range+x; xCenter < range+x; xCenter++) {				
				int slimechunks = spawningArea(xCenter, zCenter);
				if (slimechunks >= min && slimechunks <= max)
					log.logChunk(new Point(xCenter, zCenter), slimechunks);
			}
		}
		
		log.close();
		double time = (System.currentTimeMillis()-start_time);
		if (time >= 1000)
			JOptionPane.showMessageDialog(panel, "Done after " + time/1000 + "s.", null, JOptionPane.INFORMATION_MESSAGE);
		else
			JOptionPane.showMessageDialog(panel, "Done after " + time + "ms.", null, JOptionPane.INFORMATION_MESSAGE);
	}

}