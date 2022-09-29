package com.example.demo1.tests;

import com.example.demo1.random.Randomizer;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class PeriodTest {
    private final Randomizer random;
    private int num;
    private int[] result;

    public PeriodTest(int num, Randomizer random) {
        this.random = random;
        this.num = num;
    }

    public String doTesting() {
        result = new int[num];
        StringBuilder builder = new StringBuilder();
        builder.append("Кількість інтервалів ")
                .append(num)
                .append("\n");
        IntStream.range(1, num + 1).forEach(i -> {
            result[i - 1] = calculatePeriod();
            builder.append("T")
                    .append(i)
                    .append(" = ")
                    .append(result[i - 1])
                    .append("\n");
        });
        return builder.toString();
    }

    private int calculatePeriod() {
        random.clear();
        Set<Integer> arr = new HashSet<>();
        for (int i = 0;; i++) {
            int currentValue = random.nextInt();
            if (arr.contains(currentValue)) {
                return i;
            }
            arr.add(currentValue);
        }
    }

    public int[] getDataForBarChart() {
        return result;
    }
}
