package gamePackage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
public class GameControler {
    //secret code length
    private static final int game_length=4;
    //num of attempts
    private static final int max_attempts=5;
    //All Colors in box
    private List<Color> colors= Arrays.asList(
            Color.BLUE,Color.GREEN,Color.RED,Color.YELLOW,Color.ORANGE,Color.PURPLE
    );;
    //Color user will chose
    private List<Color> secret_colors;
    //attempts counter
    private int attempts;
    //color will guess
    private Color [] guess=new Color[game_length];
    //Main Edge
    private VBox mainlayout;
    //guess buttons
    private GridPane guessgrid;
    //feedback message
    private Label feedbacklabel;
    //timer
    private int seconds=0;
    private int minuts=0;
    private Timeline timer;
    private Label timerlabel;
    //display game result label
    private Label resultlabel;
    //class constractor
    public GameControler(){
        attempts=0;
        generateSecretCode();

    }

    private void generateSecretCode() {
        //main colors in box
        secret_colors=new ArrayList<>(colors);
        //to put colors in random places
        Collections.shuffle(secret_colors);
        // to store user colors from 0 to 3  just for colors
        secret_colors= secret_colors.subList(0, game_length);
    }
    public VBox createGameLayout() {
        mainlayout = new VBox(20);
        mainlayout.setAlignment(Pos.CENTER);
        timerlabel = new Label("00:00");
        timerlabel.setId("timerlabel");
        resultlabel = new Label();
        resultlabel.setFont(new javafx.scene.text.Font("Arial", 16));
        resultlabel.setTextFill(Color.WHITE);
        Label titlelable = new Label("Welcom to MasterMind Game");
        titlelable.setFont(new javafx.scene.text.Font("Arial", 22));
        titlelable.setTextFill(Color.WHITE);
        Label instructionlabel = new Label("Guess the secret code And You Have Just 5 Attempts");
        instructionlabel.setFont(new javafx.scene.text.Font("Arial", 12));
        instructionlabel.setTextFill(Color.WHITE);
        guessgrid = new GridPane();
        guessgrid.setHgap(15);
        guessgrid.setVgap(15);
        guessgrid.setAlignment(Pos.CENTER);
       for (int i = 0; i < game_length; i++) {
           Button button = new Button("Choose Color");
           button.setPrefSize(120,50);
           button.setId("colorButton");
           final int index =i;
           button.setOnAction(e -> pickColor(button,index));
           guessgrid.add(button,i,0);
       }
       Button submitbutton = new Button("Submit");
       submitbutton.setId("submitbutton");
       submitbutton.setOnAction(e -> sumbitGuess());
       feedbacklabel = new Label();
       feedbacklabel.setFont(new javafx.scene.text.Font("Arial", 14));
       feedbacklabel.setTextFill(Color.WHITE);
        HBox timerBox = new HBox(20);
        timerBox.setAlignment(Pos.TOP_LEFT);
        timerBox.getChildren().add(timerlabel);
        mainlayout.getChildren().addAll(titlelable,instructionlabel,timerBox,guessgrid,submitbutton,feedbacklabel,resultlabel);
        return mainlayout;
    }
    public void startTimer() {
        timer = new Timeline(new KeyFrame(Duration.seconds(1),e-> {
            seconds++;
            if (seconds == 60) {
                seconds = 0;
                minuts++;
            }
            updateTimerLabel();
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    private void updateTimerLabel() {
        String time = String.format("%02d:%02d", minuts, seconds);
        timerlabel.setText(time);
    }

    private void pickColor(Button button, int index) {
        Color chosen = chooseRandomColor();
        button.setStyle("-fx-background-color: " + toHexString(chosen) +";");
        guess[index]=chosen;
    }
    private String toHexString(Color c) {
        int r =(int) (c.getRed()*255);
        int g =(int) (c.getGreen()*255);
        int b =(int) (c.getBlue()*255);
        return String.format("#%02x%02x%02x",r,g,b);
    }
    private Color chooseRandomColor() {
        Random rand = new Random();
        return colors.get(rand.nextInt(colors.size()));
    }

    private void sumbitGuess() {

        if (attempts < max_attempts) {
            boolean guessed = true;
        for (Color guess : guess) {
            if (guess==null){
                guessed=false;
                break;
            }
        }
    if (!guessed) {
        feedbacklabel.setText("Choose Four Colors!!!");
        feedbacklabel.setTextFill(Color.CORAL);
        return;
    }
    String feedback = givefeedback(guess);
    feedbacklabel.setText(feedback);
    feedbacklabel.setTextFill(Color.WHITE);
    attempts++;
    if(isCorrectGuess(guess)){
        feedbacklabel.setText("Congratulations!!!");
        feedbacklabel.setTextFill(Color.GREEN);
        end(true);
    } else if (attempts == max_attempts) {
        feedbacklabel.setText("Try Again!!!");
        feedbacklabel.setTextFill(Color.RED);
        end(false);
    }
        }
    }
    private void end(boolean win) {
        timer.stop();
        if (win) {
            resultlabel.setText("You Win! in : "+ String.format("%02d:%02d",minuts,seconds));
            resultlabel.setTextFill(Color.GREEN);
        }else {
            resultlabel.setText("You Lost! in : "+ String.format("%02d:%02d",minuts,seconds));
            resultlabel.setTextFill(Color.RED);
        }

    }

    private boolean isCorrectGuess(Color[] guess) {

        return Arrays.equals(guess, secret_colors.toArray());
    }

    private String givefeedback(Color[] guess) {
       int correct_position=0;//counter for rghit positons guess
        int correct_color=0;//counter for rghit colors guess
        for (int i = 0; i < guess.length; i++) {
            if (guess[i] != null && guess[i].equals(secret_colors.get(i))) {
                correct_position++;
            } else if (guess[i] != null && secret_colors.contains(guess[i])) {
                correct_color++;
            }
        }
        return "Correct position : " + correct_position + ", Correct color but wrong position : " + correct_color;
    }

}
