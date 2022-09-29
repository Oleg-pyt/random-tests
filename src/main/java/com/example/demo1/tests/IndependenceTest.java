package com.example.demo1.tests;

import com.example.demo1.random.Randomizer;

import java.util.Arrays;
import java.util.stream.IntStream;

public class IndependenceTest {
    private int numElements;
    private int[] arr1;
    private int[] arr2;
    private Double squareDeviation1;
    private Double squareDeviation2;
    private Double correlations;
    private Double averageNum;
    private Randomizer random;
    private double k;
    private double t;

    public IndependenceTest(int numElements, Randomizer random) {
        this.numElements = numElements;
        this.random = random;
    }

    public String doTesting() {
        generateElements();
        squareDeviation1 = getSquareDeviation(squareDeviation1, arr1);
        squareDeviation2 = getSquareDeviation(squareDeviation2, arr2);
        correlations = generateCorrelations();
        averageNum = generateAverageNum();
        k = (correlations - averageNum) / (squareDeviation1 * squareDeviation2);
        t = Math.sqrt(Math.pow(k, 2) / (1 - Math.pow(k, 2))) * 10;
        return createReport(k, t);
    }

    public double[] getDataForBarChart() {
        return new double[]{k, t};
    }

    private String createReport(double k, double t) {
        StringBuilder builder = new StringBuilder();
        builder.append(".____________________________________________.\n");
        builder.append("|Число ступенів волі|Критичне значення|\n");
        builder.append("|______________________|______________________|\n");
        builder.append("|")
                .append(String.format("%32d", 5))
                .append("|")
                .append(String.format("%31.2f", 2.01f))
                .append("|\n");
        builder.append("|")
                .append(String.format("%31d", 10))
                .append("|")
                .append(String.format("%31.2f", 1.81f))
                .append("|\n");
        builder.append("|")
                .append(String.format("%31d", 20))
                .append("|")
                .append(String.format("%31.2f", 1.73f))
                .append("|\n");
        builder.append("|")
                .append(String.format("%31d", 40))
                .append("|")
                .append(String.format("%31.2f", 1.68f))
                .append("|\n");
        builder.append("|______________________|______________________|\n");
        builder.append("arr1 = ")
                .append(Arrays.toString(arr1))
                .append("\n");
        builder.append("arr2 = ")
                .append(Arrays.toString(arr2))
                .append("\n");
        builder.append("K = ")
                .append(k)
                .append("\n");
        builder.append("T = ")
                .append(t)
                .append("\n");
        return builder.toString();
    }

    private void generateElements() {
        arr1 = new int[numElements];
        arr2 = new int[numElements];
        IntStream.range(0, numElements).forEach(i -> arr1[i] = random.nextInt());
        random.clear();
        IntStream.range(0, numElements).forEach(i -> arr2[i] = random.nextInt());
    }

    private double getSquareDeviation(Double squareDeviation, int[] arr) {
        float i = (float)(Arrays.stream(arr1).sum() / numElements);
        return Math.sqrt(Arrays.stream(arr)
                .mapToDouble(value -> Math.pow(value - i, 2))
                .sum() / numElements - 1);
    }

    private double generateCorrelations() {
        int[] doubles = new int[numElements - 1];
        return IntStream.range(0, numElements)
                .mapToDouble(i -> arr1[i] * arr2[i]).sum() / numElements;
    }

    private double generateAverageNum() {
        return  (double) Arrays.stream(arr1).sum()
            * (double) Arrays.stream(arr2).sum()
            / Math.pow(numElements, 2);
    }
}
