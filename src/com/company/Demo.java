package com.company;

//This class is for experimenting

import java.util.*;

public class Demo {
    public static void main(String[] args) {
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();

        while(!priorityQueue.isEmpty()) {
            System.out.println(priorityQueue.poll());
        }
    }
}
