package szachy.plansza;

import szachy.bierki.Bierka;

import java.util.HashMap;
import java.util.Map;

public abstract class Pole {
    int wspolrzednePola;
    public abstract boolean zajetePole();
    public abstract Bierka getBierka();
    private static final Map <Integer, PustePole> PUSTE_POLE = pokazWszystkiePustePola();

    private static Map<Integer, PustePole> pokazWszystkiePustePola() {
        final Map<Integer, PustePole> pustePoleMap = new HashMap<>();
        for (int i=0; i < Narzedzia.ILOSC_POLA; i++) {
            pustePoleMap.put(i, new PustePole(i));
        }
        return pustePoleMap;
    }

    public static Pole stworzPole(final int wspolrzednePola, final Bierka bierka) {
        return bierka != null ?
                new ZajetePole(wspolrzednePola, bierka) :
                PUSTE_POLE.get(wspolrzednePola);
    }

    private Pole(final int wspolrzednePola) {
        this.wspolrzednePola = wspolrzednePola;
    }

    public static final class PustePole extends Pole {
        PustePole(int wspolrzedne) {
            super(wspolrzedne);
        }

        @Override
        public String toString() {
            return "[ ]";
        }

        @Override
        public boolean zajetePole() {
            return false;
        }

        @Override
        public Bierka getBierka() {
            return null;
        }

    }

    public static final class ZajetePole extends Pole {
        private final Bierka bierkaNaPolu;
        ZajetePole (int wspolrzednePola, final Bierka bierkaNaPolu) {
            super(wspolrzednePola);
            this.bierkaNaPolu = bierkaNaPolu;
        }

        @Override
        public String toString() {
            return getBierka().getKolorBierki().isBlack() ?
                    getBierka().toString().toLowerCase() :
                    getBierka().toString();
        }

        @Override
        public boolean zajetePole() {
            return true;
        }

        @Override
        public Bierka getBierka() {
            return this.bierkaNaPolu;
        }
    }
}
