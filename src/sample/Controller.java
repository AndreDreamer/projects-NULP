package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {

    @FXML
    private BorderPane imagePane;

    @FXML
    private Text speedTF;
    @FXML
    TextField textField;
    @FXML
    ImageView imageView;
    @FXML
    private Slider slider;

    private boolean isShowing;
    private double promX, promY;//position of window
    private int speed;//speed of play

    public void initialize() {
        speed = 50;
        setListeners();
    }

    private void setListeners() {
        slider.valueProperty().addListener(e -> {
            speed = (int) slider.getValue();
            speedTF.setText((speed * 1.0) / 1000 + "сек/жест");
        });
    }

    private void changeImageSize() {
        imageView.setFitHeight(imagePane.getHeight());
        imageView.setFitWidth(imagePane.getWidth());

    }

    @FXML
    void goClicked() {
        if (!isShowing) {
            String input = textField.getText().toLowerCase();
            char[] string = input.toCharArray();

            Timer timer = new Timer();
            TimerTask myTask = new TimerTask() {
                int currentCharIndex = 0;
                int size = string.length;

                @Override
                public void run() {
                    if (currentCharIndex < size) {
                        isShowing = true;
                        File file = new File("src/sample/photos/" + string[currentCharIndex++] + ".jpg");
                        changeImageSize();
                        Image image = new Image(file.toURI().toString());
                        imageView.setImage(image);

                    } else {
                        cancel();
                        isShowing = false;
                    }
                }
            };

            timer.schedule(myTask, 0, speed);
        }

    }

    @FXML
    void close() {
        Main.stage.close();
    }

    @FXML
    void max() {
        if (Main.stage.isFullScreen()) {
            Main.stage.setFullScreen(false);
        } else {
            Main.stage.setFullScreenExitHint("");
            Main.stage.setFullScreen(true);
        }
    }

    @FXML
    void min() {
        Main.stage.setIconified(true);
    }


    @FXML
    void titleBarDragged(MouseEvent event) {
        if (!Main.stage.isFullScreen()) {
            Main.stage.setX(event.getScreenX() - promX);
            Main.stage.setY(event.getScreenY() - promY);
        }
    }

    @FXML
    void titleBarPressed(MouseEvent event) {

        promX = event.getSceneX();
        promY = event.getSceneY();
    }
}