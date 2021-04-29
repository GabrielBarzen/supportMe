package org.supportmeinc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.supportmeinc.view.*;
import org.supportmeinc.view.GuideEditorUi;
import shared.Card;
import org.supportmeinc.model.*;
import shared.User;

import java.io.*;
import java.net.ConnectException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.HashMap;



/**
 * JavaFX App
 */
public class Main extends Application {

    private static Scene scene;
    private static Stage mainStage;
    private int port;
    private String ip;
    private Connection connection;
    private GuideManager guideManager;
    private GuideEditor guideEditor;

    public static void main(String[] args) {
        launch();
    }

    public void startBackend() {
        readConfig(getClass().getResource("config.conf"));
        System.out.println("running init");

        User replaceWithUserFromLoginScreen = new User("Nicholas","6nice9","NiCeRdIcErDeLuXePrOfUsIoNeXTrEaMSdReaAMS", ImageUtils.toBytes("FinalLogotyp.png"));
        replaceWithUserFromLoginScreen.setNewUser(false);
        try {
            connection = new Connection(ip, port, replaceWithUserFromLoginScreen); //Todo : replace with user from login screen
            guideManager = new GuideManager(connection);
        } catch (IOException e) {
            guideManager = new GuideManager();
			System.out.println("Cannot create a connection, starting in offline mode");
            //e.printStackTrace();
        }

        System.out.println(guideManager.getGuide(0).getThumbnail().getTitle());
    }

    public void testCard() { //TODO Stubbe, eliminera
        Card testCard = guideManager.getGuide(0).getDescriptionCard();
        cardViewerController.setCard(testCard.getTitle(), testCard.getImage(), testCard.getText());
    }

    //Configuration methods//
    private void readConfig(URL url) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(Paths.get(url.toURI()).toFile()))){
            String configEntry;

            while ((configEntry = bufferedReader.readLine()) != null) {
                String[] entry = configEntry.split("=");
                switch (entry[0]) {
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

        } catch (FileNotFoundException e) {
            System.out.println("Config file not found");
        } catch (IOException e) {
            System.out.println("Read exception in config");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
    //Model methods//

    public Card getGuide(int index) {
        return guideManager.getGuide(index).getDescriptionCard();
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
        mainStage = stage;
        scene = new Scene(loadFXML("toolbar"));
        stage.setTitle("supportMe");
        stage.setScene(scene);
        stage.show();
        startBackend();
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
        if (viewController instanceof Login) {
            loginController = (Login) viewController;
        }

        if (viewController instanceof Register) {
            registerController = (Register) viewController;
        }

        if (viewController instanceof GuideBrowser) {
            guideBrowserController = (GuideBrowser) viewController;
        }

        if (viewController instanceof GuideEditorUi) {
            guideEditor = new GuideEditor();
            guideEditorUiController = (GuideEditorUi) viewController;
            guideEditorUiController.populateListView();
            guideEditorUiController.populateComboBoxes();

        }

        if (viewController instanceof CardViewer) {
            cardViewerController = (CardViewer) viewController;
        }

        if (viewController instanceof CardEditor) {
            cardEditorController = (CardEditor) viewController;
        }

        if (viewController instanceof Toolbar) {
            toolbarController = (Toolbar) viewController;
        }
    }

    public HashMap<UUID, Card> getCardsList() {
        return guideEditor.getCardsList();
       /* HashMap<UUID,Card> temp = new HashMap<>();
        UUID uuid;
        Card card = new Card();
        card.setTitle("1 Test");
        temp.put(UUID.randomUUID(), card);
        return temp;*/
    }

    public void addCardToList(String title, String description, File img, UUID affirmUUID, UUID negativeUUID) {
        guideEditor.addNewCard(title, description, img, affirmUUID, negativeUUID);
    }

    public File jfxFileChooser() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(mainStage);
        return selectedFile;
    }

    public void updateCard(String title, String text, File img, UUID affirmUUID, UUID negativeUUID, UUID cardUUID) {
        guideEditor.updateCard(title, text, img, affirmUUID, negativeUUID, cardUUID);
    }

    public void removeCard(UUID cardUUID) {
        guideEditor.removeCard(cardUUID);
    }
}
