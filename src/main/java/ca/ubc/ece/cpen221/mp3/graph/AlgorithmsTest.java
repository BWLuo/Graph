package ca.ubc.ece.cpen221.mp3.graph;

import static org.junit.Assert.*;

import ca.ubc.ece.cpen221.mp3.staff.Graph;
import ca.ubc.ece.cpen221.mp3.staff.Vertex;

import org.junit.Test;
import java.util.Set;
import java.util.List; 

import java.io.IOException;


public class AlgorithmsTest {
	@Test
	public void test0() {
		assertTrue(true);
	}

	@Test
	public void test1() throws IOException {
		
		String filename = "datasets/enron.txt";
		Graph test = Parsers.parseEnronDataset(filename,1);
		List<Vertex> time = test.getVertices();
		int i = 0;
		for(Vertex vertex: time) {
			i++;
		}
		System.out.println(i);
		//System.out.println(Algorithms.findEccentricity(test, new Vertex("3")));
		//assertEquals(13,Algorithms.graphDiameter(test));
		//for(Vertex vertex: test.getVertices()) {		//}
	}
	
	public void test2() throws IOException {
		
		String filename = "datasets/enron.txt";
		Graph test = Parsers.parseEnronDataset(filename,1);
		System.out.println(Algorithms.graphDiameter(test));
		
	}


}
