
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;


/**
 * Created by Vladyslav on 09.01.2016.
 */
class Table {
    private int x, y;
    private ArrayList<Card> lastCards;
    private int lastPos, lastrnd;
    float lastpot;
    public ArrayList<Card> myCards;
    public ArrayList<Card> boardCards;
    private BufferedImage r;
    char maxs;
    int mac, z3, z4;
    int[] rmv = new int[4];
    Robot rb;

    class Card {
        private int value;
        private Character suit;

        public Card(int x, Character y) {
            value = x;
            suit = y;
        }

        public int getValue() {
            return value;
        }

        public Character getSuit() {
            return suit;
        }

    }

    public Table(int x, int y) {
        this.x = x;
        this.y = y;
        lastCards = new ArrayList<>();
        myCards = new ArrayList<>();
        boardCards = new ArrayList<>();
        try {
            rb = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public int readPos(BufferedImage r) {
        int position = 0;

        int color = r.getRGB(x + 492, y + 165);
        if ((color & 0x00ff0000) >> 16 > 150) position = 1;
        color = r.getRGB(x + 417, y + 127);
        if ((color & 0x00ff0000) >> 16 > 150) position = 2;
        color = r.getRGB(x + 236, y + 128);
        if ((color & 0x00ff0000) >> 16 > 150) position = 3;
        color = r.getRGB(x + 154, y + 165);
        if ((color & 0x00ff0000) >> 16 > 150) position = 4;
        color = r.getRGB(x + 130, y + 196);
        if ((color & 0x00ff0000) >> 16 > 150) position = 5;
        color = r.getRGB(x + 218, y + 286);
        if ((color & 0x00ff0000) >> 16 > 150) position = 6;
        color = r.getRGB(x + 376, y + 295);
        if ((color & 0x00ff0000) >> 16 > 150) position = 7;
        color = r.getRGB(x + 440, y + 281);
        if ((color & 0x00ff0000) >> 16 > 150) position = 8;
        color = r.getRGB(x + 513, y + 193);
        if ((color & 0x00ff0000) >> 16 > 150) position = 9;

        if (getRnd(r) > 1) {
            position += 2;
            if (position == 11) position = 2;
            if (position == 10) position = 1;
        }
        return position;
    }

    public int getOps(BufferedImage r) {
        int ops = 0;


        int[] x1 = {x + 517, x + 580, x + 556, x + 432, x + 246, x + 84, x + 45, x + 115};
        int[] y1 = {y + 291, y + 200, y + 110, y + 67, y + 66, y + 104, y + 193, y + 287};

        for (int i = 0; i < readPos(r) - 1; i++) {
            if ((r.getRGB(x1[i], y1[i]) & 0x00ff0000) >> 16 > 50) ops += 1;
        }

        return ops;
    }

    private int readNumSt(int x, int y, BufferedImage r) {
        int num = 0;
        int[] st = new int[7];
        int[] n0 = {1, 0, 1, 0, 1, 0, 0};//======шаблоны
        int[] n1 = {0, 1, 0, 1, 0, 1, 0};
        int[] n2 = {1, 0, 0, 1, 0, 0, 1};
        int[] n3 = {1, 0, 0, 1, 0, 0, 0};
        int[] n4 = {0, 1, 0, 1, 1, 1, 0};
        int[] n5 = {0, 0, 0, 1, 0, 0, 0};
        int[] n6 = {1, 0, 1, 1, 1, 0, 0};
        int[] n7 = {0, 0, 0, 1, 0, 1, 0};
        int[] n8 = {1, 0, 0, 1, 1, 0, 0};
        int[] n9 = {1, 0, 1, 0, 0, 0, 0};
        int color = r.getRGB(x, y);
        if ((color & 0x0000ff00) >> 8 > 150) st[0] = 1;
        else st[0] = 0;
        color = r.getRGB(x + 3, y);
        if ((color & 0x0000ff00) >> 8 > 150) st[1] = 1;
        else st[1] = 0;
        color = r.getRGB(x, y + 2);
        if ((color & 0x0000ff00) >> 8 > 150) st[2] = 1;
        else st[2] = 0;
        color = r.getRGB(x + 3, y + 2);
        if ((color & 0x0000ff00) >> 8 > 150) st[3] = 1;
        else st[3] = 0;
        color = r.getRGB(x, y + 4);
        if ((color & 0x0000ff00) >> 8 > 150) st[4] = 1;
        else st[4] = 0;
        color = r.getRGB(x + 3, y + 4);
        if ((color & 0x0000ff00) >> 8 > 150) st[5] = 1;
        else st[5] = 0;
        color = r.getRGB(x, y + 6);
        if ((color & 0x0000ff00) >> 8 > 150) st[6] = 1;
        else st[6] = 0;
        if (Arrays.equals(st, n0)) num = 0;
        else if (Arrays.equals(st, n1)) num = 1;
        else if (Arrays.equals(st, n2)) num = 2;
        else if (Arrays.equals(st, n3)) num = 3;      //=============сравнение с шаблонами
        else if (Arrays.equals(st, n4)) num = 4;
        else if (Arrays.equals(st, n5)) num = 5;
        else if (Arrays.equals(st, n6)) num = 6;
        else if (Arrays.equals(st, n7)) num = 7;
        else if (Arrays.equals(st, n8)) num = 8;
        else if (Arrays.equals(st, n9)) num = 9;


        return num;
    }

    public int getStack(BufferedImage r) {
        int stack = 0;
        int color = r.getRGB(292 + x, 358 + y);
        if ((color & 0x0000ff00) >> 8 > 100)  //===============не целое
        {
            stack += readNumSt(296 + x, 355 + y, r) * 100;
            stack += readNumSt(306 + x, 355 + y, r) * 10;
            stack += readNumSt(313 + x, 355 + y, r);
        } else                      //===============целое
        {
            stack += readNumSt(304 + x, 355 + y, r) * 100;
        }
        return stack;
    }

    public void getMyCards(BufferedImage r) {
        if (myCards.size() == 0) {
            myCards.add(0, new Card(readCard(x + 284, y + 299, r), readSuit(x + 288, y + 318, r)));
            myCards.add(1, new Card(readCard(x + 323, y + 299, r), readSuit(x + 327, y + 318, r)));
        } else {
            myCards.set(0, new Card(readCard(x + 284, y + 299, r), readSuit(x + 288, y + 318, r)));
            myCards.set(1, new Card(readCard(x + 323, y + 299, r), readSuit(x + 327, y + 318, r)));
        }
    }

    private int readCard(int x1, int x2, BufferedImage r) {
        int card = 0;
        int[] cd = new int[7];
        int[] c2 = {1, 0, 1, 0, 1, 1, 0};//======шаблоны
        int[] c3 = {1, 0, 1, 1, 1, 1, 1};
        int[] c4 = {1, 0, 1, 1, 0, 0, 1};
        int[] c5 = {0, 1, 0, 1, 0, 1, 1};
        int[] c6 = {1, 0, 0, 1, 0, 1, 1};
        int[] c7 = {0, 1, 1, 1, 1, 1, 1};
        int[] c8 = {1, 0, 1, 1, 0, 1, 1};
        int[] c9 = {1, 0, 0, 0, 0, 1, 1};
        int[] c10 = {0, 1, 0, 0, 0, 1, 0};
        int[] c11 = {1, 0, 1, 1, 1, 0, 1};
        int[] c12 = {1, 0, 0, 0, 0, 0, 0};
        int[] c13 = {0, 1, 1, 1, 0, 0, 0};
        int[] c14 = {1, 0, 1, 1, 0, 1, 0};
        int color = r.getRGB(x1 + 4, x2 + 19);
        if ((color & 0x00ff0000) >> 16 < 100) {
            color = r.getRGB(x1 + 2, x2 + 1);          //======построение массива карты not heart
            if ((color & 0x00ff0000) >> 16 > 150) cd[0] = 1;
            else cd[0] = 0;
            color = r.getRGB(x1 + 6, x2 + 3);
            if ((color & 0x00ff0000) >> 16 > 150) cd[1] = 1;
            else cd[1] = 0;
            color = r.getRGB(x1 + 1, x2 + 5);
            if ((color & 0x00ff0000) >> 16 > 150) cd[2] = 1;
            else cd[2] = 0;
            color = r.getRGB(x1 + 8, x2 + 5);
            if ((color & 0x00ff0000) >> 16 > 150) cd[3] = 1;
            else cd[3] = 0;
            color = r.getRGB(x1 + 2, x2 + 7);
            if ((color & 0x00ff0000) >> 16 > 150) cd[4] = 1;
            else cd[4] = 0;
            color = r.getRGB(x1 + 6, x2 + 9);
            if ((color & 0x00ff0000) >> 16 > 150) cd[5] = 1;
            else cd[5] = 0;
            color = r.getRGB(x1 + 8, x2 + 11);
            if ((color & 0x00ff0000) >> 16 > 150) cd[6] = 1;
            else cd[6] = 0;
        } else {
            color = r.getRGB(x1 + 2, x2 + 1);          //======построение массива карты heart
            if ((color & 0x000000ff) > 150) cd[0] = 1;
            else cd[0] = 0;
            color = r.getRGB(x1 + 6, x2 + 3);
            if ((color & 0x000000ff) > 150) cd[1] = 1;
            else cd[1] = 0;
            color = r.getRGB(x1 + 1, x2 + 5);
            if ((color & 0x000000ff) > 150) cd[2] = 1;
            else cd[2] = 0;
            color = r.getRGB(x1 + 8, x2 + 5);
            if ((color & 0x000000ff) > 150) cd[3] = 1;
            else cd[3] = 0;
            color = r.getRGB(x1 + 2, x2 + 7);
            if ((color & 0x000000ff) > 150) cd[4] = 1;
            else cd[4] = 0;
            color = r.getRGB(x1 + 6, x2 + 9);
            if ((color & 0x000000ff) > 150) cd[5] = 1;
            else cd[5] = 0;
            color = r.getRGB(x1 + 8, x2 + 11);
            if ((color & 0x000000ff) > 150) cd[6] = 1;
            else cd[6] = 0;
        }

        if (Arrays.equals(cd, c2)) card = 2;
        else if (Arrays.equals(cd, c3)) card = 3;      //=============сравнение с шиблонами
        else if (Arrays.equals(cd, c4)) card = 4;
        else if (Arrays.equals(cd, c5)) card = 5;
        else if (Arrays.equals(cd, c6)) card = 6;
        else if (Arrays.equals(cd, c7)) card = 7;
        else if (Arrays.equals(cd, c8)) card = 8;
        else if (Arrays.equals(cd, c9)) card = 9;
        else if (Arrays.equals(cd, c10)) card = 10;
        else if (Arrays.equals(cd, c11)) card = 11;
        else if (Arrays.equals(cd, c12)) card = 12;
        else if (Arrays.equals(cd, c13)) card = 13;
        else if (Arrays.equals(cd, c14)) card = 14;

        return card;
    }

    private char readSuit(int x, int y, BufferedImage r) {
        Character suit;
        int color = r.getRGB(x, y);
        if ((color & 0x00ff0000) >> 16 > 80) suit = 'h';
        else if ((color & 0x0000ff00) >> 8 > 80) suit = 'c';
        else if ((color & 0x000000ff) > 80) suit = 'd';
        else suit = 's';
        return suit;
    }

    public int getOpps(BufferedImage r) {               //=============оппонненты после позиции
        int ops = 0;

        int[] x1 = {x + 517, x + 586, x + 556, x + 432, x + 246, x + 84, x + 45, x + 115};
        int[] y1 = {y + 291, y + 200, y + 110, y + 67, y + 66, y + 104, y + 193, y + 287};

        if (readPos(r) > 0) {
            for (int i = 0; i <= 8 - readPos(r); i++) {
                if (((r.getRGB(x1[7 - i], y1[7 - i]) & 0x00ff0000) >> 16) > 50) ops += 1;
            }
        }

        return ops;
    }

    public boolean checkOpen(BufferedImage r) {
        boolean open;
        if (((r.getRGB(x + 609, y + 9) & 0x00ff0000) >> 16) > 200) open = true;
        else open = false;
        return open;
    }

    public boolean checkFold(BufferedImage r) {
        this.r = r;
        boolean move;
        if (((r.getRGB(x + 395, y + 425) & 0x00ff0000) >> 16) > 100) move = true;
        else move = false;
        return move;
    }

    public boolean checkRaise(BufferedImage r) {
        boolean move;
        if (((r.getRGB(x + 610, y + 425) & 0x00ff0000) >> 16) > 100) move = true;
        else move = false;
        return move;
    }

    public boolean checkCheck(BufferedImage r) {
        boolean move;
        if (((r.getRGB(x + 478, y + 425) & 0x0000ff00) >> 8) > 235) move = false;
        else move = true;
        return move;
    }

    public boolean checkPause(BufferedImage r) {
        boolean move;
        if (((r.getRGB(x + 21, y + 360) & 0x0000ff00) >> 8) > 235) move = true;
        else move = false;
        return move;
    }

    public boolean firstMove(BufferedImage r) {
        boolean move;
        if (lastCards.equals(myCards) && lastPos == readPos(r) && lastpot < getPot(r) && lastrnd == getRnd(r))
            move = false;
        else move = true;
        return move;
    }

    public int getPot(BufferedImage r) {
        int pot = 0;
        pot += getNumPot(getXPot(1, r), 153 + y, r) * 100;
        pot += getNumPot(getXPot(2, r), 153 + y, r) * 10;
        pot += getNumPot(getXPot(3, r), 153 + y, r);
        return pot;
    }

    private int getNumPot(int x, int y, BufferedImage r) {
        int num = 0;
        int[] st = new int[6];
        int[] n0 = {1, 1, 0, 1, 0, 1};//======шаблоны
        int[] n1 = {0, 0, 0, 0, 0, 1};
        int[] n2 = {1, 0, 1, 0, 0, 1};
        int[] n3 = {1, 0, 1, 0, 1, 1};
        int[] n4 = {1, 0, 1, 1, 1, 0};
        int[] n5 = {1, 1, 0, 0, 1, 1};
        int[] n6 = {0, 1, 0, 1, 0, 1};
        int[] n7 = {1, 0, 0, 0, 0, 0};
        int[] n8 = {1, 1, 1, 1, 1, 1};
        int[] n9 = {1, 1, 0, 0, 0, 1};
        int color = r.getRGB(x + 4, y);
        if ((color & 0x000000ff) > 150) st[0] = 1;
        else st[0] = 0;
        color = r.getRGB(x, y + 2);
        if ((color & 0x000000ff) > 150) st[1] = 1;
        else st[1] = 0;
        color = r.getRGB(x + 4, y + 2);
        if ((color & 0x000000ff) > 150) st[2] = 1;
        else st[2] = 0;
        color = r.getRGB(x, y + 5);
        if ((color & 0x000000ff) > 150) st[3] = 1;
        else st[3] = 0;
        color = r.getRGB(x + 4, y + 5);
        if ((color & 0x000000ff) > 150) st[4] = 1;
        else st[4] = 0;
        color = r.getRGB(x + 2, y + 8);
        if ((color & 0x000000ff) > 150) st[5] = 1;
        else st[5] = 0;
        if (Arrays.equals(st, n0)) num = 0;
        else if (Arrays.equals(st, n1)) num = 1;
        else if (Arrays.equals(st, n2)) num = 2;
        else if (Arrays.equals(st, n3)) num = 3;      //=============сравнение с шаблонами
        else if (Arrays.equals(st, n4)) num = 4;
        else if (Arrays.equals(st, n5)) num = 5;
        else if (Arrays.equals(st, n6)) num = 6;
        else if (Arrays.equals(st, n7)) num = 7;
        else if (Arrays.equals(st, n8)) num = 8;
        else if (Arrays.equals(st, n9)) num = 9;

        return num;
    }

    public int getXPot(int q, BufferedImage r) {
        int num = 0;
        if (q == 1) {
            for (int i = 324 + x; i < 327 + x; i++) {
                for (int j = 152 + y; j < 162 + y; j++) {
                    if (((r.getRGB(i, j) & 0x00ff0000) >> 16) > 250) {
                        num = i;
                        break;
                    }

                }
                if (num == i) break;
            }
        }
        if (q == 2) {
            for (int i = 336 + x; i < 339 + x; i++) {
                for (int j = 152 + y; j < 162 + y; j++) {
                    if (((r.getRGB(i, j) & 0x00ff0000) >> 16) > 250) {
                        num = i;
                        break;
                    }

                }
                if (num == i) break;
            }
        }
        if (q == 3) {
            for (int i = 344 + x; i < 349 + x; i++) {
                for (int j = 152 + y; j < 162 + y; j++) {
                    if (((r.getRGB(i, j) & 0x00ff0000) >> 16) > 250) {
                        num = i;
                        break;
                    }

                }
                if (num == i) break;
            }
        }

        return num;
    }

    public int getRaise(BufferedImage r) {
        int cll = 0;
        int y = 441 + this.y;
        cll += getNumRs(getXRs(1, r), y, r) * 100;
        cll += getNumRs(getXRs(2, r), y, r) * 10;
        cll += getNumRs(getXRs(3, r), y, r);
        return cll;
    }

    public int getNumRs(int x, int y, BufferedImage r) {
        int num = 0;
        int[] st = new int[6];
        int[] n0 = {1, 1, 0, 1, 0, 1};//======шаблоны
        int[] n1 = {0, 0, 0, 0, 0, 1};
        int[] n2 = {1, 0, 1, 0, 0, 1};
        int[] n3 = {1, 0, 1, 0, 1, 1};
        int[] n4 = {1, 0, 1, 1, 1, 0};
        int[] n5 = {1, 1, 0, 0, 1, 1};
        int[] n6 = {0, 1, 0, 1, 0, 1};
        int[] n7 = {1, 0, 0, 0, 0, 0};
        int[] n8 = {1, 1, 1, 1, 1, 1};
        int[] n9 = {1, 1, 0, 0, 0, 1};
        int color = r.getRGB(x + 4, y);
        if ((color & 0x0000ff00) >> 8 > 150) st[0] = 1;
        else st[0] = 0;
        color = r.getRGB(x, y + 2);
        if ((color & 0x0000ff00) >> 8 > 150) st[1] = 1;
        else st[1] = 0;
        color = r.getRGB(x + 4, y + 2);
        if ((color & 0x0000ff00) >> 8 > 150) st[2] = 1;
        else st[2] = 0;
        color = r.getRGB(x, y + 5);
        if ((color & 0x0000ff00) >> 8 > 150) st[3] = 1;
        else st[3] = 0;
        color = r.getRGB(x + 4, y + 5);
        if ((color & 0x0000ff00) >> 8 > 150) st[4] = 1;
        else st[4] = 0;
        color = r.getRGB(x + 2, y + 8);
        if ((color & 0x0000ff00) >> 8 > 150) st[5] = 1;
        else st[5] = 0;
        if (Arrays.equals(st, n0)) num = 0;
        else if (Arrays.equals(st, n1)) num = 1;
        else if (Arrays.equals(st, n2)) num = 2;
        else if (Arrays.equals(st, n3)) num = 3;      //=============сравнение с шаблонами
        else if (Arrays.equals(st, n4)) num = 4;
        else if (Arrays.equals(st, n5)) num = 5;
        else if (Arrays.equals(st, n6)) num = 6;
        else if (Arrays.equals(st, n7)) num = 7;
        else if (Arrays.equals(st, n8)) num = 8;
        else if (Arrays.equals(st, n9)) num = 9;

        return num;
    }

    public int getXRs(int q, BufferedImage r) {
        int num = 0;
        if (q == 1) {
            for (int i = 564 + x; i < 568 + x; i++) {
                for (int j = 440 + y; j < 450 + y; j++) {
                    if (((r.getRGB(i, j) & 0x00ff0000) >> 16) > 250) {
                        num = i;
                        break;
                    }
                }
                if (num == i) break;
            }
        }
        if (q == 2) {
            for (int i = 576 + x; i < 580 + x; i++) {
                for (int j = 440 + y; j < 450 + y; j++) {
                    if (((r.getRGB(i, j) & 0x00ff0000) >> 16) > 250) {
                        num = i;
                        break;
                    }
                }
                if (num == i) break;
            }
        }
        if (q == 3) {
            for (int i = 584 + x; i < 589 + x; i++) {
                for (int j = 440 + y; j < 450 + y; j++) {
                    if (((r.getRGB(i, j) & 0x00ff0000) >> 16) > 250) {
                        num = i;
                        break;
                    }
                }
                if (num == i) break;
            }
        }

        return num;
    }

    public int getXCll(int q, BufferedImage r) {
        int num = 0;
        if (q == 1) {
            for (int i = 564 + x - 107; i < 568 + x - 107; i++) {
                for (int j = 440 + y; j < 450 + y; j++) {
                    if (((r.getRGB(i, j) & 0x00ff0000) >> 16) > 250) {
                        num = i;
                        break;
                    }

                }
                if (num == i) break;
            }
        }
        if (q == 2) {
            for (int i = 576 + x - 107; i < 580 + x - 107; i++) {
                for (int j = 440 + y; j < 450 + y; j++) {
                    if (((r.getRGB(i, j) & 0x00ff0000) >> 16) > 250) {
                        num = i;
                        break;
                    }

                }
                if (num == i) break;
            }
        }
        if (q == 3) {
            for (int i = 584 + x - 107; i < 589 + x - 107; i++) {
                for (int j = 440 + y; j < 450 + y; j++) {
                    if (((r.getRGB(i, j) & 0x00ff0000) >> 16) > 250) {
                        num = i;
                        break;
                    }

                }
                if (num == i) break;
            }
        }

        return num;
    }

    public int getCall(BufferedImage r) {
        int cll = 0;
        if (((r.getRGB(450 + x, 425 + y) & 0x00ff0000) >> 16) < 100
                && ((r.getRGB(550 + x, 425 + y) & 0x00ff0000) >> 16) > 100) cll = getRaise(r);
        else {
            int y = 441 + this.y;
            cll += getNumRs(getXCll(1, r), y, r) * 100;
            cll += getNumRs(getXCll(2, r), y, r) * 10;
            cll += getNumRs(getXCll(3, r), y, r);
        }
        return cll;
    }

    public int getRnd(BufferedImage r) {
        int rnd;
        if (((r.getRGB(x + 395, y + 200) & 0x00ff0000) >> 16) > 200) rnd = 5;
        else if (((r.getRGB(x + 350, y + 200) & 0x00ff0000) >> 16) > 200) rnd = 4;
        else if (((r.getRGB(x + 305, y + 200) & 0x00ff0000) >> 16) > 200) rnd = 3;
        else rnd = 1;
        return rnd;
    }

    public void readBoardCards(BufferedImage r) {
        boardCards.clear();
        int[] xc = {212, 257, 302, 347, 392};
        int[] xs = {216, 261, 306, 351, 396};
        int rnd = getRnd(r);
        if (rnd > 1) {
            for (int i = 0; i < rnd; i++) {
                boardCards.add(i, new Card(readCard(x + xc[i], y + 172, r), readSuit(x + xs[i], y + 191, r)));
            }
        }
    }

    public int getMac() {
        int cd = 0, cs = 0, ch = 0, cc = 0, mac = 0;
        for (Card card : boardCards) {
            switch (card.getSuit()) {
                case 'c':
                    cc++;
                    break;
                case 'd':
                    cd++;
                    break;
                case 'h':
                    ch++;
                    break;
                case 's':
                    cs++;
                    break;
            }
        }
        if (cc > cd && cc > ch && cc > cs) {
            mac = cc;
            maxs = 'c';
        } else if (ch > cd && ch > cc && ch > cs) {
            mac = ch;
            maxs = 'h';
        } else if (cd > cc && cd > ch && cd > cs) {
            mac = cd;
            maxs = 'd';
        } else if (cs > cd && cs > ch && cs > cc) {
            mac = cs;
            maxs = 's';
        } else maxs = 'n';
        return mac;
    }

    private boolean isFlash() {
        int cd = 0, cs = 0, ch = 0, cc = 0, mac = 0;
        for (Card card : myCards) {
            switch (card.getSuit()) {
                case 'c':
                    cc++;
                    break;
                case 'd':
                    cd++;
                    break;
                case 'h':
                    ch++;
                    break;
                case 's':
                    cs++;
                    break;
            }
        }
        for (Card card : boardCards) {
            switch (card.getSuit()) {
                case 'c':
                    cc++;
                    break;
                case 'd':
                    cd++;
                    break;
                case 'h':
                    ch++;
                    break;
                case 's':
                    cs++;
                    break;
            }
        }
        if (cc > cd && cc > ch && cc > cs) {
            mac = cc;
        } else if (ch > cd && ch > cc && ch > cs) {
            mac = ch;
        } else if (cd > cc && cd > ch && cd > cs) {
            mac = cd;
        } else if (cs > cd && cs > ch && cs > cc) {
            mac = cs;
        }
        if (mac >= 5 && (myCards.get(0).getSuit().equals(maxs) & myCards.get(0).getValue() >= 12 ||
                myCards.get(1).getSuit().equals(maxs) & myCards.get(1).getValue() >= 12)) return true;
        else return false;
    }

    private boolean isStrit() {
        Integer[] cards = new Integer[getRnd(r) + 2];
        ArrayList<Integer> cards2 = new ArrayList<>();
        int j = 0, dif = 0;
        for (Card card : myCards) {
            cards[j] = card.getValue();
            j++;
        }
        for (Card card : boardCards) {
            cards[j] = card.getValue();
            j++;
        }
        Arrays.sort(cards, Collections.reverseOrder());

        for (int i = 0; i < cards.length - 1; i++) {
            if (!cards[i + 1].equals(cards[i])) {
                cards2.add(cards[i]);
            }
        }
        cards2.add(cards[cards.length - 1]);
        System.out.println("Strit:");
        for (int i : cards2) {
            System.out.print(i + " ");
        }
        System.out.println();
        if (cards2.size() < 5) return false;

        switch (cards2.size()) {
            case 5: {
                for (int i = 0; i < 4; i++) {
                    dif += cards2.get(i) - cards2.get(i + 1) - 1;
                }
                break;
            }
            case 6: {
                for (int i = 0; i < 4; i++) {
                    dif += cards2.get(i) - cards2.get(i + 1) - 1;
                }
                if (dif == 0) break;
                else dif = 0;
                for (int i = 1; i < 5; i++) {
                    dif += cards2.get(i) - cards2.get(i + 1) - 1;
                }
                break;
            }
            case 7: {
                for (int i = 0; i < 4; i++) {
                    dif += cards2.get(i) - cards2.get(i + 1) - 1;
                }
                if (dif == 0) break;
                else dif = 0;
                for (int i = 1; i < 5; i++) {
                    dif += cards2.get(i) - cards2.get(i + 1) - 1;
                }
                if (dif == 0) break;
                else dif = 0;
                for (int i = 2; i < 6; i++) {
                    dif += cards2.get(i) - cards2.get(i + 1) - 1;
                }
                break;
            }
        }
        if (dif == 0) return true;
        else return false;
    }

    public int getCombination() {
        readBoardCards(r);
        int z1 = 0,
                z2 = 0,
                cmb = 0,
                t = 0,
                t2 = 0,
                t3 = 0,
                mbrd = 0,
                mc1 = myCards.get(0).getValue(),
                mc2 = myCards.get(1).getValue();
        Integer[] brd = {0, 0, 0, 0, 0};
        z3 = 0;
        z4 = 0;
        for (Card bc : boardCards) {
            if (mc1 == bc.getValue()) z1++;
            if (mc2 == bc.getValue()) z2++;
        }
        int num = 0;
        for (Card card : boardCards) {
            if (card.getValue() > mbrd) mbrd = card.getValue();
            brd[num] = card.getValue();
            num++;
        }

        for (int i = 0; i < 4; i++) {
            for (int j = i + 1; j < 5; j++) {
                if (brd[i] == brd[j] && brd[j] > 0) {
                    if (z3 == 0) {
                        z3++;
                        t = brd[i];
                    } else if (brd[j] == t) z3++;
                    else {
                        z4++;
                        t2 = brd[j];
                    }
                }
            }
        }

        for (int i : brd) {
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.println("Z1 = " + z1 + " Z2 = " + z2 + " Z3 = " + z3 + " Z4 = " + z4);

        if (z3 == 3) for (int i = 0; i < 5; i++) if (brd[i] > t3 && brd[i] != t) t3 = brd[i];
        if (z4 == 3) for (int i = 0; i < 5; i++) if (brd[i] > t3 && brd[i] != t2) t3 = brd[i];
        if (mbrd == mc1 || mbrd == mc2) cmb = 1;                                     //=====Top pair
        if (mc1 == mc2 && mc1 > mbrd) cmb = 2;                                       //=====Overpair
        if (z1 == 1 && z2 == 1 && mc1 != mc2) cmb = 3;                               //=====Two pairs
        if ((z1 == 2 && z2 == 0) || (z1 == 0 && z2 == 2)) cmb = 4;                   //=====Triple
        if (z1 == 1 && z2 == 1 && mc1 == mc2) cmb = 5;                               //======Set
        if (isStrit()) cmb = 6;                                                      //======Strit
        if (isFlash()) cmb = 7;                                                      //======Flesh
        if ((z3 == 3 && z4 == 1) || (z3 == 1 && z4 == 3))
            cmb = 8;                                                                  //======FullHouse board
        if ((z1 == 2 && z2 == 1) || (z1 == 1 && z2 == 2)) cmb = 9;                    //======FullHouse
        if (z1 == 1 && z2 == 1 && mc1 == mc2 && z3 == 1 && mc1 != t) cmb = 9;         //======FullHouse
        if (z1 == 1 && z2 == 1 && mc1 == mc2 && z4 == 1 && mc1 != t2) cmb = 9;
        if (z3 == 3 && z1 == 1 && z2 == 0 && mc1 != t) cmb = 9;
        if (z3 == 3 && z1 == 0 && z2 == 1 && mc2 != t) cmb = 9;
        if (z3 == 1 && z4 == 1 && mc1 == t) cmb = 9;
        if (z3 == 1 && z4 == 1 && mc1 == t2) cmb = 9;
        if (z3 == 1 && z4 == 1 && mc2 == t) cmb = 9;
        if (z3 == 1 && z4 == 1 && mc2 == t2) cmb = 9;
        if (z3 == 3 && mc1 == mc2 && mc1 > t3) cmb = 9;
        if (z4 == 3 && mc1 == mc2 && mc1 > t3) cmb = 9;
        if (z1 == 2 && z2 == 2 && mc1 == mc2) cmb = 10;                                      //======Care
        if (z1 == 3 || z2 == 3) cmb = 10;
        if (z3 == 6 || z4 == 6) cmb = 20;
        return cmb;
    }

    public int moveNum(BufferedImage r) {
        this.r = r;
        getMyCards(r);
        readBoardCards(r);
        if (checkRaise(r) && checkFold(r)) return fullMove(r);
        else if (checkFold(r) && getRnd(r) == 1) return quiqMove(r);
        else return 0;
    }

    private int quiqMove(BufferedImage r) {
        int move = 0,
                pos = readPos(r),
                pot = getPot(r);
        int mc1 = myCards.get(0).getValue(), mc2 = myCards.get(1).getValue();
        if (pos > 5) {                                                             //=============late pos
            if (pos < 7 && mc1 + mc2 < 15 && mc1 < 10 && mc2 < 10) move = 10;
            if (pos < 7 && mc1 + mc2 < 7 && mc2 == mc1) move = 10;
            if (pos < 7 && mc1 == mc2 && mc1 + mc2 < 13) move = 10;
        } else {                                                                  //=====early position
            if (mc1 != mc2 && mc1 + mc2 <= 24) move = 10;
            if (mc1 == mc2 && mc1 + mc2 <= 15) move = 10;
        }
        return move;
    }

    private int fullMove(BufferedImage r) {
        int mp = readPos(r),
                ops = getOps(r),
                rnd = getRnd(r),
                mc1 = myCards.get(0).getValue(), mc2 = myCards.get(1).getValue(),
                opsa = getOpps(r),
                brrs = 0,
                cmb = 0,
                mac = getMac();
        float pot = getPot(r),
                sumcll = getCall(r);
        int rs1 = 0, cll = 0, rrs = 0, rs = 0,
                bc1 = 0, bc2 = 0, bc3 = 0, bc4 = 0, bc5 = 0, mbrd = 0;
        if (rnd > 1) {
            cmb = getCombination();
            bc1 = boardCards.get(0).getValue();
            bc2 = boardCards.get(1).getValue();
            bc3 = boardCards.get(2).getValue();
            mbrd = getMaxBoard();
        }
        if (rnd > 3) bc4 = boardCards.get(3).getValue();
        if (rnd > 4) bc5 = boardCards.get(4).getValue();
        char cm1 = myCards.get(0).getSuit(), cm2 = myCards.get(1).getSuit();

        if (firstMove(r) && sumcll > 10 && (pot - 3 - (ops - 1) * 2) / 2 < sumcll) brrs = 1;
        boolean chk = checkCheck(r);

        if (rnd == 1) {
            if (firstMove(r)) {
                if (mp > 5) {          //=============late pos
                    if (ops == 0) {
                        if (mp == 8 || mp == 7) rs1 = 1;
                        if ((mc1 > 10 || mc2 > 10) && cm1 == cm2) rs1 = 1;
                        if (mc1 + mc2 > 17 && (mc1 > 10 || mc2 > 10)) rs1 = 1;
                        if (mc2 == mc1) rs1 = 1;
                    } else                   //========== >0 ops
                    {
                        if (((pot - 3) / ops) == 2)    //only calls
                        {
                            if (mc1 + mc2 >= 25 && ops == 1 ||                   //AJ+ 1 ops
                                    mc1 + mc2 >= 26 ||                          //AQ+
                                    mc1 + mc2 >= 22 && mc1 == mc2) rrs = 1;     //JJ+
                            if (mp == 8 && mc1 == mc2 ||
                                    ops > 1 && mc1 == mc2 && mc1 + mc2 >= 10 ||
                                    mc1 == mc2 && mc1 + mc2 >= 14) cll = 1;
                        } else {
                            if (mc1 == mc2 && mc1 + mc2 >= 24 && sumcll <= 8 ||
                                    mc1 == mc2 && mc1 + mc2 >= 26) rrs = 1;
                            if (mc1 == mc2 && mc1 + mc2 >= 16 && brrs == 0 && ops >= 2 && sumcll <= 8) cll = 1;
                            if (mc1 == mc2 && brrs == 0 && mc1 + mc2 >= 16 && sumcll <= 6) cll = 1;
                            if (mc1 + mc2 >= 26 && sumcll <= 10) cll = 1;
       /* if (mp==9 && ops==1 && sumcll<=7/100){
        if (ops1[1]>0)
                {
        if ((mc1+mc2)>=25 && (mc1=14) or (mc2=14))   rrs=1;
        if (mc1=mc2) &&(mc1+mc2>=14)   rrs=1;
        };
        if (ops1[2]>0) or (ops1[3]>0)
                {
        if ((mc1+mc2)>=25 && (mc1=14) or (mc2=14))   rrs=1;
        if (mc1=mc2) &&(mc1+mc2>=14)   rrs=1;
        };
        };*/
                            if (ops > 1 && mc1 == mc2 && mc1 + mc2 > 15 && pot - 3 / ops <= sumcll && sumcll <= 8)
                                cll = 1;
                            if (ops > 1 && mc1 == mc2 && sumcll <= 6 && mp <= 7 && sumcll / pot <= 3 / 10 ||
                                    ops > 1 && mc1 == mc2 && sumcll <= 5 && mp == 8 && sumcll / pot <= 4 / 10 ||
                                    ops > 1 && mc1 == mc2 && sumcll <= 4 && mp == 9 && sumcll / pot <= 4 / 10) cll = 1;
                        }
                    }
                } else {                        //=====early position
                    if (ops == 0) {                              //======0 ops
                        if (mp > 3 && mc1 + mc2 >= 25) rs1 = 1;
                        if (mc1 + mc2 >= 25 && (mc1 == 14 || mc2 == 14)) rs1 = 1;
                        if (mc1 == mc2 && mc1 + mc2 >= 14 && mp <= 3) rs1 = 1;
                        if (mc1 == mc2 && mc1 + mc2 >= 12 && mp > 3) rs1 = 1;
                    } else {
                        if (pot - 3 / ops == 2) {
                            if (mc1 == mc2 && mc1 + mc2 >= 20 ||
                                    mc1 + mc2 >= 26) rrs = 1;
                            if (mc1 == mc2 && mc1 >= 8) cll = 1;
                        } else {
                            if (mc1 == mc2 && mc1 + mc2 >= 24 && sumcll <= 8 ||
                                    mc1 == mc2 && mc1 + mc2 >= 26) rrs = 1;
                            if (mc1 == mc2 && mc1 + mc2 >= 16 && brrs == 0 && ops >= 2 && sumcll <= 8 ||
                                    mc1 + mc2 >= 26 && ops == 1 && sumcll <= 8) cll = 1;
                        }
                    }
                }
            } else                  //====Not first move
            {
                if (mc1 == mc2 && mc1 + mc2 > 25) rrs = 1;
                if (mc1 + mc2 > 26 && sumcll / pot <= 0.35 && sumcll <= 35 && ops + opsa == 1) cll = 1;
                if (mc1 + mc2 >= 26 && mp > 4 && mp < 8 && sumcll <= 12 && ops + opsa == 1) cll = 1;
                if (mc1 + mc2 >= 16 && mc1 == mc2 && sumcll / pot <= 0.15 && sumcll <= 12) cll = 1;
                if (mc1 + mc2 >= 20 && mc1 == mc2 && sumcll / pot <= 0.25 && sumcll <= 7) cll = 1;
            }
        }

        if (rnd == 3) {
            if (firstMove(r))                //===============FLOP
            {
                if (ops == 0 && opsa == 1) {
                    if (mac > 2) {
                        if (maxs != cm1 && maxs != cm2 && cmb > 2) rs = 1;
                        if (maxs == cm1 && mc1 > 11) rs = 1;
                        if (maxs == cm2 && mc2 > 11) rs = 1;
                        if (cmb > 6) rs = 1;
                        //if (mp = 1 && rmv[0] = 3 && ops2[1] = 1) rs = 1;
                    } else {
                        if (mac == 2 && cm1 == cm2 && cm1 == maxs && mc1 > mbrd && mc2 > mbrd) rs = 1;
                        if (bc1 > 10 && bc2 < 10 && bc3 < 10 && mp > 2 && rmv[0] >= 3) rs = 1;
                        if (bc2 > 10 && bc1 < 10 && bc3 < 10 && mp > 2 && rmv[0] >= 3) rs = 1;
                        if (bc3 > 10 && bc2 < 10 && bc1 < 10 && mp > 2 && rmv[0] >= 3) rs = 1;
                        if (z3 == 0 && z4 == 0 && cmb > 2 && cmb < 6 && chk)
                            cll = 1;
                        if (z3 == 0 && z4 == 0 && cmb > 2 && cmb < 6 && !chk)
                            rrs = 1;
                        if (z3 == 0 && z4 == 0 && cmb > 2 && cmb < 6 && !chk
                                && sumcll / pot <= 0.3) cll = 1;
                        if (cmb >= 10) cll = 1;
                        if (z3 == 0 && z4 == 0 && cmb <= 2 && cmb >= 1) rs = 1;
                        if (z3 == 0 && z4 == 0 && cmb > 5 && cmb < 10) rs = 1;
                        if (z3 == 1 && cmb > 0 && cmb < 10) rs = 1;
                        if (z3 == 3 && cmb > 7 && cmb < 10) rs = 1;
                        if (z3 == 0 && z4 == 0 && mc1 == mc2 && mc2 >= 12 && mc2 < mbrd && chk) rs = 1;
                        if (mc1 + mc2 >= 26 && mbrd <= 10 && z3 <= 1 && rmv[0] > 2) rs = 1;
                        // if (mp == 1 && rmv[0] == 3 && ops2[1] == 1) rs = 1; //RS after steal
                        if (rmv[0] == 3) rs = 1;
                    }
                }

                if (ops == 1 && opsa == 0) {
                    if (checkCheck(r)) {
                        if (mac > 2) {
                            if (maxs != cm1 && maxs != cm2 && cmb > 0) rs = 1;
                            if (maxs == cm1 && mc1 > 11) rs = 1;
                            if (maxs == cm2 && mc2 > 11) rs = 1;
                            if (cmb > 6) rs = 1;
                            if (rmv[0] == 3 && mp == 9) rs = 1;
                            if (rmv[0] == 3 && mp == 8) rs = 1;
                            if (rmv[0] == 4 && mp == 2) rs = 1;
                        } else {
                            if (mac == 2 && cm1 == cm2 && cm1 == maxs && mc1 > mbrd && mc2 > mbrd) rs = 1;
                            if (mac == 2 && cm1 == cm2 && cm1 == maxs && rmv[0] > 2) rs = 1;
                            if (rmv[0] == 3 && pot <= 40) rs = 1;              //Cont bet
                            if (rmv[0] == 4 && mp == 2 && pot <= 40) rs = 1;    //Cont bet
                            if (z3 == 0 && z4 == 0 && mc1 == mc2 && mc2 >= 12 && mc2 < mbrd)
                                rs = 1;
                            if (bc1 > 10 && bc2 < 10 && bc3 < 10 && pot <= 40) rs = 1;
                            if (bc2 > 10 && bc1 < 10 && bc3 < 10 && pot <= 40) rs = 1;
                            if (bc3 > 10 && bc2 < 10 && bc1 < 10 && pot <= 40) rs = 1;
                            if (z3 == 0 && z4 == 0 && cmb > 0) rs = 1;
                            if (z3 == 1 && z4 == 0 && cmb > 0) rs = 1;
                            if (z3 == 3 && cmb > 7) rs = 1;
                            if (z3 <= 1 && mc1 > mbrd && mc2 > mbrd && rmv[0] > 2) rs = 1;
                        }

                    } else {
                        if (mac == 3) {
                            if (maxs != cm1 && maxs != cm2 && cmb > 3) rrs = 1;
                            if (maxs != cm1 && maxs != cm2 && cmb > 3 && sumcll / pot < 0.3) cll = 1;
                            if (maxs != cm1 && maxs != cm2 && cmb > 5)
                                rrs = 1;
                            if (maxs == cm1 && mc1 > 11 && (sumcll / pot) < 0.45) cll = 1;
                            if (maxs == cm2 && mc2 > 11 && (sumcll / pot) < 0.45) cll = 1;
                            if (maxs == cm1 && mc1 > 11 && cmb > 1) rrs = 1;
                            if (maxs == cm2 && mc2 > 11 && cmb > 1) rrs = 1;
                            if (cmb > 3 && (sumcll / pot) < 0.4) cll = 1;
                            if (cmb > 6) rrs = 1;
                        } else {
                            if (mc1 > mbrd && mc2 > mbrd && sumcll / pot <= 0.15 && chk)
                                cll = 1;
                            if (mac == 2 && z4 == 0 && z3 <= 1 && maxs == cm1 && maxs == cm2
                                    && sumcll / pot <= 35 / 100 && mc1 > 12) cll = 1;
                            if (mac == 2 && z4 == 0 && z3 <= 1 && maxs == cm1 && maxs == cm2
                                    && sumcll / pot <= 35 / 100 && mc2 > 12) cll = 1;
                            if (z3 == 0 && z4 == 0 && cmb == 1 && mc1 == mbrd
                                    && mc2 >= 11 && sumcll / pot <= 4 / 10) cll = 1;
                            if (z3 == 0 && z4 == 0 && cmb == 1 && mc2 == mbrd
                                    && mc1 >= 11 && sumcll / pot <= 4 / 10) cll = 1;
                            if (z3 == 0 && z4 == 0 && cmb == 2 && mc1 + mc2 >= 24) rrs = 1;
                            if (z3 == 0 && z4 == 0 && cmb > 2) rrs = 1;
                            if (z3 == 1 && cmb > 0 && sumcll / pot < 3 / 10) cll = 1;
                            if (z3 == 1 && cmb > 0 && sumcll / pot <= 0.5 && sumcll <= 25) cll = 1;
                            if (z3 == 1 && cmb >= 4) rrs = 1;
                            if (z3 == 3 && cmb > 7) rrs = 1;
                            if (z3 == 0 && z4 == 0 && mc1 == mc2 && mc2 >= 12 && mc2 < mbrd
                                    && sumcll / pot < 0.2) cll = 1;
                        }
                    }
                }

                if (ops + opsa > 1) {
                    if (chk) {
                        if (mac == 3) {
                            if (maxs != cm1 && maxs != cm1 && cmb > 3) rs = 1;
                            if (cmb > 6) rs = 1;
                        } else {
                            if (z3 == 0 && cmb > 0 && pot <= 25) rs = 1;
                            if (z3 == 0 && cmb > 0) rs = 1;
                            if (z3 == 1 && cmb > 0) rs = 1;
                            if (z3 == 3 && cmb > 7) rs = 1;
                        }
                    } else {
                        if (mac == 3) {
                            if (maxs != cm1 && maxs != cm1 && cmb > 3) rrs = 1;
                            if (maxs == cm1 && mc1 > 11 && (sumcll / pot) < (30 / 100)) cll = 1;
                            if (maxs == cm2 && mc2 > 11 && (sumcll / pot) < (30 / 100)) cll = 1;
                            if (cmb > 5) rrs = 1;
                        } else {
                            if (mac == 2 && z4 == 0 && z3 <= 1 && maxs == cm1 && maxs == cm2
                                    && sumcll / pot <= 35 / 100 && mc1 > 12) cll = 1;
                            if (mac == 2 && z4 == 0 && z3 <= 1 && maxs == cm1 && maxs == cm2
                                    && sumcll / pot <= 35 / 100 && mc2 > 12) cll = 1;
                            if (z3 == 0 && z4 == 0 && cmb > 1) rrs = 1;
                            if (z3 == 1 && cmb > 3) cll = 1;
                            if (z3 == 1 && cmb > 3) rrs = 1;
                            if (z3 == 3 && cmb > 6) rrs = 1;
                            if (z3 == 0 && z4 == 0 && cmb > 0 &&  (sumcll / pot) < 0.35) cll = 1;
                            if (z3 == 1 && z4 == 0 && cmb > 0 && (sumcll / pot) < 0.35) cll = 1;
                        }
                    }
                }
            } else {
                if (mac == 3) {
                    if (maxs != cm1 && maxs != cm2 && cmb > 3) rrs = 1;
                    if (maxs == cm1 && mc1 > 11 && (sumcll / pot) < (25 / 100)) cll = 1;
                    if (maxs == cm1 && mc1 > 11 && cmb > 0 && (sumcll / pot) < (40 / 100)
                            && sumcll <= 2) cll = 1;
                    if (maxs == cm2 && mc2 > 11 && (sumcll / pot) < (25 / 100)) cll = 1;
                    if (maxs == cm2 && mc1 > 11 && cmb > 0 && (sumcll / pot) < (40 / 100)
                            && sumcll <= 2) cll = 1;
                    if (cmb > 6) rrs = 1;
                } else {
                    if (mac == 2 && cm1 == cm2 && cm1 == maxs && mc1 > mbrd
                            && mc2 > mbrd && (sumcll / pot) < 0.4) cll = 1;
                    if (mac == 2 && cm1 == cm2 && cm1 == maxs && (mc1 > 12
                            || mc2 > 12) && sumcll / pot < 0.35) cll = 1;
                    //if (mac == 2 && cm1 == cm2 && cm1 == maxs && ddm <= 1
                    //  && sumcll / pot <= 4 / 10) cll = 1;
                    //if (ddm == 0 && (sumcll / pot) < (30 / 100)) cll = 1;
                    if (z3 == 0 && z4 == 0 && cmb == 1 && mc1 == mbrd
                            && mc2 > 11 && (sumcll / pot) < 0.3) cll = 1;
                    if (z3 == 0 && z4 == 0 && cmb == 1 && mc2 == mbrd
                            && mc1 > 11 && (sumcll / pot) < 0.3) cll = 1;
                    if (z3 == 0 && z4 == 0 && cmb > 0 && (sumcll / pot) < 0.35) cll = 1;
                    if (z3 == 0 && z4 == 0 && cmb > 2) rrs = 1;
                    if (z3 == 1 && cmb > 0 && cmb < 4 && (sumcll / pot) < (35 / 100)) cll = 1;
                    if (z3 == 1 && cmb > 3) rrs = 1;
                    if (z3 == 3 && cmb > 7) rrs = 1;
                    if (mac <= 2 && mc1 + mc2 >= 26 && mbrd <= 10 && z3 == 0 && ops + opsa == 1
                            && sumcll / pot < 0.35) cll = 1;
                }
            }
        }

        if (rnd == 4) {
            if (firstMove(r))                //===============ïåðâûé õîä
            {
                if (ops == 0 && opsa == 1) {
                    if (mac > 3) {
                        if (cmb == 7 && cm1 == maxs && mc1 >= 12) rs = 1;
                        if (cmb == 7 && cm2 == maxs && mc2 >= 12) rs = 1;
                        if (cmb > 7) rs = 1;
                    }
                    if (mac == 3) {
                        //if (mp == 1 && rmv[1] == 3 && rmv[1] == 3 && ops2[1] == 1) rs=1;
                        if (maxs == cm1 && mc1 > 11 && (sumcll / pot) <= (30 / 100)) cll = 1;
                        if (maxs == cm2 && mc2 > 11 && (sumcll / pot) <= (30 / 100)) cll = 1;
                        if (z3 == 0 && z4 == 0 && cmb > 0) rs = 1;
                        if (z3 == 1 && z4 == 0 && cmb > 0) rs = 1;
                        if (z3 == 1 && z4 == 1 && cmb > 3) rs = 1;
                        if (z3 == 3 && cmb > 6) rs = 1;
                        if (z4 == 3 && cmb > 6) rs = 1;
                    }
                    if (mac < 3) {
                        //if (mp == 1 && rmv[0] == 3 && rmv[1] == 3 && ops2[1] == 1) rs = 1;
                        if (chk && bc1 < 10 && bc2 < 10 && bc3 < 10
                                && bc4 > 10 && rmv[1] > 2) rs = 1;
                        if (z3 == 0 && z4 == 0 && cmb > 0) rs = 1;
                        if (z3 == 1 && z4 == 0 && cmb > 0) rs = 1;
                        if (z3 == 1 && z4 == 1 && cmb > 3) rs = 1;
                        if (z3 == 3 && cmb > 5) rs = 1;
                        if (z4 == 3 && cmb > 5) rs = 1;
                    }
                }
                if (ops == 1 && opsa == 0) {
                    if (chk) {
                        if (mac > 3 && cmb > 2) rs = 1;
                        if (mac == 3) {
                            if (z3 == 0 && z4 == 0 && bc4 != mbrd && pot > 20) rs = 1;
                            if (bc1 < 10 && bc2 < 10 && bc3 < 10 && bc4 > 10 && rmv[1] == 1) rs = 1;
                            if (z3 == 0 && z4 == 0 && cmb > 0) rs = 1;
                            if (z3 == 1 && z4 == 0 && cmb > 0) rs = 1;
                            if (z3 == 1 && z4 == 1 && cmb > 3) rs = 1;
                            if (z3 == 3 && cmb > 6) rs = 1;
                            if (z4 == 3 && cmb > 6) rs = 1;
                            if (rmv[1] == 1 || rmv[1] == 3) rs = 1;
                        }
                        if (mac < 3) {
                            if (bc1 < 10 && bc2 < 10 && bc3 < 10 && bc4 > 10 && rmv[1] < 3 && pot < 60) rs = 1;
                            if (z3 == 0 && z4 == 0 && cmb > 0) rs = 1;
                            if (z3 == 1 && z4 == 0 && cmb > 0) rs = 1;
                            if (z3 == 1 && z4 == 1 && cmb > 3) rs = 1;
                            if (z3 == 3 && cmb > 5) rs = 1;
                            if (z4 == 3 && cmb > 5) rs = 1;
                            if (rmv[1] == 1 || rmv[1] == 3 && pot <= 70) rs = 1;
                        }
                    } else {
                        if (mac > 3) {
                            if (cmb == 7 && cm1 == maxs && mc1 >= 12) rrs = 1;
                            if (cmb == 7 && cm2 == maxs && mc2 >= 12) rrs = 1;
                            if (cmb > 7) rrs = 1;
                        }
                        if (mac == 3) {
                            if (z3 == 0 && z4 == 0 && cmb > 0 && (sumcll / pot) < 0.45) cll = 1;
                            if (z3 == 0 && z4 == 0 && cmb > 2) rrs = 1;
                            if (z3 == 1 && z4 == 0 && cmb > 3) rrs = 1;
                            if (z3 == 1 && z4 == 1 && cmb > 7) rrs = 1;
                            if (z3 == 3 && cmb > 6) rrs = 1;
                            if (z4 == 3 && cmb > 6) rrs = 1;
                            if (maxs == cm1 && mc1 > 11 && (sumcll / pot) < (45 / 100)) cll = 1;
                            if (maxs == cm2 && mc2 > 11 && (sumcll / pot) < (45 / 100)) cll = 1;
                            if (maxs == cm1 && mc1 > 11 && cmb > 1) rrs = 1;
                            if (cmb > 3 && (sumcll / pot) < (4 / 10)) cll = 1;
                        }
                        if (mac < 3) {
                            if (mac == 2 && z4 == 0 && z3 <= 1 && maxs == cm1 && maxs == cm2
                                    && sumcll / pot <= 25 / 100 && mc1 > 12) cll = 1;
                            if (mac == 2 && z4 == 0 && z3 <= 1 && maxs == cm1 && maxs == cm2
                                    && sumcll / pot <= 25 / 100 && mc2 > 12) cll = 1;
                            if (z3 == 0 && z4 == 0 && cmb == 1 && mc1 == mbrd
                                    && mc2 > 11) rrs = 1;
                            if (z3 == 0 && z4 == 0 && cmb == 1 && mc1 == mbrd
                                    && mc2 > 11 && sumcll / pot <= 3 / 10) cll = 1;
                            if (z3 == 0 && z4 == 0 && cmb == 1 && mc2 == mbrd
                                    && mc1 > 11) rrs = 1;
                            if (z3 == 0 && z4 == 0 && cmb == 1 && mc2 == mbrd
                                    && mc1 > 11 && sumcll / pot <= 3 / 10) cll = 1;
                            if (z3 == 0 && z4 == 0 && cmb == 2 && mc1 + mc2 >= 24) rrs = 1;
                            if (z3 == 0 && z4 == 0 && cmb > 2) rrs = 1;
                            if (z3 == 1 && cmb > 0 && sumcll / pot < 0.36) cll = 1;
                            if (z3 == 1 && cmb >= 4) rrs = 1;
                            if (z3 == 3 && cmb > 7) rrs = 1;
                            if (z3 == 0 && z4 == 0 && mc1 == mc2 && mc2 >= 12 && mc2 < mbrd
                                    && sumcll / pot < (20 / 100)) cll = 1;
                        }
                    }
                }
                if (ops + opsa > 1) {
                    if (chk) {
                        if (mac > 3 && cmb > 3) rs = 1;
                        if (mac <= 3) {
                            if (z3 == 0 && z4 == 0 && cmb > 0) rs = 1;
                            if (z3 == 1 && z4 == 0 && cmb > 0 && rmv[1] < 3) rs = 1;
                            if (z3 == 1 && z4 == 0 && cmb > 3) rs = 1;
                            if (z3 == 1 && z4 == 1 && cmb > 7) rs = 1;
                            if (z3 == 3 && cmb > 7) rs = 1;
                            if (z4 == 3 && cmb > 7) rs = 1;
                        }
                    } else {
                        if (mac > 3) {
                            if (cmb == 7 && cm1 == maxs && mc1 >= 12) rs = 1;
                            if (cmb == 7 && cm2 == maxs && mc2 >= 12) rs = 1;
                            if (cmb > 7) rs = 1;

                        }
                        if (mac == 3) {
                            if (maxs == cm1 && mc1 > 11 && z3 == 0 && z4 == 0 && cmb > 0) cll = 1;
                            if (maxs == cm2 && mc2 > 11 && z3 == 0 && z4 == 0 && cmb > 0) cll = 1;
                            if (z3 == 0 && z4 == 0 && cmb > 4) rrs = 1;
                            if (maxs == cm1 && mc1 > 11 && z3 == 1 && z4 == 0 && cmb >= 2) cll = 1;
                            if (maxs == cm2 && mc2 > 11 && z3 == 1 && z4 == 0 && cmb >= 2) cll = 1;
                            if (z3 == 1 && z4 == 0 && cmb > 5) rrs = 1;
                            if (z3 == 1 && z4 == 1 && cmb > 7) rrs = 1;
                            if (z3 == 3 && cmb > 7) rrs = 1;
                            if (z4 == 3 && cmb > 7) rrs = 1;
                        }
                        if (mac < 3) {
                            if (mac == 2 && z4 == 0 && z3 <= 1 && maxs == cm1 && maxs == cm2
                                    && sumcll / pot <= 25 / 100 && mc1 > 12) cll = 1;
                            if (mac == 2 && z4 == 0 && z3 <= 1 && maxs == cm1 && maxs == cm2
                                    && sumcll / pot <= 25 / 100 && mc2 > 12) cll = 1;
                            if (z3 == 0 && z4 == 0 && cmb > 2) cll = 1;
                            if (z3 == 0 && z4 == 0 && cmb > 4) rrs = 1;
                            if (z3 == 1 && z4 == 0 && cmb > 0 && sumcll / pot <= 4 / 10) cll = 1;
                            if (z3 == 1 && z4 == 0 && cmb > 5) rrs = 1;
                            if (z3 == 1 && z4 == 1 && cmb > 7) rrs = 1;
                            if (z3 == 3 && cmb > 7) rrs = 1;
                            if (z4 == 3 && cmb > 7) rrs = 1;
                            if (z3 == 0 && z4 == 0 && cmb > 1 && (sumcll / pot) < (20 / 100)) cll = 1;
                        }
                    }
                }
            } else {
                if (mac > 3) {
                    if (cmb == 7 && cm1 == maxs && mc1 >= 12) rrs = 1;
                    if (cmb == 7 && cm2 == maxs && mc2 >= 12) rrs = 1;
                    if (cmb > 7) rrs = 1;
                }
                if (mac == 3) {
                    if (maxs == cm1 && mc1 > 11 && z3 == 0 && z4 == 0
                            && sumcll / pot <= (35 / 100)) cll = 1;
                    if (maxs == cm2 && mc2 > 11 && z3 == 0 && z4 == 0
                            && (sumcll / pot) <= (35 / 100)) cll = 1;
                    if (maxs == cm1 && mc1 > 11 && z3 == 0 && z4 == 0
                            && sumcll / pot <= (45 / 100) && cmb > 0) cll = 1;
                    if (maxs == cm2 && mc2 > 11 && z3 == 0 && z4 == 0
                            && (sumcll / pot) <= (45 / 100) && cmb > 0) cll = 1;
                    if (maxs == cm1 && mc1 > 11 && z3 == 1 && z4 == 0 && (sumcll / pot) <= (30 / 100)) cll = 1;
                    if (maxs == cm2 && mc2 > 11 && z3 == 1 && z4 == 0 && (sumcll / pot) <= (30 / 100)) cll = 1;
                    if (z3 == 0 && z4 == 0 && cmb > 0 && (sumcll / pot) <= (25 / 100) && sumcll <= 30) cll = 1;
                    if (z3 == 0 && z4 == 0 && cmb > 2 && (sumcll / pot) <= (30 / 100)) cll = 1;
                    if (z3 == 0 && z4 == 0 && cmb > 4) rrs = 1;
                    if (z3 == 1 && z4 == 0 && cmb > 0 && (sumcll / pot) <= (25 / 100) && sumcll <= 30) cll = 1;
                    if (z3 == 1 && z4 == 0 && cmb > 3 && (sumcll / pot) <= (30 / 100)) cll = 1;
                    if (z3 == 1 && z4 == 1 && cmb > 7) rrs = 1;
                    if (z3 == 3 && cmb > 7) rrs = 1;
                    if (z4 == 3 && cmb > 7) rrs = 1;
                }
                if (mac < 3) {
                    if (mac == 2 && cm1 == cm2 && cm1 == maxs && mc1 > mbrd
                            && mc2 > mbrd && (sumcll / pot) < (40 / 100)) cll = 1;
                    if (mac == 2 && cm1 == cm2 && cm1 == maxs && (mc1 > 12)
                            || mc2 > 12 && sumcll / pot < (35 / 100)) cll = 1;
                    //if (mac == 2 && cm1 == cm2 && cm1 == maxs && ddm <= 1 && sumcll / pot <= 4 / 10) cll = 1;
                    // if ((ddm = 0 && sumcll / pot) < (30 / 100)) cll = 1;
                    if (z3 == 0 && z4 == 0 && cmb == 1 && mc1 == mbrd && mc2 > 11 && (sumcll / pot) < 0.35) cll = 1;
                    if (z3 == 0 && z4 == 0 && cmb == 1 && mc2 == mbrd && mc1 > 11 && (sumcll / pot) < 0.35) cll = 1;
                    if (z3 == 0 && z4 == 0 && cmb > 1) rrs = 1;
                    if (z3 == 0 && z4 == 0 && cmb > 1 && (sumcll / pot) < 0.3) rrs = 1;
                    if (z3 == 0 && z4 == 0 && cmb > 3) rrs = 1;
                    if (z3 == 1 && z4 == 0 && cmb > 1 && cmb < 4 && (sumcll / pot) < (30 / 100)) cll = 1;
                    if (z3 == 1 && cmb > 3) rrs = 1;
                    if (z3 == 3 && cmb > 7) rrs = 1;
                    if (mac <= 2 && mc1 + mc2 >= 26 && mbrd <= 10 && z3 == 0
                            && ops + opsa == 1 && (sumcll / pot) < (35 / 100)) cll = 1;
                }
            }
        }

        if (rnd == 5) {

            if (firstMove(r))                //===============ïåðâûé õîä
            {
                if (ops == 0 && opsa == 1) {
                    if (mac > 3) {
                        if (cmb == 7 && cm1 == maxs && mc1 >= 12) rs = 1;
                        if (cmb == 7 && cm2 == maxs && mc2 >= 12) rs = 1;
                        if (cmb > 7) rs = 1;
                    }
                    if (mac == 3) {
                        if (z3 == 0 && z4 == 0 && cmb > 0) rs = 1;
                        if (z3 == 1 && z4 == 0 && cmb > 0) rs = 1;
                        if (z3 == 1 && z4 == 1 && cmb > 3) rs = 1;
                        if (z3 == 3 && cmb > 6) rs = 1;
                        if (z4 == 3 && cmb > 6) rs = 1;
                        if (bc5 == mc1 && z3 <= 1 && z4 == 0
                                && mc1 >= 10 && rmv[2] == 1 && rmv[1] == 1) rs = 1;
                        if (bc5 == mc2 && z3 <= 1 && z4 == 0 && cmb == 0
                                && mc2 >= 10 && rmv[2] == 1 && rmv[1] == 1) rs = 1;
                    }
                    if (mac < 3) {
                        if (z3 == 0 && z4 == 0 && cmb > 0) rs = 1;
                        if (z3 == 1 && z4 == 0 && cmb > 0) rs = 1;
                        if (z3 == 1 && z4 == 1 && cmb > 3) rs = 1;
                        if (z3 == 3 && cmb > 5) rs = 1;
                        if (z4 == 3 && cmb > 5) rs = 1;
                        if (bc5 == mc1 && z3 <= 1 && z4 == 0
                                && mc1 >= 10 && rmv[2] == 1 && rmv[1] == 1) rs = 1;
                        if (bc5 == mc2 && z3 <= 1 && z4 == 0 && cmb == 0
                                && mc2 >= 10 && rmv[2] == 1 && rmv[1] == 1) rs = 1;
                    }
                }
                if (ops == 1 && opsa == 0) {
                    if (chk) {
                        if (mac > 3 && cmb > 2) rs = 1;
                        if (mac == 3) {
                            if (z3 <= 1 && z4 == 0 && cmb == 1 && rmv[1] < 3 &&
                                    rmv[2] < 3) rs = 1;
                            //  if (z3 <= 1 && z4 == 0 && cmb > 1 && rmv[2] < 2 && ddm > 1) rs = 1;
                            // if (z3 <= 1 && z4 == 0 && cmb > 2 && ddm > 1) rs = 1;
                            if (z3 <= 1 && z4 == 0 && cmb > 4) rs = 1;
                            if (z3 == 1 && z4 == 1 && cmb > 3) rs = 1;
                            if (z3 == 3 && cmb > 6) rs = 1;
                            if (z4 == 3 && cmb > 6) rs = 1;
                            if (bc5 == mc1 && z3 <= 1 && z4 == 0
                                    && mc1 >= 10 && rmv[2] == 1 && rmv[1] == 1) rs = 1;
                            if (bc5 == mc2 && z3 <= 1 && z4 == 0
                                    && mc2 >= 10 && rmv[2] == 1 && rmv[1] == 1) rs = 1;
                        }
                        if (mac < 3) {
                            if (z3 <= 1 && z4 == 0 && cmb == 1 && rmv[1] < 3 &&
                                    rmv[2] < 3) rs = 1;
                            // if (z3 <= 1 && z4 == 0 && cmb > 1 && rmv[2] < 2 && ddm > 1) rs = 1;
                            //if (z3 <= 1 && z4 == 0 && cmb > 2 && ddm > 1) rs = 1;
                            if (z3 <= 1 && z4 == 0 && cmb > 4) rs = 1;
                            if (z3 == 1 && z4 == 1 && cmb > 3) rs = 1;
                            if (z3 == 3 && cmb > 5) rs = 1;
                            if (z4 == 3 && cmb > 5) rs = 1;
                            if (bc5 == mc1 && z3 <= 1 && z4 == 0
                                    && mc1 >= 10 && rmv[2] == 1 && rmv[1] == 1) rs = 1;
                            if (bc5 == mc2 && z3 <= 1 && z4 == 0
                                    && mc2 >= 10 && rmv[2] == 1 && rmv[1] == 1) rs = 1;
                        }
                    } else {
                        if (mac > 3) {
                            if (cmb == 7 && cm1 == maxs && mc1 >= 12) rrs = 1;
                            if (cmb == 7 && cm2 == maxs && mc2 >= 12) rrs = 1;
                            if (cmb > 7) rrs = 1;
                        }
                        if (mac == 3) {
                            //if (pot > 0.3 && sumcll <= 0.04 && (z1 < > 0) or(z2 < > 0)
                            //or(mc1 = mc2))cll = 1;
                            if (z3 <= 1 && z4 == 0 && rmv[1] > 2 && rmv[2] > 2
                                    && sumcll / pot < 0.25) cll = 1;
                            if (z3 == 0 && z4 == 0 && cmb > 0 && sumcll / pot < 4 / 10) cll = 1;
                            if (z3 == 0 && z4 == 0 && cmb > 2) rrs = 1;
                            if (z3 == 1 && z4 == 0 && cmb >= 1 && sumcll / pot < 4 / 10) cll = 1;
                            if (z3 == 1 && z4 == 0 && cmb > 3) rrs = 1;
                            if (z3 == 1 && z4 == 1 && cmb > 7) rrs = 1;
                            if (z3 == 3 && cmb > 6) rrs = 1;
                            if (z4 == 3 && cmb > 6) rrs = 1;
                        }
                        if (mac < 3) {
                            //if (pot > 0.3 && sumcll <= 0.04 && (z1 < > 0) or(z2 < > 0)
                            //or(mc1 = mc2))cll = 1;
                            if (z3 <= 1 && z4 == 0 && rmv[1] > 2 && rmv[2] > 2
                                    && sumcll / pot < 0.25) cll = 1;
                            if (z3 == 0 && z4 == 0 && cmb > 0 && sumcll / pot < 0.4) cll = 1;
                            if (z3 == 0 && z4 == 0 && cmb > 2) rrs = 1;
                            if (z3 == 1 && z4 == 0 && cmb >= 1 && sumcll / pot < 0.35 && rmv[2] > 1) cll = 1;
                            if (z3 == 1 && z4 == 0 && cmb >= 1 && sumcll / pot < 0.5 && rmv[2] == 1) cll = 1;
                            if (z3 == 1 && z4 == 0 && cmb > 3) rrs = 1;
                            if (z3 == 1 && z4 == 1 && cmb > 7) rrs = 1;
                            if (z3 == 3 && cmb > 6) rrs = 1;
                            if (z4 == 3 && cmb > 6) rrs = 1;
                        }
                    }
                }
                if (ops + opsa > 1) {
                    if (chk) {
                        if (mac > 3 && cmb > 3) rs = 1;
                        if (mac <= 3) {
                            if (z3 == 0 && z4 == 0 && cmb > 0) rs = 1;
                            if (z3 == 1 && z4 == 0 && cmb > 0) rs = 1;
                            if (z3 == 1 && z4 == 1 && cmb > 7) rs = 1;
                            if (z3 == 3 && cmb > 7) rs = 1;
                            if (z4 == 3 && cmb > 7) rs = 1;
                        }
                    } else {
                        if (mac > 3) {
                            if (cmb == 7 && cm1 == maxs && mc1 >= 12) rrs = 1;
                            if (cmb == 7 && cm2 == maxs && mc2 >= 12) rrs = 1;
                            if (cmb > 7) rrs = 1;
                        }
                        if (mac == 3) {
                            if (z3 == 0 && z4 == 0 && cmb > 0 && sumcll / pot < 4 / 10) cll = 1;
                            if (z3 == 0 && z4 == 0 && cmb > 2) rrs = 1;
                            if (z3 == 1 && z4 == 0 && cmb >= 1 && sumcll / pot < 4 / 10) cll = 1;
                            if (z3 == 1 && z4 == 0 && cmb > 3) rrs = 1;
                            if (z3 == 1 && z4 == 1 && cmb > 7) rrs = 1;
                            if (z3 == 3 && cmb > 6) rrs = 1;
                            if (z4 == 3 && cmb > 6) rrs = 1;
                        }
                        if (mac < 3) {
                            if (z3 == 0 && z4 == 0 && cmb > 0 && sumcll / pot < 5 / 10) cll = 1;
                            if (z3 == 0 && z4 == 0 && cmb > 2) rrs = 1;
                            if (z3 == 1 && z4 == 0 && cmb >= 1 && sumcll / pot < 5 / 10) cll = 1;
                            if (z3 == 1 && z4 == 0 && cmb > 3) rrs = 1;

                            if (z3 == 1 && z4 == 1 && cmb > 7) rrs = 1;
                            if (z3 == 3 && cmb > 6) rrs = 1;
                            if (z4 == 3 && cmb > 6) rrs = 1;
                        }
                    }
                }
            } else {
                if (mac > 3) {
                    if (cmb == 7 && cm1 == maxs && mc1 >= 12) rrs = 1;
                    if (cmb == 7 && cm2 == maxs && mc2 >= 12) rrs = 1;
                    if (cmb > 7) rrs = 1;
                }
                if (mac <= 3) {
                    if (z3 == 0 && z4 == 0 && cmb > 0 && sumcll / pot < 4 / 10 && sumcll <= 50) cll = 1;
                    if (z3 == 0 && z4 == 0 && cmb > 2 && sumcll / pot < 4 / 10) cll = 1;
                    if (z3 == 0 && z4 == 0 && cmb > 4 && sumcll / pot < 5 / 10) cll = 1;
                    if (z3 == 0 && z4 == 0 && cmb > 5) rrs = 1;
                    if (z3 == 1 && z4 == 0 && cmb > 3 && sumcll / pot < 5 / 10) cll = 1;
                    if (z3 == 1 && z4 == 1 && cmb > 7) rrs = 1;
                    if (z3 == 3 && cmb > 7) rrs = 1;
                    if (z4 == 3 && cmb > 7) rrs = 1;
                    if (opsa == 0 && ops == 1 && bc5 == mc1 && z3 <= 1
                            && z4 == 0 && mc1 >= 10 && sumcll / pot <= 35 / 100) cll = 1;
                    if (opsa == 0 && ops == 1 && bc5 == mc2 && z3 <= 1
                            && z4 == 0 && mc2 >= 10 && sumcll / pot <= 35 / 100) cll = 1;
                }
            }
        }

        if (rrs > 0) {
            if (rnd == 1) rmv[0] = 4;
            else rmv[rnd - 2] = 4;
            return 4;
        } else if (rs1 > 0 || rs > 0) {
            if (rnd == 1) rmv[0] = 3;
            else rmv[rnd - 2] = 3;
            return 3;
        } else if (cll > 0) {
            if (rnd == 1) rmv[0] = 2;
            else rmv[rnd - 2] = 2;
            return 2;
        } else {
            if (rnd == 1) rmv[0] = 1;
            else rmv[rnd - 2] = 1;
            return 1;
        }
    }

    private int getMaxBoard() {
        int max = 0;
        for (Card card : boardCards) {
            if (card.getValue() > max) max = card.getValue();
        }
        return max;
    }

    public void ActiveTable() {
        int x, y;
        if (this.x > 600) {
            x = this.x + 15 + (int) (Math.random() * 65);
            y = this.y + 250 + (int) (Math.random() * 40);
        } else {
            x = this.x + 560 + (int) (Math.random() * 60);
            y = this.y + 250 + (int) (Math.random() * 100);
        }
        rb.mouseMove(x, y);
        rb.delay((int) (Math.random() * 300 + 200));
    }

    public boolean isTableActive(BufferedImage r) {
        if ((r.getRGB(x + 180, y + 31) & 0x0000ff00) >> 8 > 100) return true;
        else return false;
    }

    private void Fold() {
        rb.keyPress('D');
        rb.delay((int) (Math.random() * 300) + 200);
        rb.keyRelease('D');
    }

    private void Check() {
        rb.keyPress('W');
        rb.delay((int) (Math.random() * 300) + 200);
        rb.keyRelease('W');
    }

    private void Call() {
        rb.keyPress('S');
        rb.delay((int) (Math.random() * 300) + 200);
        rb.keyRelease('S');
    }

    private void Raise() {
        rb.keyPress('E');
        rb.delay((int) (Math.random() * 300) + 200);
        rb.keyRelease('E');
    }

    private void Reraise() {
        rb.keyPress('A');
        rb.delay((int) (Math.random() * 300) + 200);
        rb.keyRelease('A');
    }

    private void Allin() {
        rb.keyPress('Q');
        rb.delay((int) (Math.random() * 300) + 200);
        rb.keyRelease('Q');
    }

    public void move(int num) {
        if (num>0&&!isTableActive(r)) ActiveTable();
        int ms = getStack(r),
                pot = getPot(r),
                pos = readPos(r),
                rnd = getRnd(r);
        if (num > 0) {
            lastpot = pot;
            lastPos = pos;
            lastCards = myCards;
            lastrnd = rnd;
        }
        if (num == 10) Fold();
        else if (num == 1) {
            if (checkCheck(r)) Check();
            else Fold();
        } else if (num == 2) Call();
        else if (num == 3 && pot*0.6 >= ms / 2) Allin();
        else if (num == 3 && rnd == 1 && pos != 8) Reraise();
        else if (num == 3 && rnd == 1) Raise1();
        else if (num == 3 && rnd > 1) Raise();
        else if (num == 4 && pot*0.9 >= ms / 2) Allin();
        else if (num == 4) Raise();
    }

    private void Raise1() {
        rb.keyPress('R');
        rb.delay((int) (Math.random() * 300) + 200);
        rb.keyRelease('R');
    }

    public boolean canBack(BufferedImage r) {
        if (((r.getRGB(x + 395, y + 425) & 0x00ff0000) >> 16) < 100 &&
                ((r.getRGB(x + 564, y + 413) & 0x00ff0000) >> 16) > 150) return true;
        else return false;

    }

    public void Open() {
        int x= (int) (Math.random() * 200) + 700;
        int y = getYOpen()-(int) (Math.random() * 20);
        rb.mouseMove(x,y);
        rb.mousePress(InputEvent.BUTTON1_MASK);
        rb.delay((int) (Math.random() * 100 + 200));
        rb.mouseRelease(InputEvent.BUTTON1_MASK);
        rb.delay((int) (Math.random() * 200 + 100));
        rb.mousePress(InputEvent.BUTTON1_MASK);
        rb.delay((int) (Math.random() * 100 + 200));
        rb.mouseRelease(InputEvent.BUTTON1_MASK);
        rb.delay((int) (Math.random() * 500 + 1000));
    }

    private int getYOpen() {
        int y = 0, x = 765;
        for (int i = 820; i > 550; i--) {
            if (((r.getRGB(x, i) & 0x00ff0000) >> 16) < 200) {
                y = i;
                break;
            }
        }
        System.out.println(" Y = "+y);
        return y;
    }

    public void pause() {
        int x, y;
        x = (int) (Math.random() * 4) + this.x + 16;
        y = (int) (Math.random() * 4) + this.y + 361;
        rb.mouseMove(x, y);
        rb.mousePress(InputEvent.BUTTON1_MASK);
        rb.delay((int) (Math.random() * 300 + 200));
        rb.mouseRelease(InputEvent.BUTTON1_MASK);
        int i = (int) (Math.random() * 4);
        if (i == 0) {
            x = this.x + 45 + (int) (Math.random() * 105);
            y = this.y + 55 + (int) (Math.random() * 45);
        } else if (i == 1) {
            x = this.x + 15 + (int) (Math.random() * 65);
            y = this.y + 250 + (int) (Math.random() * 40);
        } else if (i == 2) {
            x = this.x + 480 + (int) (Math.random() * 140);
            y = this.y + 55 + (int) (Math.random() * 25);
        } else {
            x = this.x + 560 + (int) (Math.random() * 60);
            y = this.y + 250 + (int) (Math.random() * 100);
        }
        rb.mouseMove(x, y);
    }

    public void close() {
        int x, y;
        x = (int) (Math.random() * 35) + this.x + 590;
        y = (int) (Math.random() * 5) + this.y + 10;
        rb.mouseMove(x, y);
        rb.mousePress(InputEvent.BUTTON1_MASK);
        rb.delay((int) (Math.random() * 300 + 200));
        rb.mouseRelease(InputEvent.BUTTON1_MASK);
        int i = (int) (Math.random() * 4);
        if (i == 0) {
            x = this.x + 45 + (int) (Math.random() * 105);
            y = this.y + 55 + (int) (Math.random() * 45);
        } else if (i == 1) {
            x = this.x + 15 + (int) (Math.random() * 65);
            y = this.y + 250 + (int) (Math.random() * 40);
        } else if (i == 2) {
            x = this.x + 480 + (int) (Math.random() * 140);
            y = this.y + 55 + (int) (Math.random() * 25);
        } else {
            x = this.x + 560 + (int) (Math.random() * 60);
            y = this.y + 250 + (int) (Math.random() * 100);
        }
        rb.mouseMove(x, y);
    }

    public void resume() {
        int x, y;
        x = (int) (Math.random() * 122) + this.x + 488;
        y = (int) (Math.random() * 19) + this.y + 404;
        rb.mouseMove(x, y);
        rb.mousePress(InputEvent.BUTTON1_MASK);
        rb.delay((int) (Math.random() * 300 + 200));
        rb.mouseRelease(InputEvent.BUTTON1_MASK);
        int i = (int) (Math.random() * 4);
        if (i == 0) {
            x = this.x + 45 + (int) (Math.random() * 105);
            y = this.y + 55 + (int) (Math.random() * 45);
        } else if (i == 1) {
            x = this.x + 15 + (int) (Math.random() * 65);
            y = this.y + 250 + (int) (Math.random() * 40);
        } else if (i == 2) {
            x = this.x + 480 + (int) (Math.random() * 140);
            y = this.y + 55 + (int) (Math.random() * 25);
        } else {
            x = this.x + 560 + (int) (Math.random() * 60);
            y = this.y + 250 + (int) (Math.random() * 100);
        }
        rb.mouseMove(x, y);
    }
}
