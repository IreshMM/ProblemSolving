package com.company;

import java.util.*;

public class QueueOfDolls {
    public static final Scanner scanner = new Scanner(System.in);

    /*
    public static void main(String[] args) {
        String line1Args[] = scanner.nextLine().split(" ");

        int n = Integer.parseInt(line1Args[0]);
        int l = Integer.parseInt(line1Args[1]);
        int t = Integer.parseInt(line1Args[2]);

        char[] arr = scanner.nextLine().trim().substring(0, n).toCharArray();

        calculateBestQ(arr, t, l);

        System.out.println(arr);

    }
    */

    public static void calculateBestQ(char[] arr, int t, int l) {
        for (int i = 0; t > 0 && i < arr.length; i += 1) {
            if (arr[i] == 'D') t -= bringToFront(arr, i, l, t);
        }
    }

    public static void swap(char[] arr, int i, int j) {
        char tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static int bringToFront(char arr[], int start, int step, int maxSteps) {
        if(arr[start] == 'N') return 0;

        int counter = 0;
        for (int i = start + step; i < arr.length; i += step) {
            counter++;
            if (arr[i] == 'N') {
                swap(arr, start, i);
                return counter;
            }

            if (counter >= maxSteps) return 0;
        }
        return 0;
    }

    public static void printHello() {
        System.out.println("Hellow");
    }
}