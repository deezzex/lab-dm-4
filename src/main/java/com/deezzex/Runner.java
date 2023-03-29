package com.deezzex;

import java.util.Scanner;

public class Runner {
    public static void main(String[] args) {
        System.out.println("Введіть назву файлу з матрицею суміжності: ");

        Scanner scanner = new Scanner(System.in);

        String fileName = scanner.nextLine();

        scanner.close();

        int[][] inputMatrix = MatrixUtil.getInputMatrix(fileName);

        MaxThroughputProblem maxThroughputProblem = new MaxThroughputProblem(inputMatrix, 0, 7);

        maxThroughputProblem.solveByFordFulkerson();
    }
}