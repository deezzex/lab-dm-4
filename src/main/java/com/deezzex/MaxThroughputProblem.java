package com.deezzex;

import java.util.LinkedList;

public class MaxThroughputProblem {
    private final int numberOfNodes;
    private final int[][] adjacencyMatrix;
    private final boolean[] visitedNodes;
    private final int[] parentPath;
    private final int sourceNode;
    private final int sinkNode;
    private int maxThroughput;

    public MaxThroughputProblem(int[][] adjacencyMatrix, int sourceNode, int sinkNode) {
        this.adjacencyMatrix = adjacencyMatrix;
        this.sourceNode = sourceNode;
        this.sinkNode = sinkNode;
        this.numberOfNodes = this.adjacencyMatrix[0].length;
        this.visitedNodes = new boolean[numberOfNodes];
        this.parentPath = new int[numberOfNodes];
        this.maxThroughput = 0;
    }

    public void solveByFordFulkerson() {
        System.out.println("Розв'язання задачі пошуку максильної пропускної здатності в графі методом Форда-Фалкерсона");
        int i;
        int j;

        int[][] residuals = new int[numberOfNodes][numberOfNodes];

        for (i = 0; i < numberOfNodes; i++){
            for (j = 0; j < numberOfNodes; j++){
                residuals[i][j] = adjacencyMatrix[i][j];
            }
        }

        while (breadthSearch(residuals, parentPath)) {
            int pathThroughput = Integer.MAX_VALUE;
            for (j = sinkNode; j != sourceNode; j = parentPath[j]) {
                i = parentPath[j];
                pathThroughput = Math.min(pathThroughput, residuals[i][j]);
            }

            for (j = sinkNode; j != sourceNode; j = parentPath[j]) {
                i = parentPath[j];
                residuals[i][j] -= pathThroughput;
                residuals[j][i] += pathThroughput;
            }

            maxThroughput += pathThroughput;
        }

        printInputMatrix(adjacencyMatrix);
        System.out.println("Максимальна пропускна здатність шляху [" + sourceNode + " -> " + sinkNode + "] : " + maxThroughput);
    }

    private boolean breadthSearch(int[][] residuals, int[] parentPath) {
        for (int i = 0; i < numberOfNodes; ++i){
            visitedNodes[i] = false;
        }

        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(sourceNode);
        visitedNodes[sourceNode] = true;
        parentPath[sourceNode] = -1;

        while (queue.size() != 0) {
            int u = queue.poll();

            for (int v = 0; v < numberOfNodes; v++) {
                if (!visitedNodes[v] && residuals[u][v] > 0) {

                    if (v == sinkNode) {
                        parentPath[v] = u;
                        return true;
                    }
                    queue.add(v);
                    parentPath[v] = u;
                    visitedNodes[v] = true;
                }
            }
        }

        return false;
    }

    private void printInputMatrix(int[][] matrix) {
        System.out.println();
        System.out.println("Вхідна матриця: ");
        for (int[] curRow : matrix) {
            for (int curCol : curRow) System.out.printf("%d ", curCol);
            System.out.println();
        }
        System.out.println();
    }
}
