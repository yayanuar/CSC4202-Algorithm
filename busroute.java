import java.util.*;

class Edge {
    String destination;
    int weight;
    boolean open;

    public Edge(String destination, int weight, boolean open) {
        this.destination = destination;
        this.weight = weight;
        this.open = open;
    }
}

class Node implements Comparable<Node> {
    String vertex;
    int distance;

    public Node(String vertex, int distance) {
        this.vertex = vertex;
        this.distance = distance;
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.distance, other.distance);
    }
}

public class busroute {

    static Map<String, List<Edge>> graph = new HashMap<>();

    // map station
    static Map<String, String> stationMapping = new HashMap<>();

    static {
        stationMapping.put("A", "KL SENTRAL");
        stationMapping.put("B", "MID VALLEY");
        stationMapping.put("C", "BANGSAR");
        stationMapping.put("D", "CHERAS");
        stationMapping.put("E", "AMPANG");
        stationMapping.put("F", "PETALING JAYA");
        stationMapping.put("G", "SUBANG JAYA");
        stationMapping.put("H", "SHAH ALAM");
        stationMapping.put("I", "KLANG");
        stationMapping.put("J", "PUCHONG");
        stationMapping.put("K", "CYBERJAYA");
        stationMapping.put("L", "PUTRAJAYA");
    }

    public static void addEdge(String sourceKey, String destKey, int weight, boolean open) {

        String source = stationMapping.get(sourceKey.toUpperCase());
        String destination = stationMapping.get(destKey.toUpperCase());

        graph.putIfAbsent(source, new ArrayList<>());
        graph.putIfAbsent(destination, new ArrayList<>());

        graph.get(source).add(new Edge(destination, weight, open));
        graph.get(destination).add(new Edge(source, weight, open));
    }

    // input accept letters / road name
    private static String resolveInput(String input) {

        String cleanInput = input.trim().toUpperCase();

        if (stationMapping.containsKey(cleanInput))
            return stationMapping.get(cleanInput);

        if (stationMapping.containsValue(cleanInput))
            return cleanInput;

        return null;
    }

    public static void dijkstra(String rawStart, String rawEnd) {

        String start = resolveInput(rawStart);
        String end = resolveInput(rawEnd);

        if (start == null || end == null ||
                !graph.containsKey(start) || !graph.containsKey(end)) {

            System.out.println("\n[ERROR] Invalid bus stop entered.");
            return;
        }

        Map<String, Integer> distance = new HashMap<>();
        Map<String, String> previous = new HashMap<>();

        PriorityQueue<Node> pq = new PriorityQueue<>();

        for (String vertex : graph.keySet())
            distance.put(vertex, Integer.MAX_VALUE);

        distance.put(start, 0);

        pq.offer(new Node(start, 0));

        while (!pq.isEmpty()) {

            Node current = pq.poll();

            if (current.distance > distance.get(current.vertex))
                continue;

            for (Edge edge : graph.get(current.vertex)) {

                // skip closed roads
                if (!edge.open)
                    continue;

                int newDistance = distance.get(current.vertex) + edge.weight;

                if (newDistance < distance.get(edge.destination)) {

                    distance.put(edge.destination, newDistance);

                    previous.put(edge.destination, current.vertex);

                    pq.offer(new Node(edge.destination, newDistance));
                }
            }
        }

        if (distance.get(end) == Integer.MAX_VALUE) {

            System.out.println("\n=================================================");
            System.out.println("No available route found.");
            System.out.println("Possible reason:");
            System.out.println("- Road closures");
            System.out.println("- Destination disconnected");
            System.out.println("=================================================");
            return;
        }

        List<String> path = new ArrayList<>();

        String current = end;

        while (current != null) {
            path.add(current);
            current = previous.get(current);
        }

        Collections.reverse(path);

        System.out.println("\n=================================================");
        System.out.println("      RAPID KL BUS ROUTE OPTIMIZATION SYSTEM");
        System.out.println("=================================================");

        System.out.println("\nOptimal Route:");

        for (int i = 0; i < path.size(); i++) {

            System.out.print(path.get(i));

            if (i != path.size() - 1)
                System.out.print("\n->\n");
        }

        System.out.println("\n");

        System.out.println("Total Estimated Travel Time : "
                + distance.get(end) + " minutes");

        System.out.println("=================================================");
    }

    public static void main(String[] args) {

        // open/closed route

        addEdge("A", "B", 8, true);
        addEdge("A", "C", 5, true);
        addEdge("A", "D", 10, true);

        addEdge("D", "E", 4, true);

        addEdge("B", "C", 3, true);

        addEdge("C", "F", 8, true);

        addEdge("F", "J", 8, true);

        addEdge("J", "G", 5, true);

        addEdge("G", "H", 7, true);

        addEdge("J", "K", 8, true);

        addEdge("K", "I", 12, true);

        addEdge("J", "L", 9, false);

        addEdge("B", "F", 12, false);

        addEdge("F", "G", 6, false);

        Scanner scanner = new Scanner(System.in);

        System.out.println("=================================================");
        System.out.println("        KUALA LUMPUR BUS NETWORK");
        System.out.println("=================================================\n");

        System.out.println("Available Bus Stops:\n");

        for (Map.Entry<String, String> entry :
                new TreeMap<>(stationMapping).entrySet()) {

            System.out.printf("%s : %s%n",
                    entry.getKey(),
                    entry.getValue());
        }

        System.out.println();

        System.out.print("Enter Source (Letter or Name): ");
        String source = scanner.nextLine();

        System.out.print("Enter Destination (Letter or Name): ");
        String destination = scanner.nextLine();

        dijkstra(source, destination);

        scanner.close();
    }
}