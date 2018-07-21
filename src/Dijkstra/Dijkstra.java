package Dijkstra;

import java.util.ArrayList;

import graph.Edge;
import graph.EdgeContainer;
import graph.StartVertex;
import graph.TargetVertex;
import graph.Vertex;

public class Dijkstra {
	ArrayList<Vertex> vertices;
	EdgeContainer edges;
	StartVertex start;
	TargetVertex target;
	G g = new G();
	ArrayList<Vertex> visited = new ArrayList<>();
	
	public Dijkstra(StartVertex start, ArrayList<Vertex> vertices, TargetVertex target, EdgeContainer edges) {
		this.start = start;
		this.vertices = vertices;
		this.target = target;
		g.add(start);
		g.add(target);
		g.addAll(vertices);
		this.edges = edges;
	}

	public void start() {
		Vertex u;
		while (g.size() > 0) {
			u = g.getSmallestVertex();
			visited.add(u);
			g.remove(u);
			for(Vertex v : edges.getNeighbours(u)){
				if(g.contains(v)){
					updateDistance(u,v);
				}
			}
		}
	}
	
	public void updateDistance(Vertex u, Vertex v){
		int alternative = u.getDistanceToStart() + edges.getWeight(new Edge(u,v, 0));
		
		if(alternative < v.getDistanceToStart()){
			v.setDistanceToStart(alternative);
			v.setPredecessor(u);
		}
	}
	
	public ArrayList<Vertex> getShortestPath(Vertex target){
		ArrayList<Vertex> path = new ArrayList<>();
		int index = visited.indexOf(target);
		Vertex v = visited.get(index);
		path.add(v);
		Vertex u = v;
		while(!(u.getPredecessor() == null)){
			u = u.getPredecessor();
			path.add(0, u);
		}
		
		return path;
	}
}
