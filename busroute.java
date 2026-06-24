import java.util.*;

class Edge {
    String destination;
    int weight; // Travel time in minutes

    public Edge(String destination, int weight) {
        this.destination = destination;
        this.weight = weight;
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
    
    // Quick reference map to let users type either the letter key or the full name
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

    public static void addEdge(String sourceKey, String destKey, int weight) {
        String source = stationMapping.get(sourceKey.toUpperCase());
        String destination = stationMapping.get(destKey.toUpperCase());

        graph.putIfAbsent(source, new ArrayList<>());
        graph.putIfAbsent(destination, new ArrayList<>());

        graph.get(source).add(new Edge(destination, weight));
        graph.get(destination).add(new Edge(source, weight));
    }

    // Resolves mixed inputs safely (handles "A", "a", or "KL Sentral")
    private static String resolveInput(String input) {
        String cleanInput = input.trim().toUpperCase();
        if (stationMapping.containsKey(cleanInput)) {
            return stationMapping.get(cleanInput);
        }
        if (stationMapping.containsValue(cleanInput)) {
            return cleanInput;
        }
        return null;
    }

    public static void dijkstra(String rawStart, String rawEnd) {
        String start = resolveInput(rawStart);
        String end = resolveInput(rawEnd);

        if (start == null || end == null || !graph.containsKey(start) || !graph.containsKey(end)) {
            System.out.println("\n[Error]: One or both bus stops do not exist in the network database.");
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
            String currentVertex = current.vertex;

            if (current.distance > distance.get(currentVertex)) continue;

            for (Edge edge : graph.get(currentVertex)) {
                int newDistance = distance.get(currentVertex) + edge.weight;

                if (newDistance < distance.get(edge.destination)) {
                    distance.put(edge.destination, newDistance);
                    previous.put(edge.destination, currentVertex);
                    pq.offer(new Node(edge.destination, newDistance));
                }
            }
        }

        if (distance.get(end) == Integer.MAX_VALUE) {
            System.out.println("\nNo path exists between " + start + " and " + end + ".");
            System.out.println("Status: Sector inaccessible due to severe transit disruptions / road closures.");
            return;
        }

        List<String> path = new ArrayList<>();
        String current = end;

        while (current != null) {
            path.add(current);
            current = previous.get(current);
        }

        Collections.reverse(path);

        System.out.println("\n========================================================");
        System.out.println("            RAPID KL OPTIMIZED ROUTE ANALYSIS           ");
        System.out.println("========================================================");
        
        for (int i = 0; i < path.size(); i++) {
            System.out.print(path.get(i));
            if (i != path.size() - 1) System.out.print(" -> \n");
        }
        System.out.println("\n_________________________________________________\n");
        System.out.println("Total Estimated Travel Time: " + distance.get(end) + " mins");
        System.out.println("========================================================");
    }

    public static void main(String[] args) {
        // Map Grid Layout Assignments using the station keys
        addEdge("A", "B", 4);  // KL Sentral to Mid Valley
        addEdge("A", "C", 2);  // KL Sentral to Bangsar
        addEdge("A", "D", 5);  // KL Sentral to Cheras
        addEdge("B", "E", 10); // Mid Valley to Ampang
        addEdge("C", "D", 8);  // Bangsar to Cheras
        addEdge("C", "F", 3);  // Bangsar to Petaling Jaya (PJ)
        addEdge("D", "E", 2);  // Cheras to Ampang
        addEdge("D", "F", 6);  // Cheras to Petaling Jaya (PJ)
        addEdge("F", "G", 7);  // Petaling Jaya (PJ) to Subang Jaya
        addEdge("G", "H", 5);  // Subang Jaya to Shah Alam
        addEdge("H", "I", 12); // Shah Alam to Klang
        addEdge("B", "J", 9);  // Mid Valley to Puchong
        addEdge("J", "K", 15); // Puchong to Cyberjaya
        addEdge("E", "K", 20); // Ampang to Cyberjaya

        // Putrajaya (L) is initialized in the system but isolated due to the scenario constraints
        String putrajayaName = stationMapping.get("L");
        graph.put(putrajayaName, new ArrayList<>()); 

        Scanner sc = new Scanner(System.in);

        System.out.println("_________________________________________________\n");
        System.out.println("Available Bus Stops across the Regional Network: ");
        System.out.println("_________________________________________________\n");
        for (Map.Entry<String, String> entry : new TreeMap<>(stationMapping).entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

        System.out.print("\nEnter Source (Key letter or full name): ");
        String source = sc.nextLine();

        System.out.print("Enter Destination (Key letter or full name): ");
        String destination = sc.nextLine();

        dijkstra(source, destination);

        sc.close();
    }
}