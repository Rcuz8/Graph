import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.Timer;

public class Graph {

	//instance variables, makes things easier
    HashMap<Node, ArrayList<Edge>> map;
    ArrayList<Node> nodeList;
    ArrayList<Edge> edgeList;
    boolean isDirected;
    PriorityQueue<Node> PQ;

    public Graph(ArrayList<Node> nodeList, ArrayList<Edge> edgeList, boolean isDirected) {
        // Store the nodes into public list
        this.nodeList = nodeList;
        this.edgeList = edgeList;

        map = new HashMap<>();

        // Iterate through edges
        for (Edge e: edgeList) {
            // Get from node
            Node from = e.getFrom();
            Node to = e.getTo();
            if (from == null || to == null)
                p("null node!");
            // Push to map
            if (map.get(from) != null)
                map.get(from).add(e);
            else {
                map.put(from, new ArrayList<Edge>());
                map.get(from).add(e);
            }

            if (!isDirected) {
                if (map.get(to) != null)
                    map.get(to).add(e);
                else {
                    map.put(to, new ArrayList<Edge>());
                    map.get(to).add(e);
                }
            }



        }


        this.isDirected = isDirected;

    }


    /**
     * Finds shortest path from a starting node to each other node using Dijkstras Algorithm.
     *
     * @param from the node we want to start at
     * @return the shortest distance between the nodes
     */
    public ReturnPath smallestDistance(int from, int to) {

        final int n = nodeList.size();

        // Start with node array 1 to N

        // Array of all nodes
        ArrayList<Node> nodes_array = (ArrayList<Node>) nodeList.clone();

        // Set each element to Int.Max, except node at index from

        for (int i = 0; i < n; i++)
            nodes_array.get(i).setShortestDistance(Integer.MAX_VALUE);

        nodes_array.get(from).setShortestDistance(0);

        PQ = new PriorityQueue(nodes_array);


        // Loop while not all elements have been chosen
        while (!PQ.isEmpty()) {
            // Get smallest non-chosen element

            Node element = PQ.remove();

            element.setChosen(true);

            relaxAdjacents(element);

        }

        ArrayList<Node> path = new ArrayList<>();

        Node last = nodes_array.get(to);

        while (last != null) {
            path.add(last);
            last = last.getParent();
        }

        return new ReturnPath(path, nodes_array.get(to).getShortestDistance());

    }
    /*relax method essentially just checks to see if the current shortest path is longer than another possible one,
     * and if it is then we replace it by the new shorter one
    */
    private void relax(Node u, Node v, double weight) {

        if (v.getShortestDistance() > u.getShortestDistance() + weight) {
            PQ.remove(v);
            v.setShortestDistance(u.getShortestDistance() + weight);
            v.setParent(u);
         //   p("Setting " + v.getID() + "'s parent to " + u.getID());
            PQ.add(v);
        }
    }

    /*
     * helper method for relaxing, we just iterate through the list of edges and then for each one that hasn't been 
     * visited, we go ahead and call the relax method on it
     */
    private void relaxAdjacents(Node from) {
        ArrayList<Edge> edges = map.get(from);
        if (edges != null)
            for (Edge e: edges) {
                if (!e.getTo().isChosen())
                    relax(e.getFrom(), e.getTo(), e.getWeight());
            }

    }


    /**
     * Implementation of bellman-ford's algorithm.
     *
     * @param from the source node
     */
    String bellmanFord(Node from) {
        // Clone the array
        ArrayList<Node> nodes_array = (ArrayList<Node>) nodeList.clone();
        // Iterate, set distance to INF, set parent to null
        for (Node n: nodes_array) {
            n.setShortestDistance(Integer.MAX_VALUE);
            n.setParent(null);
        }
        // Set source node distance to zero
        from.setShortestDistance(0);
        // Iterate and relax adjacent node
        for (int i = 0; i < nodes_array.size(); i++) {
            relaxAdjacents(nodes_array.get(i));
        }

        // Iterate and check for negative weight
        for (int i = 0; i < edgeList.size(); i++) {
            Edge edge = edgeList.get(i);
            Node u = edge.getFrom();
            Node v = edge.getTo();
            if (v.getShortestDistance() > u.getShortestDistance() + edge.getWeight()) {
                p("There exists a negative cycle!");
                return "YES";
            }
        }

        return "NO";
    }

