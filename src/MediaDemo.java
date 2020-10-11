import javafx.application.Application;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;

import javafx.event.EventHandler;

import javafx.geometry.Pos;

import javafx.scene.Scene;

import javafx.scene.control.Button;

import javafx.scene.control.Control;
import javafx.scene.control.Label;

import javafx.scene.control.Slider;

import javafx.scene.image.Image;

import javafx.scene.image.ImageView;

import javafx.scene.layout.BorderPane;

import javafx.scene.layout.HBox;

import javafx.scene.layout.Region;

import javafx.scene.media.Media;

import javafx.scene.media.MediaPlayer;

import javafx.scene.media.MediaView;

import javafx.stage.Stage;

import javafx.util.Duration;

import java.net.MalformedURLException;
import java.net.URL;

public class MediaDemo extends Application {
//http://music.163.com/outchain/player?type=2&id=432506345&auto=1
    private static final String MEDIA_URL = "http://explicative-offense.000webhostapp.com/upload/testvideo.mp4";
    private static Media media;
    private static MediaPlayer mediaPlayer;
    private Slider progress;
    private Duration duration;
    private double volumeValue;


    public void start(Stage primaryStage) throws MalformedURLException {
        String path = "file:///C:/Users/user/OneDrive/桌面/pics/one-punch-man-amv-opening-1-the-hero.mp4";
//        path="file:///D:/Music/我是歌手_第二季_第6期_柔情品冠温暖听众《我不难过》_【湖南卫视官方版1080P】20140207.mp3";

        java.net.URL paath=MediaDemo.class.getResource("/sound/music.mp3");
        URL url=getClass().getResource("/sound/music.mp3");
//java.net.URL url=new java.net.URL(path);


         media = new Media(url.toExternalForm());//

        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnReady(new Runnable() {
            @Override
            public void run() {
                duration=mediaPlayer.getMedia().getDuration();


            }
        });

        MediaView mediaView = new MediaView(mediaPlayer);

        final Button playButton = new Button(">");

        playButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override

            public void handle(ActionEvent e) {

                if (playButton.getText().equals(">")) {

                    mediaPlayer.play();

                    playButton.setText("||");

                } else {

                    mediaPlayer.pause();

                    playButton.setText(">");

                }

            }

        });

        Button rewindButton = new Button("<<");

//按鈕事件處理

        rewindButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override

            public void handle(ActionEvent e) {
                System.out.println(mediaPlayer.getCurrentTime());
//                mediaPlayer.seek(Duration.seconds(0));
                mediaPlayer.seek(Duration.ZERO);


              /*  if(mediaPlayer.getCurrentTime()==Duration.ZERO){
                    mediaPlayer.stop();
                    playButton.setText(">");
                }*/

            }

        });


//音量條的控制

        Slider slVolume = new Slider();

        slVolume.setPrefWidth(150);

        slVolume.setMaxWidth(Region.USE_COMPUTED_SIZE);

        slVolume.setMinWidth(30);




        ImageView imageView = new ImageView(new Image("file:///C:/Users/user/OneDrive/桌面/pics/Kevin大頭貼"));

        //


        progress = new Slider();

        progress.setPrefWidth(150);

        progress.setMaxWidth(Region.USE_COMPUTED_SIZE);

        progress.setMinWidth(30);
        progress.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                if(progress.isValueChanging()){
                    mediaPlayer.seek(duration.multiply(progress.getValue()/100.0));

                }
            }
        });

        mediaPlayer.volumeProperty().bind(slVolume.valueProperty().divide(100));

        mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observableValue, Duration duration, Duration t1) {
             updateTime();
            }
        });

//布局格式

        HBox hBox = new HBox(10);

        hBox.setAlignment(Pos.CENTER);

        hBox.getChildren().addAll(playButton, rewindButton, new Label("Volume"), slVolume,new Label("Time"),progress);


hBox.getChildren().add(imageView);

//新建一個面板

        BorderPane pane = new BorderPane();

        pane.setCenter(mediaView);

        pane.setBottom(hBox);

//new一個場景對象

        Scene scene = new Scene(pane, 650, 650);

        primaryStage.setTitle("MediaPlayer");

        primaryStage.setScene(scene);

        primaryStage.show();

    }

    public static void main(String[] args) {

        Application.launch(args);

    }

    public void updateTime() {
        if (progress != null ) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Duration currentTime = mediaPlayer.getCurrentTime();

                    progress.setDisable(duration.isUnknown());   //无法读取时间是隐藏进度条
                    if (!progress.isDisabled() && duration.greaterThan(Duration.ZERO) && !progress.isValueChanging()) {
                        progress.setValue(currentTime.toMillis() / duration.toMillis() * 100);   //设置进度条
                    }
                }
            });
        }
    }



}



