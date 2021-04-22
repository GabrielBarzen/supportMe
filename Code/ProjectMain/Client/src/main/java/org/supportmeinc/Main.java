package org.supportmeinc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.supportmeinc.view.*;
import org.supportmeinc.view.GuideEditorUi;
import shared.Card;
import org.supportmeinc.model.*;
import shared.User;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;


/**
 * JavaFX App
 */
public class Main extends Application {

    private static Scene scene;
    private static Stage stage;
    private int port;
    private String ip;
    private Connection connection;
    private GuideManager guideManager;

    public static void main(String[] args) {
        launch();
    }

    public void startBackend() {
        readConfig(getClass().getResource("config.conf"));
        System.out.println("running init");

        User replaceWithUserFromLoginScreen = new User("Nicholas","6nice9","NiCeRdIcErDeLuXePrOfUsIoNeXTrEaMSdReaAMS",JfxUtils.toBytes("FinalLogotyp.png"));
        replaceWithUserFromLoginScreen.setNewUser(false);
        connection = new Connection(ip, port, replaceWithUserFromLoginScreen); //Todo : replace with user from login screen
        guideManager = new GuideManager(connection);
//      testCard();
    }

    public void testCard() { //TODO Stubbe, eliminera

        Card testCard = initGuide(0);
        cardViewerController.setCard(testCard.getTitle(), testCard.getImage(), testCard.getText());
    }

    //Configuration methods//
    private void readConfig(URL url) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(Paths.get(url.toURI()).toFile()))){
            String configEntry;

            while ((configEntry = bufferedReader.readLine()) != null){
                String[] entry = configEntry.split("=");
                switch (entry[0]){
                    case "ip":
                        ip = entry[1];
                        break;
                    case "port":
                        port = Integer.parseInt(entry[1]);
                        break;
                    default:
                        System.out.println("Config entry : " + entry[0] + " is not a valid config entry");
                }
            }

        } catch (FileNotFoundException e){
            System.out.println("Config file not found");
        } catch (IOException e){
            System.out.println("Read exception in config");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
    //Model methods//

    public Card initGuide(int index) {
        return guideManager.initGuide(index);
    }

    public Card getCard(boolean choice) {
        return null;
    }

    public void exitCardView() {

    }

    //UI methods// //TODO Möjligtvis refactor --> Toolbar
    private CardEditor cardEditorController;
    private CardViewer cardViewerController;
    private GuideBrowser guideBrowserController;
    private GuideEditorUi guideEditorUiController;
    private Login loginController;
    private Register registerController;
    private Toolbar toolbarController;


    @Override
    public void start(Stage stage) throws IOException {
      //  scene = new Scene(loadFXML("toolbar"));
        scene = new Scene(loadFXML("login"));
        stage.setTitle("supportMe");
        stage.setScene(scene);
        stage.show();
        System.out.println("nice");
        startBackend();
    }

    public void changeScene(String fxml) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        stage.getScene().setRoot(root);
    }

    public void checkUser(String email, String password){

    }

    public void setRoot(String resourceName) throws IOException { //TODO Möjligtvis refactor --> Toolbar



    }

    public Parent loadFXML(String resourceName) throws IOException { //TODO Möjligtvis refactor --> Toolbar

        System.out.println();
        System.out.println(getClass().getResource("view/" + resourceName + ".fxml"));
        System.out.println(getClass().getResource("view/stylesheets/" + resourceName + "Style.css"));
        System.out.println();

        String fxml = String.valueOf(getClass().getResource("view/" + resourceName + ".fxml"));
        String styleSheet = String.valueOf(getClass().getResource("view/stylesheets/" + resourceName + "Style.css"));

        FXMLLoader fxmlLoader = new FXMLLoader(new URL(fxml));

        Parent root = fxmlLoader.load();
        System.out.println("fxml item : " + root.getClass());
        JFXcontroller jfXcontroller = fxmlLoader.getController();
        jfXcontroller.initData(this);

        root.getStylesheets().add(styleSheet);

        return root;
    }

    public void registerController(JFXcontroller viewController) { //TODO Möjligtvis refactor --> Toolbar
        if (viewController instanceof Login) {loginController = (Login) viewController;}
        if (viewController instanceof Register) {registerController = (Register) viewController;}
        if (viewController instanceof GuideBrowser) {guideBrowserController = (GuideBrowser) viewController;}
        if (viewController instanceof GuideEditorUi) {
            guideEditorUiController = (GuideEditorUi) viewController;}
        if (viewController instanceof CardViewer) {cardViewerController = (CardViewer) viewController;}
        if (viewController instanceof CardEditor) {cardEditorController = (CardEditor) viewController;}
        if (viewController instanceof Toolbar) {toolbarController = (Toolbar) viewController;}
    }

}