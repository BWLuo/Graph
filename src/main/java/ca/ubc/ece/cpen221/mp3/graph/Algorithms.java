package ca.ubc.ece.cpen221.mp3.graph;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import java.util.Queue;
import java.util.ArrayDeque;
import java.util.HashMap;

import ca.ubc.ece.cpen221.mp3.staff.Graph;
import ca.ubc.ece.cpen221.mp3.staff.Vertex;

public class Algorithms {

	//Shared
	private static List<Vertex> visited = new ArrayList<Vertex>();
	private static int graphDiameter = 0;

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
		visited = new ArrayList<Vertex>();
		
		
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
	//Helper function to do recursive call for depthFirstSearch
	
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
		Set<List<Vertex>> setOfLists = new HashSet<List<Vertex>> ();
		List<Vertex> vertices = graph.getVertices();
		for (Vertex vertex : vertices) {
						
			visited = new ArrayList<Vertex>();
			Queue<Vertex> queue = new ArrayDeque<Vertex>();
			queue.add(vertex);
			visited.add(vertex);

			while(!queue.isEmpty()) {
				
				Vertex currentVertex = queue.remove();				
				
				List<Vertex> neighbours = graph.getDownstreamNeighbors(currentVertex);
				for(Vertex toVisit : neighbours) {
					if(!visited.contains(toVisit)) {
						queue.add(toVisit);
						visited.add(toVisit);
					}
				}
				
			}
			setOfLists.add(visited);
		}
		return setOfLists;
	}

	/**
	 * You should write the spec for this method
	 */
	 public static Vertex center(Graph graph) {
		 // TODO: Implement this method
		 
		 List<Vertex> vertices = graph.getVertices();

		 int smallestEccentricity = -1;
		 Vertex center = vertices.get(0);

		 for(Vertex vertex : vertices) {
			 int distance = findEccentricity(graph,vertex);
			 if(distance<smallestEccentricity) {
				 smallestEccentricity = distance;
				 center = vertex;
			 }
			 if(smallestEccentricity == -1 ) {
				 smallestEccentricity = distance;
				 center = vertex;
			 }
		 }
		 return center;
	 }
	 
	 public static int findEccentricity (Graph graph, Vertex vertex) {
		 List<Vertex> vertices = graph.getVertices();
		 //creating marker table to indicate visited vertices
		 HashMap<Vertex,Integer> indexValues = new HashMap<Vertex,Integer> ();
		 Boolean[] checker = new Boolean[vertices.size()];
		 int index = 0;
		 Arrays.fill(checker, false);
		 for (Vertex vert : vertices) {
			indexValues.put(vert, index);
			index++;
		 }
		 
		 int distance = 0;
		 //finding farthest vertex
		 
		 //starting BFS from initial vertex
		 checker[indexValues.get(vertex)] = true;
		 List<Vertex> neighbours = graph.getDownstreamNeighbors(vertex); 
		 while(!neighbours.isEmpty()) {
			 List<Vertex> nextNeighbours = new ArrayList<Vertex>();
			 for(Vertex points: neighbours) {
				 List<Vertex> tempNeighbours = graph.getDownstreamNeighbors(points);
				 for(Vertex point: tempNeighbours) {
					 int checkerIndex = indexValues.get(point);
					 if(!checker[checkerIndex]) {
						 nextNeighbours.add(point);
						 checker[checkerIndex] = true;
						 
					 }

				 }
			 }
			 neighbours=nextNeighbours;
			 distance ++;
		 }
		 return distance;
	 }

	 /**
	  * You should write the spec for this method
		*/
		public static int graphDiameter(Graph graph) {
			// TODO: Implement this method
			
			graphDiameter = 0;
			List<Vertex> vertices = graph.getVertices();
			for(Vertex vertex : vertices) {
				int length = findEccentricity(graph,vertex);
				if (length>graphDiameter) {
					graphDiameter = length;
				}
			}
			return graphDiameter;
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

}
