package Dijkstra;

import java.util.ArrayList;

import graph.Vertex;

public class G extends ArrayList<Vertex> {
	public G(){
		super();
	}
	
	public Vertex getSmallestVertex(){
		Vertex vertex = this.get(0);
		for (Vertex v : this){
			if(v.getDistanceToStart() < vertex.getDistanceToStart())
				vertex = v;
		}
		return vertex;
	}
}
