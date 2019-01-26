package app;

public class VisionObject implements java.io.Serializable
{
    public static final long serialVersionUID = 2637L;

    private double heading;
    private double distance;

    public VisionObject(double heading, double distance)
    {
        this.heading = heading;
        this.distance = distance;
    }

    public double getHeading()
    {
        return this.heading;
    }

    public void setHeading(double heading)
    {
        this.heading = heading;
    }

    public double getDistance()
    {
        return this.distance;
    }

    public void setDistance(double distance)
    {
        this.distance = distance;
    }

    public String toString()
    {
        return "(H: " + heading + "|D: " + distance + ")";
    }
}