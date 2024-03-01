

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;

import java.awt.Color;

import javax.swing.JPanel;

/**
 * Simulation of Kruskal algorithm.
 * 
 */
class Kruskal310 implements ThreeTenAlg {
	/**
	 * The graph the algorithm will run on.
	 */
	Graph<GraphNode, GraphEdge> graph;

	/**
	 * The priority queue of edges for the algorithm.
	 */
	WeissBST<GraphEdge> pqueue;

	/**
	 * The subgraph of the MST in construction.
	 */
	private Graph310 markedGraph;

	/**
	 * Whether or not the algorithm has been started.
	 */
	private boolean started = false;

	/**
	 * The color when a node has "no color".
	 */
	public static final Color COLOR_NONE_NODE = Color.WHITE;

	/**
	 * The color when an edge has "no color".
	 */
	public static final Color COLOR_NONE_EDGE = Color.BLACK;

	/**
	 * The color when a node is inactive.
	 */
	public static final Color COLOR_INACTIVE_NODE = Color.LIGHT_GRAY;

	/**
	 * The color when an edge is inactive.
	 */
	public static final Color COLOR_INACTIVE_EDGE = Color.LIGHT_GRAY;

	/**
	 * The color when a node is highlighted.
	 */
	public static final Color COLOR_HIGHLIGHT = new Color(255, 204, 51);

	/**
	 * The color when a node is in warning.
	 */
	public static final Color COLOR_WARNING = new Color(255, 51, 51);

	/**
	 * The color when a node/edge is selected and added to MST.
	 */
	public static final Color COLOR_SELECTED = Color.BLUE;

	/**
	 * {@inheritDoc}
	 */
	public EdgeType graphEdgeType() {
		return EdgeType.UNDIRECTED;
	}

