package dataStructure;

import utils.Point3D;

public class Node implements node_data
{
    private int key;
    private Point3D location;
    private double weight;
    private String info;
    private int tag;

    //default constructor
    public Node()
    {
        this.key = 0;
        this.location = null;
        this.weight = 0;
        this.info = "";
        this.tag = 0;
    }
    //constructor without info and tag
    public Node(int key , Point3D p , double weight)
    {
        this.key = key;
        this.location = new Point3D(p);
        this.weight = weight;
        this.info = "";
        this.tag = 0;
    }

    //full constructor
    public Node(int key , Point3D location , double weight , String info , int tag)
    {
        this.key = key;
        this.location = location;
        this.weight = weight;
        this.info = info;
        this.tag = tag;
    }

    //copy constructor
    public Node (Node node)
    {
        this.key = node.key;
        this.location = node.location;
        this.weight = node.weight;
        this.info = node.info;
        this.tag = node.tag;
    }

    //getters and setters method

    public int getKey()
    {
        return this.key;
    }

    public void setKey(int key)
    {
        this.key = key;
    }

    public Point3D getLocation()
    {
        return this.location;
    }

    public void setLocation(Point3D p)
    {
        this.location = new Point3D(p);
    }

    public double getWeight()
    {
        return this.weight;
    }

    public void setWeight(double weight)
    {
        this.weight = weight;
    }

    public String getInfo()
    {
        return this.info;
    }

    public void setInfo(String info)
    {
        this.info = info;
    }

    public int getTag()
    {
        return this.tag;
    }

    public void setTag(int tag)
    {
        this.tag = tag;
    }

    //equals for the Junit
    public boolean equals(Node node)
    {
        if (this.location.equals(node.location) && this.weight == node.weight && this.info.equals(node.info)
                && this.tag == node.tag && this.key == node.key)
        {
            return true;
        }
        return false;
    }

    //toString
    public String toString()
    {
        return "key = " + this.key + " , location = " + this.location.toString() +
                " , weight = " + this.weight + " , tag = " + this.tag + " , info = " + this.info;
    }
}
