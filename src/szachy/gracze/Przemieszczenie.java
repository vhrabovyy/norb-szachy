package szachy.gracze;

import szachy.plansza.Plansza;
import szachy.plansza.Ruch;

public class Przemieszczenie {
    private final Plansza planszaPoPrzemieszczeniu;
    private final Ruch ruch;
    private final StatusRuchu statusRuchu;

    public Przemieszczenie(final Plansza planszaPoPrzemieszczeniu, final Ruch ruch, final StatusRuchu statusRuchu) {
        this.planszaPoPrzemieszczeniu = planszaPoPrzemieszczeniu;
        this.ruch = ruch;
        this.statusRuchu = statusRuchu;
    }

    public StatusRuchu getStatusRuchu() {
        return this.statusRuchu;
    }
}
