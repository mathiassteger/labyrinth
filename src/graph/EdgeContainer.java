package graph;

import java.util.ArrayList;

public class EdgeContainer extends ArrayList<Edge> {

	public EdgeContainer() {
		super();
	}

	public ArrayList<Vertex> getNeighbours(Vertex v) {
		ArrayList<Vertex> temp = new ArrayList<>();

		for (Edge edge : this) {
			Vertex neighbour = edge.getNeighbour(v);

			if (neighbour != null) {
				temp.add(neighbour);
			}
		}

		return temp;
	}

	public int getWeight(Edge edge) {
		int index = indexOf(edge);

		if (index > -1)
			return get(index).getWeight();
		else
			return -1;
	}
}