    /**
     * Implementation of Prims algorithm.
     *
     * @param from the source node
     */
    ArrayList<Edge> prims(Node from, ArrayList<Edge> F) {
        // Clone the array
        ArrayList<Node> vertices = (ArrayList<Node>) nodeList.clone();
        // Init PQ
        PriorityQueue<Node> Q = new PriorityQueue<>();
        // Iterate, set distance to INF, set parent to null, add to PQ
        for (Node n: vertices) {
            n.setShortestDistance(Integer.MAX_VALUE);
            n.setParent(null);
            Q.add(n);
        }
        // Set source node distance to zero
        from.setShortestDistance(0);

        // While PQ is not empty
        while (!Q.isEmpty()) {
            // Remove top
            Node u = Q.remove();
            // Get adjacent nodes that are in the PQ
            ArrayList<Node> adjacentsInPQ = adjacentInPQ(u, Q);
            // Iterate, relax, and decrease key ( by removing, updating, and inserting )
            for (Node adj: adjacentsInPQ) {
                if (((adj.getShortestDistance() > u.getShortestDistance() + edgeBetween(u, adj).getWeight()) && !F.contains(edgeBetween(u,adj))) || (F.contains(edgeBetween(u,adj)))) {
                    Q.remove(adj);
                    adj.setShortestDistance(u.getShortestDistance() + edgeBetween(u, adj).getWeight());
                    vertices.get(vertices.indexOf(adj)).setParent(u);
                    Q.add(adj);
                }
            }
        }

        ArrayList<Edge> edges = new ArrayList<Edge>();
        for(int i = 1; i < vertices.size(); i++) {
        	edges.add(edgeBetween(vertices.get(i),vertices.get(i).getParent()));
        }
        return edges;

    }



    ArrayList<Node> adjacentInPQ(Node from, PriorityQueue<Node> PQ) {

        // Track adjacent nodes that are also in PQ
        ArrayList<Integer> adjacentIndices = adjacentNodes(from);
        ArrayList<Node> adjacentNodes = new ArrayList<>();
        for (Integer i: adjacentIndices)
            adjacentNodes.add(nodeList.get(i));

        ArrayList<Node> adjacentsInPQ = new ArrayList<>();
        // Iterating over PQ elements
        for (Iterator i = PQ.iterator(); i.hasNext(); ) {
            if (adjacentNodes.contains((Node) i.next()))
                adjacentsInPQ.add((Node) i.next());
        }

        return adjacentsInPQ;
    }


    /*
     * method that just takes two nodes and returns the edge that connects them
     */
    Edge edgeBetween(Node from, Node to) {
        ArrayList<Edge> from_edges = map.get(from);
        for (Edge e: from_edges)
            if (e.getTo().equals(to))
                return e;
        return new Edge();
    }


    /**
     * Print a string
     *
     * @param a a string
     */
    static void print(String a) {
        System.out.println(a);
    }

    /**
     * Print an array
     *
     * @param a an array
     */
    void printArray(int[] a) {
        System.out.print("{ ");
        // Iterate
        for (int i: a) {
            if (i != Integer.MAX_VALUE)
                System.out.print(i + " ");
            else
                System.out.print("∞ ");
        }
        System.out.println("}");
    }

    /**
     * Print an array;list
     *
     * @param a an array
     */
    void printArray(ArrayList<Integer> a) {
        // Iterate
        for (Integer i: a)
            System.out.print(i + " ");
    }

    /**
     * Return the indices of each node adjacent to the provided one
     *
     * @param from the root node of the check
     * @return the indices of adjacent nodes
     */
    ArrayList<Integer> adjacentNodes(Node from) {
        ArrayList<Edge> edges = map.get(from);
        // New temp adj node index list
        ArrayList<Integer> adj = new ArrayList<>();
        // Iterate through each node in column 'from'
        for (int i = 0; i < edges.size(); i++) {
            // If connection (edge) exists, node is adjacent so add index to list
            adj.add(indexOfNode(edges.get(i).getTo().getID()));
        }

        return adj;
    }

    /*
     * given some string which represents the id of a given node, this method returns the position in the nodesList,
     * this is meant to make it easier to take in input from the user when they say which points they want the shortest
     * path to be between.
     */
    Integer indexOfNode(String id) {
        for (int i = 0; i < nodeList.size(); i++) {
            if (nodeList.get(i).getID().equals(id))
                return i;
        }
        return -1;
    }

    static void p(Object o) {
        System.out.println(o);
    }


}