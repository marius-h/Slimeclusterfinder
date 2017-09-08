package tools;

import java.awt.Point;
import java.io.FileWriter;
import java.io.IOException;

import userinterface.GUI;

public class Logging {
	
	private FileWriter writing;
	private Point largestCluster, smallestCluster, closestCluster;
	private int largestAmount, smallestAmount, closestAmount, found;
	
	public Logging(long seed, int range, int min, int max) {	
		largestAmount = -1;
		smallestAmount = 10000;
		smallestCluster = new Point(10000000, 10000000);
		largestCluster = new Point(10000000, 10000000);
		closestCluster = new Point(10000000, 10000000);
		// Create a new FileWriter
		try {
			writing = new FileWriter("Slimefinder.txt");
		} catch (Exception e) {	}
		
		String str = "Slimechunk Cluster Finder by Couch\n\nSearching for a cluster with " + min + "-" + max + " slimechunks for seed <" + seed + "> in a radius of <" + range
				+ "> chunks...\n\n";
		try {
			writing.write(str);
		} catch (IOException e) {}	
	}

	public void logChunk(Point c, int amount) {		
		String out = "\nNew cluster found at " + (int)c.getX() * 16 + "/" + (int)c.getY() * 16 + " with " + amount + " chunks";
		try {
			writing.write(out);
		} catch (IOException e) {}
		
		found++;
		
		if (GUI.getChckbxClosestCluster().isSelected())
			this.testIfCloser(c, amount);
		if (GUI.getChckbxLargestCluster().isSelected())
			this.testIfLarger(c, amount);
		if (GUI.getChckbxSmallestCluster().isSelected())
			this.testIfSmaller(c, amount);
	}

	public void close() {		
		if (largestAmount == closestAmount)
			largestCluster.setLocation(closestCluster);
		
		String str = "";
		if (found != 0) {
			str = "\n\n\n===[STATS]===\n<" + found + "> slimechunks found which fulfill your requirements";
			if (GUI.getChckbxClosestCluster().isSelected())
				str += "\nClosest cluster found at: " + (int)closestCluster.getX() * 16 +"/" + (int)closestCluster.getY() * 16 + " with " + closestAmount + " slimechunks";;
			if (GUI.getChckbxLargestCluster().isSelected())
				str += "\nLargest Cluster found at: " + (int)largestCluster.getX() * 16 +"/" + (int)largestCluster.getY() * 16 + " with " + largestAmount + " slimechunks";
			if (GUI.getChckbxSmallestCluster().isSelected())
				str += "\nSmallest Cluster found at: " + (int)smallestCluster.getX() * 16 +"/" + (int)smallestCluster.getY() * 16 + " with " + smallestAmount + " slimechunks";
		} else
			str = "\n\nno slimechunk clusters found which would fulfill the requirements :,(";
		try {
			writing.write(str);
			writing.close();
		} catch (IOException e) {}
	}

	private void testIfCloser(Point chunk, int amount) {
		if (chunk.distance(0, 0) < closestCluster.distance(0, 0)) {
			closestCluster.setLocation(chunk);
			closestAmount = amount;
		}
	}

	private void testIfLarger(Point chunk, int amount) {
		if (amount > largestAmount) {
			largestCluster.setLocation(chunk);
			largestAmount = amount;
		}
	}
	
	private void testIfSmaller(Point chunk, int amount) {
		if (amount < smallestAmount) {
			smallestCluster.setLocation(chunk);
			smallestAmount = amount;
		}
	}

}