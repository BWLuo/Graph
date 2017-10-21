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

	private static List<Vertex> visited = new ArrayList<Vertex>();
	private static int graphDiameter = 0;

	/**
	 * find the distance between two vertices in a graph
	 *
	 * @param graph
	 *            a graph with at least one vertex
	 * @param a
	 *            vertex in the graph
	 * @param b
	 *            vertex in the graph
	 * @return the distance between a and b in the graph. returns -1 if a and b are
	 *         not connected and 0 if a and b are the same vertex
	 */

	public static int shortestDistance(Graph graph, Vertex a, Vertex b) {
		// finding distance between a vertex and itself
		if (a.equals(b)) {
			return 0;
		}

		List<Vertex> vertices = graph.getVertices();

		// creating marker table to indicate visited vertices
		HashMap<Vertex, Integer> indexValues = new HashMap<Vertex, Integer>();
		Boolean[] checker = new Boolean[vertices.size()];
		int index = 0;
		Arrays.fill(checker, false);
		for (Vertex vert : vertices) {
			indexValues.put(vert, index);
			index++;
		}

		// setting initial condition if cannot reach vertex b from a
		int distance = 0;

		// starting BFS from first vertex
		checker[indexValues.get(a)] = true;
		List<Vertex> neighbours = graph.getDownstreamNeighbors(a);
		while (!neighbours.contains(b) && !neighbours.isEmpty()) {
			List<Vertex> nextNeighbours = new ArrayList<Vertex>();
			for (Vertex points : neighbours) {
				List<Vertex> tempNeighbours = graph.getDownstreamNeighbors(points);
				for (Vertex point : tempNeighbours) {
					int checkerIndex = indexValues.get(point);
					if (!checker[checkerIndex]) {
						nextNeighbours.add(point);
						checker[checkerIndex] = true;

					}

				}
			}
			neighbours = nextNeighbours;
			distance++;
		}
		if (distance == 0) {
			return distance;
		}
		return distance;
	}

	/**
	 * Perform a complete depth first search of the given graph. Start with the
	 * search at each vertex of the graph and create a list of the vertices visited.
	 * Return a set where each element of the set is a list of elements seen by
	 * starting a DFS at a specific vertex of the graph (the number of elements in
	 * the returned set should correspond to the number of graph vertices).
	 *
	 * @param a
	 *            graph with at least one vertex
	 * @return a set of lists where every element of the set is a list of vertices
	 *         in the order that the DFS visited each vertex connected to the vertex
	 *         where the search was started
	 */
	public static Set<List<Vertex>> depthFirstSearch(Graph graph) {
		Set<List<Vertex>> setOfLists = new HashSet<List<Vertex>>();
		List<Vertex> vertices = graph.getVertices();

		// Performing a DFS at every vertex in the graph
		for (Vertex vertex : vertices) {
			visited = new ArrayList<Vertex>();
			dFS(graph, visited, vertex);
			setOfLists.add(visited);
		}
		return setOfLists;

	}

	/**
	 * Helper method to continue the recursive call of the DFS
	 * 
	 * @param graph
	 *            graph with at least one point
	 * @param visited
	 *            a list that contains vertices that have already been visited by a
	 *            search
	 * @param currentVertex
	 *            the current vertex to continue the DFS
	 */
	private static void dFS(Graph graph, List<Vertex> visited, Vertex currentVertex) {
		visited.add(currentVertex);

		List<Vertex> goVisit = new ArrayList<Vertex>();
		goVisit = graph.getDownstreamNeighbors(currentVertex);

		List<Vertex> toVisit = new ArrayList<Vertex>();
		// check if the vertex to visit has already been visited
		for (Vertex vertex : goVisit) {
			if (!visited.contains(vertex)) {
				toVisit.add(vertex);
			}
		}
		// terminating step
		if (toVisit.isEmpty()) {
			return;
		}
		// recursively call the method again on the unvisited vertices
		for (Vertex vertex : toVisit) {
			if (!visited.contains(vertex)) {
				dFS(graph, visited, vertex);
			}
		}
		return;
	}

	/**
	 * Perform a complete breadth first search of the given graph. Start with the
	 * search at each vertex of the graph and create a list of the vertices visited.
	 * Return a set where each element of the set is a list of elements seen by
	 * starting a BFS at a specific vertex of the graph (the number of elements in
	 * the returned set should correspond to the number of graph vertices).
	 *
	 * @param a
	 *            graph with at least one vertex
	 * @return a set of lists where every element of the set is a list of vertices
	 *         in the order that the BFS visited each vertex connected to the vertex
	 *         where the search was started
	 */
	public static Set<List<Vertex>> breadthFirstSearch(Graph graph) {
		Set<List<Vertex>> setOfLists = new HashSet<List<Vertex>>();
		List<Vertex> vertices = graph.getVertices();

		// performing a BFS at every vertex in the graph
		for (Vertex vertex : vertices) {
			visited = new ArrayList<Vertex>();
			Queue<Vertex> queue = new ArrayDeque<Vertex>();
			queue.add(vertex);
			visited.add(vertex);

			// starting queue to continue until the last connected vertex is reached
			while (!queue.isEmpty()) {
				Vertex currentVertex = queue.remove();
				List<Vertex> neighbours = graph.getDownstreamNeighbors(currentVertex);

				// checking if the vertex has already been visited before adding to queue
				for (Vertex toVisit : neighbours) {
					if (!visited.contains(toVisit)) {
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
	 * Find the center of the graph
	 * 
	 * @param graph
	 *            a graph with at least one vertex
	 * @return the vertex that is at the center of the graph (vertex with smallest
	 *         eccentricity). Returns the first vertex in the graph if all vertices are isolated
	 */
	public static Vertex center(Graph graph) {

		List<Vertex> vertices = graph.getVertices();

		int smallestEccentricity = -1;
		Vertex center = vertices.get(0);

		// calculating the eccentricity for every vertex in graph
		for (Vertex vertex : vertices) {
			int distance = findEccentricity(graph, vertex);

			// if the eccentricity of the vertex is smaller that the previous ones
			// cache the new vertex and new smallest eccentricity
			// values of 0 are ignored for eccentricity as it means that they are isolated
			// vertices
			if (distance != 0) {
				if (distance < smallestEccentricity) {
					smallestEccentricity = distance;
					center = vertex;
				}

				//
				if (smallestEccentricity == -1) {
					smallestEccentricity = distance;
					center = vertex;
				}
			}
		}
		return center;
	}

	/**
	 * finds the eccentricity of a vertex in its graph
	 * 
	 * @param graph
	 *            a graph with at least one vertex
	 * @param vertex
	 *            a vertex in the graph
	 * @return the distance between vertex and the farthest connected vertex
	 */
	private static int findEccentricity(Graph graph, Vertex vertex) {
		List<Vertex> vertices = graph.getVertices();
		// creating marker table to indicate visited vertices
		HashMap<Vertex, Integer> indexValues = new HashMap<Vertex, Integer>();
		Boolean[] checker = new Boolean[vertices.size()];
		int index = 0;
		Arrays.fill(checker, false);
		for (Vertex vert : vertices) {
			indexValues.put(vert, index);
			index++;
		}

		//initial distance if no other vertices are visited
		int distance = 0;

		// starting BFS from initial vertex
		checker[indexValues.get(vertex)] = true;
		List<Vertex> neighbours = graph.getDownstreamNeighbors(vertex);
		while (!neighbours.isEmpty()) {
			List<Vertex> nextNeighbours = new ArrayList<Vertex>();
			for (Vertex points : neighbours) {
				List<Vertex> tempNeighbours = graph.getDownstreamNeighbors(points);
				for (Vertex point : tempNeighbours) {
					int checkerIndex = indexValues.get(point);
					if (!checker[checkerIndex]) {
						nextNeighbours.add(point);
						checker[checkerIndex] = true;

					}

				}
			}
			neighbours = nextNeighbours;
			distance++;
		}
		return distance;
	}

	/**
	 * find the largest distance between two vertices in the graph
	 * 
	 * @param graph
	 *            a graph with at least one vertex
	 * @return the diameter of the graph (farthest distance between two points)
	 *         diameter is zero if none of the vertices are connect to another
	 *         vertex
	 */
	public static int graphDiameter(Graph graph) {

		graphDiameter = 0;
		List<Vertex> vertices = graph.getVertices();
		for (Vertex vertex : vertices) {
			int length = findEccentricity(graph, vertex);
			if (length > graphDiameter) {
				graphDiameter = length;
			}
		}
		return graphDiameter;
	}

	/**
	 * find common downstream neighbours of two vertices
	 * 
	 * @param graph
	 *            a graph that contains at least two vertices
	 * @param a
	 *            a vertex in the graph
	 * @param b
	 *            a vertex in the graph
	 * @return a list of vertices where each vertex is downstream of both vertex a
	 *         and b, returns an empty list if there are none
	 */
	public static List<Vertex> commonDownstreamVertices(Graph graph, Vertex a, Vertex b) {
		List<Vertex> commonVertices = new ArrayList<Vertex>();
		List<Vertex> aNeighbours = graph.getDownstreamNeighbors(a);
		List<Vertex> bNeighbours = graph.getDownstreamNeighbors(b);

		for (Vertex vertex : aNeighbours) {
			if (bNeighbours.contains(vertex)) {
				commonVertices.add(vertex);
			}
		}
		return commonVertices;
	}

	/**
	 * find common upstream neighbours of two vertices
	 * @param graph
	 *            graph that contains at least 2 vertices
	 * @param a
	 *            a vertex in the graph
	 * @param b
	 *            a vertex in the graph
	 * @return a list of vertices where each vertex is upstream of both vertex a and
	 *         b, returns an empty list if there are none
	 */
	public static List<Vertex> commonUpstreamVertices(Graph graph, Vertex a, Vertex b) {
		List<Vertex> commonVertices = new ArrayList<Vertex>();
		List<Vertex> aNeighbours = graph.getUpstreamNeighbors(a);
		List<Vertex> bNeighbours = graph.getUpstreamNeighbors(b);

		for (Vertex vertex : aNeighbours) {
			if (bNeighbours.contains(vertex)) {
				commonVertices.add(vertex);
			}
		}
		return commonVertices;
	}

}
