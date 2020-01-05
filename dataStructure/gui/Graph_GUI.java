package gui;

import dataStructure.*;
import dataStructure.DGraph;

import dataStructure.edge_data;
import dataStructure.node_data;
import utils.Point3D;
import utils.Range;
import utils.StdDraw;

import java.awt.*;
import java.io.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;


public class Graph_GUI implements Runnable 
{
	private static graph g;
	private static int mc = 0;
	/**
	 * saves updated graph to a file.
	 * @param filename
	 * @throws IOException
	 */
	public static void save(String filename) throws IOException 
	{
		FileOutputStream output = new FileOutputStream(filename + ".ser");
		ObjectOutputStream objoutput = new ObjectOutputStream(output);
		if(g == null)
		{
			return;
		}else 
		{
			objoutput.writeObject(g);
			objoutput.close();
			output.close();
		}
	}

	/**
	 * draws and loads from file.
	 * @param filename
	 * @throws Exception
	 */
	public static void load(String filename)throws Exception 
	{
		FileInputStream streamInput = new FileInputStream(filename);
		ObjectInputStream objinput = new ObjectInputStream(streamInput);
		DGraph r = (DGraph) objinput.readObject();
		StdDraw.clear();
		draw(r);
		g = r;
		streamInput.close();
		objinput.close();
	}

	public static graph getLastGraph() 
	{
		return g;
	}

	public static void setLastGraph(graph graph) 
	{
		g = (DGraph) graph;
	}
	//set the range.
	private static void setRange(graph graph)
	{
		Collection<node_data> nodes = graph.getV();
		Iterator<node_data> nodeIterator = nodes.iterator();
		double srcx = 0;
		double srcy = 0;
		double destx = 0;
		double desty = 0;

		if(nodeIterator.hasNext())
		{
			Point3D p = nodeIterator.next().getLocation();
			srcx = p.x();
			srcy = p.y();
			destx = p.x();
			desty = p.y();
		}
		else 
		{
			return;
		}
		while (nodeIterator.hasNext()) 
		{
			Point3D p = nodeIterator.next().getLocation();
			if(p.x() < srcx)
			{
				srcx = p.x();
			}
			else if(p.x() > destx)
			{
				destx = p.x();
			}
			if(p.y() < srcy)
			{
				srcy = p.y();
			}
			else if(p.y() > desty)
			{
				desty = p.y();
			}
		}
		Range rangex = new Range(srcx - 20, destx + 20);
		Range rangey = new Range(srcy - 20, desty + 20);
		StdDraw.setXscale(rangex.get_min(), rangex.get_max());
		StdDraw.setYscale(rangey.get_min(), rangey.get_max());
	}
	/**
	 * draws the graph.
	 * @param graph
	 */
	public static void draw(graph graph)
	{
		Graph_GUI.mc = g.getMC();
		StdDraw.setCanvasSize(750, 750);
		setRange(graph);
		
		Collection<node_data> nodeData = graph.getV();
		Iterator<node_data> nodeIterator = nodeData.iterator();
		
		StdDraw.setPenColor(Color.BLACK);
		StdDraw.setPenRadius(0.05);
		
		while (nodeIterator.hasNext())
		{
			node_data nd = nodeIterator.next();
			Point3D p = nd.getLocation();
			StdDraw.point(p.x(),p.y());
			String s = nd.getKey() +"";
			StdDraw.text(p.x(),p.y()+5,s);
		}
		
		StdDraw.setPenRadius(0.01);
		nodeIterator = nodeData.iterator();
		
		while (nodeIterator.hasNext())
		{
			node_data nd =nodeIterator.next();
			Iterator<edge_data> edgeIterator = graph.getE(nd.getKey()).iterator();
			while (edgeIterator.hasNext())
			{
				edge_data edgeData = edgeIterator.next();
				Point3D src = nd.getLocation();
				Point3D dest = graph.getNode(edgeData.getDest()).getLocation();
				double w = edgeData.getWeight() ;

				StdDraw.setPenColor(Color.RED);
				StdDraw.line(src.x(), src.y(),dest.x(),dest.y());
				StdDraw.setPenColor(Color.RED);
				StdDraw.square(src.x()+(dest.x()-src.x())*0.85,src.y()+(dest.y()-src.y())*0.85,1);
				StdDraw.text((dest.x()+src.x())/2,(dest.y()+src.y())/2,String.format("%.5f", w));
			}
		}
		g = graph;
	}

	@Override
	public void run() 
	{
		draw(g);
		while (true) 
		{
			synchronized (g) 
			{
				if (mc < g.getMC()) 
				{
					StdDraw.clear();
					draw(g);
				} else 
				{
					try 
					{
						Thread.sleep(750);
					} catch (Exception e) 
					{
						e.printStackTrace();
					}
				}
			}
		}
	}
	public static void main(String[] args) {
        Random random = new Random();
        DGraph graph = new DGraph();
        long startTime = System.nanoTime();
        int nodes = 7;
        for (int i = 0; i <nodes ; i++) {
            node_data n= new Node(i,new Point3D(-100 + random.nextDouble()*100,-100 + random.nextDouble()*100),1 + random.nextDouble()*10);
            graph.addNode(n);
        }
        for (int i = 0; i < 13; i++) {
            int src =  random.nextInt(nodes);
            int dest =  random.nextInt(nodes);
            while (dest==src)
            {
                src =  random.nextInt(nodes);
                dest =  random.nextInt(nodes);
            }
            graph.connect(src, dest,1 + random.nextDouble()*10);
        }
        long endTime = System.nanoTime();
        double runtime =(double) endTime - startTime;
        System.out.println(runtime/1000000000.0);//takes about 1.5 seconds for 1000000 nodes and 10000000 edges
        Graph_GUI r = new Graph_GUI();
        Graph_GUI.setLastGraph(graph);
        Thread t1 = new Thread(r);
        t1.start();
        graph.addNode(new Node(100, new Point3D(0, 0), 0));
        //Graph_Algo ga = new Graph_Algo(graph);
        //ga.shortestPath(1,5);
        //System.out.println(ga.isConnected());

    }
}




