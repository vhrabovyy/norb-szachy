package szachy.gracze;

import szachy.Kolor;
import szachy.bierki.Bierka;
import szachy.bierki.Krol;
import szachy.plansza.Plansza;
import szachy.plansza.Ruch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public abstract class Gracz {
    protected final Plansza plansza;
    protected final Krol graczKrol;
    protected final Collection<Ruch> legalneRuchy;
    private final boolean szach;

    Gracz(final Plansza plansza, final  Collection<Ruch> legalneRuchy, final Collection<Ruch> przeciwnikRusza) {
        this.plansza = plansza;
        this.graczKrol = ustanowKrola();
        this.legalneRuchy = legalneRuchy;
        this.szach = !Gracz.atakNaPole(this.graczKrol.getPozycjaBierki(), przeciwnikRusza).isEmpty();
    }

    public Krol getGraczKrol() {
        return this.graczKrol;
    }

    public Collection<Ruch> getLegalneRuchy() {
        return this.legalneRuchy;
    }

    private static Collection<Ruch> atakNaPole(int pozycjaBierki, Collection<Ruch> ruchy) {
        final List<Ruch> AtakujacyRuch = new ArrayList<>();
        for (final Ruch ruch : ruchy) {
            if (pozycjaBierki == ruch.getWspolrzedneCelu()) {
                AtakujacyRuch.add(ruch);
            }
        }
        return AtakujacyRuch;
    }

    private Krol ustanowKrola() {
        for (final Bierka bierka : getAktywneBierki()) {
            if (bierka.getBierkaTyp().isKrol()) {
                return (Krol) bierka;
            }
        }
        throw new RuntimeException("Nie powinno Cie tu byc !!!");
    }

    public boolean legalnoscRuchu(final Ruch ruch) {
        return this.legalneRuchy.contains(ruch);
    }

    protected boolean mozeUciec() {
        for (final Ruch ruch : this.legalneRuchy) {
            final Przemieszczenie przemieszczenie = zrobRuch(ruch);
            if(przemieszczenie.getStatusRuchu().wykonano()) {
                return true;
            }
        }
        return false;
    }

    public boolean szach() {
        return this.szach;
    }

    public boolean mat() {
        return this.szach && !mozeUciec();
    }

    public boolean pat() {
        return !this.szach && !mozeUciec();
    }

    public Przemieszczenie zrobRuch(final Ruch ruch) {
        if (!legalnoscRuchu(ruch)) {
            return new Przemieszczenie(this.plansza, ruch, StatusRuchu.NIELEGALNY_RUCH);
        }
        final Plansza planszaPoPrzemieszczeniu = ruch.wykonaj();
        final Collection<Ruch> atakNaKrola = Gracz.atakNaPole(planszaPoPrzemieszczeniu.aktualnyGracz().
                getPrzeciwnik().getGraczKrol().getPozycjaBierki(), planszaPoPrzemieszczeniu.aktualnyGracz().getLegalneRuchy());
        if (!atakNaKrola.isEmpty()) {
            return new Przemieszczenie(this.plansza, ruch, StatusRuchu.ZOSTAW_GRACZA_W_SZACHU);
        }
        return new Przemieszczenie(planszaPoPrzemieszczeniu, ruch, StatusRuchu.WYKONANO);
    }

    public abstract Collection<Bierka> getAktywneBierki();
    public abstract Kolor getKolor();
    public abstract Gracz getPrzeciwnik();
}
