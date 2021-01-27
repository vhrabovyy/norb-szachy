package szachy;

import szachy.gracze.BialyGracz;
import szachy.gracze.CzarnyGracz;
import szachy.gracze.Gracz;

public enum Kolor {
    WHITE {
        @Override
        public int getKierunek() {
            return -1;
        }

        @Override
        public boolean isWhite() {
            return true;
        }

        @Override
        public boolean isBlack() {
            return false;
        }

        @Override
        public Gracz wybierzGracza(final BialyGracz bialyGracz, final CzarnyGracz czarnyGracz) { return bialyGracz; }
    },
    BLACK {
        @Override
        public int getKierunek() {
            return 1;
        }

        @Override
        public boolean isWhite() {
            return false;
        }

        @Override
        public boolean isBlack() {
            return true;
        }

        @Override
        public Gracz wybierzGracza(final BialyGracz bialyGracz, final CzarnyGracz czarnyGracz) { return czarnyGracz; }
    };

    public abstract int getKierunek();
    public abstract boolean isWhite();
    public abstract boolean isBlack();

    public abstract Gracz wybierzGracza(BialyGracz bialyGracz, CzarnyGracz czarnyGracz);
}
