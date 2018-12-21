public class Node implements Comparable {

    boolean wasInitialized;
    private String intersectionID;
    private double longitude;
    private double latitude;
    private boolean chosen;

    public double getShortestDistance() {
        return shortestDistance;
    }

    public void setShortestDistance(double shortestDistance) {
        this.shortestDistance = shortestDistance;
    }

    private double shortestDistance;


    private Node parent;

    public Node() {
        wasInitialized = false;
    }

    public Node(String id, double lon, double lat) {
        wasInitialized = true;
        intersectionID = id;
        longitude = lon;
        latitude = lat;
    }

    public void setLong(double l) { longitude = l; }
    public void setLat(double l) { latitude = l; }
    public void setIntersectionID(String s) { intersectionID = s; }

    public double getLong() { return longitude; }
    public double getLat() { return latitude; }
    public String getID() { return intersectionID; }
    public boolean initialized() {return wasInitialized; }
    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public boolean isChosen() {
        return chosen;
    }

    public void setChosen(boolean chosen) {
        this.chosen = chosen;
    }


    @Override
    public int compareTo(Object o) {
        Node other = (Node) o;
        if (this.shortestDistance < other.getShortestDistance())
            return -1;
        else if (this.shortestDistance == other.getShortestDistance()) return 0;
        else return 1;
    }

    @Override
    public boolean equals(Object obj) {
        Node other = (Node) obj;
        if (this.getID().equals(other.getID()))
            return true;
        else
            return false;
    }
}