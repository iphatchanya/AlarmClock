package code;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;

public class ProblemController {

    private Clock clock = new Clock();
    private Problem problem = new Problem();
    private MediaPlayer mediaPlayer;
    private DropShadow dsp = new DropShadow();
    private DropShadow dsd = new DropShadow();

    @FXML
    private Label clockLabel, dateLabel;
    @FXML
    private TextField answerText;
    @FXML
    private Label problemLabel;


    @FXML
    public void initialize() {
        dsp.setOffsetY(3.0f);
        dsp.setColor(Color.ALICEBLUE);
        problemLabel.setEffect(dsp);
        problemLabel.setText(problem.getProblem());
        mediaPlayer = new MediaPlayer(new Media(getClass().getResource("/sound/buzzer.mp3").toString()));
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String currentTime = clock.getCurrentTimeFormat();
                String currentDate = clock.getCurrentDateFormat();
                updateDateAndTime(currentTime, currentDate);
            }
        }), new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }

    @FXML
    public void submitBtn(ActionEvent e) {
        if(answerText.getText().equals(problem.getAnswer())) {
            mediaPlayer.stop();
            Button b = (Button) e.getSource();
            Stage stage = (Stage) b.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/alarm.fxml"));
            try {
                Scene scene = new Scene(loader.load(),600,400);
                scene.getStylesheets().add(this.getClass().getResource("/style/StyleSheet.css").toExternalForm());
                stage.setScene(scene);
                ClockController clockController = (ClockController) loader.getController();
                stage.setTitle("Alarm Clock");
                stage.show();

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Wrong answer, please try again.");
            alert.showAndWait();
        }
    }
    private void updateDateAndTime(String timeFormat, String dateFormat) {
        clockLabel.setText(timeFormat);
        dsd.setOffsetY(1.0f);
        dsd.setColor(Color.valueOf("#ffd1dc"));
        dateLabel.setEffect(dsd);
        dateLabel.setText(dateFormat);
    }
}
