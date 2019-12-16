package code;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class ClockController {

    private Clock clock  = new Clock();
    private boolean isAlarm = false;
    private MediaPlayer mediaPlayer;
//    private FadeTransition ftIn, ftOut;
    private DropShadow dsd = new DropShadow();

    @FXML
    private Label clockLabel, dateLabel;
    @FXML
    private ComboBox hourSelector, minuteSelector, daySelector;
    @FXML
    private TableView<Clock> alarmTable;
    @FXML
    private TableColumn<Clock, String> timeCol, dayCol;
//    @FXML
//    private Button stopBtnFade;


    @FXML
    public void initialize() {
        mediaPlayer = new MediaPlayer(new Media(getClass().getResource("/sound/buzzer.mp3").toString()));
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        drawTable();
        updateTable();
        hourSelector.getItems().addAll("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
        minuteSelector.getItems().addAll("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59");
        daySelector.getItems().addAll("Everyday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday");
        resetSelector();

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String currentTime = clock.getCurrentTimeFormat();
                String currentDate = clock.getCurrentDateFormat();
                String currentHour = "" + Integer.parseInt(clock.getCurrentHour());
                String currentMinute = "" + Integer.parseInt(clock.getCurrentMinute());
                String currentSecond = "" + Integer.parseInt(clock.getCurrentSecond());
                String currentDay = clock.getCurrentDay();
                updateDateAndTime(currentTime, currentDate);
                try {
                    if(clock.alarmTime(currentHour, currentMinute, currentSecond, currentDay)) {
                        if(!isAlarm) {
                            isAlarm = true;
                            clockLabel.setTextFill(Color.RED);
                            mediaPlayer.play();
//                            fadeIn();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }), new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    @FXML
    public void addBtn() throws Exception {
        String addHour = hourSelector.getValue().toString();
        String addMinute = minuteSelector.getValue().toString();
        String addDay = daySelector.getValue().toString();
        clock.addTime(new Clock(addHour, addMinute, addDay));
        updateTable();
        resetSelector();
    }

    @FXML
    public void deleteBtn() throws Exception {
        Clock time = alarmTable.getSelectionModel().getSelectedItem();
        if(time != null) {
            clock.deleteTime(time);
            updateTable();
        }
    }

    @FXML
    public void stopBtn(ActionEvent e) throws IOException {
        if(isAlarm) {
            isAlarm = false;
            mediaPlayer.stop();
            clockLabel.setTextFill(Color.BLACK);
//            fadeOut();
            Button b = (Button) e.getSource();
            Stage stage = (Stage) b.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/question.fxml"));
            try {
                Scene scene = new Scene(loader.load(),600,400);
                scene.getStylesheets().add(this.getClass().getResource("/style/StyleSheet.css").toExternalForm());
                stage.setScene(scene);
                ProblemController problemController = (ProblemController) loader.getController();
                stage.setTitle("Alarm Clock");
                stage.show();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void drawTable() {
        timeCol.setCellValueFactory(new PropertyValueFactory<>("format"));
        dayCol.setCellValueFactory(new PropertyValueFactory<>("alarmDate"));
    }

    private void updateTable() {
        alarmTable.setItems(FXCollections.observableArrayList(clock.getAlarm()));
    }

    private void resetSelector() {
        hourSelector.getSelectionModel().selectFirst();
        minuteSelector.getSelectionModel().selectFirst();
        daySelector.getSelectionModel().selectFirst();
    }

    private void updateDateAndTime(String timeFormat, String dateFormat) {
        clockLabel.setText(timeFormat);
        dsd.setOffsetY(1.0f);
        dsd.setColor(Color.valueOf("#ffd1dc"));
        dateLabel.setEffect(dsd);
        dateLabel.setText(dateFormat);

    }

//    private void fadeIn() {
//        ftIn = new FadeTransition(Duration.millis(1000));
//        ftIn.setNode(stopBtnFade);
//        ftIn.setFromValue(0.0);
//        ftIn.setToValue(1.0);
//        ftIn.setCycleCount(1);
//        ftIn.setAutoReverse(false);
//        stopBtnFade.setVisible(true);
//        ftIn.playFromStart();
//    }
//
//    private void fadeOut() {
//        ftOut = new FadeTransition(Duration.millis(1000));
//        ftOut.setNode(stopBtnFade);
//        ftOut.setFromValue(1.0);
//        ftOut.setToValue(0.0);
//        ftOut.setCycleCount(1);
//        ftOut.setAutoReverse(false);
//        stopBtnFade.setVisible(false);
//        ftOut.playFromStart();
//
//    }

}
