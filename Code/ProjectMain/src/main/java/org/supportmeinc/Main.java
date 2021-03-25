package org.supportmeinc;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.supportmeinc.Model.Connection;
import org.supportmeinc.Model.GuideManager;
import org.supportmeinc.Model.Card;
import org.supportmeinc.View.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


/**
 * JavaFX App
 */
public class Main extends Application {

    private static Scene scene;
    private int port;
    private String ip;
    private Connection connection;
    private GuideManager guideManager;

    public Main() {
        //readConfig();
        System.out.println("running main");
    }

    @Override
    public void init() throws Exception {

    }

    public static void main(String[] args) {
        launch();
    }

    //Configuration methods//
    private void readConfig() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("config.conf"))){
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
        }
    }
    //Model methods//

    public Card getGuide(int index) {
        return guideManager.getGuide(index);
    }

    public Card getCard(boolean choice) {
        return null;
    }

    public void exitCardView() {

    }

    //UI methods//
    private CardEditor cardEditorController;
    private CardViewer cardViewerController;
    private GuideBrowser guideBrowserController;
    private GuideEditor guideEditorController;
    private Login loginController;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("cardViewer"));
        stage.setScene(scene);
        stage.show();
    }

    public void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));
        Parent returnParent = fxmlLoader.load();
        System.out.println("fxml item : " + returnParent.getClass());
        JFXcontroller jfXcontroller = fxmlLoader.getController();
        jfXcontroller.initData(this);
        System.out.println("new controller of type : " + jfXcontroller.getClass());
        return returnParent;
    }

    public void registerController(JFXcontroller viewController) {
        if (viewController instanceof Login) {loginController = (Login) viewController;}
        if (viewController instanceof GuideBrowser) {guideBrowserController = (GuideBrowser) viewController;}
        if (viewController instanceof GuideEditor) {guideEditorController = (GuideEditor) viewController;}
        if (viewController instanceof CardViewer) { cardViewerController = (CardViewer) viewController;}
        if (viewController instanceof CardEditor) {cardEditorController = (CardEditor) viewController;}
        startBackend();
    }

    private void startBackend() {
        System.out.println("running init");
        connection = new Connection(ip, port);
        guideManager = new GuideManager(connection);
        Card card = getGuide(0);
        cardViewerController.setCard(card.getTitle(),card.getImage(),card.getText());
    }
}