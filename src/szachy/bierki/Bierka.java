package szachy.bierki;

import szachy.Kolor;
import szachy.plansza.Plansza;
import szachy.plansza.Ruch;

import java.util.Collection;

public abstract class Bierka {
    protected final BierkaTyp bierkaTyp;
    protected final int pozycjaBierki;
    protected final Kolor kolorBierki;
    protected final boolean PierwszyRuch;

    private final int wezHashCode;

    public Bierka(final BierkaTyp bierkaTyp, final int pozycjaBierki, final Kolor kolorBierki) {
        this.bierkaTyp = bierkaTyp;
        this.kolorBierki = kolorBierki;
        this.pozycjaBierki = pozycjaBierki;
        this.PierwszyRuch = false;
        this.wezHashCode = wyliczHashCode();
    }

    public int getPozycjaBierki() { return this.pozycjaBierki; }

    public Kolor getKolorBierki() {
        return this.kolorBierki;
    }

    public boolean PierwszyRuch() {
        return this.PierwszyRuch;
    }

    public BierkaTyp getBierkaTyp() { return this.bierkaTyp; }

    public abstract Collection<Ruch> sprawdzLegalnoscRuchu (final Plansza plansza);

    public abstract Bierka przesunBierke(Ruch ruch);

    private int wyliczHashCode() {
        int result = bierkaTyp.hashCode();
        result = 31 * result + kolorBierki.hashCode();
        result = 31 * result + pozycjaBierki;
        result = 31 * result + (PierwszyRuch ? 1 : 0);
        return result;
    }

    public enum BierkaTyp {
        PION("[P]") {
            @Override
            public boolean isKrol() { return false; }},
        WIEZA("[W]"){
            @Override
            public boolean isKrol() { return false; }},
        SKOCZEK("[S]"){
            @Override
            public boolean isKrol() { return false; }},
        GONIEC("[G]"){
            @Override
            public boolean isKrol() { return false; }},
        HETMAN("[H]"){
            @Override
            public boolean isKrol() { return false; }},
        KROL("[K]"){
            @Override
            public boolean isKrol() { return true; }};

        private String nazwaBierki;
        BierkaTyp(final String nazwaBierki) {
            this.nazwaBierki = nazwaBierki;
        }

        @Override
        public String toString() {
            return this.nazwaBierki;
        }

        public abstract boolean isKrol();
    }

    @Override
    public boolean equals(final Object inne) {
        if (this == inne) {
            return true;
        }
        if (!(inne instanceof Bierka)) {
            return false;
        }
        final Bierka innaBierka = (Bierka) inne;
        return pozycjaBierki == innaBierka.getPozycjaBierki() && bierkaTyp == innaBierka.getBierkaTyp() &&
                kolorBierki == innaBierka.getKolorBierki() && innaBierka.PierwszyRuch == innaBierka.PierwszyRuch();
    }

    @Override
    public int hashCode() { return this.wezHashCode; }
}
