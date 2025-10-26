/*
 * Course: CSC1020
 * Morse Code Decoder
 * Name: Alexander Horton
 * Last Updated: 03/28/2025
 */

package hortona;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

/**
 * Controller class for Morse Decoder
 */
public class Controller {
    @FXML
    private Label filename;
    @FXML
    private TextArea encoded;
    @FXML
    private TextArea decoded;

    @FXML
    private void openFile() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        filename.setText(file.getName());
        try(Scanner scanner = new Scanner(file)) {
            StringBuilder stringBuilder = new StringBuilder();
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine());
                stringBuilder.append("\n");
            }
            encoded.setText(stringBuilder.toString());
            List<String> decodedMessage = MorseDecoder.decodeMessage(file, Main.getDecoderTree());
            decoded.setText(decodedMessage.getFirst());
            if (decodedMessage.size() > 1) {
                String warnings = "";
                for (int i = 1; i < decodedMessage.size(); i++) {
                    warnings += decodedMessage.get(i) + "\n";
                }
                Alert alert = new Alert(Alert.AlertType.WARNING, warnings);
                alert.show();
            }
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Could not access chosen file.");
            alert.show();
        }
    }
    @FXML
    private void saveFile() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(null);
        String decodedText = decoded.getText();
        try(PrintWriter printWriter = new PrintWriter(file)) {
            printWriter.print(decodedText);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "File saved successfully.");
            alert.show();
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Could not save to file.");
            alert.show();
        }
    }
}
