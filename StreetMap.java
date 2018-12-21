import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class StreetMap {
	 static MapWindow m = new MapWindow();

    public static void main(String[] args) {

    	
    	String file = args[0];
    	
    	if(args[1].equals("--show")) {
    		
    		if (args.length > 2 && args[2].equals("--directions")) {
                System.out.println("S");
                scanFile2(file,args[3],args[4]);
//    		    m.setVisible(false);
            } else {
               //render graph
    		scanFile2(file,null,null);
            }
            m.setVisible(true);

    	} else if(args[1].equals("--directions")) {
    		
            scanFile2(file,args[2],args[3]);

    		if (args.length > 4 && args[4].equals("--show"))
                m.setVisible(true);

    	}
    }

    // Store the delimiters we want to use
    static char[] commas = {','};
    static char[] spaces = {'\t'};

    /**
     * Scan file by filename and fill graph with input info
     *
     * @param fileName the filename
     */
  

    /**
     * Scan file by filename and fill graph with input info
     *
     * @param fileName the filename
     */
    public static void scanFile2(String fileName, String start, String destination) {

        try {
            // Input from file
            Scanner in = new Scanner(new File(fileName));

            ArrayList<Node> nodeList = new ArrayList<>();
            ArrayList<Edge> edgeList = new ArrayList<>();

            // First line
            while (in.hasNextLine()) {

                String s = in.nextLine();

                // Get new line's input, split by delimiters
                ArrayList<String> data = splitByDelims(s, spaces);

                while (data.size() > 0) {

                    // Case of intersection (node)
                    if (data.get(0).equals("i")) {
                        String intersectionId = data.get(1).trim();
                        Double lattitude = Double.parseDouble(data.get(2).trim());
                        Double longitude = Double.parseDouble(data.get(3).trim());
                        nodeList.add(new Node(intersectionId, longitude, lattitude));
                    }
                    // Case of Road (edge)
                    if (data.get(0).equals("r")) {
//                        p("Found edge!");
                        String roadId = data.get(1).trim();
                        String from_Id = data.get(2).trim();
                        String to_Id = data.get(3).trim();

                        Node from = nodeFromList(nodeList, from_Id);
                        Node to = nodeFromList(nodeList, to_Id);


                        if (from != null && to != null){}
                        else
                            p("Error connecting nodes. Found from: " + (from != null) + " Found to: " + (to != null));



                        edgeList.add(new Edge(roadId, from, to));
                    }

                    data.clear();
                }

            }

            //map the new graph 
            Graph g = new Graph(nodeList, edgeList, false);

           
            //add the segments and points to the map
            for (Node n_: nodeList)
                m.addPOI(get_POI_from_node(n_));

            for (Edge e: edgeList)
                m.addSegment(get_Segment_from_edge(e,Color.black));

            
            
            if (start != null || destination != null) {
                ReturnPath smallest = g.smallestDistance(nodeList.indexOf(nodeFromList(nodeList,start)), nodeList.indexOf(nodeFromList(nodeList,destination)));

                for (int i = 0; i < smallest.path.size()-1; i++) {
                    m.addSegment(get_Segment_from_edge(new Edge("", smallest.path.get(i), smallest.path.get(i+1)),Color.GREEN));
                }

                //Stack is for printing out the shortest path in the correct order
                Stack<Node> s = new Stack<Node>();
                for(Node node : smallest.path) {
                    s.push(node);
                }
                while(!s.isEmpty())
                    p(s.pop().getID());
                p(smallest.distance);
                }
            
            
        } catch (FileNotFoundException f) {
            System.out.println("File not found!!");
        }
        
    }

    //helper methods 
    static POI get_POI_from_node(Node n) {
        return new POI(n.getLat(), n.getLong(), ""/*n.getID()*/);
    }

    static Segment get_Segment_from_edge(Edge e, Color c) {
        return new Segment(get_POI_from_node(e.getFrom()), get_POI_from_node(e.getTo()), c);
    }

    static Node nodeFromList(ArrayList<Node> nodes, String intersectionId) {
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).getID().equals(intersectionId))
                return nodes.get(i);
        }
        return new Node();
    }

    /**
     * Split string by delimiters into an ArrayList
     *
     * @param str input string
     * @param delims delimiters list to split by
     * @return list of integers
     */
    static ArrayList<String> splitByDelims(String str, char[] delims) {
        /*
            Build the delimiter from all the ones we want
         */
        // Init value
        String del = "[";
        // Add in delimiters
        for (char d: delims)
            del += d;
        // Close delimiter
        del += "]+";

        /*
            Split tokens by conjoined delimiter
         */

        // Split tokens
        String[] tokens = str.split(del);
        // Init temp AL
        ArrayList<String> ints = new ArrayList<>();
        // Iterate through tokens
        for (String tok: tokens)
            // Append parsed token
            ints.add((tok));

        return ints;
    }

    //didn't feel like typing a lot so we have quick print methods
    static void p(Object o) {
        System.out.println(o);
    }

    static <E> void pArray(ArrayList<E> a) {
        for (E e: a)
            p(e);
    }


}
