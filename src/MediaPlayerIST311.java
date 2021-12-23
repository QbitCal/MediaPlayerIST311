
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.scene.media.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import javafx.util.*;
import javax.imageio.ImageIO;


/**
 * This program plays a media file with the options to
 * play, pause, stop, and exit the program.
 *
 * This program requires the following files to function properly:
 * "bensound-slowmotion.mp3"
 * Royalty Free Music Provided by: www.bensound.com/
 *
 * @author Quenten Calvano
 * @version 12/22/2021
 *
 * (Previously updated: 12/1/2020)
 */

public class MediaPlayerIST311 extends Application {

    public void start(Stage primaryStage) throws IOException {
        //Create a file to link the audio file...
        File mFile = new File("bensound-slowmotion.mp3");
        //Create a Media object linked to the file...
        Media audio = new Media(mFile.toURI().toString());
        //Create the flowpane and set padding...
        FlowPane root = new FlowPane(20,20);
        root.setAlignment(Pos.CENTER); //Center the FlowPane.
        //Set the scene dimensions...
        Scene scene = new Scene(root, 500, 450);
        root.setStyle("-fx-background-color: F9F1F0;"); //Stylize the FlowPane.

        //Create a media player to play the audio file I created...
        MediaPlayer player = new MediaPlayer(audio);
        player.setCycleCount(1); //Set cycle count to 1.

        //Create media player buttons...
        Button playBtn = new Button("Play"); //Plays the song.
        Button pauseBtn = new Button("Pause"); //Pauses the song in place.
        Button stopBtn = new Button("Stop"); //Stops the song and loses current playback position.
        Button exitBtn = new Button("Exit"); //Exits the program.

        //Create a button bar to hold all of the buttons on the same bar....
        ButtonBar options = new ButtonBar();
        options.getButtons().addAll(playBtn, pauseBtn, stopBtn, exitBtn);

        //Create a label for the title...
        Label myLabel = new Label("IST 311 Media Player");
        //Create a label for my name...
        Label myName = new Label("\t\t\t\t\t\tBy, Quenten Calvano");
        myName.setFont(new Font("Cooper Black", 12));//Set font to Cooper Black
        myLabel.setFont(new Font("Cooper Black", 30));//Set font to Cooper Black

        root.getChildren().addAll(myLabel);
        root.getChildren().add(myName);


        String currentDir = System.getProperty("user.dir");

        //Create a string to hold the imageURL...
        String imageURL = "IST311 LP.png";

        //Create a URL from the filename in the current directory and create the image...
        URL url = new File(currentDir + "\\" + imageURL).toURI().toURL();
        BufferedImage buffImage = ImageIO.read(url);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(buffImage, "png", bos );
        Image image = SwingFXUtils.toFXImage(buffImage, null);


        //Create a new ImageView from the chosen image...
        ImageView playImage = new ImageView(image);
        playImage.setFitHeight(300);//Set height.
        playImage.setFitWidth(300);//Set width.
        root.getChildren().add(playImage);//Add the image.

        //Create an animation to rotate the image...
        Animation animation;
        animation = new RotateTransition(Duration.seconds(2.5), playImage); //Set the animation to the playImage.
        ((RotateTransition) animation).setByAngle(360);//Set the animation to rotate clockwise.
        ((RotateTransition)animation).setInterpolator(Interpolator.LINEAR);//Set the image to rotate in place.
        animation.setCycleCount(Animation.INDEFINITE);//Rotate until stopped.

        player.setOnError(() ->
        {
            System.out.println("Stopped");
            player.dispose();
            primaryStage.close();
        });
        player.setOnEndOfMedia(() ->
        {
            System.out.println("Done");
            player.stop();
        });

        //Set the track to start at the beginning...
        player.setStartTime(Duration.seconds(0));

        //Handle the action events for the play button.
        playBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                animation.play(); //Rotate the record.
                player.play(); //Play the music.
            }
        });
        //Handle the action events for the pause button.
        pauseBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                animation.pause();//Pause the rotation.
                player.pause();//Pause the music.
            }
        });
        //Handle the action events for the stop button.
        stopBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                animation.stop();//Stop the rotation.
                player.stop();//Stop the music.
                playImage.setRotate(0);//Reset rotation.
            }
        });
        //Handle the action events for the exit button.
        exitBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                System.exit(0);
            }
        });

        //Finish adding to primaryStage...
        root.getChildren().addAll(options);
        primaryStage.setTitle("Media2");
        primaryStage.setScene(scene);
        //Set the stage to be un-resizable to prevent improper wrapping.
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
