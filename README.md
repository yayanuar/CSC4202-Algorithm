# RAPID KL Bus Route Optimization System Using Dijkstra's Algorithm

## 1. Project Overview

The **Malaysia Express Bus Route Optimization System** is a Java-based application developed using **Dijkstra's Algorithm** to determine the shortest available route between selected bus stops.

The system represents the Malaysia's bus network as a weighted graph, where:

- Each bus stop is represented as a **vertex (node)**.
- Each connecting road is represented as an **edge**.
- Each edge weight represents the estimated travel time in minutes.
- Closed roads are excluded during route calculation.

The main objective of this system is to help passengers find the fastest available route while avoiding unavailable roads caused by disruptions such as road closures.

---

# 2. Problem Statement

Passengers using public transportation may experience delays when selecting inefficient routes or when certain roads become unavailable. Since multiple routes can exist between two bus stops, manually identifying the shortest route can be difficult and time-consuming.

Therefore, an optimization system is required to automatically determine the shortest available route between a source and destination bus stop.

## Goal

The goal of this project is to develop a bus route optimization system that:

- Finds the shortest travel route.
- Minimizes total estimated travel time.
- Avoids closed roads.
- Provides an optimal route recommendation for passengers.

---

# 3. Algorithm Paradigm

## Selected Algorithm: Dijkstra's Algorithm

This project applies **Dijkstra's Algorithm**, which is a greedy-based graph algorithm used to solve shortest path problems in weighted graphs with non-negative edge weights.

The algorithm repeatedly selects the bus stop with the smallest known travel time and updates the shortest distance to its neighbouring bus stops until the optimal route is obtained.

---

## Why Dijkstra's Algorithm?

Dijkstra's Algorithm was selected because:

- It guarantees the shortest path when all edge weights are non-negative.
- It is suitable for transportation networks represented as weighted graphs.
- It efficiently calculates the minimum travel time between two locations.
- It can handle multiple possible routes between bus stops.

---

## Flowchart

<img width="563" height="890" alt="djiktra flowchart" src="https://github.com/user-attachments/assets/07ac2ce8-f314-49a9-84fb-6322a3cf4156" />

# 4. System Model

The Malaysia bus network is represented using a weighted graph.

<img width="826" height="457" alt="Screenshot 2026-07-01 024007" src="https://github.com/user-attachments/assets/6e50de7e-a7fb-4d6c-b349-787d94ebb0c1" />

## Graph Representation

The system uses an **adjacency list** to store the graph.

Example:

```

KL SENTRAL
|
|-- MID VALLEY (8 minutes)
|
|-- BANGSAR (5 minutes)
|
|-- CHERAS (10 minutes)

````

Each route contains:

- Destination bus stop
- Estimated travel time
- Road availability status

The edge structure is represented using:

```java
class Edge {
    String destination;
    int weight;
    boolean open;
}
````

Where:

* `destination` represents the connected bus stop.
* `weight` represents estimated travel time.
* `open` represents road availability.

---

# 5. Available Bus Stops

The system contains 12 bus stops:

| Code | Bus Stop      |
| ---- | ------------- |
| A    | KL SENTRAL    |
| B    | MID VALLEY    |
| C    | BANGSAR       |
| D    | CHERAS        |
| E    | AMPANG        |
| F    | PETALING JAYA |
| G    | SUBANG JAYA   |
| H    | SHAH ALAM     |
| I    | KLANG         |
| J    | PUCHONG       |
| K    | CYBERJAYA     |
| L    | PUTRAJAYA     |

Users can enter either:

* Bus stop code (A, B, C...)
* Full bus stop name

Example:

```
Enter Source (Letter or Name): A

Enter Destination (Letter or Name): K
```

---

# 6. System Features

## 6.1 Shortest Route Calculation

The system applies Dijkstra's Algorithm to calculate the shortest travel route between two selected bus stops.

The algorithm:

1. Initializes all bus stop distances as infinity.
2. Sets the source distance as zero.
3. Uses a priority queue to process the nearest bus stop first.
4. Calculates possible routes.
5. Updates shorter travel times.
6. Reconstructs and displays the optimal route.

---

## 6.2 Closed Road Handling

The system considers road availability during route calculation.

Example:

```java
addEdge("J", "L", 9, false);
addEdge("B", "F", 12, false);
addEdge("F", "G", 6, false);
```

