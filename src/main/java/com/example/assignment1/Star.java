package com.example.assignment1;

import javafx.scene.paint.Color;

public class Star {

    //fields
    private int size;
    private int minX;
    private int maxX;
    private int minY;
    private int maxY;
    private Color color;



    //constructor
    public Star(int size, int minX, int maxX, int minY, int maxY, Color color) {
        setSize(size);
        setMinX(minX);
        setMaxX(maxX);
        setMinY(minY);
        setMaxY(maxY);
        setColor(color);
    }



    //getters & setters
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getMinX() {
        return minX;
    }

    public void setMinX(int minX) {
        this.minX = minX;
    }

    public int getMaxX() {
        return maxX;
    }

    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }

    public int getMinY() {
        return minY;
    }

    public void setMinY(int minY) {
        this.minY = minY;
    }

    public int getMaxY() {
        return maxY;
    }

    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

}
