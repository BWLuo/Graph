package ca.ubc.ece.cpen221.mp3.graph;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.util.HashMap;

import ca.ubc.ece.cpen221.mp3.staff.Graph;
import ca.ubc.ece.cpen221.mp3.staff.Vertex;

public class Parsers {

	/**
	 * This method returns a Graph after parsing a file with the corresponding
	 * graph data stored in the same format as the Enron dataset
	 * 
	 * @param fileName
	 *            is the name of the file with the dataset
	 * @param graphRep
	 *            is 1 for AdjacencyListGraph and 2 for AdjacencyMatrixGraph
	 * @return a Graph that represents the dataset in fileName and uses the
	 *         representation specified by graphRep
	 * @throws IOException
	 *             if there is a problem opening/reading data from the specified
	 *             file
	 */
	static public Graph parseEnronDataset(String fileName, int graphRep) throws IOException {
		List<Vertex> inNodes = new ArrayList<Vertex>();
		List<Vertex> toNodes = new ArrayList<Vertex>();

		Scanner document = new Scanner(new File(fileName));
		while(document.hasNextLine()) {
			String currentLine = document.nextLine();
			if (currentLine.startsWith("#") || currentLine.startsWith("\\n")||currentLine == "") {
				continue;
			}
			else{
				String[] words = currentLine.split("\\t");
				inNodes.add(new Vertex(words[0]));
				toNodes.add(new Vertex(words[1]));
				
				inNodes.add(new Vertex(words[1]));
				toNodes.add(new Vertex(words[0]));
			}
		}
		document.close();
		
		if(graphRep == 1) {
			return new AdjacencyListGraph (inNodes,toNodes);
		}
		if(graphRep == 2) {
			return new AdjacencyMatrixGraph(inNodes,toNodes);
		}
		else {
			throw new IOException();
		}
	}

	/**
	 * This method returns a Graph after parsing a file with the corresponding
	 * graph data stored in the same format as the Marvel dataset
	 * 
	 * @param fileName
	 *            is the name of the file with the dataset
	 * @param graphRep
	 *            is 1 for AdjacencyListGraph and 2 for AdjacencyMatrixGraph
	 * @return a Graph that represents the dataset in fileName and uses the
	 *         representation specified by graphRep
	 * @throws IOException
	 *             if there is a problem opening/reading data from the specified
	 *             file
	 */
	static public Graph parseMarvelDataset(String fileName, int graphRep) throws IOException {
		List<Vertex> inNodes = new ArrayList<Vertex>();
		List<Vertex> toNodes = new ArrayList<Vertex>();
		List<Vertex> issolated = new ArrayList<Vertex>();
		
		HashMap<String,ArrayList<Vertex>> groups = new HashMap<String,ArrayList<Vertex>>();

		Scanner document = new Scanner(new File(fileName));
		while(document.hasNextLine()) {
			String currentLine = document.nextLine();
			if (currentLine.charAt(0) == '#' || currentLine.charAt(0) == '\n'||currentLine == "" ) {
				continue;
			}
			else{
				String[] words = currentLine.split("\\t");
				if(!groups.containsKey(words[1])) {
					groups.put(words[1], new ArrayList<Vertex>());
				}
				groups.get(words[1]).add(new Vertex (words[0]));
			}
		}
		document.close();
		
		for(String vertex: groups.keySet()) {
			ArrayList<Vertex> temp = groups.get(vertex);
			if(temp.size()==1) {
				issolated.add(temp.get(0));
			}
			for(int i = 0; i < temp.size() -1 ; i++) {
				for (int j = 1; i+j<temp.size(); j++) {
					inNodes.add(temp.get(i));
					toNodes.add(temp.get(i+j));
					
					inNodes.add(temp.get(i+j));
					toNodes.add(temp.get(i));
				}
			}
		}

		if(graphRep == 1) {
			Graph graph = new AdjacencyListGraph (inNodes,toNodes);
			for(Vertex vert : issolated) {
				graph.addVertex(vert);
			}
			return graph;
			
		}
		if(graphRep == 2) {
			Graph graph = new AdjacencyMatrixGraph(inNodes,toNodes);
			for(Vertex vert: issolated) {
				graph.addVertex(vert);
			}
			return graph;
		}
		else {
			throw new IOException();
		}
	}
}
