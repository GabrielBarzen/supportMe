package org.supportmeinc.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ButtonItem {

    @FXML private Button cardListBtn = new Button();

    private int listIndex;
    private GuideEditorUi guideEditorUi;

    public void setGuideEditorUi(GuideEditorUi guideEditorUi) {
        this.guideEditorUi = guideEditorUi;
    }

    public int returnIndex() {
        return listIndex;
    }

    public void onClick() {
        guideEditorUi.updateCardViewOnClick(listIndex);
        cardListBtn.setDisable(true);
    }

    public void setData(String title, int index) {
        this.listIndex = index;
        cardListBtn.setText(title);
    }

}
