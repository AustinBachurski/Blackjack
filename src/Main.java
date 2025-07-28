import Cards.*;

public class Main {
    public static void main(String[] args) {
        Card card = new Card(CardFace.KING, CardSuite.HEARTS);

        for (String line : card._art)
        System.out.println(line);
    }
}

