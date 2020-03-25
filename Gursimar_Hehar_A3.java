package gursimar_hehar_a3;

import java.io.File;
import com.google.gson.*;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.Scanner;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Gursimar Singh Hehar This Program Allows Users To add, remove or
 * view,save and load different subject's marks on a graphical user interface.
 */
public class Gursimar_Hehar_A3 extends Application implements EventHandler<ActionEvent> {

    //Creating Global varibales to be used across this class in different methods.
    private TextField txtCourse;
    private TextField txtGrade;
    private ArrayList<Course> courseDetails;
    private Button btnRemove;
    private Button btnCreateFile;
    private Button btnReadFile;
    private Button btnAdd;
    private Button btnRemoveAll;
    private double grades;
    private ListView<Course> lstShowGrades;
    Course course;
    Stage stage;
    Alert alert;

    //Setting Up The Gui Or The Frontend For The User.
    @Override
    public void start(Stage stage) {

        //Creating All Buttons
        btnAdd = new Button("Add");
        btnRemove = new Button("Remove");
        btnReadFile = new Button("Read File");
        btnCreateFile = new Button("Create File");
        btnRemoveAll = new Button("Remove All");

        //Assisgins ids to the buttons to use css from Styles.css
        btnAdd.getStyleClass().add("Buttons");
        btnRemove.getStyleClass().add("Buttons");
        btnReadFile.getStyleClass().add("Buttons");
        btnCreateFile.getStyleClass().add("Buttons");
        btnRemoveAll.getStyleClass().add("Buttons");

        //Creating TextField and labels For The USer To Input Subject and Their Marks.
        Label lblCourse = new Label("Course:");
        txtCourse = new TextField();
        Label lblGrade = new Label("Grade:");
        txtGrade = new TextField();

        //Styling Labels
        lblCourse.setStyle("-fx-font-weight: bold;");
        lblGrade.setStyle("-fx-font-weight: bold;");

        //Creating ListView to Show Subjects and their respective grades
        lstShowGrades = new ListView<>();
        lstShowGrades.setPrefWidth(300);
        lstShowGrades.setPrefHeight(300);
        lstShowGrades.setId("listView");

        //App Name
        stage.setTitle("Assignment 3");

        //App Icon
        Image image = new Image("gursimar_hehar_a3/logo.png");
        stage.getIcons().add(image);

        //Using GridPane To get add Course,Grade and their labels.
        GridPane gridPane = new GridPane();

        //Setting padding around gridpane.
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        //Adding vertical and horizontal gap between the elmenents on stage.
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        //Adding objects to gridpane also aligning them on the scene
        gridPane.add(lblCourse, 0, 0);
        gridPane.add(txtCourse, 1, 0);

        //This is for gradelabel and grade textfield
        gridPane.add(lblGrade, 0, 1);
        gridPane.add(txtGrade, 1, 1);

        //Adding an hbox to add :add, remove buttons and listview.
        HBox topBox = new HBox();
        topBox.getChildren().addAll(btnAdd, btnRemove, btnRemoveAll);
        topBox.setSpacing(20);
        topBox.setPadding(new Insets(10, 10, 10, 10));

        //Hbox For TableView
        HBox middleBox = new HBox();
        middleBox.setAlignment(Pos.CENTER);
        middleBox.setPadding(new Insets(10, 10, 10, 10));
        middleBox.getChildren().addAll(lstShowGrades);

        //Another hbox that contains bottom buttons
        HBox bottomBox = new HBox();
        bottomBox.getChildren().addAll(btnCreateFile, btnReadFile);
        bottomBox.setSpacing(20);
        bottomBox.setPadding(new Insets(10, 10, 10, 10));

        //Adding each element to a vbox 
        VBox root = new VBox(gridPane, topBox, middleBox, bottomBox);

        //Creating a new scene using vbox and setting its width and length .
        Scene scene = new Scene(root, 400, 600);

        //Loading up css
        scene.getStylesheets().add("gursimar_hehar_a3/Styles.css");

        //adding scene to stage and showing it.
        stage.setScene(scene);
        stage.show();

        //Adding actions to buttons
        btnAdd.setOnAction(this);
        btnRemove.setOnAction(this);
        btnReadFile.setOnAction(this);
        btnCreateFile.setOnAction(this);
        btnRemoveAll.setOnAction(this);

        //Setting the add button as default
        btnAdd.setDefaultButton(true);

        //CourseDetails arrayList to add marks and subject names.
        courseDetails = new ArrayList<>();

    }

    //Main Method To Launch The App.
    public static void main(String[] args) {
        //launch method required to launch gui for users.
        launch(args);
    }

