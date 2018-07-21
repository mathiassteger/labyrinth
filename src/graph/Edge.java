package graph;

import java.util.Arrays;

public class Edge {
	private Vertex[] vertices = new Vertex[2];
	private int weight;

	public Edge(Vertex vertex1, Vertex vertex2, int weight) {
		this.vertices[0] = vertex1;
		this.vertices[1] = vertex2;
		this.weight = weight;
	}

	public Vertex[] getVertices() {
		return vertices;
	}

	public int getWeight() {
		return weight;
	}

	@Override
	public String toString() {
		return ("Vertex " + "A: " + vertices[0] + ", Vertex B: " + vertices[1] + ". Weight: " + weight);
	}

	@Override
	public boolean equals(Object o) {
		Edge e = (Edge) o;
		Vertex[] vs = e.getVertices();
		if (vertices[0].equals(vs[0])) {
			if (vertices[1].equals(vs[1]))
				return true;
		} else if (vertices[1].equals(vs[0])) {
			if (vertices[0].equals(vs[1]))
				return true;
		}

		return false;
	}

	public boolean contains(Vertex v) {
		return vertices[0].equals(v) || vertices[1].equals(v);
	}

	public Vertex getNeighbour(Vertex v) {
		if (vertices[0].equals(v))
			return vertices[1];
		else if (vertices[1].equals(v))
			return vertices[0];
		else
			return null;
	}
}
