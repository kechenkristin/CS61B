import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class provides a shortestPath method for finding routes between two points
 * on the map. Start by using Dijkstra's, and if your code isn't fast enough for your
 * satisfaction (or the autograder), upgrade your implementation by switching it to A*.
 * Your code will probably not be fast enough to pass the autograder unless you use A*.
 * The difference between A* and Dijkstra's is only a couple of lines of code, and boils
 * down to the priority you use to order your vertices.
 */
public class Router {

    private static GraphDB.Node start;
    private static GraphDB.Node dest;
    private static GraphDB graph;
    private static PriorityQueue<SearchNode> fringe;
    private static HashMap<Long, Boolean> marked;


    private static class SearchNode implements Comparable<SearchNode> {
        private SearchNode parent;
        private double priority;
        private Long id;
        private double distanceToStart;


        public SearchNode(Long id, SearchNode parent, double distanceToStart) {
            this.id = id;
            this.parent = parent;
            this.distanceToStart = distanceToStart;
            priority = calPriority();
        }


        /**
         * calculate the distance to start  destination point
         */
        private double calDistanceToDest() {
            return graph.distance(this.id, dest.getId());
        }

        private double calPriority() {
            return distanceToStart + calDistanceToDest();
        }

        private boolean isGoal() {
            return calDistanceToDest() == 0;
        }

        @Override
        public int compareTo(SearchNode o) {
            if ((this.priority - o.priority) < 0) {
                return -1;
            } else if ((this.priority - o.priority) > 0) {
                return 1;
            }
            return 0;
        }
    }

    private static void setStartDest(double stlon, double stlat, double destlon, double destlat) {
        Long startId = graph.closest(stlon, stlat);
        start = graph.nodes.get(startId);
        Long destId = graph.closest(destlon, destlat);
        dest = graph.nodes.get(destId);
    }


    private static void AStarPath() {
        fringe.add(new SearchNode(start.getId(), null, 0));
        while (!fringe.isEmpty() && !fringe.peek().isGoal()) {
            SearchNode v = fringe.poll();
            assert v != null;
            marked.put(v.id, true);
            for (Long w : graph.adjNode.get(v.id)) {
                if (!marked.containsKey(w) || !marked.get(w)) {
                    fringe.offer(new SearchNode(w, v, v.distanceToStart + graph.distance(w, v.id)));
                }
            }
        }
    }

    private static ArrayList<Long> findPath() {
        SearchNode n = fringe.peek();
        ArrayList<Long> path = new ArrayList<>();
        while (n != null) {
            path.add(n.id);
            n = n.parent;
        }
        Collections.reverse(path);
        return path;
    }


    /**
     * Return a List of longs representing the shortest path from the node
     * closest to a start location and the node closest to the destination
     * location.
     *
     * @param g       The graph to use.
     * @param stlon   The longitude of the start location.
     * @param stlat   The latitude of the start location.
     * @param destlon The longitude of the destination location.
     * @param destlat The latitude of the destination location.
     * @return A list of node id's in the order visited on the shortest path.
     */
    public static List<Long> shortestPath(GraphDB g, double stlon, double stlat,
                                          double destlon, double destlat) {
        graph = g;
        setStartDest(stlon, stlat, destlon, destlat);
        fringe = new PriorityQueue<>();
        marked = new HashMap<>();
        AStarPath();
        return findPath();
    }


    /**
     * return a list of edges of the routes
     */
    private static List<GraphDB.Edge> getEdges(List<Long> routes) {
        List<GraphDB.Edge> edges = new ArrayList<>();
        for (int i = 1; i < routes.size(); i++) {
            Long pre = routes.get(i - 1);
            Long cur = routes.get(i);

            for (GraphDB.Edge edge : graph.adjEdge.get(pre)) {
                if (edge.other(pre).equals(cur)) {
                    edges.add(edge);
                }
            }
        }
        return edges;
    }

    private static int relativeBearing(double preBearing, double curBearing) {
        double reBear = curBearing - preBearing;
        double abBear = Math.abs(preBearing - curBearing);

        if (abBear > 180) {
            abBear = 360 - abBear;
            reBear *= -1;
        }

        if (abBear <= 15) {
            return NavigationDirection.STRAIGHT;
        }
        if (abBear <= 30) {
            return reBear < 0 ? NavigationDirection.SLIGHT_LEFT : NavigationDirection.SLIGHT_RIGHT;
        }
        if (abBear <= 100) {
            return reBear < 0 ? NavigationDirection.LEFT : NavigationDirection.RIGHT;
        } else {
            return reBear < 0 ? NavigationDirection.SHARP_LEFT : NavigationDirection.SHARP_RIGHT;
        }
    }

