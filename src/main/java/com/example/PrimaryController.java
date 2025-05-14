package com.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class PrimaryController {
    @FXML
    private Pane visualizationPane;
    
    @FXML
    private Button generateButton;
    
    @FXML
    private Button sortButton;
    
    @FXML
    private Button resetButton;
    
    @FXML
    private Slider speedSlider;
    
    @FXML
    private Slider sizeSlider;    
    
    @FXML
    private ComboBox<String> algorithmComboBox;
    
    private List<Integer> array = new ArrayList<>();
    private List<Rectangle> bars = new ArrayList<>();
    private Random random = new Random();
    private Timeline timeline;
    
    private SortAlgorithm sortAlgorithm;
    
    private boolean sorting = false;
    private int[] originalArray;
    private Text statusText;    
    
    @FXML
    private void initialize() {
        // Initialize status text for showing algorithm steps
        statusText = new Text(10, 20, "");
        visualizationPane.getChildren().add(statusText);
        
        // Initialize algorithm selection ComboBox
        if (algorithmComboBox != null) {
            algorithmComboBox.getItems().addAll("Insertion Sort", "Selection Sort");
            algorithmComboBox.getSelectionModel().selectFirst();
            algorithmComboBox.setOnAction(e -> generateNewArray());
        }
        
        generateNewArray();
    }
    
    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
    
    @FXML
    private void generateNewArray() {
        if (sorting) {
            stopSorting();
        }
        
        int size = (int) sizeSlider.getValue();
        array.clear();
        
        for (int i = 0; i < size; i++) {
            array.add(random.nextInt(100) + 1); // Values between 1 and 100
        }
        
        // Save original array for reset functionality
        originalArray = new int[array.size()];
        for (int i = 0; i < array.size(); i++) {
            originalArray[i] = array.get(i);
        }
        
        // Initialize the algorithm based on ComboBox selection
        String selected = algorithmComboBox != null ? algorithmComboBox.getValue() : "Insertion Sort";
        if ("Selection Sort".equals(selected)) {
            sortAlgorithm = new SelectionSortAlgorithm();
        } else {
            sortAlgorithm = new InsertionSortAlgorithm();
        }
        sortAlgorithm.initialize(array);
        
        updateVisualization();
        sortButton.setDisable(false);
        resetButton.setDisable(false);
    }
    
    @FXML
    private void resetArray() {
        if (sorting) {
            stopSorting();
        }
        
        if (originalArray != null) {
            array.clear();
            for (int value : originalArray) {
                array.add(value);
            }
            // Re-initialize the algorithm based on ComboBox selection
            String selected = algorithmComboBox != null ? algorithmComboBox.getValue() : "Insertion Sort";
            if ("Selection Sort".equals(selected)) {
                sortAlgorithm = new SelectionSortAlgorithm();
            } else {
                sortAlgorithm = new InsertionSortAlgorithm();
            }
            sortAlgorithm.initialize(array);
            updateVisualization();
            sortButton.setDisable(false);
        }
    }
    
    @FXML
    private void startSort() {
        if (sorting) {
            stopSorting();
            return;
        }
        
        sorting = true;
        sortButton.setText("Stop Sort");
        generateButton.setDisable(true);
        
        // Start the insertion sort visualization
        startInsertionSortVisualization();
    }
    
    private void stopSorting() {
        if (timeline != null) {
            timeline.stop();
        }
        sorting = false;
        sortButton.setText("Start Sort");
        generateButton.setDisable(false);
    }
    
    private void startInsertionSortVisualization() {
        // Always use the current sortAlgorithm
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        
        KeyFrame kf = new KeyFrame(Duration.millis(1000 / speedSlider.getValue()), e -> {
            boolean done = sortAlgorithm.step();
            updateVisualization();
            if (done || !sorting) {
                stopSorting();
            }
        });
        
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }
    
    private void updateVisualization() {
        visualizationPane.getChildren().clear();
        bars.clear();
        
        double width = visualizationPane.getPrefWidth() / array.size();
        double height = visualizationPane.getPrefHeight();
        
        int currentIndex = sortAlgorithm != null ? sortAlgorithm.getCurrentIndex() : 1;
        int keyInsertIndex = sortAlgorithm != null ? sortAlgorithm.getKeyInsertIndex() : -1;
        int key = sortAlgorithm != null ? sortAlgorithm.getKey() : 0;
        boolean inserting = sortAlgorithm != null && sortAlgorithm.isInserting();
        int comparingIndex = sortAlgorithm != null ? sortAlgorithm.getComparingIndex() : -1;
        
        for (int i = 0; i < array.size(); i++) {
            double barHeight;
            Color barColor;
            if (sorting && inserting && i == keyInsertIndex) {
                barHeight = (key / 100.0) * (height - 30);
                barColor = Color.ORANGE;
            } else if (sorting && comparingIndex == i) {
                barHeight = (array.get(i) / 100.0) * (height - 30);
                barColor = Color.GOLD;
            } else {
                barHeight = (array.get(i) / 100.0) * (height - 30);
                if (sorting && i == currentIndex) {
                    barColor = Color.RED;
                } else if (i < currentIndex) {
                    barColor = Color.GREEN;
                } else if (sorting && i == keyInsertIndex) {
                    barColor = Color.ORANGE;
                } else {
                    barColor = Color.BLUE;
                }
            }
            Rectangle bar = new Rectangle(width - 1, barHeight);
            bar.setX(i * width);
            bar.setY(height - barHeight);
            bar.setFill(barColor);
            bars.add(bar);
            visualizationPane.getChildren().add(bar);
        }
        
        if (sortAlgorithm != null) {
            statusText.setText(sortAlgorithm.getStatusMessage());
        }
        
        visualizationPane.getChildren().add(statusText);
    }
}
