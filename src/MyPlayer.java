import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MyPlayer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage stage) throws Exception {
        stage.setTitle("My Player");

        // 使用 StackPane 可使影片在中間播放，使用 Group 則會靠左上對齊
        StackPane root = new StackPane();
        Media media = new Media("file:///C:/Users/user/OneDrive/桌面/2020_09_11.mp4"); // 影片路徑

        MediaPlayer player = new MediaPlayer(media);
        MediaView view = new MediaView(player);

        // view.fitWidthProperty().set(1920); // 手動設定解析度 (寬度)
        // view.fitHeightProperty().set(1080); // 手動設定解析度 (高度)

        // 自動設定解析度
        view.fitWidthProperty().bind(Bindings.selectDouble(view.sceneProperty(), "width"));
        view.fitHeightProperty().bind(Bindings.selectDouble(view.sceneProperty(), "height"));

        view.setPreserveRatio(true); // 視窗縮放的時候，必須維持比例

        root.getChildren().add(view);
        Scene scene = new Scene(root, 600, 400, Color.BLACK); // 設定寬高，背景顏色為黑色
        stage.setScene(scene);
//        stage.setFullScreen(true); // 設定全螢幕
        stage.show();

        // player.setMute(true); // 靜音
        // player.setRate(10); // 播放速度快10倍
        player.setCycleCount(MediaPlayer.INDEFINITE); // 循環播放
        player.play(); // 開始播放
    }
}
