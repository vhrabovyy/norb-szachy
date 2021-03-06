package szachy.bierki;

import szachy.Kolor;
import szachy.plansza.Narzedzia;
import szachy.plansza.Plansza;
import szachy.plansza.Pole;
import szachy.plansza.Ruch;
import szachy.plansza.Ruch.AtakujacyRuch;
import szachy.plansza.Ruch.WlasciwyRuch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Goniec extends Bierka {
    private final static int [] GDZIE_MOZNA_SIE_RUSZYC = {-9, -7, 7, 9};
    public Goniec(int pozycjaBierki, Kolor kolorBierki) {
        super(BierkaTyp.GONIEC, pozycjaBierki, kolorBierki);
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
                        legalneRuchy.add(new WlasciwyRuch(plansza, this, mozliwyCelRuchu));
                    }
                    else {
                        final Bierka bierkaDotarlaDoCelu = mozliwyCelRuchuPole.getBierka();
                        final Kolor kolorBierki = bierkaDotarlaDoCelu.getKolorBierki();
                        if (this.kolorBierki != kolorBierki) {
                            legalneRuchy.add(new AtakujacyRuch(plansza, this, mozliwyCelRuchu, bierkaDotarlaDoCelu));
                        }
                        break;
                    }
                }
            }
        }
        return legalneRuchy;
    }

    @Override
    public Goniec przesunBierke(final Ruch ruch) {
        return new Goniec(ruch.getWspolrzedneCelu(), ruch.getRuszBierke().getKolorBierki());
    }

    private static boolean wykluczeniePierwszejKolumny (final int obecnaPozycja, final int proponowanaPozycja) {
        return Narzedzia.PIERWSZA_KOLUMNA[obecnaPozycja] && (proponowanaPozycja == -9 || proponowanaPozycja == 7);
    }

    private static boolean wykluczenieOsmejKolumny (final int obecnaPozycja, final int proponowanaPozycja) {
        return Narzedzia.OSMA_KOLUMNA[obecnaPozycja] && (proponowanaPozycja == -7 || proponowanaPozycja == 9);
    }

    @Override
    public String toString() {
        return BierkaTyp.GONIEC.toString();
    }
}
