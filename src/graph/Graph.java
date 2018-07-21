package graph;

import java.util.ArrayList;
import java.util.Arrays;

import static labyrinth.Main.getTileSize;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class Graph {
	boolean[][] field;
	Pane root;
	ArrayList<Vertex> vertices = new ArrayList<>();
	EdgeContainer edges = new EdgeContainer();
	StartVertex startVertex;
	TargetVertex targetVertex;

	public Graph(boolean[][] field, Pane root) {
		this.field = field;
		this.root = root;
		initGraph();
	}

	public void initGraph() {
		for (int column = 0; column < field[0].length; column++) {
			if (field[0][column]) {
				this.startVertex = new StartVertex(column, 0);
				int tileSize = getTileSize();
				Circle c = new Circle(column * tileSize + tileSize / 2, 0 * tileSize + tileSize / 2, tileSize/6, Color.DARKRED);
				c.setMouseTransparent(true);
				root.getChildren().add(c);
			}
		}

		for (int column = 0; column < field[0].length; column++) {
			if (field[field.length - 1][column]) {
				this.targetVertex = new TargetVertex(column, field.length - 1);
				int tileSize = getTileSize();
				Circle c = new Circle(column * tileSize + tileSize / 2, (field.length - 1) * tileSize + tileSize / 2, tileSize/6,
						Color.DARKRED);
				c.setMouseTransparent(true);
				root.getChildren().add(c);
			}
		}

		for (int row = 1; row < field.length - 1; row++) {
			for (int column = 1; column < field[0].length - 1; column++) {
				if (field[row][column]) {
					boolean northSouthFalse = (!field[row - 1][column] && !field[row + 1][column]);
					boolean eastWestFalse = (!field[row][column - 1] && !field[row][column + 1]);
					if (northSouthFalse || eastWestFalse) {
						continue;
					} else {
						MiddleVertex middleVertex = new MiddleVertex(column, row);
						this.vertices.add(middleVertex);
						int tileSize = getTileSize();
						Circle c = new Circle(column * tileSize + tileSize / 2, row * tileSize + tileSize / 2, tileSize/6,
								Color.DARKRED);
						c.setMouseTransparent(true);
						root.getChildren().add(c);
					}
				}
			}
		}

		initEdges();
		int temp = isWayY(startVertex, targetVertex);
		if (temp > -1 && vertices.size() == 0) {
			edges.add(new Edge(startVertex, targetVertex, temp));
		}

		for (Edge edge : edges) {
			Vertex a = edge.getVertices()[0];
			Vertex b = edge.getVertices()[1];
			int tileSize = getTileSize();
			Line line = new Line(a.getX() * tileSize + tileSize / 2, a.getY() * tileSize + tileSize / 2,
					b.getX() * tileSize + tileSize / 2, b.getY() * tileSize + tileSize / 2);
			line.setStroke(Color.CADETBLUE);
			line.setMouseTransparent(true);
			line.setStrokeWidth(tileSize/30);
			root.getChildren().add(line);
		}
	}

	public int isWayX(Vertex v1, Vertex v2) {
		int lower = v1.getX() < v2.getX() ? v1.getX() : v2.getX();
		int higher = v1.getX() > v2.getX() ? v1.getX() : v2.getX();
		int y = v1.getY();
		int r = -1;
		for (int i = lower; i < higher; i++) {
			if (!field[y][i]) {
				r = -1;
				break;
			} else {
				r++;
			}
		}
		if (r > -1)
			return r + 1;
		else
			return r;
	}

	public int isWayY(Vertex v1, Vertex v2) {
		int lower = v1.getY() < v2.getY() ? v1.getY() : v2.getY();
		int higher = v1.getY() > v2.getY() ? v1.getY() : v2.getY();
		int x = v1.getX();
		int r = -1;
		for (int i = lower; i < higher; i++) {
			if (!field[i][x]) {
				r = -1;
				break;
			} else {
				r++;
			}
		}

		if (r > -1)
			return r + 1;
		else
			return r;
	}

	public void initEdges() {
		for (Vertex middleVertex : vertices) {
			int row = middleVertex.getY();
			int column = middleVertex.getX();

			int x = column - 1;
			int counter = 1;
			while (x >= 0) {
				int index = vertices.indexOf(new Vertex(x, row));
				if (!field[row][x]) {
					break;
				} else if (index > -1) {
					Vertex v = vertices.get(index);
					Edge edge = new Edge(middleVertex, v, counter);
					if (!edges.contains(edge))
						edges.add(edge);
					break;
				} else {
					x--;
					counter++;
				}
			}

			x = column + 1;
			counter = 1;
			while (x < field[0].length) {
				int index = vertices.indexOf(new Vertex(x, row));
				if (!field[row][x]) {
					break;
				} else if (index > -1) {
					Vertex v = vertices.get(index);
					Edge edge = new Edge(middleVertex, v, counter);
					if (!edges.contains(edge))
						edges.add(edge);
					break;
				} else {
					x++;
					counter++;
				}
			}

			int y = row - 1;
			counter = 1;
			while (y >= 0) {
				Vertex vertex = new Vertex(column, y);
				int index = vertices.indexOf(vertex);
				if (!field[y][column]) {
					break;
				} else if (index > -1) {
					Vertex v = vertices.get(index);
					Edge edge = new Edge(middleVertex, v, counter);
					if (!edges.contains(edge))
						edges.add(edge);
					break;
				} else if (startVertex.equals(vertex)) {
					edges.add(new Edge(middleVertex, startVertex, counter));
					break;
				} else {
					y--;
					counter++;
				}
			}

			y = row + 1;
			counter = 1;
			while (y < field.length) {
				Vertex vertex = new Vertex(column, y);
				int index = vertices.indexOf(vertex);
				if (!field[y][column]) {
					break;
				} else if (index > -1) {
					Vertex v = vertices.get(index);
					Edge edge = new Edge(middleVertex, v, counter);
					if (!edges.contains(edge))
						edges.add(edge);
					break;
				} else if (targetVertex.equals(vertex)) {
					edges.add(new Edge(middleVertex, targetVertex, counter));
					break;
				} else {
					y++;
					counter++;
				}
			}

		}
	}
	
	public TargetVertex getTarget(){
		return targetVertex;
	}
	
	public StartVertex getStart(){
		return startVertex;
	}
	
	public ArrayList<Vertex> getVertices(){
		return vertices;
	}
	
	public EdgeContainer getEdges(){
		return edges;
	}
}
