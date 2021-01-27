package szachy.bierki;

import szachy.Kolor;
import szachy.plansza.Narzedzia;
import szachy.plansza.Plansza;
import szachy.plansza.Pole;
import szachy.plansza.Ruch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Wieza extends Bierka {
    private final static int [] GDZIE_MOZNA_SIE_RUSZYC = {-8, -1, 1, 8};
    public Wieza(int pozycjaBierki, Kolor kolorBierki) {
        super(BierkaTyp.WIEZA, pozycjaBierki, kolorBierki);
    }

    @Override
    public Collection<Ruch> sprawdzLegalnoscRuchu(final Plansza plansza) {
        final List<Ruch> legalneRuchy = new ArrayList<>();
        for (final int mozliwosciRuchu : GDZIE_MOZNA_SIE_RUSZYC) {
            int mozliwyCelRuchu = this.pozycjaBierki;
            while (Narzedzia.prawidloweWspolrzednePola(mozliwyCelRuchu) ) {
                if (wykluczeniePierwszejKolumny(this.pozycjaBierki, mozliwosciRuchu) ||
                        wykluczenieOsmejKolumny(this.pozycjaBierki, mozliwosciRuchu)) {
                    break;
                }
                mozliwyCelRuchu += mozliwosciRuchu;
                if (Narzedzia.prawidloweWspolrzednePola(mozliwyCelRuchu) ) {
                    final Pole mozliwyCelRuchuPole = plansza.getPole(mozliwyCelRuchu);
                    if (!mozliwyCelRuchuPole.zajetePole()) {
                        legalneRuchy.add(new Ruch.WlasciwyRuch(plansza, this, mozliwyCelRuchu));
                    }
                    else {
                        final Bierka bierkaDotarlaDoCelu = mozliwyCelRuchuPole.getBierka();
                        final Kolor kolorBierki = bierkaDotarlaDoCelu.getKolorBierki();
                        if (this.kolorBierki != kolorBierki) {
                            legalneRuchy.add(new Ruch.AtakujacyRuch(plansza, this, mozliwyCelRuchu, bierkaDotarlaDoCelu));
                        }
                        break;
                    }
                }
            }
        }
        return legalneRuchy;
    }

    @Override
    public Wieza przesunBierke(final Ruch ruch) {
        return new Wieza(ruch.getWspolrzedneCelu(), ruch.getRuszBierke().getKolorBierki());
    }

    private static boolean wykluczeniePierwszejKolumny (final int obecnaPozycja, final int proponowanaPozycja) {
        return Narzedzia.PIERWSZA_KOLUMNA[obecnaPozycja] && (proponowanaPozycja == -1);
    }

    private static boolean wykluczenieOsmejKolumny (final int obecnaPozycja, final int proponowanaPozycja) {
        return Narzedzia.OSMA_KOLUMNA[obecnaPozycja] && (proponowanaPozycja == 1);
    }

    @Override
    public String toString() {
        return BierkaTyp.WIEZA.toString();
    }
}
