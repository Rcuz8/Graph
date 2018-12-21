public class Edge {

    String roadId;

    Node from;

    Node to;

    double weight;

    boolean wasInitialized;

    public Edge(){
        wasInitialized = false;
    }

    public Edge(String _roadId, Node from, Node to) {
        this.roadId = _roadId;


        this.from = from;
        this.to = to;

        double x1 = from.getLat();
        double y1 = from.getLong();

        double x2 = to.getLat();
        double y2 = to.getLong();

        double w = Math.sqrt(Math.abs(Math.pow(y2-y1,2) + Math.pow(x2-x1,2)));
        weight = Math.abs(w);
        wasInitialized = true;
    }

    public String getRoadId() {
        return roadId;
    }

    public void setRoadId(String roadId) {
        this.roadId = roadId;
    }

    public Node getFrom() {
        return from;
    }

    public void setFrom(Node from) {
        this.from = from;
    }

    public Node getTo() {
        return to;
    }

    public void setTo(Node to) {
        this.to = to;
    }

    public double getWeight() {
        return weight;
    }

    public boolean initialized() {return wasInitialized; }


}