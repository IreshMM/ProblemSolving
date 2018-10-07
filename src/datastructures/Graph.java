package datastructures;

import java.util.*;

public class Graph<T> {
    private int verticesCount = 0;
    private int uniqueIdentifier = 0;
    private HashMap<T, Integer> verticesMapping = new HashMap<>();
    private HashMap<Integer, HashSet<Integer>> vertices = new HashMap<>();

    public Graph() {
        super();
    }

    public void addVertex(T newNode) {
        verticesMapping.put(newNode, uniqueIdentifier);
        vertices.put(uniqueIdentifier, new HashSet<>());
        uniqueIdentifier++; verticesCount++;
    }

    public int addEdge(T from, T to) {
        Integer source = verticesMapping.get(from);
        Integer destination = verticesMapping.get(to);

        if(source != null && destination != null) {
            vertices.get(source).add(destination);
            return 1;
        }
        return 0;
    }

    public int removeVertex(T node) {
        Integer nodeIdentifier = verticesMapping.get(node);

        if(nodeIdentifier != null) {
            vertices.remove(nodeIdentifier);

            for(HashSet<Integer> adjacentNodes : vertices.values()) {
                adjacentNodes.remove(nodeIdentifier);
            }
            verticesMapping.remove(nodeIdentifier);
            verticesCount--;
            return 1;
        }
        return 0;
    }

    public int removeEdge(T from, T to) {
        Integer source = verticesMapping.get(from);
        Integer destination = verticesMapping.get(to);

        if(source != null && destination != null) {
            vertices.get(source).remove(destination);
            return 1;
        }
        return 0;
    }

    public int vertexCount() {
        return verticesCount;
    }

    public ArrayList<Integer> breadthFirstSearch(T startNode) {
        Queue<Integer> queue = new LinkedList<>();
        HashSet<Integer> visited = new HashSet<>();
        ArrayList<Integer> resultArray = new ArrayList<>();

        Integer startNodeIdentifier = verticesMapping.get(startNode);
        queue.add(startNodeIdentifier);
        visited.add(startNodeIdentifier);
        resultArray.add(startNodeIdentifier);

        while(!queue.isEmpty()) {
            Integer curNode = queue.poll();

            for(Integer vertex : vertices.get(startNodeIdentifier)) {
                if(!visited.contains(vertex)) {
                    queue.add(vertex);
                    visited.add(vertex);
                    resultArray.add(vertex);
                }
            }
        }

        return resultArray;
    }

    private void depthFirstSearch(Integer startNode, HashSet<Integer> visited) {
        visited.add(startNode);

        for(Integer vertex : vertices.get(startNode)) {
            if(!visited.contains(vertex)) {
                depthFirstSearch(vertex, visited);
            }
        }
    }

    public Integer[] getVertices() {
        Integer[] arr = new Integer[verticesCount];

        Object[] vert = verticesMapping.keySet().toArray();
        for (int i = 0; i < verticesCount; i++) {
            arr[i] = (Integer) vert[i];
        }

        return arr;
    }

    //Temporary helper functions for testing
    public void printGraph() {
        for(Map.Entry<T, Integer> entry : verticesMapping.entrySet()) {
            System.out.print(entry.getKey() + ", " + "Identifier: " + entry.getValue() + " ");
            System.out.println("AdjNodes: " + vertices.get(entry.getValue()).toString());
        }
    }
}
