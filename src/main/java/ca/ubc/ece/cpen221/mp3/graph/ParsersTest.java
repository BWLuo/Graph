package ca.ubc.ece.cpen221.mp3.graph;

import static org.junit.Assert.*;

import org.junit.Test;

import ca.ubc.ece.cpen221.mp3.staff.Graph;
import ca.ubc.ece.cpen221.mp3.staff.Vertex;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;


public class ParsersTest {


	//Test for AdjacencyListGraph
	public void test1() throws IOException {
		//testing initialization
		String filename = "datasets/enronStyled.txt";
		Graph test = Parsers.parseEnronDataset(filename,1);
		Vertex one = new Vertex("1");
		Vertex two = new Vertex("2");
		Vertex three = new Vertex("3");
		Vertex four = new Vertex("4");
		Vertex five = new Vertex("5");

		assertTrue(test.edgeExists(one, two));
		assertTrue(test.edgeExists(two, one));
		assertTrue(test.edgeExists(three, four));
		assertTrue(test.edgeExists(three, five));
		
		Graph test2 = new AdjacencyListGraph();
		assertEquals(new ArrayList<Vertex>(),test2.getVertices());
		
		//testing edgeExists
		assertFalse(test.edgeExists(two,five));
		
		//testing for addEdge
		test.addEdge(two, five);
		assertTrue(test.edgeExists(two, five));
		
		//testing getVertices
		List<Vertex> vertices = new ArrayList<Vertex>();
		vertices.add(one);
		vertices.add(two);
		vertices.add(three);
		vertices.add(four);
		vertices.add(five);
		
		assertEquals(vertices,test.getVertices());
		
		//testing addVertices
		Vertex six = new Vertex("6");
		vertices.add(new Vertex("6"));
		test.addVertex(new Vertex("6"));
		
		assertEquals(vertices,test.getVertices());
		
		//testing downStreamNeighbours
		
		assertEquals(new ArrayList<Vertex>(),test.getDownstreamNeighbors(six));
		
		List<Vertex> oneDown = new ArrayList<Vertex>();
		oneDown.add(two);
		oneDown.add(three);
		
		assertEquals(oneDown,test.getDownstreamNeighbors(one));
		
		//testing upStreamNeighbours
		
		List<Vertex> oneUp = new ArrayList<Vertex>();
		oneUp.add(two);
		oneUp.add(three);
		
		assertEquals(oneUp,test.getUpstreamNeighbors(one));
	}
}