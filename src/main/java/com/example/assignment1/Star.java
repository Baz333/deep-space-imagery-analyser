package com.example.assignment1;

import javafx.scene.paint.Color;

public class Star {

    //fields
    private int number;
    private int size; //amount of pixels in object
    private int minX; //leftmost pixel
    private int maxX; //rightmost pixel
    private int minY; //uppermost pixel
    private int maxY; //lowermost pixel
    private int red; //total amount of red in object
    private int green; //total amount of green in object
    private int blue; //total amount of blue in object



    //constructor
    public Star(int number, int size, int minX, int maxX, int minY, int maxY, int red, int green, int blue) {
        setNumber(number);
        setSize(size);
        setMinX(minX);
        setMaxX(maxX);
        setMinY(minY);
        setMaxY(maxY);
        setRed(red);
        setGreen(green);
        setBlue(blue);
    }



    //getters & setters
    public int getNumber() {
        return  number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

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

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

}
