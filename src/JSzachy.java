import GUI.Szachownica;
import szachy.plansza.Plansza;

public class JSzachy {
    public static void main (String[]args) {
        Plansza plansza1 = Plansza.stworzPlanszeDoSzachowKlascznych();
        Plansza plansza = Plansza.stworzPlanszeDoSzachowDiagonalnych();

        System.out.println(plansza);
        Szachownica szachownica = new Szachownica();
    }
}
