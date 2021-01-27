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

public class Krol extends Bierka{
    private final static int [] GDZIE_MOZNA_SIE_RUSZYC = {-9, -8, -7, -1, 1, 7, 8, 9};
    public Krol(final int pozycjaBierki, final Kolor kolorBierki) {
        super(BierkaTyp.KROL, pozycjaBierki, kolorBierki);
    }

    @Override
    public Collection<Ruch> sprawdzLegalnoscRuchu(Plansza plansza) {
        final List<Ruch> legalneRuchy = new ArrayList<>();
        for (final int mozliwosciRuchu : GDZIE_MOZNA_SIE_RUSZYC) {
            final int mozliwyCelRuchu = this.pozycjaBierki + mozliwosciRuchu;
            if (wykluczeniePierwszejKolumny(this.pozycjaBierki, mozliwosciRuchu) ||
                    wykluczenieOsmejKolumny(this.pozycjaBierki, mozliwosciRuchu)) {
                continue;
            }
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
                }
            }
        }

        return legalneRuchy;
    }

    @Override
    public Krol przesunBierke(final Ruch ruch) {
        return new Krol(ruch.getWspolrzedneCelu(), ruch.getRuszBierke().getKolorBierki());
    }

    private static boolean wykluczeniePierwszejKolumny (final int obecnaPozycja, final int proponowanaPozycja) {
        return Narzedzia.PIERWSZA_KOLUMNA[obecnaPozycja] && (proponowanaPozycja == -9 ||
                proponowanaPozycja == -1 || proponowanaPozycja == 7);
    }

    private static boolean wykluczenieOsmejKolumny (final int obecnaPozycja, final int proponowanaPoyzcja) {
        return Narzedzia.OSMA_KOLUMNA[obecnaPozycja] && (proponowanaPoyzcja == -7 || proponowanaPoyzcja == 1 ||
                proponowanaPoyzcja == 9);
    }

    @Override
    public String toString() {
        return BierkaTyp.KROL.toString();
    }
}