A closed road has the value:

```
open = false
```

During execution, unavailable routes are ignored:

```java
if (!edge.open)
    continue;
```

This ensures that the final recommended route only contains available roads.

---

# 7. Pseudocode

```
DIJKSTRA(Source, Destination)

1. Initialize distance of all vertices as infinity.
2. Set distance(Source) = 0.
3. Create an empty priority queue.
4. Insert Source into the priority queue.

5. WHILE priority queue is not empty:

      Remove node with the smallest distance.

      FOR each neighbouring edge:

          IF edge is closed:
              Skip this route.

          Calculate new travel distance.

          IF new distance is smaller:
              Update distance.
              Store previous node.
              Insert node into priority queue.

6. IF destination cannot be reached:
      Display "No available route found."

7. Reconstruct shortest path.

8. Display route and total travel time.
```

---

# 8. Program Demonstration

## Butterworth (A) to Alor Setar (D)

<img width="516" height="705" alt="butterworth to alor setar" src="https://github.com/user-attachments/assets/e4bc12b0-1273-4a96-b324-ea88a7dc1fab" />

## Kuantan (J) to Melaka Sentral (G)

<img width="773" height="707" alt="kuantan to melaka sentral" src="https://github.com/user-attachments/assets/67664208-e3d0-459a-be97-57d31ec25ea8" />

## Alor Setar (D) to Temerloh (L)

<img width="566" height="656" alt="alor setar to temerloh" src="https://github.com/user-attachments/assets/25896dec-67c6-4b26-a414-09446488c477" />

```

The system successfully identifies the shortest available route while avoiding closed roads.

---

# 9. Algorithm Analysis

## Time Complexity

The system implements:

* Adjacency list graph representation.
* Priority queue for selecting the smallest distance node.

Therefore, the time complexity is:

[
O((V+E)\log V)
]

Where:

* `V` = number of bus stops (vertices).
* `E` = number of bus routes (edges).

---

## Best Case Analysis

The best case occurs when the destination can be reached with fewer route updates.

Although fewer nodes may require processing, the algorithm still maintains the same asymptotic complexity.

Time Complexity:

```
O((V+E)logV)
```

---

## Average Case Analysis

The average case occurs when several possible routes exist between the source and destination.

The algorithm evaluates available routes and updates distances when a shorter path is found.

Time Complexity:

```
O((V+E)logV)
```

---

## Worst Case Analysis

The worst case occurs when the algorithm needs to explore almost the entire bus network before confirming the shortest route.

Every vertex and edge may be processed.

Time Complexity:

```
O((V+E)logV)
```

---

# 10. Correctness Analysis

The correctness of the system is supported by the properties of Dijkstra's Algorithm:

* All travel times are non-negative.
* The priority queue always selects the bus stop with the smallest current travel time.
* Distance values are updated only when a shorter route is discovered.
* Closed roads are excluded from route calculation.
* The previous node information allows reconstruction of the optimal path.

Therefore, the system is able to produce the shortest available route between two valid bus stops.

---

# 11. Technologies Used

| Component            | Description                        |
| -------------------- | ---------------------------------- |
| Programming Language | Java                               |
| JDK Version          | Java 17                            |
| Algorithm            | Dijkstra's Algorithm               |
| Data Structures      | HashMap, ArrayList, Priority Queue |
| IDE                  | Visual Studio Code                 |
| Operating System     | Windows 11                         |

---

# 12. How to Run the Program

## Requirements

* Java Development Kit (JDK) 17 or above.

## Steps

1. Clone or download this repository.

2. Open the project folder.

3. Compile the Java file:

```
javac busroute.java
```

4. Run the program:

```
java busroute
```

5. Enter the source and destination bus stops.

---

# 13. Future Improvements

Possible improvements for future development:

* Integrate real-time traffic information.
* Add graphical map visualization.
* Include bus arrival schedules.
* Develop a mobile application version.
* Apply advanced algorithms such as A* for larger transportation networks.

---

# 14. Project Information

**Course:** CSC4202 Design and Analysis of Algorithms

**Project Title:**
Rapid KL Bus Route Optimization System Using Dijkstra's Algorithm

**Group:** Group 6

## Group Members

* Haikal
* Afiah
* Alieya
* Farhanie
* Zulaikha

```
