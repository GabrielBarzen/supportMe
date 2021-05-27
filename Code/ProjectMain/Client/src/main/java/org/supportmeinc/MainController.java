package org.supportmeinc;

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
    // Methods for handling user management
    public void login(String email, String pass) {
        User user = new User(email, pass);
        handleUserLoginRegister(user);
    }

    public void registerUser(String email, String userNameString, String pass, byte[] image) {
        User user = new User(email, userNameString, pass, image);
        user.setNewUser(true);
        handleUserLoginRegister(user);
    }

    public void handleUserLoginRegister(User user) {
        try {
            Connection connection = new Connection(controller.getIp(), controller.getPort(), user);
            guideManager = new GuideManager(connection);
            guideManager.setController(this);
            guideBrowser.onlineMode();
        } catch (IOException e) {
            System.out.println("Could not connect");
            if (user.isNewUser()) {
                System.out.println("Shutting down");
                AlertUtils.alertError("Error registering user", "No connection to server!", "Can't register new users in offline-mode, please try again when you are connected to the internet");
                System.exit(0);
            } else {
                System.out.println("Attempting offline mode");
                guideManager = new GuideManager(user);
                if (!guideManager.isHasOfflineGuides()) {
                    AlertUtils.alertError("Error getting offline-guides", "No offline-guides available!",
                            "There does not seems to be any guides saved to be viewed offline, please try again when your internet is back and download the guides you would want access to");

                    System.exit(0);
                } else {
                    guideBrowser.offlineMode();
                }
            }
        }

        if (guideManager != null) {
            try {
                switchLoginStage(SceneName.toolbar);
                refreshThumbnails();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.exit(0);
        }
    }

    public byte[] getUserImage() {
        byte[] userImage = null;
        if (guideManager.getConnection() != null) {
            userImage = guideManager.getConnection().getUser().getImage();
        }
        return userImage;
    }

    public String getUsername() {
        String userName = null;
        if (guideManager.getConnection() != null) {
            userName = guideManager.getConnection().getUser().getUserName();
        }
        return userName;
    }

    //Stage and scene start and setup
    public void populateScenes() {
        for (SceneName sceneName : SceneName.values()) {
            if (!(sceneName.equals(SceneName.login) || sceneName.equals(SceneName.register) || sceneName.equals(SceneName.toolbar))) {
                try {
                    AnchorPane scene = new AnchorPane(loadFXML(sceneName));
                    scenes.put(sceneName, scene);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Parent loadFXML(SceneName sceneName) throws IOException {

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

    public void setGuideBrowser(GuideBrowser guideBrowser) {
        this.guideBrowser = guideBrowser;
    }

    public AnchorPane getScene(SceneName scene) {
        return scenes.get(scene);
    }

    public void registerToolbar(Toolbar toolbar) {
        this.toolbarController = toolbar;
    }

    public void toolbarSwitchSubscene(SceneName sceneName) {
        toolbarController.swapScene(scenes.get(sceneName), sceneName);
    }

    public void switchLoginStage(SceneName sceneName) throws IOException {
        Scene scene = new Scene(loadFXML(sceneName));
        stage.setScene(scene);
        stage.show();
//        toolbarSwitchSubscene(SceneName.guideBrowser);
    }

    //GuideBrowser methods
    public void setThumbnailInView(Thumbnail[] access, Thumbnail[] author) {
        guideBrowser.resetView();
        for (Thumbnail thumbnail: access) {
            guideBrowser.addThumbnailAccess(thumbnail.getTitle(), thumbnail.getImage(), thumbnail.getDescription(), thumbnail.getGuideUUID());
        }
        for (Thumbnail thumbnail: author) {
            guideBrowser.addThumbnailAuthor(thumbnail.getTitle(), thumbnail.getImage(), thumbnail.getDescription(), thumbnail.getGuideUUID());
        }
    }
    public void setThumbnailInView(Thumbnail[] downloaded) {
        guideBrowser.resetView();
        for (Thumbnail thumbnail: downloaded) {
            guideBrowser.addThumbnailDownloaded(thumbnail.getTitle(), thumbnail.getImage(), thumbnail.getDescription(), thumbnail.getGuideUUID());
        }
    }

    public void openGuide(UUID uuid) {
        Guide guide = guideManager.getGuide(uuid);
        guideViewer = new GuideViewer(guide, this);
        Card card = guide.getDescriptionCard();
        guideViewerUi.setStartButtons();
        guideViewerUi.setCard(card.getTitle(), card.getImage(), card.getText());
        toolbarSwitchSubscene(SceneName.guideViewer);
    }

    //Guide editor methods

    /*
    Retrieves finalized guide from guideEditor.
    If the guide is valid, aka not null, it attempts to save the guide to the server via guideManager.
    If an error occurs it will alert the user of the error.
     */
    public boolean saveGuide() {
        Guide guide = guideEditor.getOutputGuide();
        boolean success = false;
        if (guide != null) {
            success = guideManager.saveGuide(guide);
        } else {
            //TODO alert user if guide error
        }
        return success;
    }

    public UUID createNewCard() {
        guideEditor.createNewCard();
        UUID returnUUID = guideEditor.getCurrentCard().getCardUUID();
        return returnUUID;
    }

    public void saveCard(String title, String description, byte[] img, UUID affirmUUID, UUID negativeUUID, UUID cardUUID) {
        guideEditor.saveCard(title, description, img, affirmUUID, negativeUUID, cardUUID);
    }

    public void removeCard(UUID cardUUID) {
        guideEditor.removeCard(cardUUID);
    }

    public UUID[] getGuideEditorCardUUIDs() {
        UUID[] returnUUID = guideEditor.getCardsList().keySet().toArray(new UUID[0]);
        return returnUUID;
    }

    public void setGuideEditorUi(GuideEditorUi guideEditorUi) {
        this.guideEditorUi = guideEditorUi;
    }

    public void setNewGuideEditorModel() {
        this.guideEditor = new GuideEditor(this);
        guideEditorUi.resetView();
        guideEditorSave.repopulateLists();
    }




    public void refreshThumbnails() {
        try {
            if (guideManager.getConnection() != null) {
                guideManager.refreshThumbnails();
                Thumbnail[] accessThumbnails = guideManager.getAccessThumbnails();
                Thumbnail[] authorThumbnails = guideManager.getAuthorThumbnails();
                setThumbnailInView(accessThumbnails, authorThumbnails);
            } else {
                Thumbnail[] downloadThumbnails = guideManager.getDownloadThumbnails();
                setThumbnailInView(downloadThumbnails);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void refreshThumbnails(String searchStr) {
        ArrayList<Thumbnail> tempArrayList = new ArrayList<>();

        try {
            if (guideManager.getConnection() != null) {
                guideManager.refreshThumbnails();

                for (Thumbnail thumbnail : guideManager.getAccessThumbnails()) {
                    if (thumbnail.getTitle().toUpperCase().contains(searchStr.toUpperCase())) {
                        tempArrayList.add(thumbnail);
                    }
                }

                Thumbnail[] accessThumbnails = tempArrayList.toArray(new Thumbnail[0]);

                for (Thumbnail thumbnail : guideManager.getAuthorThumbnails()) {
                    if (thumbnail.getTitle().toUpperCase().contains(searchStr.toUpperCase())) {
                        tempArrayList.add(thumbnail);
                    }
                }

                Thumbnail[] authorThumbnails = tempArrayList.toArray(new Thumbnail[0]);
                setThumbnailInView(accessThumbnails, authorThumbnails);
            } else {
                for (Thumbnail thumbnail : guideManager.getDownloadThumbnails()) {
                    if (thumbnail.getTitle().toUpperCase().contains(searchStr.toUpperCase())) {
                        tempArrayList.add(thumbnail);
                    }
                }

                Thumbnail[] downloadThumbnails = tempArrayList.toArray(new Thumbnail[0]);
                setThumbnailInView(downloadThumbnails);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void setGuideEditorSave(GuideEditorSave guideEditorSave) {
        this.guideEditorSave = guideEditorSave;
    }

    public void downloadGuide(UUID uuid) {
        guideManager.downloadGuide(uuid);
    }

    //Compiles the final guide into a finished guide object to be saved.
    public void packGuide(String title, String description, byte[] img) {
        guideEditor.packGuide(title, description, img);
    }

    public boolean checkCardLinksValid() {
        return guideEditor.checkCardLinksValid();
    }

    public String getAuthor() {
        return guideManager.getCurrentUser().getEmail();
    }


    public String getAuthorOfGuide(UUID uuid) {
        return guideManager.getGuide(uuid).getAuthorEmail();
    }

    public void onLoadGuideEditorSave() {
        guideEditorSave.onLoad();
    }

    public void createNewGuide() {
        guideEditor = new GuideEditor(this);
        guideEditorSave.repopulateLists();
        guideEditorUi.initializeEditor();
        toolbarSwitchSubscene(SceneName.guideEditor);
    }

    public void deleteGuide(UUID uuid) {
        System.out.println("Deleted the guide: " + guideManager.getGuide(uuid));
        guideManager.deleteGuide(uuid);
    }

    public void getNextCard(boolean choice) {
        Card card = guideViewer.getNext(choice);
        guideViewerUi.setCard(card.getTitle(), card.getImage(), card.getText());
    }

    public void setGuideViewer(GuideViewerUi guideViewerUi) {
        this.guideViewerUi = guideViewerUi;
    }

    public void lastCard() {
        guideViewerUi.lastCard();
    }

    public String[] getAccessList() {
        return guideManager.getAccessList(guideEditor.getGuideUUID());
    }

    public void setEditGuide(UUID uuid) {
        Guide guide = guideManager.getGuide(uuid);
        guideEditor = new GuideEditor(this);
        guideEditorUi.resetList();
        guideEditor.setEditGuide(guide);
        toolbarSwitchSubscene(SceneName.guideEditor);


        for (Card card: guideEditor.getCardsList().values()) {
//            guideEditorUi.addToCardList(card.getCardUUID());
            Card dCard = guideEditor.getDescriptionCard();
            if(!(card.getCardUUID().equals(dCard.getCardUUID()))) {
                guideEditorUi.addCardToMap(card.getCardUUID());
            }
        }
    }

    //Manages access to guide given uuid and email, grant/revoke access with grantAccess boolean
    public void manageAccess(UUID uuid, String email, boolean grantAccess) {
        if (grantAccess) {
            guideManager.grantAccess(uuid, email);
        } else {
            guideManager.revokeAccess(uuid, email);
        }
    }

    //Passthrough getters and setters for guideEditor
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
    public String getGuideTitle() {
        return guideEditor.getGuideTitle();
    }
    public String getGuideDescription() {
        return guideEditor.getGuideDescription();
    }
    public byte[] getImg() {
        return guideEditor.getOutputGuide().getThumbnail().getImage();
    }
    public UUID getOutputGuideUUID() {
        return guideEditor.getOutputGuide().getGuideUUID();
    }
    public GuideEditor getGuideEditor() {
        return guideEditor;
    }

    //GUID util methods

    public byte[] jfxImageChooser() {
        byte[] byteFile = null;
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(stage);
        String fileName = selectedFile.toString();
        int index = fileName.lastIndexOf(".");
        String extension = fileName.substring(index+1);

        if (extension.equalsIgnoreCase("png") || extension.equalsIgnoreCase("jpg")) {
            byteFile = ImageUtils.toBytes(selectedFile);
            if (byteFile.length >= 5242880) {
                byteFile = null;

                AlertUtils.alertWarning("Image size warning", "Could not add selected image", "Selected image cannot exceed 5 MB");
            }
        } else {
            AlertUtils.alertWarning("File type warning", "Could not add selected image", "Selected file must be of type .png or .jpg, please try again");
        }
        return byteFile;
    }

    //Methods for handling logout and exits
    public void logout() {
        try {
            guideManager.disconnect();
            stage.setScene(new Scene(loadFXML(SceneName.login)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean hasAffirmativeUUID(UUID cardUUID) {
        Card card = guideEditor.getCard(cardUUID);
        return card.getAffirmUUID() != null;
    }

    public boolean hasNegativeUUID(UUID cardUUID) {
        Card card = guideEditor.getCard(cardUUID);
        return card.getNegUUID() != null;
    }

    public void removeSelfAccess(UUID id) {
        System.out.println("Removed the guide: " + guideManager.getGuide(id).getGuideUUID());
        guideManager.revokeSelfAccess(id);

    }

    public GuideManager getGuideManager() {
        return guideManager;
    }

    public void setEditorFirstCard(UUID cardUUID) {
        guideEditor.setFirstCard(cardUUID);
    }

    public UUID getEditorFirstCard() {
        return guideEditor.getFirstCard();
    }

    public void previousCard() {
        Card previousCard = guideViewer.previousCard();
        if (previousCard != null) {
            guideViewerUi.setCard(previousCard.getTitle(), previousCard.getImage(), previousCard.getText());
        }
    }
}