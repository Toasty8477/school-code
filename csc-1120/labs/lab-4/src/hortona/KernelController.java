/*
 * Course: CSC1110 - 111
 * Spring 2025
 * Lab 4 - Image Manipulator 3001
 * Name: Alexander Horton
 * Modified 2/18/2025
 */

package hortona;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Controller class for the kernel filter window
 */
public class KernelController {
    @FXML
    private TextField box00;
    @FXML
    private TextField box10;
    @FXML
    private TextField box20;
    @FXML
    private TextField box01;
    @FXML
    private TextField box11;
    @FXML
    private TextField box21;
    @FXML
    private TextField box02;
    @FXML
    private TextField box12;
    @FXML
    private TextField box22;

    private Lab4Controller main;

    public void setMain(Lab4Controller scene) {
        this.main = scene;
    }

    @FXML
    private void blur() {
        box00.setText("0");
        box10.setText("1");
        box20.setText("0");
        box01.setText("1");
        box11.setText("5");
        box21.setText("1");
        box02.setText("0");
        box12.setText("1");
        box22.setText("0");
    }
    @FXML
    private void sharpen() {
        box00.setText("0");
        box10.setText("-1");
        box20.setText("0");
        box01.setText("-1");
        box11.setText("5");
        box21.setText("-1");
        box02.setText("0");
        box12.setText("-1");
        box22.setText("0");
    }
    @FXML
    private void apply() {
        final int arraySize = 9;
        double total = 0;
        total += Double.parseDouble(box00.getText());
        total += Double.parseDouble(box10.getText());
        total += Double.parseDouble(box20.getText());
        total += Double.parseDouble(box01.getText());
        total += Double.parseDouble(box11.getText());
        total += Double.parseDouble(box21.getText());
        total += Double.parseDouble(box02.getText());
        total += Double.parseDouble(box12.getText());
        total += Double.parseDouble(box22.getText());

        double[] nums = new double[arraySize];
        nums[0] = Double.parseDouble(box00.getText())/total;
        nums[1] = Double.parseDouble(box10.getText())/total;
        nums[2] = Double.parseDouble(box20.getText())/total;
        nums[3] = Double.parseDouble(box01.getText())/total;
        nums[4] = Double.parseDouble(box11.getText())/total;
        nums[arraySize-4] = Double.parseDouble(box21.getText())/total;
        nums[arraySize-3] = Double.parseDouble(box02.getText())/total;
        nums[arraySize-2] = Double.parseDouble(box12.getText())/total;
        nums[arraySize-1] = Double.parseDouble(box22.getText())/total;
        main.getImageView().setImage(ImageUtil.convolve(main.getImageView().getImage(), nums));
    }
}
