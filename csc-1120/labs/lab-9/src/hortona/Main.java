/*
 * Course: CSC1020
 * Morse Code Decoder
 * Name: Alexander Horton
 * Last Updated: 03/28/2025
 */

package hortona;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Launching class for JavaFX
 */
public class Main extends Application {

    private static MorseTree<Character> decoderTree;

    public static void main(String[] args) throws IOException {
        decoderTree = MorseDecoder.loadDecoder(Path.of("data/morseCode.txt"));
        launch(args);
    }

    public static MorseTree<Character> getDecoderTree() {
        return decoderTree;
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("morseDecoder.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.setTitle("Morse Decoder 3000");
        stage.show();
    }
}