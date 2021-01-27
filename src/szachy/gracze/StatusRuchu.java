package szachy.gracze;

public enum StatusRuchu {
    WYKONANO {
        @Override
        public boolean wykonano() { return true; }},
    NIELEGALNY_RUCH {
        @Override
        public boolean wykonano() { return false; }},
    ZOSTAW_GRACZA_W_SZACHU {
        @Override
        public boolean wykonano() { return false; }};

    public abstract boolean wykonano();
}
