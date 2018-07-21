package labyrinth;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

import javax.imageio.ImageIO;

import Dijkstra.Dijkstra;
import graph.Graph;
import graph.Vertex;
import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {
	Pane root;
	private static int xTileCount = 11, yTileCount = 17, tileSize = 15;
	private int xSpawn = xTileCount / 2, ySpawn = 0, xTarget = xTileCount / 2, yTarget = yTileCount - 1;
	boolean[][] field = new boolean[yTileCount][xTileCount];
	String resource = "test.bmp";
	boolean freeDraw = false;
	private Scene scene;
	Stage stage;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		root = new Pane();

		stage = new Stage();
		stage.setTitle("My New Stage Title");
		
		System.out.println("Initiating Fields...");
		if (freeDraw) {
			initField();
			initRects();
		} else {
			initField(resource);
			initRects();
		}
		
		System.out.println("Done!");
		
		stage.setScene(scene);

	   
		stage.show();
		System.out.println("Performing Dijkstra...");
		Graph graph = new Graph(field, root);
		Dijkstra d = new Dijkstra(graph.getStart(), graph.getVertices(), graph.getTarget(),
				graph.getEdges());
		d.start();
		
		System.out.println("Done!");
		
		for (Vertex v : d.getShortestPath(graph.getTarget())) {
			if (v.getPredecessor() == null)
				continue;
			Line line = new Line(v.getX() * tileSize + tileSize / 2,
					v.getY() * tileSize + tileSize / 2,
					v.getPredecessor().getX() * tileSize + tileSize / 2,
					v.getPredecessor().getY() * tileSize + tileSize / 2);
			line.setStroke(Color.RED);
			line.setStrokeWidth(tileSize);
			line.setMouseTransparent(true);
			root.getChildren().add(line);
		}
	}

	public void initField() {
		this.scene = new Scene(root, xTileCount * tileSize, yTileCount * tileSize);
		for (int row = 0; row < field.length; row++) {
			for (int column = 0; column < field[0].length; column++) {
				field[row][column] = true;
			}
		}
		for (int row = 0; row < field.length; row++) {
			for (int column = 0; column < field[0].length; column++) {
				if (row == 0 || row == field.length - 1)
					field[row][column] = false;
				else {
					field[row][0] = false;
					field[row][field[0].length - 1] = false;
				}
			}
		}
	}

	public void initField(String resource) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(getClass().getResource(resource).toURI()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int w = img.getWidth();
		int h = img.getHeight();

		int[][] pixels = new int[w][h];

		for (int i = 0; i < w; i++)
			for (int j = 0; j < h; j++)
				pixels[i][j] = img.getRGB(i, j);

		pixels = transposeMatrix(pixels);

		this.tileSize = 400 / pixels.length;
		this.xTileCount = pixels[0].length;
		this.yTileCount = pixels.length;
		stage.setMinWidth(xTileCount * tileSize);
		stage.setMinHeight(yTileCount * tileSize);
	
		this.scene = new Scene(root, xTileCount * tileSize, yTileCount * tileSize);
		field = new boolean[pixels.length][pixels[0].length];

		for (int row = 0; row < field.length; row++) {
			for (int column = 0; column < field[0].length; column++) {
				if (pixels[row][column] < -1) {
					if (row == 0) {
						this.xSpawn = column;
						this.ySpawn = row;
					}

					if (row == pixels.length - 1) {
						this.xTarget = column;
						this.yTarget = row;
					}
					
					field[row][column] = true;
				} else {
					field[row][column] = false;
				}
			}
		}
	}

	public void initRects() {
		for (int row = 0; row < field.length; row++) {
			for (int column = 0; column < field[0].length; column++) {
				Rectangle rec;

				if (row == ySpawn && column == xSpawn) {
					rec = new Rectangle(column * tileSize, row * tileSize, tileSize, tileSize);
					rec.setFill(Color.GREY);
					root.getChildren().add(rec);
					field[ySpawn][xSpawn] = true;
					continue;
				} else if (row == yTarget && column == xTarget) {
					rec = new Rectangle(column * tileSize, row * tileSize, tileSize, tileSize);
					rec.setFill(Color.GREY);
					root.getChildren().add(rec);
					field[yTarget][xTarget] = true;
					continue;
				} else {
					rec = new Rectangle(column * tileSize, row * tileSize, tileSize, tileSize);
					if (field[row][column])
						rec.setFill(Color.WHITE);
					else
						rec.setFill(Color.BLACK);
					root.getChildren().add(rec);
				}

				if (!((row == 0 || row == field.length - 1) || column == 0 || column == field[0].length - 1)) {
					rec.setOnMousePressed(new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent t) {
							int x = (int) (rec.getX() / tileSize), y = (int) (rec.getY() / tileSize);
							if (field[y][x])
								rec.setFill(Color.BLACK);
							else
								rec.setFill(Color.WHITE);

							field[y][x] = !field[y][x];

							System.out.println("\n");

							root.getChildren().removeIf(n -> n instanceof Circle || n instanceof Line);
							
							System.out.println("Performing Dijkstra...");
							Graph graph = new Graph(field, root);
							Dijkstra d = new Dijkstra(graph.getStart(), graph.getVertices(), graph.getTarget(),
									graph.getEdges());
							d.start();

							System.out.println("Done!");
							
							for (Vertex v : d.getShortestPath(graph.getTarget())) {
								if (v.getPredecessor() == null)
									continue;
								Line line = new Line(v.getX() * tileSize + tileSize / 2,
										v.getY() * tileSize + tileSize / 2,
										v.getPredecessor().getX() * tileSize + tileSize / 2,
										v.getPredecessor().getY() * tileSize + tileSize / 2);
								line.setStroke(Color.RED);
								line.setStrokeWidth(tileSize);
								line.setMouseTransparent(true);
								root.getChildren().add(line);
							}
						}
					});
				}
			}
		}
	}

	public static int[][] transposeMatrix(int[][] m) {
		int[][] temp = new int[m[0].length][m.length];
		for (int i = 0; i < m.length; i++)
			for (int j = 0; j < m[0].length; j++)
				temp[j][i] = m[i][j];
		return temp;
	}

	public static int getTileSize() {
		return tileSize;
	}
}
