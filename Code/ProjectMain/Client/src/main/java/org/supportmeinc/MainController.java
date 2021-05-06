package org.supportmeinc;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.*;
import org.supportmeinc.model.Connection;
import org.supportmeinc.model.GuideEditor;
import org.supportmeinc.model.GuideManager;
import org.supportmeinc.model.GuideViewer;
import org.supportmeinc.view.GuideEditorUi;
import org.supportmeinc.view.GuideBrowser;
import org.supportmeinc.view.JFXcontroller;
import org.supportmeinc.view.Toolbar;
import org.supportmeinc.view.*;
import shared.Card;
import shared.Guide;
import shared.Thumbnail;
import shared.User;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class MainController {

    private Main controller;
    private HashMap<SceneName, AnchorPane> scenes;
    private Stage stage;
    private Toolbar toolbarController;
    private GuideManager guideManager;
    private GuideEditor guideEditor;
    private GuideEditorUi guideEditorUi;
    private GuideBrowser guideBrowser;
    private GuideViewerUi guideViewerUi;
    private GuideEditorSave guideEditorSave;
    private GuideViewer guideViewer;


    public MainController(Stage stage, Main controller) {
        this.controller = controller;
        this.stage = stage;
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                stage.close();
                guideManager.disconnect();
            }
        });

        try {
            stage.setScene(new Scene(loadFXML(SceneName.login)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        scenes = new HashMap<>();
        stage.setTitle("supportMe");
        stage.show();
        populateScenes();
        guideEditor = new GuideEditor(this);
    }

    public void setGuideBrowser(GuideBrowser guideBrowser) {
        this.guideBrowser = guideBrowser;
    }

    public void populateScenes() {
        for (SceneName sceneName : SceneName.values()) {
            if(!(sceneName.equals(SceneName.login) || sceneName.equals(SceneName.register) || sceneName.equals(SceneName.toolbar))) {
                try {
                    AnchorPane scene = new AnchorPane(loadFXML(sceneName));
                    scenes.put(sceneName, scene);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public UUID createNewCard() {
        guideEditor.createNewCard();
        return guideEditor.getCurrentCard().getCardUUID();
    }

    public AnchorPane getScenes(SceneName scene) {
        return scenes.get(scene);
    }

    public void registerToolbar(Toolbar toolbar) {
        this.toolbarController = toolbar;
    }

    public Parent loadFXML(SceneName sceneName) throws IOException { //TODO Möjligtvis refactor --> Toolbar

        String resourceName = sceneName.name();
        String fxml = String.valueOf(getClass().getResource("view/" + resourceName + ".fxml"));
        String styleSheet = String.valueOf(getClass().getResource("view/stylesheets/" + resourceName + "Style.css"));

        FXMLLoader fxmlLoader = new FXMLLoader(new URL(fxml));
        Parent root = fxmlLoader.load();
        JFXcontroller jfXcontroller = fxmlLoader.getController();
        jfXcontroller.initData(this);
        root.getStylesheets().add(styleSheet);

        return root;
    }

    public void switchScene(SceneName sceneName) {
        toolbarController.swapScene(scenes.get(sceneName));
    }

    public void sceneSwitch(SceneName sceneName, Event event) throws IOException {
        Scene scene = new Scene(loadFXML(sceneName));
        stage.setScene(scene);
        stage.show();
    }

    public File jfxFileChooser() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(stage);
        return selectedFile;
    }

    public void saveCard(String title, String description, byte[] img, UUID affirmUUID, UUID negativeUUID, UUID cardUUID) {
        guideEditor.saveCard(title, description, img, affirmUUID, negativeUUID, cardUUID);
    }

    public void removeCard(UUID cardUUID) {
        guideEditor.removeCard(cardUUID);
    }

    public UUID[] getGuideEditorCardUUIDs() {
        return guideEditor.getCardsList().keySet().toArray(new UUID[0]);
    }

    public String getCardTitle(UUID uuid) {
        return guideEditor.getCardTitle(uuid);
    }
    public String getCardText(UUID uuid) {
        return guideEditor.getCardText(uuid);
    }
    public UUID getCardAffirmUUID(UUID uuid) {
        return guideEditor.getCardAffirmUUID(uuid);
    }
    public UUID getCardNegUUID(UUID uuid) {
        return guideEditor.getCardNegUUID(uuid);
    }
    public byte[] getCardImage(UUID uuid) {
        return guideEditor.getCardImage(uuid);
    }

    public void setGuideEditorUi(GuideEditorUi guideEditorUi) {
        this.guideEditorUi = guideEditorUi;
    }

    public void setNewGuideEditorModel() {
        this.guideEditor = new GuideEditor(this);
        guideEditorUi.repopulateLists();
        guideEditorSave.repopulateLists();
    }

    public void initGuideEditor() {
        guideEditorUi.createNewCard();
    }

    public void refreshThumbnails() {
        guideManager.getThumbnails();
        Thumbnail[] accessThumbnails = guideManager.getAccessThumbnails();
        Thumbnail[] authorThumbnails = guideManager.getAuthorThumbnails();
        setThumbnailInView(accessThumbnails, authorThumbnails);
    }


    public void setGuideEditorSave(GuideEditorSave guideEditorSave) {
        this.guideEditorSave = guideEditorSave;
    }

    public boolean saveGuide() {
        Guide guide = guideEditor.getOutputGuide();

        if (guide != null) {
            boolean success = guideManager.saveGuide(guide);
            return true;
        } else {
            //TODO alert user if guide error
            return false;
        }
    }

    public void packGuide(String title, String description, byte[] img, UUID affirmUUID) {
        guideEditor.packGuide(title, description, img, affirmUUID);
    }

    public boolean checkCardLinksValid() {
        return guideEditor.checkCardLinksValid();
    }

    public String getAuthor() {
        return guideManager.getCurrentUser().getEmail();
    }

    public void login(String email, String pass) {
        guideManager = controller.Login(email, pass);
        if (guideManager != null) {
            try {
                stage.setScene(new Scene(loadFXML(SceneName.toolbar)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.exit(0);
        }
    }

    public void registerUser(User user) {
        guideManager = controller.register(user);
        if (guideManager != null) {
            try {
                stage.setScene(new Scene(loadFXML(SceneName.toolbar)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.exit(0);
        }
    }

    public void onLoadGuideEditorSave() {
        guideEditorSave.onLoad();
    }

    public void createNewGuide() {
        initGuideEditor();
        switchScene(SceneName.guideEditor);
    }


    public void setThumbnailInView(Thumbnail[] access, Thumbnail[] author) {
        guideBrowser.resetView();
        for (Thumbnail thumbnail: access) {
            guideBrowser.addThumbnailAccess(thumbnail.getTitle(), thumbnail.getImage(), thumbnail.getDescription(), thumbnail.getGuideUUID());
        }
        for (Thumbnail thumbnail: author) {
            guideBrowser.addThumbnailAuthor(thumbnail.getTitle(), thumbnail.getImage(), thumbnail.getDescription(), thumbnail.getGuideUUID());
        }
    }

    public void openGuide(UUID uuid) {
        Guide guide = guideManager.getGuide(uuid);
        guideViewer = new GuideViewer(guide, this);
        Card card = guide.getDescriptionCard();
        guideViewerUi.setCard(card.getTitle(), card.getImage(), card.getText());
        switchScene(SceneName.guideViewer);
    }

    public void getNext(boolean choice) {
        Card card = guideViewer.getNext(choice);
        guideViewerUi.setCard(card.getTitle(), card.getImage(), card.getText());
    }

    public void setGuideViewer(GuideViewerUi guideViewerUi) {
        this.guideViewerUi = guideViewerUi;
    }

    public void lastCard() {
        guideViewerUi.lastCard();
    }

    public ArrayList<String> getAccessList() {
        ArrayList<String> temp;
        if(controller.getConnection().getAccessList(guideEditor.getGuideUUID()) != null) {
            temp = (ArrayList<String>) Arrays.asList(controller.getConnection().getAccessList(guideEditor.getGuideUUID()));
        } else {
            temp = null;
        }
        return temp;
    }

    public Connection getConnection() {
        return controller.getConnection();
    }

    public UUID getOutputGuideUUID() {
        return guideEditor.getOutputGuide().getGuideUUID();
    }

    public void setEditGuide(UUID uuid) {
        Guide guide = guideManager.getGuide(uuid);
        guideEditor = new GuideEditor(this);
        guideEditor.setEditGuide(guide);
        switchScene(SceneName.guideEditor);
        guideEditorUi.repopulateLists();
    }

    public boolean checkAccessList() {
        boolean retVal;

        retVal = getConnection().getAccessList(guideEditor.getGuideUUID()) != null;

        return retVal;
    }

    public void grantAccess(UUID uuid, String email) {
        getConnection().grantAccess(uuid, email);
    }

    public void revokeAccess(UUID uuid, String email) {
        getConnection().revokeAccess(uuid, email);
    }
}
