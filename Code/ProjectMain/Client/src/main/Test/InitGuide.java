import org.supportmeinc.Main;
import shared.Card;

public class InitGuide  {
    public static void main(String[] args) {
        Main main = new Main();
        main.startBackend();
        Card card = main.initGuide(0);
        System.out.println(card.getTitle());
    }
}
