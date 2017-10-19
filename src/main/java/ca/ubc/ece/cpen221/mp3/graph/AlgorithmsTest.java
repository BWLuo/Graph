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
		System.out.println(Algorithms.center(test));
		
	}
	@Test
	public void test2() throws IOException {
		
		String filename = "datasets/enron.txt";
		Graph test = Parsers.parseEnronDataset(filename,1);
		System.out.println(Algorithms.graphDiameter(test));
		
	}


}
