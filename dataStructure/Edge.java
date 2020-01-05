package dataStructure;

public class Edge implements edge_data
{
    private int source;
    private int destination;
    private double weight;
    private String info;
    private int tag;

    //default constructor
    public Edge()
    {
        this.source = 0;
        this.destination = 0;
        this.weight = 0;
        this.info = "";
        this.tag = 0;
    }

    //constructor without tag and info
    public Edge(int source , int destination , double weight)
    {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
        this.info = "";
        this.tag = 0;
    }

    //full constructor
    public Edge(int source , int destination , double weight , String info , int tag)
    {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
        this.info = info;
        this.tag = tag;
    }

    //copy constructor
    public Edge(Edge edge)
    {
        this.source = edge.source;
        this.destination = edge.destination;
        this.weight = edge.weight;
        this.info = edge.info;
        this.tag = edge.tag;
    }

    //getters and setters method
    public int getSource()
    {
        return this.source;
    }

    public void setSource(int source)
    {
        this.source = source;
    }

    public int getDestination()
    {
        return this.destination;
    }

    public void setDestination(int destination)
    {
        this.destination = destination;
    }

    public double getWeight()
    {
        return this.weight;
    }

    public void setWeight(double weight)
    {
        this.weight = weight;
    }

    public int getTag()
    {
        return this.tag;
    }

    public void setTag(int tag)
    {
        this.tag = tag;
    }

    public String getInfo()
    {
        return this.info;
    }

    public void setInfo(String info)
    {
        this.info = info;
    }

    //equals for the Junit
    public boolean equals(Edge edge)
    {
        if (this.destination == edge.destination && this.weight == edge.weight && this.info.equals(edge.info)
                && this.tag == edge.tag && this.source == edge.source)
        {
            return true;
        }
        return false;
    }

    //toString
    public String toString()
    {
        return "source = " + this.source + " , destination = " + this.destination +
        " , weight = " + this.weight + " , tag = " + this.tag + " , info = " + this.info;
    }
}