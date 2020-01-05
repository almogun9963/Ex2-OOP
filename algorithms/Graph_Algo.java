package algorithms;

import dataStructure.*;

import java.io.*;
import java.util.*;

/**
 * This empty class represents the set of graph-theory algorithms
 * which should be implemented as part of Ex2 - Do edit this class.
 * @author
 *
 */
public class Graph_Algo implements graph_algorithms
{
	private graph graph;

	//Default constructor.
	public Graph_Algo()
	{
		;
	}

	//Constructor with a given graph
	public Graph_Algo(graph graph)
	{
		this.graph = graph;
	}

	/**
	 * Init this set of algorithms on the parameter - graph.
	 * @param graph is the given graph
	 */
	public void init(graph graph)
	{
		this.graph = graph;
	}

	/**
	 * copy the graph.
	 * @return the new copied graph.
	 */
	public graph copy()
	{
		DGraph dGraph = new DGraph();
		for (node_data node_data : this.graph.getV())
		{
			dGraph.addNode(node_data);
		}
		for (node_data node_data : this.graph.getV())
		{
			if(!dGraph.getNodes().containsKey(node_data.getKey()))
			{
				dGraph.addNode(node_data);
			}
			for (edge_data e : this.graph.getE(node_data.getKey()))
			{
				dGraph.connect(e.getSrc() , e.getDest() , e.getWeight());
			}
		}
		return dGraph;
	}
	/**
	 * Init a graph from file
	 * @param file_name represents the graph file.
	 */
	public void init(String file_name)
	{
		try
		{
			FileInputStream fileInputStream = new FileInputStream(file_name);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			this.graph = (graph) objectInputStream.readObject();
			objectInputStream.close();
			fileInputStream.close();
		}
		catch(IOException e)
		{
			System.out.println("IOException is caught");
		}
		catch(ClassNotFoundException e)
		{
			System.out.println("ClassNotFoundException is caught");
		}
	}

	/**
	 * Saves the graph to a file.
	 * @param file_name represents the name of the new file.
	 */
	@Override
	public void save(String file_name)
	{
		try
		{
			FileOutputStream fileOutputStream = new FileOutputStream(file_name);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(this.graph);
			objectOutputStream.close();
			fileOutputStream.close();
		}
		catch(IOException e)
		{
			System.out.println("IOException is caught");
		}
	}

