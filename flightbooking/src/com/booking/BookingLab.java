package com.booking;

import java.util.List;
import java.util.Map;
import java.util.Set;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Collections;

/**
 * 
 * Book Lab program is used to calculate the minimum weighted path from source to the destination
 * 
 */
public class BookingLab {
	private final List<Vertex> nodes;
	private final List<Edge> edges;
	private Set<Vertex> settledNodes;
	private Set<Vertex> unSettledNodes;
	private Map<Vertex, Vertex> predecessors;
	private Map<Vertex, Integer> distance;

	/**
	 * Constructor which initialize the nodes and edges as per the maximum vertex and edges available in the graph
	 */
	public BookingLab(Graph graph) {
		// create a copy of the array so that we can operate on this array
		this.nodes = new ArrayList<Vertex>(graph.getVertexes());
		this.edges = new ArrayList<Edge>(graph.getEdges());
	}

	/**
	 * @param vertex Vertex 
	 * Traverse over all the unSettlednodes and put the nodes one by one into settled nodes and remove it from unsettled collection.
	 */
	public void traverseNodes(Vertex vertex) {
		settledNodes = new HashSet<Vertex>();
		unSettledNodes = new HashSet<Vertex>();
		distance = new HashMap<Vertex, Integer>();
		predecessors = new HashMap<Vertex, Vertex>();
		distance.put(vertex, 0);
		unSettledNodes.add(vertex);
		while (unSettledNodes.size() > 0) {
			Vertex node = getMinimum(unSettledNodes);
			settledNodes.add(node);
			unSettledNodes.remove(node);
			findMinimalDistances(node);
		}
	}

	/*
	 * @param node Vertex 
	 * Traverse over all the unSettlednodes and put the nodes one by one into settled nodes and remove it from unsettled collection.
	 */
	private void findMinimalDistances(Vertex node) {
		List<Vertex> adjacentNodes = getNeighbors(node);
		for (Vertex destination : adjacentNodes) {
			if (getShortestDistance(destination) > getShortestDistance(node) + getWeight(node, destination)) {
				distance.put(destination, getShortestDistance(node) + getWeight(node, destination));
				predecessors.put(destination, node);
				unSettledNodes.add(destination);
			}
		}

	}

	/**
	 * This method returns the weight of the edges between the source and destination 
	 *
	 * @param  source Vertex started point of node from which the weight has to be calculated
	 * @param  destination Vertex node upto which the weight has to be calucated
	 * @return      the weight of the edges
	 * @see         Weight could be the distance between two vertex.
	 */

	private int getWeight(Vertex source, Vertex destination) {
		for (Edge edge : edges) {
			if (edge.getSource().equals(source) && edge.getDestination().equals(destination)) {
				return edge.getWeight();
			}
		}
		throw new RuntimeException("Nodes are not connected");
	}
	
	
	/**
	 * Returns the collection of all the neighbors reachable from the source node
	 *
	 * @param  source 
	 * @param  destination 
	 * @return      the weight of the edges
	 * @see         Weight could be the distance between two vertex.
	 */
	private List<Vertex> getNeighbors(Vertex node) {
		List<Vertex> neighbors = new ArrayList<Vertex>();
		for (Edge edge : edges) {
			if (edge.getSource().equals(node) && !settledNodes.contains(edge.getDestination())) {
				neighbors.add(edge.getDestination());
			}
		}
		return neighbors;
	}

	/**
	 * Returns the node which has minimum weight
	 *
	 * @param  collection of all the reachable nodes 
	 * @return 	the minimum weighted node
	 */
	private Vertex getMinimum(Set<Vertex> vertexes) {
		Vertex minimum = null;
		for (Vertex vertex : vertexes) {
			if (minimum == null) {
				minimum = vertex;
			} else {
				if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
					minimum = vertex;
				}
			}
		}
		return minimum;
	}

	
	/**
	 * Shortest distance to the destination or else it returns the MAX Value of the Integer
	 *
	 * @param  destination Vertex
	 * @return 	the distance
	 */
	public int getShortestDistance(Vertex destination) {
		Integer d = distance.get(destination);
		if (d == null) {
			return Integer.MAX_VALUE;
		} else {
			return d;
		}
	}

	/**
	 * This method returns the path from the source to the selected target and
	 * NULL if no path exists
	 * @param destination target node
	 * @return all the nodes collections from source upto the destination, which has minimum weight
	 */
	public LinkedList<Vertex> getPath(Vertex destination) {
		LinkedList<Vertex> path = new LinkedList<Vertex>();
		Vertex step = destination;
		// check if a path exists
		if (predecessors.get(step) == null) {
			return null;
		}
		path.add(step);
		while (predecessors.get(step) != null) {
			step = predecessors.get(step);
			path.add(step);
		}
		// Put it into the correct order
		Collections.reverse(path);
		return path;
	}
}
