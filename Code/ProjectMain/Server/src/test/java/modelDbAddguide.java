import org.supportmeinc.ModelDatabaseConnection;
import org.supportmeinc.ServerLog;
import shared.Card;
import shared.Guide;
import shared.Thumbnail;

public class modelDbAddguide {
    public static void main(String[] args) {
        new ServerLog();
        new modelDbAddguide();
    }

    ModelDatabaseConnection dbcon = new ModelDatabaseConnection();

    modelDbAddguide(){
        Guide guide = new Guide();

        byte[] image = ImageUtils.toBytes("nicer.png");

        Card Dcard = new Card();
        Dcard.setTitle("good title");
        Dcard.setText("good text");
        Dcard.setImage(image);
        Card Acard = new Card();
        Acard.setTitle("Better title");
        Acard.setText("better text");
        Acard.setImage(image);
        Card Ncard = new Card();
        Ncard.setTitle("Betterer title");
        Ncard.setText("betterer text");
        Ncard.setImage(image);

        Dcard.setAffirmUUID(Acard.getAffirmUUID());
        Dcard.setNegUUID(Ncard.getNegUUID());

        Thumbnail thumbnail = new Thumbnail(guide.getGuideUUID());
        thumbnail.setDescription("The best guide you ever seen");
        thumbnail.setTitle("Fix internet");
        thumbnail.setImage(image);

        guide.setThumbnail(thumbnail);
        guide.setDescriptionCard(Dcard);
        guide.setCards(new Card[]{Dcard,Acard,Ncard});

        System.out.println(guide.getGuideUUID());
        System.out.println(thumbnail.getGuideUUID());
        System.out.println(Dcard.getCardUUID());
        System.out.println(thumbnail.getTitle());
        System.out.println(Dcard.getTitle());
        System.out.println(Acard.getTitle());
        System.out.println(Ncard.getTitle());

        dbcon.addGuide(guide);
    }

}
