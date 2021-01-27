package szachy.bierki;

import szachy.Kolor;
import szachy.plansza.Plansza;
import szachy.plansza.Pole;
import szachy.plansza.Ruch;
import szachy.plansza.Narzedzia;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static szachy.plansza.Ruch.*;

public class Skoczek extends Bierka {
    private final static int [] GDZIE_MOZNA_SIE_RUSZYC = {-17, -15, -10, -6, 6, 10, 15, 17};
    public Skoczek(final int pozycjaBierki, final Kolor kolorBierki) {
        super(BierkaTyp.SKOCZEK, pozycjaBierki, kolorBierki);
    }

    @Override
    public Collection<Ruch> sprawdzLegalnoscRuchu(final Plansza plansza) {
        final List<Ruch> legalneRuchy = new ArrayList<>();
        for (final int mozliwosciRuchu : GDZIE_MOZNA_SIE_RUSZYC) {
            final int mozliwyCelRuchu = this.pozycjaBierki + mozliwosciRuchu;
            if (Narzedzia.prawidloweWspolrzednePola(mozliwyCelRuchu) ) {
                if (wykluczeniePierwszejKolumny(this.pozycjaBierki, mozliwosciRuchu) ||
                        wykluczenieDrugiejKolumny(this.pozycjaBierki, mozliwosciRuchu) ||
                        wykleczenieSiodmejKolumny(this.pozycjaBierki, mozliwosciRuchu) ||
                        wykluczenieOsmejKolumny(this.pozycjaBierki, mozliwosciRuchu)) {
                    continue;
                }
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
                }
            }
        }
        return legalneRuchy;
    }

    @Override
    public Skoczek przesunBierke(final Ruch ruch) {
        return new Skoczek(ruch.getWspolrzedneCelu(), ruch.getRuszBierke().getKolorBierki());
    }

    private static boolean wykluczeniePierwszejKolumny (final int obecnaPozycja, final int proponowanaPozycja) {
        return Narzedzia.PIERWSZA_KOLUMNA[obecnaPozycja] && (proponowanaPozycja == -17 ||
                proponowanaPozycja == -10 || proponowanaPozycja ==6 || proponowanaPozycja == 15);
    }

    private static boolean wykluczenieDrugiejKolumny (final int obecnaPozycja, final int proponowanaPoyzcja) {
        return Narzedzia.DRUGA_KOLUMNA[obecnaPozycja] && (proponowanaPoyzcja == -10 || proponowanaPoyzcja ==6);
    }

    private static boolean wykleczenieSiodmejKolumny (final int obecnaPozycja, final int proponowanaPozycja) {
        return Narzedzia.SIODMA_KOLUMNA[obecnaPozycja] && (proponowanaPozycja == -6 || proponowanaPozycja == 10);
    }

    private static boolean wykluczenieOsmejKolumny (final int obecnaPozycja, final int proponowanaPozycja) {
        return Narzedzia.OSMA_KOLUMNA[obecnaPozycja] && (proponowanaPozycja == -15 ||
                proponowanaPozycja == -6 || proponowanaPozycja == 10 || proponowanaPozycja == 17);
    }

    @Override
    public String toString() {
        return BierkaTyp.SKOCZEK.toString();
    }
}
