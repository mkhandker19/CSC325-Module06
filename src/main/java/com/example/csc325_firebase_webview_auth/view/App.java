package com.example.csc325_firebase_webview_auth.view;

import com.example.csc325_firebase_webview_auth.model.FirestoreContext;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuth;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * JavaFX App
 */
public class App extends Application {

    public static Firestore fstore;
    public static FirebaseAuth fauth;
    public static Scene scene;
    private final FirestoreContext contxtFirebase = new FirestoreContext();

    private static final int SPLASH_SCREEN_DELAY = 3000; // 3 seconds delay for splash

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Initialize Firebase services
        fstore = contxtFirebase.firebase();
        fauth = FirebaseAuth.getInstance();
        showSplashScreen(primaryStage);
    }

    private void showSplashScreen(Stage primaryStage) throws IOException {
        // Load the SplashScreen.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/files/SplashScreen.fxml"));
        BorderPane splashPane = loader.load();

        // Create a new stage for the splash screen
        Stage splashStage = new Stage(StageStyle.UNDECORATED);
        splashStage.setScene(new Scene(splashPane));
        splashStage.show();

        // Simulate a loading process in a separate thread
        new Thread(() -> {
            try {
                Thread.sleep(3000); // Simulated delay

                javafx.application.Platform.runLater(() -> {
                    // Close splash screen and show main application
                    splashStage.close();
                    showMainApplication(primaryStage);
                });

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void showMainApplication(Stage primaryStage) {
        try {
            // Load the main application FXML (AccessFBView.fxml)
            scene = new Scene(loadFXML("/files/AccessFBView.fxml"));
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
