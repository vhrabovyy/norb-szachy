package szachy.bierki;

import szachy.Kolor;
import szachy.plansza.Narzedzia;
import szachy.plansza.Plansza;
import szachy.plansza.Pole;
import szachy.plansza.Ruch;
import szachy.plansza.Ruch.WlasciwyRuch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Pion extends Bierka {
    private final static int [] GDZIE_MOZNA_SIE_RUSZYC = {8, 16, 7, 9};
    public Pion(final int pozycjaBierki, final Kolor kolorBierki) {
        super(BierkaTyp.PION, pozycjaBierki, kolorBierki);
    }

    @Override
    public Collection<Ruch> sprawdzLegalnoscRuchu(final Plansza plansza) {
        final List<Ruch> legalneRuchy = new ArrayList<>();
        for (final int mozliwosciRuchu : GDZIE_MOZNA_SIE_RUSZYC) {
            final int mozliwyCelRuchu = this.pozycjaBierki + (this.getKolorBierki().getKierunek() * mozliwosciRuchu);
            if (!Narzedzia.prawidloweWspolrzednePola(mozliwyCelRuchu) ) {
                continue;
            }
            if (mozliwosciRuchu == 0 && plansza.getPole(mozliwyCelRuchu).zajetePole()) {
                legalneRuchy.add(new WlasciwyRuch(plansza, this, mozliwyCelRuchu));
            }
            else if (mozliwosciRuchu == 16 && this.PierwszyRuch() &&
                    (Narzedzia.DRUGI_WIERSZ[this.pozycjaBierki] && this.getKolorBierki().isBlack()) ||
                    (Narzedzia.SIODMY_WIERSZ[this.pozycjaBierki] && this.getKolorBierki().isWhite())) {
                final int proponowanyCel = this.pozycjaBierki + (this.kolorBierki.getKierunek() * 8);
                if (!plansza.getPole(proponowanyCel).zajetePole() && !plansza.getPole(mozliwyCelRuchu).zajetePole()) {
                    legalneRuchy.add(new WlasciwyRuch(plansza, this, mozliwyCelRuchu));
                }
            }
            else if (mozliwosciRuchu == 7 &&
                    !(Narzedzia.OSMA_KOLUMNA[this.pozycjaBierki] && this.kolorBierki.isWhite() ||
                    (Narzedzia.PIERWSZA_KOLUMNA[this.pozycjaBierki] && this.kolorBierki.isBlack()))) {
                if (plansza.getPole(mozliwyCelRuchu).zajetePole()) {
                    final Bierka bierkaNaPropozycji = plansza.getPole(mozliwyCelRuchu).getBierka();
                    if (this.kolorBierki != bierkaNaPropozycji.getKolorBierki()) {
                        legalneRuchy.add(new WlasciwyRuch(plansza, this, mozliwyCelRuchu));
                    }
                }
            }
            else if (mozliwosciRuchu == 9 &&
                    !(Narzedzia.PIERWSZA_KOLUMNA[this.pozycjaBierki] && this.kolorBierki.isWhite() ||
                    (Narzedzia.OSMA_KOLUMNA[this.pozycjaBierki] && this.kolorBierki.isBlack()))) {
                if (plansza.getPole(mozliwyCelRuchu).zajetePole()) {
                    final Bierka bierkaNaPropozycji = plansza.getPole(mozliwyCelRuchu).getBierka();
                    if (this.kolorBierki != bierkaNaPropozycji.getKolorBierki()) {
                        legalneRuchy.add(new WlasciwyRuch(plansza, this, mozliwyCelRuchu));
                    }
                }
            }
        }
        return legalneRuchy;
    }

    @Override
    public Pion przesunBierke(final Ruch ruch) {
        return new Pion(ruch.getWspolrzedneCelu(), ruch.getRuszBierke().getKolorBierki());
    }

    @Override
    public String toString() {
        return BierkaTyp.PION.toString();
    }
}
