package graph;

import static org.junit.Assert.*;
import java.util.ArrayList;

import ca.ubc.ece.cpen221.mp3.graph.Algorithms;
import ca.ubc.ece.cpen221.mp3.graph.Parsers;
import ca.ubc.ece.cpen221.mp3.staff.Graph;
import ca.ubc.ece.cpen221.mp3.staff.Vertex;

import org.junit.Test;
import java.util.List; 

import java.io.IOException;


public class AlgorithmsTest {
	@Test
	public void test0() throws IOException{
		String filename = "datasets/algorithmTest.txt";
		Graph test = Parsers.parseEnronDataset(filename,1);
		Vertex one = new Vertex("1");
		Vertex two = new Vertex("2");
		Vertex three = new Vertex("3");
		Vertex four = new Vertex("4");
		Vertex five = new Vertex("5");
		Vertex nine = new Vertex("9");


		assertEquals(2,Algorithms.shortestDistance(test, one, five));
		assertEquals(2,Algorithms.shortestDistance(test, one, four));
		assertEquals(2,Algorithms.shortestDistance(test, five, four));
		assertEquals(0,Algorithms.shortestDistance(test, one, one));
		assertEquals(1,Algorithms.shortestDistance(test, two, three));
		assertEquals(1,Algorithms.shortestDistance(test, two, three));
		assertEquals(-1,Algorithms.shortestDistance(test, two, nine));


	}

	@Test
	public void test1() throws IOException{
		String filename = "datasets/algorithmTest.txt";
		Graph test = Parsers.parseEnronDataset(filename,1);
		
		Vertex one = new Vertex("1");
		Vertex two = new Vertex("2");
		
		assertEquals(new Vertex("3"),Algorithms.center(test));
		assertEquals(4,Algorithms.graphDiameter(test));
		
		List<Vertex> tester = new ArrayList<Vertex>();
		tester.add(new Vertex("3"));
		
		assertEquals(tester, Algorithms.commonDownstreamVertices(test,one ,two));
		assertEquals(tester, Algorithms.commonUpstreamVertices(test,one ,two));



	}
	
	@Test
	public void test2() throws IOException{
		String filename = "datasets/enronStyled.txt";
		Graph test = Parsers.parseEnronDataset(filename,1);
		
		Vertex one = new Vertex("1");
		Vertex two = new Vertex("2");
		Vertex three = new Vertex("3");
		Vertex four = new Vertex("4");
		Vertex five = new Vertex("5");
		
		ArrayList<Vertex>result1 = new ArrayList<Vertex>();
		result1.add(one);result1.add(two);result1.add(three);result1.add(four);result1.add(five);
		assertTrue(Algorithms.breadthFirstSearch(test).contains(result1));
		
		ArrayList<Vertex>result2 = new ArrayList<Vertex>();
		result2.add(three);result2.add(one);result2.add(two);result2.add(four);result2.add(five);
		assertTrue(Algorithms.depthFirstSearch(test).contains(result2));
		
	}
	
	@Test
	public void test3() throws IOException{
		String filename = "datasets/marvelStyled.txt";
		Graph test = Parsers.parseMarvelDataset(filename,1);
		
		assertEquals(new Vertex("1"),Algorithms.center(test));
	
	}
}