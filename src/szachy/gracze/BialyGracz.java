package szachy.gracze;

import szachy.Kolor;
import szachy.bierki.Bierka;
import szachy.plansza.Plansza;
import szachy.plansza.Ruch;

import java.util.Collection;

public class BialyGracz extends Gracz{
    public BialyGracz(final Plansza plansza, final Collection<Ruch> ruchyBialych, final Collection<Ruch> ruchyCzarnych) {
        super(plansza, ruchyBialych, ruchyCzarnych);
    }

    @Override
    public Collection<Bierka> getAktywneBierki() {
        return this.plansza.getBialeBierki();
    }

    @Override
    public Kolor getKolor() {
        return Kolor.WHITE;
    }

    @Override
    public Gracz getPrzeciwnik() {
        return this.plansza.czarnyGracz();
    }
}