	/**
	 * {@inheritDoc}
	 */
	public void reset(Graph<GraphNode, GraphEdge> graph) {
		this.graph = graph;
		started = false;
		pqueue = null;
		markedGraph = new Graph310();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isStarted() {
		return started;
	}

	/**
	 * {@inheritDoc}
	 */
	public void cleanUpLastStep() {
		// Unused. Required by the interface.
	}

	
	/**
	 * this calss is a provate class created to help the alogrethem because we were not able to add class veriables.
	 *
	 */
	private static class UnionFind {
		/**
		 * An array root.
		 */
		private static int[] root;
		/**
		 * An array level.
		 */
		private static int[] level;
		/**
		 * this methode was created in this helper class that intialize the union.
		 * @param n which is the number.
		 */
		public static void initiat(int n) {
			root = new int[n];
			level = new int[n];
			for (int i = 0; i < n; i++) {
				root[i] = i;
			}
		}

		/**
		 * This method finds the value at an index.
		 * 
		 * @param x index int
		 * @return value at index in root array.
		 */
		public static int find(int x) {
			if (root[x] != x) {
				root[x] = find(root[x]);
			}
			return root[x];
		}

		/**
		 * .
		 * 
		 * @param x int
		 * @param y int
		 */
		public static void union(int x, int y) {
			int rootX = find(x);
			int rootY = find(y);
			if (rootX != rootY) {
				if (level[rootX] < level[rootY]) {
					root[rootX] = rootY;
				} else if (level[rootX] > level[rootY]) {
					root[rootY] = rootX;
				} else {
					root[rootY] = rootX;
					level[rootX]++;
				}
			}
		}
	}
	/**
	 * this method is to check if a cycle will be created.
	 * @param edge thedge that we are ading.
	 * @param source the source of the edge.
	 * @param dest the destenation of the edge.
	 * @return true or false.
	 */
	private boolean isCycle(GraphEdge edge, GraphNode source, GraphNode dest) {

		if (UnionFind.find(source.getId()) == UnionFind.find(dest.getId())) {
			return true;
		}

		UnionFind.union(source.getId(), dest.getId());
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public void start() {
		started = true;

		

		if (graph != null) {

			pqueue = new WeissBST<GraphEdge>();

			int n = graph.getVertexCount();
			UnionFind.initiat(n);

			for (GraphEdge edge : graph.getEdges()) {
				pqueue.insert(edge);
			}
			markedGraph = new Graph310();

		

			// highlight the edge with min weight
			highlightNext();
		}

	}
	/**
	 * this method highlites the next edge that we are going to step in.
	 */
	public void highlightNext() {

		// Find the current min edge in the priority queue
		// and change the color of the edge to be COLOR_HIGHLIGHT.
		// Note: do not dequeue the node.

		if (pqueue == null) {
			return;
		}
		if (pqueue.findMin() != null) {
			pqueue.findMin().setColor(COLOR_HIGHLIGHT);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	public void finish() {

		// Mark all nodes not in the constructed MST with COLOR_WARNING
		for (GraphNode vertix : graph.getVertices()) {
			if (!markedGraph.containsVertex(vertix)) {
				vertix.setColor(COLOR_WARNING);
			}
		}
		for (GraphEdge edge : graph.getEdges()) {
			if (!markedGraph.containsEdge(edge)) {
				edge.setColor(COLOR_INACTIVE_EDGE);
			}
		}

	}

	/**
	 * {@inheritDoc}
	 */
	public boolean setupNextStep() {

		// decide whether we are done with the MST algorithm
		// return true if more steps to continue; return false if done
		// Hint: you may not always need to check all edges.

		int notConected = 0;
		for (GraphNode vertix : graph.getVertices()) {
			if (((Graph310) graph).getNeighbors(vertix).size() == 0) {
				notConected++;

			}

		}

		return ((markedGraph.getEdgeCount() < graph.getVertexCount() - (notConected + 1)));
	}

	/**
	 * {@inheritDoc}
	 */
	public void doNextStep() {

		if (!pqueue.isEmpty()) {
			GraphEdge min = pqueue.findMin();
			pqueue.removeMin();

			if (!markedGraph.containsVertex(graph.getSource(min))) {
				markedGraph.addVertex(graph.getSource(min));
			}
			if (!markedGraph.containsVertex(graph.getDest(min))) {
				markedGraph.addVertex(graph.getDest(min));
			}

			if (!isCycle(min, graph.getSource(min), graph.getDest(min))) {
				markedGraph.addEdge(min, graph.getSource(min), graph.getDest(min));
				min.setColor(COLOR_SELECTED);
				graph.getSource(min).setColor(COLOR_SELECTED);
				graph.getDest(min).setColor(COLOR_SELECTED);

			}
			if (!markedGraph.containsEdge(min)) {
				min.setColor(COLOR_INACTIVE_EDGE);

			}
			highlightNext();
		}

	}


	
	/**
	 * this is the main method to test.
	 * @param args which the comanned argument.
	 */
	public static void main(String[] args) {

		Graph310 graph = new Graph310();
		Kruskal310 kruskal = new Kruskal310();

		GraphNode[] nodes = { new GraphNode(0), new GraphNode(1) };

		GraphEdge[] edges = { new GraphEdge(0) };

		// a graph of two nodes, one edge
		graph.addVertex(nodes[0]);
		graph.addVertex(nodes[1]);
		graph.addEdge(edges[0], nodes[0], nodes[1]);

		kruskal.reset(graph);
		while (kruskal.step()) {
		} // execution of all steps

		if (nodes[0].getColor() == COLOR_SELECTED && nodes[1].getColor() == COLOR_SELECTED
				&& edges[0].getColor() == COLOR_SELECTED && kruskal.pqueue.size() == 0) {
			System.out.println("Yay1!");
		}

		// start over with a new graph
		graph = new Graph310();
		GraphNode[] nodes2 = { new GraphNode(0), new GraphNode(1), new GraphNode(2), new GraphNode(3), new GraphNode(4),new GraphNode(5) };

		GraphEdge[] edges2 = { new GraphEdge(0, 7), new GraphEdge(1, 1), new GraphEdge(2, 19), new GraphEdge(3, 3), new GraphEdge(4, 16),new GraphEdge(5, 2), new GraphEdge(6, 9) };

		graph.addVertex(nodes2[0]);
		graph.addVertex(nodes2[1]);
		graph.addVertex(nodes2[2]);
		graph.addVertex(nodes2[3]);
		graph.addVertex(nodes2[4]);
		graph.addVertex(nodes2[5]);

		graph.addEdge(edges2[0], nodes2[2], nodes2[0]);
		graph.addEdge(edges2[1], nodes2[3], nodes2[1]);
		graph.addEdge(edges2[2], nodes2[1], nodes2[5]);
		graph.addEdge(edges2[3], nodes2[3], nodes2[2]);
		graph.addEdge(edges2[4], nodes2[2], nodes2[5]);
		graph.addEdge(edges2[5], nodes2[3], nodes2[0]);
		graph.addEdge(edges2[6], nodes2[0], nodes2[5]);

		kruskal.reset(graph);
		while (kruskal.step()) {
		} // execution of all steps

		// edges 1,3,5,6 selected, nodes 0,1,2,3,5 selected
		if (nodes2[4].getColor() == COLOR_WARNING && nodes2[0].getColor() == COLOR_SELECTED
				&& edges2[1].getColor() == COLOR_SELECTED && edges2[0].getColor() == COLOR_INACTIVE_EDGE) {
			System.out.println("Yay2!");
		}

		// write your own testing code ...
	}

}