    @Override
    //This method handles all the actions that we assign to buttons.
    public void handle(ActionEvent event) {

        //If the user types grades and want to add it.
        if (event.getSource() == btnAdd) {

            //Now if user enters a string or a number more than 100, this condition comes into action.
            if (isDouble(txtGrade) == false || grades > 100 || grades < 0 || txtGrade.getText().isEmpty() || txtCourse.getText().isEmpty()) {

                //Creating alertbox that user entered a wrong value ie a string or a number more than 100
                error("Incorrect Value, Please Try Again");

                // color turns red if wrong info added.               
                txtGrade.setStyle("-fx-background-color:#cc354e;-fx-text-fill:white;-fx-font-weight:bold;");
            } // If everything is proper,the details are added to arraylist and displayed on listview.
            else {

                //Instance of course is called
                course = new Course(txtCourse.getText(), grades);

                // Details are added
                courseDetails.add(course);
                lstShowGrades.getItems().add(course);

                //styles set to null if every info is correct.
                txtGrade.setStyle(null);

                //clear the input fields so users don't have to delete everything for entering more data
                txtGrade.clear();
                txtCourse.clear();
            }
        } //If the user wants to remove the course
        else if (event.getSource() == btnRemove) {
            //Creating alert so that user gets a chance to revert if he clicked remove by chance
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setContentText("Delete The Selected Item?");
            alert.setHeaderText(null);

            //Creating ok and cancel optional buttons
            Optional<ButtonType> action = alert.showAndWait();

            //if ok is pressed item gets deleted
            if (action.get() == ButtonType.OK) {
                Course selectedItem =lstShowGrades.getSelectionModel().getSelectedItem();
                 lstShowGrades.getItems().removeAll(selectedItem);
                //Updates the arraylist alonside the deleted items
                courseDetails.remove(selectedItem);
            }
            //if cancel main menu is displayed    
            if (action.get() == ButtonType.CANCEL) {
                alert.close();
            }

        } //This part is useful if the user decides to delete all enteries
        else if (event.getSource() == btnRemoveAll) {

            //First alert is displayed,to Ask confirmation from the user
            alert = new Alert(AlertType.CONFIRMATION);
            alert.setContentText("This will delete all items, Continue?");
            alert.setHeaderText(null);

            //Optional buttons
            Optional<ButtonType> action = alert.showAndWait();

            //If ok is pressed all items are deleted and elements are also removed from arraylist
            if (action.get() == ButtonType.OK) {
                lstShowGrades.getItems().clear();
                courseDetails.removeAll(courseDetails);

                //else main menu is displayed.               
            }
            if (action.get() == ButtonType.CANCEL) {
                alert.close();
            }
        } //If the user wants to save his data.
        else if (event.getSource() == btnCreateFile) {
            //we will save our data as json, so we'll create a new Json Object
            Gson rootObj = new Gson();
            //here we convereted coursedetails to json object
            String jsonCourseObj = rootObj.toJson(courseDetails);

            //Initial directory          
            File file = new File("C:\\");
            FileChooser chooser = new FileChooser();

            //Set extension filter for JSON files
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON", "*.json");
            chooser.getExtensionFilters().add(extFilter);

            if (file != null) {
                //Show save file dialog
                file = chooser.showSaveDialog(stage);
                try {
                    //Writing the file using printwriter
                    PrintWriter writer;
                    writer = new PrintWriter(file);
                    writer.println(jsonCourseObj);
                    writer.close();
                } catch (IOException ex) {
                    error("Error, Please Try Again");

                }
            }

        } else {
            File file = new File("C:\\");
            FileChooser chooser = new FileChooser();
            chooser.setInitialDirectory(file);
            chooser.setTitle("Open File");
            File openFile = chooser.showOpenDialog(stage);
            StringBuilder stringBuilder = new StringBuilder();

            //Set extension filter for JSON files
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON", "*.json");
            chooser.getExtensionFilters().add(extFilter);

            if (openFile != null) {
                try {
                    //Loading file
                    Scanner input = new Scanner(openFile);
                    while (input.hasNextLine()) {
                        String line = input.nextLine();
                        stringBuilder.append(line);

                        //Two datafields to get parsed data out of json file and store it
                        String courseIn = "";
                        double gradeIn = 0;
                        
                        //Now we parse the loaded json file to get its data
                        JSONParser parser = new JSONParser();
                        //Creating an json array to store parsed data
                        JSONArray objArray = (JSONArray) parser.parse(stringBuilder.toString());
                        for (int i = 0; i < objArray.size(); i++) {
                            //Getting the data
                            JSONObject objIn = (JSONObject) objArray.get(i);
                            courseIn = (String) objIn.get("courseName");
                            gradeIn = (double) objIn.get("grades");
                            course = new Course(courseIn, gradeIn);
                            //Display it on listview.
                            courseDetails.add(course);
                            lstShowGrades.getItems().add(course);
                        }
                    }
                } catch (Exception ex) {
                    error("Error, Please Try Again");

                }

            }
        }
    }

    /**
     * Gets TextField that stores grades
     *
     * @param grade
     * @return true if Text field is a double and false if not.
     */
    public boolean isDouble(TextField grade) {
        //trying to parse grades into double,if true doubles are created
        try {
            grades = Double.parseDouble(grade.getText());
            return true;
            //if not,false is returned...
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //Method for creating an alertBox.
    public void error(String errorMessage) {
        alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }

}
