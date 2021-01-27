package GUI;

import szachy.bierki.Bierka;
import szachy.gracze.Przemieszczenie;
import szachy.plansza.Narzedzia;
import szachy.plansza.Plansza;
import szachy.plansza.Pole;
import szachy.plansza.Ruch;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class Szachownica {

    private final JFrame wygladGry;
    private final PanelPlanszy panelPlanszy;
    private final Plansza planszaZ_Szachami;
    private Pole poleZrodlowe;
    private Pole poleDocelowe;
    private Bierka przemieszczenieBierki;

    private final static Dimension ZEWNETRZNY_ROZMIAR = new Dimension(600, 600);
    private final static Dimension ROZMIAR_PANELU_PLANSZY = new Dimension(400, 350);
    private final static Dimension ROZMIAR_PANELU_POLA = new Dimension(10, 10);

    private Color jasnyKolorPola = Color.decode("#CC6633");
    private Color ciemnyKolorPola = Color.decode("#993300");
    private static String sciezkaDoObrazkowBierek = "bierki/";

    public Szachownica() {
        final JMenuBar szachownicaMenuBar = new JMenuBar();
        wypelnijMenuBar(szachownicaMenuBar);
        this.wygladGry = new JFrame("Szachy Diagonalne");
        this.wygladGry.setLayout(new BorderLayout());
        this.wygladGry.setSize(ZEWNETRZNY_ROZMIAR);
        this.panelPlanszy = new PanelPlanszy();
        this.planszaZ_Szachami = Plansza.stworzPlanszeDoSzachowDiagonalnych();
        this.wygladGry.setVisible(true);
        this.wygladGry.setJMenuBar(szachownicaMenuBar);
        this.wygladGry.add(this.panelPlanszy, BorderLayout.CENTER);
    }

    private void wypelnijMenuBar(JMenuBar szachownicaMenuBar) {
        szachownicaMenuBar.add(stworzMenu());
    }

    private JMenu stworzMenu() {
        final JMenu menu = new JMenu("Pliki");
        final JMenuItem otworzPGN = new JMenuItem("Zaladuj plik PGN");
        otworzPGN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Otworz plik PGN");
            }
        });
        menu.add(otworzPGN);

        final JMenuItem wyjscieMenuItem = new JMenuItem("Zamknij");
        wyjscieMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menu.add(wyjscieMenuItem);
        return menu;
    }

    private class PanelPlanszy extends JPanel {
        final List<PanelPola> planszaPola;

        PanelPlanszy() {
            super(new GridLayout(8,8));
            this.planszaPola = new ArrayList<>();
            for (int i=0; i< Narzedzia.ILOSC_POLA; i++) {
                final PanelPola panelPola = new PanelPola(this, i);
                this.planszaPola.add(panelPola);
                add(panelPola);
            }
            setPreferredSize(ROZMIAR_PANELU_PLANSZY);
            validate();
        }
    }

    private class PanelPola extends JPanel {
        private final int numerIDPola;

        PanelPola(final PanelPlanszy panelPlanszy, final int numerIDPola) {
            super(new GridBagLayout());
            this.numerIDPola = numerIDPola;
            setPreferredSize(ROZMIAR_PANELU_POLA);
            przypiszKolorPolu();
            przypiszIkoneBierkiPolu(planszaZ_Szachami);
            /*addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(final MouseEvent e) {
                    if (isRightMouseButton(e)) {
                        poleZrodlowe = null;
                        poleDocelowe = null;
                        przemieszczenieBierki = null;
                    }
                    else if (isLeftMouseButton(e)) {
                        if (poleZrodlowe == null) {
                            poleZrodlowe = planszaZ_Szachami.getPole(numerIDPola);
                            przemieszczenieBierki = poleZrodlowe.getBierka();
                            if (przemieszczenieBierki == null) {
                                poleZrodlowe = null;
                            }
                        }
                        else {
                            poleDocelowe = planszaZ_Szachami.getPole(numerIDPola);
                            final Ruch ruch = Ruch.Posuniecie.zrobRuch(planszaZ_Szachami, poleZrodlowe.getWspolrzednePola, poleDocelowe.getWspolrzednePola);
                        }
                    }
                }


                @Override
                public void mousePressed(final MouseEvent e) {

                }

                @Override
                public void mouseReleased(final MouseEvent e) {

                }

                @Override
                public void mouseEntered( MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });*/
            validate();
        }

        private void przypiszIkoneBierkiPolu (final Plansza plansza) {
            this.removeAll();
            if (plansza.getPole(this.numerIDPola).zajetePole()) {
                try {
                    final BufferedImage image = ImageIO.read(new File(sciezkaDoObrazkowBierek +
                            plansza.getPole(this.numerIDPola).getBierka().getKolorBierki().toString().substring(0,1) +
                            plansza.getPole(this.numerIDPola).getBierka().toString() + ".gif"));
                    add(new JLabel(new ImageIcon(image)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void przypiszKolorPolu() {
            if (Narzedzia.PIERWSZY_WIERSZ[this.numerIDPola] ||
                    Narzedzia.TRZECI_WIERSZ[this.numerIDPola] ||
                    Narzedzia.PIATY_WIERSZ[this.numerIDPola] ||
                    Narzedzia.SIODMY_WIERSZ[this.numerIDPola]) {
                setBackground(this.numerIDPola % 2 == 0 ? jasnyKolorPola : ciemnyKolorPola);
            }
            else if (Narzedzia.DRUGI_WIERSZ[this.numerIDPola] ||
                    Narzedzia.CZWARTY_WIERSZ[this.numerIDPola] ||
                    Narzedzia.SZOSTY_WIERSZ[this.numerIDPola] ||
                    Narzedzia.OSMY_WIERSZ[this.numerIDPola]) {
                setBackground(this.numerIDPola % 2 != 0 ? jasnyKolorPola : ciemnyKolorPola);
            }
        }
    }
}
