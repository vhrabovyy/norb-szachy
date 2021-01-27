package szachy.plansza;

import szachy.Kolor;
import szachy.bierki.*;
import szachy.gracze.BialyGracz;
import szachy.gracze.CzarnyGracz;
import szachy.gracze.Gracz;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Plansza {

    private final List<Pole> planszaDoGry;
    private final Collection<Bierka> bialeBierki;
    private final Collection<Bierka> czarneBierki;

    private final BialyGracz bialyGracz;
    private final CzarnyGracz czarnyGracz;
    private final Gracz aktualnyGracz;

    private Plansza(final Kreator kreator) {
        this.planszaDoGry = stworzPlanszeDoGry(kreator);
        this.bialeBierki = podliczAktywneBierki(this.planszaDoGry, Kolor.WHITE);
        this.czarneBierki = podliczAktywneBierki(this.planszaDoGry, Kolor.BLACK);

        final Collection<Ruch> ruchyBialych = zbadajLegalneRuchy(this.bialeBierki);
        final Collection<Ruch> ruchyCzarnych = zbadajLegalneRuchy(this.czarneBierki);

        this.bialyGracz = new BialyGracz(this, ruchyBialych, ruchyCzarnych);
        this.czarnyGracz = new CzarnyGracz(this, ruchyBialych, ruchyCzarnych);
        this.aktualnyGracz = kreator.kolejnyRuch.wybierzGracza(this.bialyGracz, this.czarnyGracz);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (int i=0; i<Narzedzia.ILOSC_POLA; i++) {
            final String poleText = this.planszaDoGry.get(i).toString();
            builder.append(String.format("%3s", poleText));
            if ((i+1) % Narzedzia.ILOSC_POLA_NA_WIERSZ == 0) {
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    public Gracz bialyGracz() {
        return this.bialyGracz;
    }

    public Gracz czarnyGracz() {
        return this.czarnyGracz;
    }

    public Gracz aktualnyGracz() {
        return this.aktualnyGracz;
    }

    public Collection<Bierka> getCzarneBierki() {
        return this.czarneBierki;
    }

    public Collection<Bierka> getBialeBierki() {
        return this.bialeBierki;
    }

    private Collection<Ruch> zbadajLegalneRuchy(Collection<Bierka> bierki) {
        final List<Ruch> legalneRuchy = new ArrayList<>();
        for (final Bierka bierka : bierki) {
            legalneRuchy.addAll(bierka.sprawdzLegalnoscRuchu(this));
        }
        return legalneRuchy;
    }

    private static Collection<Bierka> podliczAktywneBierki(final List<Pole> planszaDoGry, final Kolor kolor) {
        final List<Bierka> aktywneBierki = new ArrayList<>();
        for (final Pole pole : planszaDoGry) {
            if (pole.zajetePole()) {
                final Bierka bierka = pole.getBierka();
                if (bierka.getKolorBierki() == kolor) {
                    aktywneBierki.add(bierka);
                }
            }
        }
        return aktywneBierki;
    }

    private static List<Pole> stworzPlanszeDoGry(final Kreator kreator) {
        final Pole[] pola = new Pole[Narzedzia.ILOSC_POLA];
        for (int i=0; i<Narzedzia.ILOSC_POLA; i++) {
            pola[i] = Pole.stworzPole(i, kreator.konfiguracjaPlanszy.get(i));
        }
        return Arrays.asList(pola);
    }

    public static Plansza stworzPlanszeDoSzachowKlascznych() {
        final Kreator kreator = new Kreator();

        kreator.setBierka(new Wieza(0, Kolor.BLACK));
        kreator.setBierka(new Skoczek(1, Kolor.BLACK));
        kreator.setBierka(new Goniec(2, Kolor.BLACK));
        kreator.setBierka(new Hetman(3, Kolor.BLACK));
        kreator.setBierka(new Krol(4, Kolor.BLACK));
        kreator.setBierka(new Goniec(5, Kolor.BLACK));
        kreator.setBierka(new Skoczek(6, Kolor.BLACK));
        kreator.setBierka(new Wieza(7, Kolor.BLACK));
        kreator.setBierka(new Pion(8, Kolor.BLACK));
        kreator.setBierka(new Pion(9, Kolor.BLACK));
        kreator.setBierka(new Pion(10, Kolor.BLACK));
        kreator.setBierka(new Pion(11, Kolor.BLACK));
        kreator.setBierka(new Pion(12, Kolor.BLACK));
        kreator.setBierka(new Pion(13, Kolor.BLACK));
        kreator.setBierka(new Pion(14, Kolor.BLACK));
        kreator.setBierka(new Pion(15, Kolor.BLACK));

        kreator.setBierka(new Wieza(63, Kolor.WHITE));
        kreator.setBierka(new Skoczek(62, Kolor.WHITE));
        kreator.setBierka(new Goniec(61, Kolor.WHITE));
        kreator.setBierka(new Krol(60, Kolor.WHITE));
        kreator.setBierka(new Hetman(59, Kolor.WHITE));
        kreator.setBierka(new Goniec(58, Kolor.WHITE));
        kreator.setBierka(new Skoczek(57, Kolor.WHITE));
        kreator.setBierka(new Wieza(56, Kolor.WHITE));
        kreator.setBierka(new Pion(55, Kolor.WHITE));
        kreator.setBierka(new Pion(54, Kolor.WHITE));
        kreator.setBierka(new Pion(53, Kolor.WHITE));
        kreator.setBierka(new Pion(52, Kolor.WHITE));
        kreator.setBierka(new Pion(51, Kolor.WHITE));
        kreator.setBierka(new Pion(50, Kolor.WHITE));
        kreator.setBierka(new Pion(49, Kolor.WHITE));
        kreator.setBierka(new Pion(48, Kolor.WHITE));

        kreator.setWykonanieRuchu(Kolor.WHITE);

        return kreator.budowanie();
    }

    public static Plansza stworzPlanszeDoSzachowDiagonalnych() {
        final Kreator kreator = new Kreator();

        kreator.setBierka(new Pion(3, Kolor.BLACK));
        kreator.setBierka(new Wieza(4, Kolor.BLACK));
        kreator.setBierka(new Skoczek(5, Kolor.BLACK));
        kreator.setBierka(new Goniec(6, Kolor.BLACK));
        kreator.setBierka(new Krol(7, Kolor.BLACK));
        kreator.setBierka(new Pion(12, Kolor.BLACK));
        kreator.setBierka(new Pion(13, Kolor.BLACK));
        kreator.setBierka(new Hetman(14, Kolor.BLACK));
        kreator.setBierka(new Skoczek(15, Kolor.BLACK));
        kreator.setBierka(new Pion(21, Kolor.BLACK));
        kreator.setBierka(new Pion(22, Kolor.BLACK));
        kreator.setBierka(new Goniec(23, Kolor.BLACK));
        kreator.setBierka(new Pion(30, Kolor.BLACK));
        kreator.setBierka(new Wieza(31, Kolor.BLACK));
        kreator.setBierka(new Pion(39, Kolor.BLACK));

        kreator.setBierka(new Pion(60, Kolor.WHITE));
        kreator.setBierka(new Wieza(59, Kolor.WHITE));
        kreator.setBierka(new Skoczek(58, Kolor.WHITE));
        kreator.setBierka(new Goniec(57, Kolor.WHITE));
        kreator.setBierka(new Krol(56, Kolor.WHITE));
        kreator.setBierka(new Pion(51, Kolor.WHITE));
        kreator.setBierka(new Pion(50, Kolor.WHITE));
        kreator.setBierka(new Hetman(49, Kolor.WHITE));
        kreator.setBierka(new Skoczek(48, Kolor.WHITE));
        kreator.setBierka(new Pion(42, Kolor.WHITE));
        kreator.setBierka(new Pion(41, Kolor.WHITE));
        kreator.setBierka(new Goniec(40, Kolor.WHITE));
        kreator.setBierka(new Pion(33, Kolor.WHITE));
        kreator.setBierka(new Wieza(32, Kolor.WHITE));
        kreator.setBierka(new Pion(24, Kolor.WHITE));

        kreator.setWykonanieRuchu(Kolor.WHITE);

        return kreator.budowanie();
    }

    public Pole getPole(final int wspolrzednePola) {
        return planszaDoGry.get(wspolrzednePola);
    }

    public Collection<Ruch> getWszystkieLegalneRuchy() {
        return Stream.concat(this.bialyGracz.getLegalneRuchy().stream(),
                this.czarnyGracz.getLegalneRuchy().stream()).collect(Collectors.toList());
    }

    public static class Kreator {

        Map<Integer, Bierka> konfiguracjaPlanszy;
        Kolor kolejnyRuch;

        public Kreator() {
            this.konfiguracjaPlanszy = new HashMap<>();
        }

        public Kreator setBierka(final Bierka bierka) {
            this.konfiguracjaPlanszy.put(bierka.getPozycjaBierki(), bierka);
            return this;
        }

        public Kreator setWykonanieRuchu(final Kolor kolejnyRuch) {
            this.kolejnyRuch = kolejnyRuch;
            return this;
        }

        public Plansza budowanie() {
            return new Plansza(this);
        }
    }
}