    /**
     * Create the list of directions corresponding to a route on the graph.
     *
     * @param g     The graph to use.
     * @param route The route to translate into directions. Each element
     *              corresponds to a node from the graph in the route.
     * @return A list of NavigatiionDirection objects corresponding to the input
     * route.
     */
    public static List<NavigationDirection> routeDirections(GraphDB g, List<Long> route) {
        graph = g;
        List<GraphDB.Edge> edges = getEdges(route);
        int relativeDirection = NavigationDirection.START;
        double distance = 0;
        List<NavigationDirection> ret = new ArrayList<>();

        if (edges.size() == 1) {
            NavigationDirection nav = new NavigationDirection(NavigationDirection.START,
                    edges.get(0).getName(), edges.get(0).getWeight());
            ret.add(nav);
            return ret;
        }

        for (int i = 1; i < edges.size(); i++) {
            GraphDB.Edge preEdge = edges.get(i - 1);
            GraphDB.Edge curEdge = edges.get(i);

            String preEdgeName = preEdge.getName();
            String curEdgeName = curEdge.getName();

            distance += preEdge.getWeight();

            if (!preEdgeName.equals(curEdgeName)) {
                Long preNode = route.get(i - 1);
                Long curNode = route.get(i);
                Long nextNode = route.get(i + 1);

                double preBear = g.bearing(preNode, curNode);
                double curBear = g.bearing(curNode, nextNode);
                NavigationDirection nav = new NavigationDirection(relativeDirection, preEdgeName, distance);
                ret.add(nav);

                distance = 0;
                relativeDirection = relativeBearing(preBear, curBear);
            }

            if (i == edges.size() - 1) {
                distance += curEdge.getWeight();
                NavigationDirection nav = new NavigationDirection(relativeDirection, curEdgeName, distance);
                ret.add(nav);
            }
        }
        return ret;
    }


    /**
     * Class to represent a navigation direction, which consists of 3 attributes:
     * a direction to go, a way, and the distance to travel for.
     */
    public static class NavigationDirection {

        /**
         * Integer constants representing directions.
         */
        public static final int START = 0;
        public static final int STRAIGHT = 1;
        public static final int SLIGHT_LEFT = 2;
        public static final int SLIGHT_RIGHT = 3;
        public static final int RIGHT = 4;
        public static final int LEFT = 5;
        public static final int SHARP_LEFT = 6;
        public static final int SHARP_RIGHT = 7;

        /**
         * Number of directions supported.
         */
        public static final int NUM_DIRECTIONS = 8;

        /**
         * A mapping of integer values to directions.
         */
        public static final String[] DIRECTIONS = new String[NUM_DIRECTIONS];

        /**
         * Default name for an unknown way.
         */
        public static final String UNKNOWN_ROAD = "unknown road";

        /** Static initializer. */
        static {
            DIRECTIONS[START] = "Start";
            DIRECTIONS[STRAIGHT] = "Go straight";
            DIRECTIONS[SLIGHT_LEFT] = "Slight left";
            DIRECTIONS[SLIGHT_RIGHT] = "Slight right";
            DIRECTIONS[LEFT] = "Turn left";
            DIRECTIONS[RIGHT] = "Turn right";
            DIRECTIONS[SHARP_LEFT] = "Sharp left";
            DIRECTIONS[SHARP_RIGHT] = "Sharp right";
        }

        /**
         * The direction a given NavigationDirection represents.
         */
        int direction;
        /**
         * The name of the way I represent.
         */
        String way;
        /**
         * The distance along this way I represent.
         */
        double distance;

        /**
         * Create a default, anonymous NavigationDirection.
         */
        public NavigationDirection() {
            this.direction = STRAIGHT;
            this.way = UNKNOWN_ROAD;
            this.distance = 0.0;
        }

        public NavigationDirection(int direction, String way, double distance) {
            this.direction = direction;
            this.way = way;
            this.distance = distance;
        }

        public String toString() {
            return String.format("%s on %s and continue for %.3f miles.",
                    DIRECTIONS[direction], way, distance);
        }

        /**
         * Takes the string representation of a navigation direction and converts it into
         * a Navigation Direction object.
         *
         * @param dirAsString The string representation of the NavigationDirection.
         * @return A NavigationDirection object representing the input string.
         */
        public static NavigationDirection fromString(String dirAsString) {
            String regex = "([a-zA-Z\\s]+) on ([\\w\\s]*) and continue for ([0-9\\.]+) miles\\.";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(dirAsString);
            NavigationDirection nd = new NavigationDirection();
            if (m.matches()) {
                String direction = m.group(1);
                if (direction.equals("Start")) {
                    nd.direction = NavigationDirection.START;
                } else if (direction.equals("Go straight")) {
                    nd.direction = NavigationDirection.STRAIGHT;
                } else if (direction.equals("Slight left")) {
                    nd.direction = NavigationDirection.SLIGHT_LEFT;
                } else if (direction.equals("Slight right")) {
                    nd.direction = NavigationDirection.SLIGHT_RIGHT;
                } else if (direction.equals("Turn right")) {
                    nd.direction = NavigationDirection.RIGHT;
                } else if (direction.equals("Turn left")) {
                    nd.direction = NavigationDirection.LEFT;
                } else if (direction.equals("Sharp left")) {
                    nd.direction = NavigationDirection.SHARP_LEFT;
                } else if (direction.equals("Sharp right")) {
                    nd.direction = NavigationDirection.SHARP_RIGHT;
                } else {
                    return null;
                }

                nd.way = m.group(2);
                try {
                    nd.distance = Double.parseDouble(m.group(3));
                } catch (NumberFormatException e) {
                    return null;
                }
                return nd;
            } else {
                // not a valid nd
                return null;
            }
        }


        @Override
        public boolean equals(Object o) {
            if (o instanceof NavigationDirection) {
                return direction == ((NavigationDirection) o).direction
                        && way.equals(((NavigationDirection) o).way)
                        && distance == ((NavigationDirection) o).distance;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(direction, way, distance);
        }
    }


}
