package com.example;

import java.util.List;

public interface SortAlgorithm {
    /**
     * Initialize the algorithm with the array to sort.
     */
    void initialize(List<Integer> array);

    /**
     * Perform one step of the algorithm. Returns true if sorting is done.
     */
    boolean step();

    /**
     * Get the current index being processed (for visualization).
     */
    int getCurrentIndex();

    /**
     * Get the index where the key is being inserted (for visualization).
     */
    int getKeyInsertIndex();

    /**
     * Get the value of the key being inserted (for visualization).
     */
    int getKey();

    /**
     * Is the algorithm currently inserting the key?
     */
    boolean isInserting();

    /**
     * Get a status message for the current step.
     */
    String getStatusMessage();

    /**
     * Get the index currently being compared (for visualization, optional).
     */
    default int getComparingIndex() { return -1; }
}
