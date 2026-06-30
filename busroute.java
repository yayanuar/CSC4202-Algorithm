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
    static Map<String, String> stationMapping = new HashMap<>();

    static {
        stationMapping.put("A", "BUTTERWORTH");
        stationMapping.put("B", "IPOH");
        stationMapping.put("C", "KUALA LUMPUR (TBS)");
        stationMapping.put("D", "ALOR SETAR");
        stationMapping.put("E", "KANGAR");
        stationMapping.put("F", "SEREMBAN");
        stationMapping.put("G", "MELAKA SENTRAL");
        stationMapping.put("H", "JOHOR BAHRU");
        stationMapping.put("I", "KOTA BHARU");
        stationMapping.put("J", "KUANTAN");
        stationMapping.put("K", "KUALA TERENGGANU");
        stationMapping.put("L", "TEMERLOH");
    }

    public static void addEdge(String sourceKey, String destKey, int weight, boolean open) {
        String source = stationMapping.get(sourceKey.toUpperCase());
        String destination = stationMapping.get(destKey.toUpperCase());

        graph.putIfAbsent(source, new ArrayList<>());
        graph.putIfAbsent(destination, new ArrayList<>());

        graph.get(source).add(new Edge(destination, weight, open));
        graph.get(destination).add(new Edge(source, weight, open));
    }

    private static String resolveInput(String input) {
        String cleanInput = input.trim().toUpperCase();

        if (stationMapping.containsKey(cleanInput)) {
            return stationMapping.get(cleanInput);
        }

        for (String station : stationMapping.values()) {
            if (station.equalsIgnoreCase(input.trim())) {
                return station;
            }
    }

    return null;
}

    public static void dijkstra(String rawStart, String rawEnd) {

        String start = resolveInput(rawStart);
        String end = resolveInput(rawEnd);

        if (start == null || end == null ||
                !graph.containsKey(start) ||
                !graph.containsKey(end)) {

            System.out.println("\n[ERROR] Invalid bus station.");
            return;
        }

        Map<String, Integer> distance = new HashMap<>();
        Map<String, String> previous = new HashMap<>();

        PriorityQueue<Node> pq = new PriorityQueue<>();

        for (String vertex : graph.keySet()) {
            distance.put(vertex, Integer.MAX_VALUE);
        }

        distance.put(start, 0);
        pq.offer(new Node(start, 0));

        while (!pq.isEmpty()) {

            Node current = pq.poll();

            if (current.distance > distance.get(current.vertex))
                continue;

            for (Edge edge : graph.get(current.vertex)) {

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
            System.out.println("\n===============================================");
            System.out.println("No available route found.");
            System.out.println();
            System.out.println("Possible reason:");
            System.out.println("- Road closure due to maintenance");
            System.out.println("- Flood or accident");
            System.out.println("- Destination temporarily inaccessible");
            System.out.println("===============================================");
            return;
        }

        List<String> path = new ArrayList<>();
        String current = end;

        while (current != null) {
            path.add(current);
            current = previous.get(current);
        }

        Collections.reverse(path);

        System.out.println("\n===============================================");
        System.out.println(" MALAYSIA INTERCITY BUS ROUTE OPTIMIZATION");
        System.out.println("===============================================");

        System.out.println("\nOptimal Route:\n");

        for (int i = 0; i < path.size(); i++) {

            System.out.print(path.get(i));

            if (i != path.size() - 1)
                System.out.print(" -> ");
        }

        System.out.println();

        System.out.println("\nTotal Estimated Travel Time : "
                + distance.get(end) + " hours");

        System.out.println("===============================================");
    }

        public static void main(String[] args) {
     
        addEdge("A", "B", 2, true);      // Butterworth <-> Ipoh
        addEdge("A", "C", 4, true);      // Butterworth <-> Kuala Lumpur (TBS)
        addEdge("A", "D", 1, true);      // Butterworth <-> Alor Setar
        addEdge("B", "E", 3, true);      // Ipoh <-> Kangar
        addEdge("C", "F", 1, true);      // Kuala Lumpur (TBS) <-> Seremban
        addEdge("D", "E", 1, true);      // Alor Setar <-> Kangar
        addEdge("G", "H", 3, true);      // Melaka Sentral <-> Johor Bahru
        addEdge("H", "I", 9, true);      // Johor Bahru <-> Kota Bharu
        addEdge("B", "J", 4, true);      // Ipoh <-> Kuantan
        addEdge("J", "F", 5, true);      //Kuantan <-> Seremban
        addEdge("J", "K", 3, true);      // Kuantan <-> Kuala Terengganu
        addEdge("J", "G", 3, true);      // Kuantan <-> Melaka Sentral
        addEdge("J", "L", 2, false);     // Kuantan <-> Temerloh (Closed)
        addEdge("K", "I", 3, true);      // Kuala Terengganu <-> Kota Bharu
        addEdge("B", "F", 3, false);     // Ipoh <-> Seremban (Closed)
        addEdge("F", "G", 2, false);     // Seremban <-> Melaka Sentral (Closed)

        Scanner scanner = new Scanner(System.in);

        System.out.println("======================================================");
        System.out.println("     MALAYSIA INTERCITY BUS ROUTE OPTIMIZATION");
        System.out.println("======================================================");

        System.out.println("\nAvailable Bus Stations:\n");

        for (Map.Entry<String, String> entry :
                new TreeMap<>(stationMapping).entrySet()) {

            System.out.printf("%s : %s%n", entry.getKey(), entry.getValue());
        }

        System.out.println();

        System.out.print("Enter Source (Letter or Full Station Name): ");
        String source = scanner.nextLine();

        System.out.print("Enter Destination (Letter or Full Station Name): ");
        String destination = scanner.nextLine();

        dijkstra(source, destination);

        scanner.close();
    }
}

