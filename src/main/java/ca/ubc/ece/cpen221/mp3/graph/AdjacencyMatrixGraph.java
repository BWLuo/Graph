package ca.ubc.ece.cpen221.mp3.graph;

import java.util.List;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;

import ca.ubc.ece.cpen221.mp3.staff.Graph;
import ca.ubc.ece.cpen221.mp3.staff.Vertex;


/**
 * Representation Invariant: the vertices in the graph must not be null
 * Abstraction Function: creates a unique graph for every unique input two Lists of non-null Vertex
 * @author Bowen
 *
 */
public class AdjacencyMatrixGraph implements Graph {
	private ArrayList<BitSet> matrix = new ArrayList<BitSet>();
	private HashMap<Vertex, Integer> vertValues = new HashMap<Vertex, Integer>();
	private ArrayList<Vertex> vertices = new ArrayList<Vertex>();
	private int nextMapIndex = 0;

	public AdjacencyMatrixGraph(List<Vertex> inNodes, List<Vertex> outNodes) {
		for (int i = 0; i < inNodes.size(); i++) {
			Vertex inVertex = inNodes.get(i);
			Vertex outVertex = outNodes.get(i);

			if (!vertValues.containsKey(inVertex)) {
				vertValues.put(inVertex, nextMapIndex);
				vertices.add(inVertex);
				nextMapIndex++;
				matrix.add(new BitSet());

			}
			if (!vertValues.containsKey(outVertex)) {
				vertValues.put(outVertex, nextMapIndex);
				vertices.add(outVertex);
				nextMapIndex++;
				matrix.add(new BitSet());
			}
			int inIndex = vertValues.get(inVertex);
			int outIndex = vertValues.get(outVertex);
			matrix.get(inIndex).set(outIndex);
		}
	}

	/**
	 * Adds a vertex to the graph.
	 *
	 * Precondition: v is not already a vertex in the graph
	 */
	public void addVertex(Vertex v) {
		if (!vertices.contains(v)) {
			vertices.add(v);
			vertValues.put(v, nextMapIndex);
			nextMapIndex++;
			matrix.add(new BitSet());
		}
	}

	/**
	 * Adds an edge from v1 to v2.
	 *
	 * Precondition: v1 and v2 are vertices in the graph
	 */
	public void addEdge(Vertex v1, Vertex v2) {
		int inIndex = vertValues.get(v1);
		int outIndex = vertValues.get(v2);
		matrix.get(inIndex).set(outIndex);
	}

	/**
	 * Check if there is an edge from v1 to v2.
	 *
	 * Precondition: v1 and v2 are vertices in the graph Postcondition: return true
	 * iff an edge from v1 connects to v2
	 */
	public boolean edgeExists(Vertex v1, Vertex v2) {
		int inIndex = vertValues.get(v1);
		int outIndex = vertValues.get(v2);
		return matrix.get(inIndex).get(outIndex);
	}

	/**
	 * Get an array containing all downstream vertices adjacent to v.
	 *
	 * Precondition: v is a vertex in the graph
	 * 
	 * Postcondition: returns a list containing each vertex w such that there is an
	 * edge from v to w. The size of the list must be as small as possible (No
	 * trailing null elements). This method should return a list of size 0 iff v has
	 * no downstream neighbors.
	 */
	public List<Vertex> getDownstreamNeighbors(Vertex v) {
		List<Vertex> downNeighbours = new ArrayList<Vertex>();
		int index = vertValues.get(v);
		BitSet neighbours = matrix.get(index);
		for (int i = 0; i < vertices.size(); i++) {
			if (neighbours.get(i)) {
				downNeighbours.add(vertices.get(i));
			}
		}
		return downNeighbours;
	}

	/**
	 * Get an array containing all upstream vertices adjacent to v.
	 *
	 * Precondition: v is a vertex in the graph
	 * 
	 * Postcondition: returns a list containing each vertex u such that there is an
	 * edge from u to v. The size of the list must be as small as possible (No
	 * trailing null elements). This method should return a list of size 0 iff v has
	 * no upstream neighbors.
	 */
	public List<Vertex> getUpstreamNeighbors(Vertex v) {
		List<Vertex> upNeighbours = new ArrayList<Vertex>();
		int index = vertValues.get(v);

		for (int i = 0; i < matrix.size(); i++) {
			if (matrix.get(i).get(index)) {
				upNeighbours.add(vertices.get(i));
			}
		}

		return upNeighbours;
	}

	/**
	 * Get all vertices in the graph.
	 *
	 * Postcondition: returns a list containing all vertices in the graph. This
	 * method should return a list of size 0 iff the graph has no vertices.
	 */
	public List<Vertex> getVertices() {
		return vertices;
	}
}
