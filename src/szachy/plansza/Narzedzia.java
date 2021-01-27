package szachy.plansza;

public class Narzedzia {

    public static final int ILOSC_POLA = 64;
    public static final int ILOSC_POLA_NA_WIERSZ = 8;

    public static final boolean [] PIERWSZA_KOLUMNA = initKolumna(0);
    public static final boolean [] DRUGA_KOLUMNA = initKolumna(1);
    public static final boolean [] SIODMA_KOLUMNA = initKolumna(6);
    public static final boolean [] OSMA_KOLUMNA = initKolumna(7);

    public static final boolean [] PIERWSZY_WIERSZ = initWiersz(0);
    public static final boolean [] DRUGI_WIERSZ = initWiersz(8);
    public static final boolean [] TRZECI_WIERSZ = initWiersz(16);
    public static final boolean [] CZWARTY_WIERSZ = initWiersz(24);
    public static final boolean [] PIATY_WIERSZ = initWiersz(32);
    public static final boolean [] SZOSTY_WIERSZ = initWiersz(40);
    public static final boolean [] SIODMY_WIERSZ= initWiersz(48);
    public static final boolean [] OSMY_WIERSZ= initWiersz(56);

    private static boolean[] initWiersz(int numerWiersza) {
        final boolean[] wiersz = new boolean[ILOSC_POLA];
        do {
            wiersz[numerWiersza] = true;
            numerWiersza++;
        }
        while (numerWiersza % ILOSC_POLA_NA_WIERSZ !=0);
        return wiersz;
    }

    private static boolean[] initKolumna(int numerKolumny) {
        final boolean [] kolumna = new boolean[ILOSC_POLA];
        do {
            kolumna[numerKolumny] = true;
            numerKolumny += ILOSC_POLA_NA_WIERSZ;
        }
        while (numerKolumny < ILOSC_POLA);
        return kolumna;
    }

    private Narzedzia() {
        throw new RuntimeException("Nie mozesz mnie wezwac");
    }

    public static boolean prawidloweWspolrzednePola(final int wspolrzedne) {
        return wspolrzedne >= 0 && wspolrzedne < ILOSC_POLA;
    }
}
