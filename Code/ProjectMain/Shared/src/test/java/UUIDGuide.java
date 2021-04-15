import shared.Guide;

public class UUIDGuide {
    public static void main(String[] args) {
        new UUIDGuide();
    }

    UUIDGuide(){
        for (int i = 0; i < 10; i++) {
            Guide guide = new Guide();
            System.out.println(guide.getGuideUUID());
        }
    }
}
