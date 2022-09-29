package com.example.demo1.controller;

import com.example.demo1.random.Randomizer;
import com.example.demo1.tests.IndependenceTest;
import com.example.demo1.tests.PeriodTest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;

import java.util.stream.IntStream;

public class ApplicationController {
    @FXML
    private TextField aditiv;
    @FXML
    private TextField multi;
    @FXML
    private TextField measurement;
    @FXML
    private TextField mod;
    @FXML
    private TextArea console;
    @FXML
    private ChoiceBox<String> tests;
    @FXML
    private BarChart<String, Number> barChart;

    private ObservableList<String> list = FXCollections.observableArrayList("Java", "JavaScript", "C#", "Python");
    private static final long aditivConst = 11;
    private static final long multiConst = 25214903917L;
    private static final long modConst = 10;
    private Randomizer random;
    private String lastTest;

    @FXML
    protected void returnStandartConsts() {
        aditiv.setText(String.valueOf(aditivConst));
        multi.setText(String.valueOf(multiConst));
        mod.setText(String.valueOf(modConst));
    }

    @FXML
    protected void doTesting() {
        if (tests.getValue().equals("")) {
            console.appendText("Виберіть тип тестування!\n");
            return;
        }
        if (!isGoodConstants()) {
            returnStandartConsts();
            console.appendText("Встановленно стандартні константи,\n"
                    + "так як введено данні некоректно!\n");
        }
        if (measurement.getText().equals("")) {
            console.appendText("Встановіть параметри тестування!\n");
            return;
        }
        if (random == null) {
            random = new Randomizer();
        }
        if (!tests.getValue().equals(lastTest)) {
            doClearBarChart();
            lastTest = tests.getValue();
        }
        if (tests.getValue().equals("На незалежність")) {
            random.setAdapt(Integer.parseInt(aditiv.getText()));
            random.setMulti(Long.parseLong(multi.getText()));
            random.setMod(Integer.parseInt(mod.getText()));
            IndependenceTest independenceTest =
                    new IndependenceTest(Integer.parseInt(measurement.getText()), random);
            console.appendText(independenceTest.doTesting());
            double[] dataForBarChart = independenceTest.getDataForBarChart();
            XYChart.Series<String, Number> data = new XYChart.Series<>();
            data.getData().add(new XYChart.Data<>("K", dataForBarChart[0]));
            data.getData().add(new XYChart.Data<>("T", dataForBarChart[1]));
            barChart.getData().add(data);
            return;
        }
        if (tests.getValue().equals("На період")) {
            random.setAdapt(Integer.parseInt(aditiv.getText()));
            random.setMulti(Long.parseLong(multi.getText()));
            random.setMod(Integer.parseInt(mod.getText()));
            PeriodTest periodTest =
                    new PeriodTest(Integer.parseInt(measurement.getText()), random);
            console.appendText(periodTest.doTesting());
            int[] dataForBarChart = periodTest.getDataForBarChart();
            XYChart.Series<String, Number> data = new XYChart.Series<>();
            IntStream.range(0, dataForBarChart.length)
                    .forEach(i -> data.getData()
                            .add(new XYChart.Data<>(String.valueOf(i), dataForBarChart[i])));
            barChart.getData().add(data);
            return;
        }
    }

    @FXML
    public void doClearBarChart() {
        barChart.getData().clear();
    }

    private boolean isGoodConstants() {
        String constants = aditiv.getText() + multi.getText() + mod.getText();
        return !(aditiv.getText().equals("")
                || multi.getText().equals("")
                || mod.getText().equals(""))
                && constants.replaceAll("\\d", "").equals("");
    }
}
