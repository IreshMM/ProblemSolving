package com.company;

import datastructures.Graph;

import java.util.*;

public class Solution {
    public static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String line1Args[] = scanner.nextLine().split(" ");

        int N = Integer.parseInt(line1Args[0]);
        int M = Integer.parseInt(line1Args[1]);
        int Q = Integer.parseInt(line1Args[2]);

        char[][] inputArr = new char[N+2][];
        inputArr[0] = new char[M + 2];
        inputArr[N+1] = inputArr[0];
        Arrays.fill(inputArr[0], 'T');

        for (int i = 1; i < N + 1; i++) {
            inputArr[i] = ("T" + scanner.nextLine().trim().substring(0, M) + "T").toCharArray();
        }

        Coord[] queries = new Coord[Q * 2];

        for (int i = 0; i < Q; i++) {
            String[] lineArgs = scanner.nextLine().split(" ");

            Coord source = new Coord(Integer.parseInt(line1Args[0]), Integer.parseInt(lineArgs[1]));
            Coord destination = new Coord(Integer.parseInt(line1Args[2]), Integer.parseInt(lineArgs[3]));

            queries[i] = source;
            queries[i + 1] = destination;
        }

        printMatrix(inputArr);

        int[][] resultArray = new int[N + 2][M + 2];

        identifyIslands(inputArr, resultArray);
        HashMap<Integer, HashSet<Integer>> oceans = identifyOceans(inputArr, resultArray);

        printIntMatrix(resultArray);

        oceans.forEach((i, j) -> System.out.println(i + " :" + j.toString()));

        Graph<Integer> graph = constructGraph(oceans);

        graph.printGraph();

    }

    public static HashSet<Integer> traverseAndPut(char[][] arr, int[][] resultArr, Coord coord, int value) {
        /*
        State definitions:
            0 : unvisited
            !0 : visited

        Assumptions:
            Given coordinates are a match
        **/

        HashSet<Integer> result = null;

        Coord[] directions = new Coord[8];
        directions[0] = new Coord(-1, 0);
        directions[1] = new Coord(-1, 1);
        directions[2] = new Coord(0, 1);
        directions[3] = new Coord(1, 1);
        directions[4] = new Coord(1, 0);
        directions[5] = new Coord(1, -1);
        directions[6] = new Coord(0, -1);
        directions[7] = new Coord(-1, -1);

        char match = arr[coord.i][coord.j];

        Queue<Coord> queue = new LinkedList<>();
        queue.add(coord);
        resultArr[coord.i][coord.j] = value;

        if (match == 'O') {
            while(!queue.isEmpty()) {
                Coord curCoord = queue.poll();

                //Traversing all possible adjacent cells
                for(Coord coord1 : directions) {
                    int curI = curCoord.i + coord1.i;
                    int curJ = curCoord.j + coord1.j;

                    if(arr[curI][curJ] == match && resultArr[curI][curJ] == 0) {
                        resultArr[curI][curJ] = value;
                        queue.add(new Coord(curI, curJ));
                    }
                }
            }
        } else {
            result = new HashSet<>();

            while(!queue.isEmpty()) {
                Coord curCoord = queue.poll();

                //Traversing all possible adjacent cells
                for(Coord coord1 : directions) {
                    int curI = curCoord.i + coord1.i;
                    int curJ = curCoord.j + coord1.j;

                    if(arr[curI][curJ] == match && resultArr[curI][curJ] == 0) {
                        resultArr[curI][curJ] = value;
                        queue.add(new Coord(curI, curJ));
                    } else if(resultArr[curI][curJ] > 0) {
                        result.add(resultArr[curI][curJ]);
                    }
                }
            }
        }

        return result;
    }



    public static void identifyIslands(char[][] arr, int[][] resultAr) {
        //Island labelling starting from 1
        int islandNo = 1;

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                if(resultAr[i][j] == 0 && arr[i][j] == 'O') {
                    traverseAndPut(arr, resultAr, new Coord(i, j), islandNo);
                    islandNo++;
                }
            }
        }
    }

    public static HashMap<Integer, HashSet<Integer>> identifyOceans(char[][] arr, int[][] resultArr) {
        HashMap<Integer, HashSet<Integer>> result = new HashMap<>();

        //Ocean labelling starting from -2 and going down
        int oceanNo = -2;

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                if(resultArr[i][j] == 0 && arr[i][j] == '~') {
                    HashSet<Integer> adjacentIslands = traverseAndPut(arr, resultArr, new Coord(i, j), oceanNo);
                    result.put(oceanNo, adjacentIslands);
                    oceanNo--;
                }
            }
        }

        return result;
    }

    public static Graph<Integer> constructGraph(HashMap<Integer, HashSet<Integer>> oceans) {
        Graph<Integer> graph = new Graph<>();

        //Adding vertexes
        oceans.keySet().forEach(i -> graph.addVertex(i));

        //Adding edges
        Integer[] vertices = graph.getVertices();
        for (int i = 0; i < vertices.length; i++) {
            for(Integer ajacentIsland : oceans.get(vertices[i])) {
                for(int j = i + 1; j < vertices.length; j++) {
                    if(oceans.get(vertices[j]).contains(ajacentIsland)) {
                        graph.addEdge(vertices[i], vertices[j]);
                        graph.addEdge(vertices[j], vertices[i]);
                        continue;
                    }
                }
            }
        }

        return graph;
    }


    //Helper functions for testing

    public static void printMatrix(char[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }

    public static void printIntMatrix(int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.println(Arrays.toString(arr[i]));
        }
    }
}

class Coord {
    public int i;
    public int j;

    public Coord(int i, int j) {
        this.i = i;
        this.j = j;
    }
}