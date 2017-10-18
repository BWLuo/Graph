package ca.ubc.ece.cpen221.mp3.graph;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

import ca.ubc.ece.cpen221.mp3.staff.Graph;
import ca.ubc.ece.cpen221.mp3.staff.Vertex;

public class Algorithms {

	/**
	 * *********************** Algorithms ****************************
	 *
	 * Please see the README for this machine problem for a more detailed
	 * specification of the behavior of each method that one should
	 * implement.
	 */

	/**
	 * This is provided as an example to indicate that this method and
	 * other methods should be implemented here.
	 *
	 * You should write the specs for this and all other methods.
	 *
	 * @param graph
	 * @param a
	 * @param b
	 * @return
	 */
	
	public static int shortestDistance(Graph graph, Vertex a, Vertex b) {
		//if a == b distance is 0
		if(a == b) {
			return 0;
		}
		ArrayList<Vertex> visited = new ArrayList<Vertex>();
		
		
		//find the first downStreamNeighbours
		List<Vertex> neighbours = graph.getDownstreamNeighbors(a);
		visited.add(a);
		int distance = 1;
		
		List<Vertex> prevNeighbours = new ArrayList<Vertex>(neighbours);
		for(Vertex vertex : prevNeighbours) {
			visited.add(vertex);
		}
		while(true) {
			if(prevNeighbours.contains(b)) {
				return distance;
			}
			
			List<Vertex> nextNeighbours = new ArrayList<Vertex>();
			for(Vertex vertex: prevNeighbours) {
				List<Vertex> temp = new ArrayList<Vertex>();
				temp = graph.getDownstreamNeighbors(vertex);
				
				for(Vertex vert: temp) {
					if(!visited.contains(vert)) {
						nextNeighbours.add(vert);
						visited.add(vert);
					}
				}
			}
			
			distance++;
			prevNeighbours = nextNeighbours;
			
			if(prevNeighbours.isEmpty()) {
				return -1;
			}
		}	
	}

	/**
	 * Perform a complete depth first search of the given
	 * graph. Start with the search at each vertex of the
	 * graph and create a list of the vertices visited.
	 * Return a set where each element of the set is a list
	 * of elements seen by starting a DFS at a specific
	 * vertex of the graph (the number of elements in the
	 * returned set should correspond to the number of graph
	 * vertices).
	 *
	 * @param
	 * @return
	 */
	private static List<Vertex> visited = new ArrayList<Vertex>();
	
	public static Set<List<Vertex>> depthFirstSearch(Graph graph) {
		Set<List<Vertex>> setOfLists = new HashSet<List<Vertex>>();
		List<Vertex> vertices = graph.getVertices();
		
		for(Vertex vertex : vertices) {
			visited = new ArrayList<Vertex>();
			dFS(graph, visited, vertex);
			setOfLists.add(visited);
		}
		return setOfLists;

	}
	
	private static void dFS(Graph graph, List<Vertex> visited, Vertex currentVertex) {
		visited.add(currentVertex);
		
		List<Vertex> goVisit = new ArrayList<Vertex>();
		goVisit = graph.getDownstreamNeighbors(currentVertex);
		
		List<Vertex> toVisit = new ArrayList<Vertex>();
		for(Vertex vertex : goVisit) {
			if(!visited.contains(vertex)) {
				toVisit.add(vertex);
			}
		}
		//terminating step
		if (toVisit.isEmpty()) {
			return;
		}
		for(Vertex vertex : toVisit) {
			if(!visited.contains(vertex)) {			
				dFS(graph, visited,vertex);
			}
		}
		return;
	}

	/**
	 * Perform a complete breadth first search of the given
	 * graph. Start with the search at each vertex of the
	 * graph and create a list of the vertices visited.
	 * Return a set where each element of the set is a list
	 * of elements seen by starting a BFS at a specific
	 * vertex of the graph (the number of elements in the
	 * returned set should correspond to the number of graph
	 * vertices).
	 *
	 * @param
	 * @return
	 */
	public static Set<List<Vertex>> breadthFirstSearch(Graph graph) {
		// TODO: Implement this method
		return null; // this should be changed
	}

	/**
	 * You should write the spec for this method
	 */
	 public static Vertex center(Graph graph) {
		 // TODO: Implement this method
		 return null; // this should be changed
	 }

	 /**
	  * You should write the spec for this method
		*/
		public static int diameter(Graph graph) {
			// TODO: Implement this method
			return -1; // this should be changed
		}

	/**
	 * You should write the spec for this method
	 */
	 public static List<Vertex> commonUpstreamVertices(Graph graph, Vertex a, Vertex b) {
		 List<Vertex> commonVertices = new ArrayList<Vertex>();
		 List<Vertex> aNeighbours = graph.getUpstreamNeighbors(a);
		 List<Vertex> bNeighbours = graph.getUpstreamNeighbors(b);
		 
		 for(Vertex vertex : aNeighbours) {
			 if(bNeighbours.contains(vertex)) {
				 commonVertices.add(vertex);
			 }
		 }
		 return commonVertices;
		 
	 }

	 /**
 	 * You should write the spec for this method
 	 */
 	 public static List<Vertex> commonDownstreamVertices(Graph graph, Vertex a, Vertex b) {
 		 List<Vertex> commonVertices = new ArrayList<Vertex>();
		 List<Vertex> aNeighbours = graph.getDownstreamNeighbors(a);
		 List<Vertex> bNeighbours = graph.getDownstreamNeighbors(b);
		 
		 for(Vertex vertex : aNeighbours) {
			 if(bNeighbours.contains(vertex)) {
				 commonVertices.add(vertex);
			 }
		 }
		 return commonVertices;
 	 }

}
