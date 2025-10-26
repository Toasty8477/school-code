/*
 * Course: CSC-1120A
 * Lab 11: Morse Encoder
 * Name: Alexander Horton
 * Last Updated: 04/15/2025
 */

package hortona;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.sound.sampled.LineUnavailableException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * Controller for Morse Encoder
 */
public class Controller implements Initializable {

    private Alert alert = new Alert(Alert.AlertType.WARNING);

    @FXML
    private MenuItem save;
    @FXML
    private MenuItem play;
    @FXML
    private TextArea decoded;
    @FXML
    private TextArea encoded;

    @FXML
    private void open() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        try (Scanner scanner = new Scanner(file)) {
            String show = "";
            while (scanner.hasNextLine()) {
                show += scanner.nextLine();
                show += "\n";
            }
            decoded.setText(show);
            List<String> encodedMessage = MorseEncoder.encodeMessage(show);
            encoded.setText(encodedMessage.getFirst());
            if (encodedMessage.size() > 1) {
                StringBuilder sb = new StringBuilder();
                for (int i = 1; i < encodedMessage.size(); i++) {
                    sb.append(encodedMessage.get(i)).append("\n");
                }
                alert.setContentText(sb.toString());
                alert.show();
            }
            save.setDisable(false);
            play.setDisable(false);
        } catch (IOException e) {
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }
    @FXML
    private void save() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(null);
        try (PrintWriter printWriter = new PrintWriter(file)) {
            String encodedMessage = encoded.getText();
            printWriter.print(encodedMessage);
        } catch (FileNotFoundException e) {
            alert.setContentText("Could not open file.");
            alert.show();
        }
    }
    @FXML
    private void quit() {
        Stage stage = (Stage) decoded.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void clear() {
        decoded.setText("");
        encoded.setText("");
        save.setDisable(true);
        play.setDisable(true);
    }
    @FXML
    private void about() {
        Alert about = new Alert(Alert.AlertType.INFORMATION);
        about.setHeaderText("About Morse Encoder 3000");
        about.setContentText("Morse Encoder 3000 encodes and plays text files as morse code\n" +
                "Shortcuts:\n" +
                "Super + O: Open File\n" +
                "Super + S: Save Encoded Message\n" +
                "Super + P: Play Encoded Message\n" +
                "Super + Q: Quit\n" +
                "Super + Del: Clear Message\n" +
                "Super + H: Help");
        about.show();
    }
    @FXML
    private void play() throws LineUnavailableException, InterruptedException {
        final int spaceSleep = 150;
        final int lineSleep = 450;
        String encodedMessage = encoded.getText();
        for (int i = 0; i < encodedMessage.length(); i++) {
            if (encodedMessage.charAt(i) == '.') {
                MorseEncoder.playTone(true);
            } else if (encodedMessage.charAt(i) == '-') {
                MorseEncoder.playTone(false);
            } else if (encodedMessage.charAt(i) == ' ') {
                Thread.sleep(spaceSleep);
            } else if (encodedMessage.charAt(i) == '\n') {
                Thread.sleep(lineSleep);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            MorseEncoder.loadTable(Path.of("data/morseCode.txt"));
        } catch (IOException e) {
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }
}