	/**
	 * computes a relatively short path which visit each node in the targets List.
	 * Note: this is NOT the classical traveling salesman problem,
	 * as you can visit a node more than once, and there is no need to return to source node -
	 * just a simple path going over all nodes in the list.
	 * @param targets
	 * @return
	 */
	public List<node_data> TSP(List<Integer> targets)
	{
		//set the variables
		int randomNode;
		int fromRandomNode;
		List <node_data> path = new ArrayList<>();;
		double minPath;
		int destination=0;
		int indexDestination = 0;
		double shortestPath;
		List<node_data> helpingList;
		node_data node;

		//checking if has path between the nodes
		if(this.isConnected() == false)
			return null;
		randomNode = (int)(Math.random() * targets.size());
		fromRandomNode = targets.remove(randomNode);
		path.add(this.graph.getNode(fromRandomNode));

		//add to path the node to create the shortest path
		while(0 < targets.size())
		{
			minPath = Double.POSITIVE_INFINITY;
			for(int i = 0; i < targets.size(); i++)
			{
				//minimum algo
				shortestPath = this.shortestPathDist(fromRandomNode, targets.get(i));
				if(minPath > shortestPath)
				{
					minPath = shortestPath;
					indexDestination = i;
					destination = targets.get(i);
				}
			}

			helpingList = this.shortestPath(fromRandomNode , destination);
			for(int i = 1; i <= helpingList.size() - 1; i++)
			{
				node = helpingList.get(i);
				path.add(node);
			}
			targets.remove(indexDestination);
			fromRandomNode = destination;
		}
		return path;
	}
	/**
	 * returns the the shortest path between src to dest - as an ordered List of nodes:
	 * src--> n1-->n2-->...dest
	 * see: https://en.wikipedia.org/wiki/Shortest_path_problem
	 * @param src - start node
	 * @param dest - end (target) node
	 * @return
	 */
	public List<node_data> shortestPath(int src, int dest)
	{
		//checking if the src and dest in the graph
		if(this.graph.getNode(src) == null || this.graph.getNode(dest) == null)
		{
			throw new ArithmeticException("Node isn't in the graph");
		}

		List <node_data> path = new ArrayList <>();

		//if the src and dest equals return the src/dest inside the list
		if(src == dest)
		{
			path.add(this.graph.getNode(src));
			return path;
		}
		//set the variables
		int fatherOfNode;
		List <node_data> returnedList = new ArrayList <>();
		Node node;

		if(Double.POSITIVE_INFINITY > this.shortestPathDist(src,dest))
		{
			node = (Node) this.graph.getNode(dest);
			path.add(node);
			while(!node.getInfo().isEmpty())
			{
				fatherOfNode = Integer.parseInt(node.getInfo());
				path.add(this.graph.getNode(fatherOfNode));
				node = (Node) this.graph.getNode(fatherOfNode);
			}
			Collections.reverse(returnedList);
			return returnedList;
		}
		return null;
	}
	/**
	 * checks if the graph is connected.
	 * @return true if connected, and false if not.
	 */
	public boolean isConnected()
	{
		for (node_data node_data : this.graph.getV())
		{
			for (node_data secondNode_data : this.graph.getV())
			{
				secondNode_data.setWeight(Double.MAX_VALUE);
				secondNode_data.setTag(0);
			}
			if (this.graph.nodeSize() > howMuchConnect(node_data))
			{
				return false;
			}
		}
		return true;
	}
	/**
	 * counts how many nodes is connected.
	 * @return the number of connected nodes.
	 */
	private int howMuchConnect(node_data node_data)
	{
		int connect = 1;
		if (node_data.getTag() == 1)
		{
			return 0;
		}
		node_data.setTag(1);
		for (edge_data edge_data : this.graph.getE(node_data.getKey()))
		{
			connect = connect + howMuchConnect(this.graph.getNode(edge_data.getDest()));
		}
		return connect;
	}


	/**
	 * finds the shortest PathDist.
	 *@param src is the start of the path.
	 *@param dest is the end of the path.
	 *@return the number of nodes that we visited.
	 */
	public double shortestPathDist(int src, int dest)
	{
		dijkstra(src);
		return this.graph.getNode(dest).getWeight();
	}

	/*
	 *https://he.wikipedia.org/wiki/%D7%90%D7%9C%D7%92%D7%95%D7%A8%D7%99%D7%AA%D7%9D_%D7%93%D7%99%D7%99%D7%A7%D7%A1%D7%98%D7%A8%D7%94
	 */
	private void dijkstra (int src)
	{
		node_data node_data;
		double nodeWeight;
		double edgeWeight;
		double fullweight;
		for (node_data secondNode_data : this.graph.getV())
		{
			secondNode_data.setWeight(Double.MAX_VALUE);
			secondNode_data.setTag(0);
		}
		PriorityQueue <node_data> priorityQueue = new PriorityQueue<>();
		this.graph.getNode(src).setWeight(0);
		priorityQueue.add(this.graph.getNode(src));
		while (!priorityQueue.isEmpty())
		{
			node_data = priorityQueue.poll();
			for (edge_data edge_data : this.graph.getE(node_data.getKey()))
			{
				nodeWeight = node_data.getWeight();
				node_data secondNode = this.graph.getNode(edge_data.getDest());
				edgeWeight = edge_data.getWeight();
				if (this.graph.getNode(edge_data.getDest()).getTag() != 1)
				{
					fullweight = edgeWeight + nodeWeight;
					if (fullweight < this.graph.getNode(edge_data.getDest()).getWeight())
					{
						priorityQueue.remove(secondNode);
						secondNode.setWeight(fullweight);
						secondNode.setInfo("" + node_data.getKey());
						priorityQueue.add(secondNode);
					}
				}
			}
		}
	}

	/**
	 * Graph getter.
	 * @return the graph in this class.
	 */
	public graph getG()
	{
		return this.graph;
	}
}




