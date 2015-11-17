package com.booking;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.junit.*;

public class TestBookingLab {
	private List<Vertex> nodes;
	private List<Edge> edges;
	private int shortestDistance =0;

	  public int getShortestDistance() {
		return shortestDistance;
	}

	public void setShortestDistance(int shortestDistance) {
		this.shortestDistance = shortestDistance;
	}

	@Test
	  public void testExcute() {
	    nodes = new ArrayList<Vertex>();
	    edges = new ArrayList<Edge>();
	    for (int i = 0; i < 11; i++) {
	      Vertex location = new Vertex("Node_" + i, "Node_" + i);
	      nodes.add(location);
	    }
	    //Assumption is - Vertex(node) "a" as 0,"b" as 1, "c" as 2, "d" as 3, "e" as 4, "f" as 5, "g" as 6, "h" as 7
	    //Weight of the edges are considered as the # of the flights between two nodes ( which can be considered as the distance between the nodes)
	    addLane("EdgeA", 1, 0, 1);
	    addLane("EdgeB", 7, 1, 1);
	    addLane("EdgeC", 0, 5, 1);
	    addLane("EdgeD", 1, 2, 1);
	    addLane("EdgeE", 4, 1, 1);
	    addLane("EdgeF", 2, 4, 9);
	    addLane("EdgeG", 2, 3, 1);
	    addLane("EdgeH", 3, 4, 1);
	    

	    // Lets check from location Loc_1 to Loc_7
	    Graph graph = new Graph(nodes, edges);
	    BookingLab bookingLab = new BookingLab(graph);
	    bookingLab.traverseNodes(nodes.get(7));
	    LinkedList<Vertex> path = bookingLab.getPath(nodes.get(4));
	    
	    Assert.assertNotNull(path);
	    Assert.assertTrue(path.size() > 0);
	    
	    for (Vertex vertex : path) {
	      System.out.println(vertex);
	      this.shortestDistance = bookingLab.getShortestDistance(vertex)+this.shortestDistance;
	      System.out.println(bookingLab.getShortestDistance(vertex));
	    }
	    System.out.println(this.shortestDistance);
	    
	  }

	  private void addLane(String laneId, int sourceLocNo, int destLocNo, int weight) {
	    Edge lane = new Edge(laneId,nodes.get(sourceLocNo), nodes.get(destLocNo), weight);
	    edges.add(lane);
	  }
}
