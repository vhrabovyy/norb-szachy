package szachy.plansza;

import szachy.bierki.Bierka;
import szachy.plansza.Plansza.Kreator;

public abstract class Ruch {
    final Plansza plansza;
    final Bierka ruszBierke;
    final int wspolrzedneCelu;

    private Ruch(final Plansza plansza, final Bierka ruszBierke, final int wspolrzedneCelu) {
        this.plansza = plansza;
        this.ruszBierke = ruszBierke;
        this.wspolrzedneCelu = wspolrzedneCelu;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.wspolrzedneCelu;
        result = prime * result + this.ruszBierke.hashCode();
        return result;
    }

    @Override
    public boolean equals(final Object inne) {
        if (this == inne) {
            return true;
        }
        if (!(inne instanceof Ruch)) {
            return false;
        }
        final Ruch innyRuch = (Ruch) inne;
        return getWspolrzedneCelu() == innyRuch.getWspolrzedneCelu() &&
                getRuszBierke().equals(innyRuch.getRuszBierke());
    }

    public int getAktualneWspolrzedne() {
        return this.getRuszBierke().getPozycjaBierki();
    }

    public int getWspolrzedneCelu() {
        return this.wspolrzedneCelu;
    }

    public Plansza wykonaj() {
        final Kreator kreator = new Kreator();
        for (final Bierka bierka : this.plansza.aktualnyGracz().getAktywneBierki()) {
            if (!this.ruszBierke.equals(bierka)) {
                kreator.setBierka(bierka);
            }
        }
        for (final Bierka bierka : this.plansza.aktualnyGracz().getPrzeciwnik().getAktywneBierki()) {
            kreator.setBierka(bierka);
        }
        kreator.setBierka(this.ruszBierke.przesunBierke(this));
        kreator.setWykonanieRuchu(this.plansza.aktualnyGracz().getPrzeciwnik().getKolor());

        return kreator.budowanie();
    }

    public Bierka getRuszBierke() {
        return this.ruszBierke;
    }

    public static final class WlasciwyRuch extends Ruch {
        public WlasciwyRuch(final Plansza plansza, final Bierka ruszBierke, final int wspolrzedneCelu) {
            super(plansza, ruszBierke, wspolrzedneCelu);
        }
    }

    public static class AtakujacyRuch extends Ruch {
        final Bierka atakujacaBierka;
        public AtakujacyRuch(final Plansza plansza, final Bierka ruszBierke,
                      final int wspolrzedneCelu, final Bierka atakujacaBierka) {
            super(plansza, ruszBierke, wspolrzedneCelu);
            this.atakujacaBierka = atakujacaBierka;
        }


        @Override
        public int hashCode() { return this.atakujacaBierka.hashCode() + super.hashCode(); }

        @Override
        public Plansza wykonaj() {
            return null;
        }

        @Override
        public boolean Atak() { return true; }

        public Bierka getAtakujacaBierka() { return  this.atakujacaBierka; }

        @Override
        public boolean equals(final Object inne) {
            if (this == inne) {
                return true;
            }
            if (!(inne instanceof AtakujacyRuch)) {
                return false;
            }
            final AtakujacyRuch innyAtakujacyRuch = (AtakujacyRuch) inne;
            return super.equals(innyAtakujacyRuch) &&
                    getAtakujacaBierka().equals(innyAtakujacyRuch.getAtakujacaBierka());
        }


    }

    public boolean atak() {
        return false;
    }

    public Bierka getZaatakowanaBierka() {
        return null;
    }

    public static final class RuchPionem extends Ruch {
        public RuchPionem(final Plansza plansza, final Bierka ruszBierke, final int wspolrzedneCelu) {
            super(plansza, ruszBierke, wspolrzedneCelu);
        }
    }

    public static final class RuchAtakujacyPionem extends AtakujacyRuch {
        public RuchAtakujacyPionem(final Plansza plansza, final Bierka ruszBierke,
                                   final int wspolrzedneCelu, final Bierka atakujacaBierka) {
            super(plansza, ruszBierke, wspolrzedneCelu, atakujacaBierka);
        }
    }

    public static class Posuniecie {
        private Posuniecie() { throw new RuntimeException("Nie obslugiwalne !!!"); }
        public static Ruch zrobRuch(final Plansza plansza, final int aktualneWspolrzedne, final int wspolrzedneCelu) {
            for (final Ruch ruch : plansza.getWszystkieLegalneRuchy()) {
                if (ruch.getAktualneWspolrzedne() == aktualneWspolrzedne &&
                ruch.getWspolrzedneCelu() == wspolrzedneCelu) {
                    return ruch;
                }
            }
            return null;
        }
    }
}
