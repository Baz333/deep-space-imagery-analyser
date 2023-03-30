package com.example.assignment1;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.HashMap;

public class HelloController {

    //fxml fields
    @FXML
    private MenuBar menuBar;
    @FXML
    private ImageView imageView;
    @FXML
    private Slider thresholdSlider;
    @FXML
    private Label thresholdLabel;
    @FXML
    private Label numberOfStarsText;
    @FXML
    private Slider starSizeSlider;
    @FXML
    private Label starSizeLabel;
    @FXML
    private Label reportLabel;

    //other fields
    private String filepath; //filepath for image being loaded
    private Image greyscaleImage; //greyscale version of loaded image
    private Image colourImage; //loaded image
    private Image blackWhiteImage; //black & white version of loaded image
    private int[] array; //array for storing if a pixel is black or white for union find
    private int starSize = 20; //minimum star size
    HashMap<Integer, Star> hashMap = new HashMap<>(); //hashmap for storing all star objects

    public void initialize() {
        //setting up menu bar
        menuBar.getMenus().clear();
        Menu menuFile = new Menu("File");
        MenuItem openImage = new MenuItem("Open Image...");
        MenuItem exitApp = new MenuItem("Exit Application");
        openImage.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*jpg", "*webp"));
            File selectedFile = fileChooser.showOpenDialog(menuBar.getScene().getWindow());
            if (selectedFile != null) {
                filepath = selectedFile.getAbsolutePath();
                //filepath = "C:\\Users\\bazda\\OneDrive - Waterford Institute of Technology\\Semester 4\\Data Structures & Algorithms 2\\space.jpg";
                try {
                    InputStream inputStream = new FileInputStream(filepath);
                    Image image = new Image(inputStream);
                    System.out.println("//Setting to selected image//");
                    imageView.setImage(image);
                    colourImage = imageView.getImage();
                    convertToGreyscale();
                    OnThresholdSliderDragged();
                    int height = (int) image.getHeight();
                    int width = (int) image.getWidth();
                    array = new int[height * width];
                    System.out.println(array.length);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        exitApp.setOnAction(actionEvent -> {
            System.exit(0);
        });
        Menu menuImage = new Menu("Image");
        menuBar.getMenus().addAll(menuFile, menuImage);
        menuFile.getItems().addAll(openImage, exitApp);

        //setting up star description label
        double labelX = reportLabel.getLayoutX();
        double labelY = reportLabel.getLayoutY();
        imageView.setOnMouseClicked(mouseEvent -> {
            reportLabel.setLayoutX(mouseEvent.getX() + labelX);
            reportLabel.setLayoutY(mouseEvent.getY() + labelY);
            updateReportLabel(mouseEvent.getX(), mouseEvent.getY());
        });
        reportLabel.setVisible(false);
    }

    //converts loaded image to greyscale and stores in greyscaleImage
    private void convertToGreyscale() {
        Image image = colourImage;
        if(image != null) {
            System.out.println("Image not null");
            PixelReader pixelReader = image.getPixelReader();
            WritableImage writableImage = new WritableImage((int) image.getWidth(), (int) image.getHeight());
            PixelWriter pixelWriter = writableImage.getPixelWriter();
            for(int i = 0; i < image.getWidth(); i++) {
                for(int j = 0; j < image.getHeight(); j++) {
                    Color color = pixelReader.getColor(i, j);
                    color = color.grayscale();
                    pixelWriter.setColor(i, j, color);
                }
            }
            greyscaleImage = writableImage;
        }
    }

    //updates the star description label with information about the clicked star + repositions it next to the cursor
    private void updateReportLabel(double x, double y) {
        if(imageView.getImage() != null && !hashMap.isEmpty()) {
            double imageWidth = colourImage.getWidth();
            double imageHeight = colourImage.getHeight();
            double containerHeight = imageView.getFitHeight();
            double containerWidth = (imageWidth/imageHeight) * containerHeight;
            x = (imageWidth/containerWidth)*x;
            y = (imageHeight/containerWidth)*y;
            boolean foundStar = false;
            for(Integer i: hashMap.keySet()){
                Star star = hashMap.get(i);
                if(x > star.getMinX() && x < star.getMaxX() && y > star.getMinY() && y < star.getMaxY()) {
                    int avgRed = star.getRed()/star.getSize();
                    int avgGreen = star.getGreen()/star.getSize();
                    int avgBlue = star.getBlue()/star.getSize();
                    if(!reportLabel.isVisible()) {
                        reportLabel.setVisible(true);
                    }
                    reportLabel.setText("Star no: " + i +
                            "\nSize (px): " + star.getSize() +
                            "\nEstimated sulphur: " + (double) Math.round(((double) avgRed/255)*10000)/100 + "%" +
                            "\nEstimated hydrogen: " + (double) Math.round(((double) avgGreen/255)*10000)/100 + "%" +
                            "\nEstimated oxygen: " + (double) Math.round(((double) avgBlue/255)*10000)/100 + "%");
                    foundStar = true;
                }
            }
            if(!foundStar && reportLabel.isVisible()) {
                reportLabel.setVisible(false);
            }
        }
    }

    //union-find algorithm methods
    public static int find(int[] a, int id) {
        while(a[id] != id) {
            id = a[id];
        }
        return id;
    }

    public static void union(int[] a, int p, int q) {
        a[find(a, q)] = p;
    }

    //sets the image view to the greyscale image
    public void OnGreyscaleButtonPressed() {
        if(imageView.getImage() != null) {
            System.out.println("//Setting to greyscale image//");
            imageView.setImage(greyscaleImage);
        }
    }

    //sets the image view to the original image
    public void OnColourButtonPressed() {
        if(imageView.getImage() != null) {
            System.out.println("//Setting to colour image//");
            imageView.setImage(colourImage);
        }
    }

    //sets the image view to the black & white image
    public void OnBlackWhiteButtonPressed() {
        if(imageView.getImage() != null) {
            System.out.println("//Setting to black & white image//");
            imageView.setImage(blackWhiteImage);
        }
    }

    //changes the colour limit for black & white conversion
    public void OnThresholdSliderDragged() {
        thresholdLabel.setText("" + (int) thresholdSlider.getValue());
        Image image = greyscaleImage;
        if(image != null) {
            PixelReader pixelReader = image.getPixelReader();
            WritableImage writableImage = new WritableImage((int) image.getWidth(), (int) image.getHeight());
            PixelWriter pixelWriter = writableImage.getPixelWriter();
            //Color black = new Color(0, 0, 0, 1);
            //Color white = new Color(1, 1, 1, 1);
            for(int i = 0; i < image.getWidth(); i++) {
                for(int j = 0; j < image.getHeight(); j++) {
                    Color color = pixelReader.getColor(i, j);
                    if((color.getRed()*255) < thresholdSlider.getValue()) {
                    pixelWriter.setColor(i, j, color.BLACK);
                    } else {
                        pixelWriter.setColor(i, j, color.WHITE);
                    }
                }
            }
            blackWhiteImage = writableImage;
        }
    }

    //changes the minimum star size
    public void OnStarSizeSliderDragged() {
        starSizeLabel.setText("" + (int) starSizeSlider.getValue());
        starSize = (int) starSizeSlider.getValue();
    }

    //union-find shenanigans
    public void OnTestButton() {
        if(imageView.getImage() == null) {
            return;
        }
        Image image = blackWhiteImage;
        PixelReader pixelReader = image.getPixelReader();
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        /*print out table of pixels
        for(int i = 0; i < array.length; i++) {
            int x = i % width;
            //int y = i / width;
            if(x == width - 1) {
                System.out.println(i);
            } else {
                System.out.print(i + ", ");
            }
        }*/
        //sets elements to index or -1, then unions them together
        for(int i = 0; i < array.length; i++) {
            int x = i % width;
            int y = i / width;
            if(pixelReader.getColor(x, y).getRed() == 1.0) {
                array[i] = i;
                boolean canUnion = true;
                if(x != 0 && y != 0) {
                    if(array[i - (width + 1)] >= 0) {
                        union(array, array[i - (width + 1)], array[i]);
                        canUnion = false;
                    }
                }
                if(y != 0 && canUnion) {
                    if(array[i - width] >= 0) {
                        union(array, array[i - width], array[i]);
                        canUnion = false;
                    }
                }
                if(x != (width - 1) && y != 0 && canUnion) {
                    if(array[i - (width - 1)] >= 0) {
                        union(array, array[i - (width - 1)], array[i]);
                        canUnion = false;
                    }
                }
                if(x != 0 && canUnion) {
                    if(array[i - 1] >= 0) {
                        union(array, array[i - 1], array[i]);
                    }
                }
            } else {
                array[i] = -1;
            }
        }
        //converts unioned objects into star objects and adds them to hashmap
        hashMap.clear();
        PixelReader colorPixelReader = colourImage.getPixelReader();
        for(int i = 0; i < array.length; i++) {
            if(array[i] != -1) {
                Color color = colorPixelReader.getColor(i%width, i/width);
                if(!hashMap.containsKey(find(array, i))) {
                    hashMap.put(i, new Star(1, i%width, i%width, i/width, i/width, (int) (color.getRed()*255), (int) (color.getGreen()*255), (int) (color.getBlue())*255));
                } else {
                    Star star = hashMap.get(find(array, i));
                    star.setSize(star.getSize() + 1);
                    if(i%width < star.getMinX()) {
                        star.setMinX(i%width);
                    }
                    if(i%width > star.getMaxX()) {
                        star.setMaxX(i%width);
                    }
                    if(i/width < star.getMinY()) {
                        star.setMinY(i/width);
                    }
                    if(i/width > star.getMaxY()) {
                        star.setMaxY(i/width);
                    }
                    star.setRed(star.getRed() + (int) color.getRed()*255);
                    star.setGreen(star.getGreen() + (int) color.getGreen()*255);
                    star.setBlue(star.getBlue() + (int) color.getBlue()*255);
                }
            }
        }
        System.out.println("Hash map size: " + hashMap.size());
        //prints out all statistics of star objects (size, boundaries, average colour, likely compostion)
        PixelReader boundaryPixelReader = colourImage.getPixelReader();
        WritableImage writableImage = new WritableImage(boundaryPixelReader, width, height);
        int numberOfStars = 0;
        for(Integer i: hashMap.keySet()) {
            Star star = hashMap.get(i);
            if(star.getSize() >= starSize) {
                int avgRed = star.getRed()/star.getSize();
                int avgGreen = star.getGreen()/star.getSize();
                int avgBlue = star.getBlue()/star.getSize();
                System.out.println("Boundaries for " + i + ", of size " + star.getSize() + "px, are x:[" + star.getMinX() + "-" + star.getMaxX() + "], y: [" + star.getMinY() + "-" + star.getMaxY() + "]. Average colors are (" + avgRed + ", " + avgGreen + ", " + avgBlue + "). Likely composition: " + (double) Math.round(((double) avgRed/255)*10000)/100 + "% sulphur, " + (double) Math.round(((double) avgGreen/255)*10000)/100 + "% hydrogen, " + (double) Math.round(((double) avgBlue/255)*10000)/100 + "% oxygen");
                PixelWriter pixelWriter = writableImage.getPixelWriter();
                numberOfStars++;
                for (int j = 0; j <= (star.getMaxX() - star.getMinX()); j++) {
                    pixelWriter.setColor((star.getMinX() + j), star.getMinY(), Color.BLUE);
                    pixelWriter.setColor((star.getMinX() + j), star.getMaxY(), Color.BLUE);
                }
                for (int j = 0; j <= (star.getMaxY() - star.getMinY()); j++) {
                    pixelWriter.setColor(star.getMinX(), (star.getMinY() + j), Color.BLUE);
                    pixelWriter.setColor(star.getMaxX(), (star.getMinY() + j), Color.BLUE);
                }
                imageView.setImage(writableImage);
            }
        }
        numberOfStarsText.setText("Number of stars: " + numberOfStars);
    }

}