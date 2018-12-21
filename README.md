
-----------------
 BACKGROUND INFO
-----------------

Author: RYAN COCUZZO
Partner: Evan Cohen-Doty

------
 TASK
------

The task was to implement a highly functional and comprehensive graph class that, in the least, could respond to command-line input to render an interactive map and give shortest-path directions. This is done in O(VlogE) time using Dijkstra's algorithm and an adjacency list. This codebase extends that functionality, although this portion of the code is dormant within the application, with implementations of prim's and bellman-ford algorithms, two powerful algorithms in the conversation of the graph data structure.

-------
 FILES
-------

    Edge.java - Represents an edge in the graph

	Graph.java - The cornerstone data structure within which the greater part of the application's functionality is centered.
	
	Node.java - Represents a node in the graph
	
	StreetMap.java - "Main" class that handles input-processing, calling upon the graph, etc.
	
	Queue.java - Simple Queue data structure
	
	QueueStructure.java - Interface for the Queue data structure
	
	ReturnPath.java - Simple class that represents the return data of dijkstra's algorithm.

	Other .txt files were included as test input files

The following map-rendering classes were borrowed and is cited within the respective classes.

	MapPanel.java
	MapWindow.java
	POI.java
	Point.java
	Segment.java


-------------------
 Run Time Analysis
-------------------

When the graph is constructed, we just process each edge, which is O(|E|). The implementation of the shortest-path algorithm used will process each edge, but, for each edge processed, we are also processing each node, resulting in O(|N||E|) runtime.

---------------
 COMPILE & RUN
---------------

	Open terminal on folder. Run the following commands.

	~$ javac StreepMap.java

	~$ java StreepMap <input-file> [--show] [--directions intersection1_ID intersection2_ID]

	or

	~$ java StreepMap <input-file> [--directions intersection1_ID intersection2_ID] [--show] 


