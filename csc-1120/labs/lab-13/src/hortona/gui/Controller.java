package hortona.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Arrays;

import hortona.model.AutoCompleter;
import hortona.model.OrderedList;
import hortona.model.UnorderedList;

public class Controller {
    List<String> rawList = new ArrayList<>();
    AutoCompleter unorderedArrayList = new UnorderedList(new ArrayList<>());
    AutoCompleter orderedArrayList = new OrderedList(new ArrayList<>());
    AutoCompleter unorderedLinkedList = new UnorderedList(new LinkedList<>());
    AutoCompleter orderedLinkedList = new OrderedList(new LinkedList<>());
    @FXML
    private TextField search;
    @FXML
    private ListView matches;
    @FXML
    private ToggleGroup unorderedListType;
    @FXML
    private RadioButton ulArrayList;
    @FXML
    private RadioButton ulLinkedList;
    @FXML
    private TextField ulQuery;
    @FXML
    private TextField ulAll;
    @FXML
    private ToggleGroup orderedListType;
    @FXML
    private RadioButton olArrayList;
    @FXML
    private RadioButton olLinkedList;
    @FXML
    private TextField olQuery;
    @FXML
    private TextField olAll;
    @FXML
    private TextField bsQuery;
    @FXML
    private TextField bsAll;

    @FXML
    private void openFile() {
        File file;
        FileChooser chooser = new FileChooser();
        file = chooser.showOpenDialog(null);
        try (Scanner scan = new Scanner(file)){
            while (scan.hasNextLine()) {
                String word = scan.nextLine();
                unorderedArrayList.add(word);
                orderedArrayList.add(word);
                unorderedLinkedList.add(word);
                orderedLinkedList.add(word);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void ulChangeList() {
        if(unorderedListType.getSelectedToggle().equals(ulArrayList)) {
            System.out.println("ArrayList");
        } else {
            System.out.println("LinkedList");
        }
    }
    @FXML
    private void olChangeList() {
        if(orderedListType.getSelectedToggle().equals(olArrayList)) {
            System.out.println("ArrayList");
        } else {
            System.out.println("LinkedList");
        }
    }
    @FXML
    private void displayList() {
        if (search.getText().length() >= 3) {
            ObservableList<String> obList = FXCollections.observableArrayList();
            rawList = Arrays.asList(unorderedArrayList.allMatches(search.getText()));
            for (String s : rawList) {
                obList.add(s);
            }
            matches.setItems(obList);
            timeList();
        }
    }
    private void timeList() {
        long startTime = System.nanoTime();
        unorderedArrayList.exactMatch(search.getText());
        long elapsed = System.nanoTime() - startTime;
        ulQuery.setText(AutoCompleter.format(elapsed));
        startTime = System.nanoTime();
        unorderedArrayList.allMatches(search.getText());
        elapsed = System.nanoTime() - startTime;
        ulAll.setText(AutoCompleter.format(elapsed));
        startTime = System.nanoTime();
        orderedArrayList.allMatches(search.getText());
        elapsed = System.nanoTime() - startTime;
        olQuery.setText(AutoCompleter.format(elapsed));
        startTime = System.nanoTime();
        orderedArrayList.allMatches(search.getText());
        elapsed = System.nanoTime() - startTime;
        olAll.setText(AutoCompleter.format(elapsed));
    }


}
