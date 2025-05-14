package com.example;

import java.util.List;

public class InsertionSortAlgorithm implements SortAlgorithm {
    private List<Integer> array;
    private int currentIndex;
    private int currentPosition;
    private int key;
    private boolean inserting;
    private int keyInsertIndex;
    private String statusMessage;

    @Override
    public void initialize(List<Integer> array) {
        this.array = array;
        this.currentIndex = 1;
        this.currentPosition = 0;
        this.inserting = false;
        this.keyInsertIndex = -1;
        this.statusMessage = "";
    }

    @Override
    public boolean step() {
        if (currentIndex >= array.size()) {
            statusMessage = "Sorting complete.";
            return true;
        }
        if (!inserting) {
            key = array.get(currentIndex);
            currentPosition = currentIndex - 1;
            inserting = true;
            keyInsertIndex = currentIndex;
            statusMessage = "Inserting value " + key + " at index " + currentIndex;
        } else {
            if (currentPosition >= 0 && array.get(currentPosition) > key) {
                array.set(currentPosition + 1, array.get(currentPosition));
                keyInsertIndex = currentPosition;
                statusMessage = "Moving " + array.get(currentPosition + 1) + " to the right";
                currentPosition--;
            } else {
                array.set(currentPosition + 1, key);
                keyInsertIndex = currentPosition + 1;
                currentIndex++;
                inserting = false;
                statusMessage = "Inserted " + key + " at position " + (currentPosition + 1);
            }
        }
        return false;
    }

    @Override
    public int getCurrentIndex() {
        return currentIndex;
    }

    @Override
    public int getKeyInsertIndex() {
        return keyInsertIndex;
    }

    @Override
    public int getKey() {
        return key;
    }

    @Override
    public boolean isInserting() {
        return inserting;
    }

    @Override
    public String getStatusMessage() {
        return statusMessage;
    }
}
