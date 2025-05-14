package com.example;

import java.util.List;

public class SelectionSortAlgorithm implements SortAlgorithm {
    private List<Integer> array;
    private int currentIndex;
    private int minIndex;
    private int scanIndex;
    private boolean swapping;
    private String statusMessage;
    private int comparingIndex = -1; // Track which index is being compared

    @Override
    public void initialize(List<Integer> array) {
        this.array = array;
        this.currentIndex = 0;
        this.minIndex = 0;
        this.scanIndex = 1;
        this.swapping = false;
        this.statusMessage = "";
    }

    @Override
    public boolean step() {
        if (currentIndex >= array.size() - 1) {
            statusMessage = "Sorting complete.";
            comparingIndex = -1;
            return true;
        }
        if (!swapping) {
            if (scanIndex < array.size()) {
                comparingIndex = scanIndex;
                statusMessage = "Comparing index " + scanIndex + " (" + array.get(scanIndex) + ") with minIndex " + minIndex + " (" + array.get(minIndex) + ")";
                if (array.get(scanIndex) < array.get(minIndex)) {
                    minIndex = scanIndex;
                }
                scanIndex++;
            } else {
                // Swap currentIndex and minIndex
                int temp = array.get(currentIndex);
                array.set(currentIndex, array.get(minIndex));
                array.set(minIndex, temp);
                statusMessage = "Swapping index " + currentIndex + " and minIndex " + minIndex;
                swapping = true;
                comparingIndex = -1;
            }
        } else {
            // Move to next index
            currentIndex++;
            minIndex = currentIndex;
            scanIndex = currentIndex + 1;
            swapping = false;
            statusMessage = "Moved to next index: " + currentIndex;
            comparingIndex = -1;
        }
        return false;
    }

    @Override
    public int getCurrentIndex() {
        return currentIndex;
    }

    @Override
    public int getKeyInsertIndex() {
        return minIndex;
    }

    @Override
    public int getKey() {
        return array != null && minIndex < array.size() ? array.get(minIndex) : 0;
    }

    @Override
    public boolean isInserting() {
        return swapping;
    }

    @Override
    public String getStatusMessage() {
        return statusMessage;
    }

    public int getComparingIndex() {
        return comparingIndex;
    }
}
