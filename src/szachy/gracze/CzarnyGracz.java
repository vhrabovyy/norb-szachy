package szachy.gracze;

import szachy.Kolor;
import szachy.bierki.Bierka;
import szachy.plansza.Plansza;
import szachy.plansza.Ruch;

import java.util.Collection;

public class CzarnyGracz extends Gracz{
    public CzarnyGracz(final Plansza plansza, final Collection<Ruch> ruchyBialych, final Collection<Ruch> ruchyCzarnych) {
        super(plansza, ruchyCzarnych, ruchyBialych);
    }

    @Override
    public Collection<Bierka> getAktywneBierki() {
        return this.plansza.getCzarneBierki();
    }

    @Override
    public Kolor getKolor() {
        return Kolor.BLACK;
    }

    @Override
    public Gracz getPrzeciwnik() {
        return this.plansza.bialyGracz();
    }
}
