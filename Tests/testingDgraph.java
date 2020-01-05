package Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dataStructure.DGraph;
import dataStructure.Edge;
import dataStructure.Node;
import dataStructure.edge_data;
import dataStructure.node_data;
import utils.Point3D;

public class testingDgraph 
{
	private DGraph g;
	private Point3D p1,p2,p3;
	private node_data n1,n2,n3;

	@BeforeEach
	void setupGraphs() 
	{

		g = new DGraph();

		p1 = new Point3D(-2,-2,2);
		p2 = new Point3D(-1,1,2);
		p3 = new Point3D(0,0,0);

		n1 = new Node(1,p1,0,null,0);
		n2 = new Node(2,p2,0,null,0);
		n3 = new Node(3,p3,0,null,0);

		g.addNode(n1);
		g.addNode(n2);
		g.addNode(n3);

		g.connect(n1.getKey(), n2.getKey(), 4);
		g.connect(n1.getKey(), n3.getKey(), 3);
	}
	/**
	 * Testing the function getNode(key).
	 */
	@Test
	void testGetNode() 
	{
		int key1 = n1.getKey();
		assertEquals("Check the node",g.getNode(key1),n1);
	}
	/**
	 * Testing the function getEdge(src,dest).
	 */
	//	@Test
	//	void testGetEdge() 
	//	{
	//		edge_data edge = new Edge (n1.getKey(),n3.getKey(),3);
	//		assertEquals("Check the edge",g.getEdge(n1.getKey(),n3.getKey()),edge);
	//	}
	/**
	 * Testing the function addNode(node_data).
	 */
	@Test
	void testAddNode()
	{
		assertEquals(g.getNode(n1.getKey()),n1);
	}

	/**
	 * Testing the function connect(src,dest,weight).
	 */
	@Test
	void testConnect()
	{
		{
			assertEquals(g.getEdge(n1.getKey(),n2.getKey()).getSrc(),n1.getKey());
			assertEquals(g.getEdge(n1.getKey(),n2.getKey()).getDest(),n2.getKey());	
		}
	}

	/**
	 * Testing the function getV().
	 */
	@Test
	void testGetV()
	{
		{
			Collection<node_data> excepted= new ArrayList<>();
			excepted.add(n1);
			excepted.add(n2);
			excepted.add(n3);
			Collection<node_data> actual=g.getV();
			assertEquals(excepted.toString(),actual.toString());
		}
	}

	/**
	 * Testing the function getE().
	 */
	@Test
	void testGetE() 
	{
		{
			Collection<edge_data> excepted= new ArrayList<>();
			excepted.add(new Edge (n1.getKey(), n2.getKey(), 4));
			excepted.add(new Edge (n1.getKey(), n3.getKey(), 3));
			Collection<edge_data> actual=g.getE(n1.getKey());
			assertEquals(excepted.toString(),actual.toString());
		}
	}
	/**
	 * Testing the function removeNode(key).
	 */
	@Test
	void testremoveNode() 
	{
		n1=g.removeNode(n1.getKey());
		n2=g.removeNode(n2.getKey());
		assertEquals("should be 1 node remaining",g.nodeSize(),1);

	}

	/**
	 * Testing the function removeEdge(src,dest).
	 */
	@Test
	void testremoveEdge() 
	{
		g.removeEdge(n1.getKey(), n3.getKey());
		assertEquals("should be 1 edge remaining",g.edgeSize(),1);
	}

	/**
	 * Testing the function nodeSize().
	 */
	@Test
	void testNodeSize() 
	{
		assertEquals(3,g.nodeSize());
		g.removeNode(n1.getKey());
		assertEquals(2,g.nodeSize());
	}

	/**
	 * Testing the function edgeSize().
	 */
	@Test
	void testEdegSize() 
	{
		assertEquals(2,g.edgeSize());
		g.removeEdge(n1.getKey(), n2.getKey());
		assertEquals("should be 1 edge remaining",1,g.edgeSize());
	}

	/**
	 * Testing the function getMC().
	 */
	@Test
	void testGetMC() 
	{
		int current_mc=g.getMC();
		g.removeEdge(n1.getKey(), n2.getKey());
		assertEquals(current_mc+1,g.getMC());
		current_mc++;
		g.connect(n1.getKey(), n2.getKey(),2);
		assertEquals(current_mc+1,g.getMC());
	}
}
