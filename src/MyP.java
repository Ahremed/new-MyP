

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Vladyslav on 26.12.2015.
 */
public class MyP extends JFrame {
    private JTextField textField, textField2, textField3, textField4, textField5, textField6,
            textField7, textField8, textField9, textField10, textField11, textField12;
    private static AtomicBoolean paused = new AtomicBoolean(true);
    private JCheckBox CPause, CStop;
    private play pl = new play();
    private Robot r = null;
    private BufferedImage image;
    private Rectangle rect = new Rectangle(0, 0, 1280, 1024);

    private MyP() {
        super("MyP");
        createGUI();
    }

    private void createGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        JButton button1 = new JButton("Start");
        JButton button2 = new JButton("Pause/Resume");
        button1.setPreferredSize(new Dimension(100, 40));
        button2.setPreferredSize(new Dimension(200, 40));
        panel.add(button1);
        panel.add(button2);
        Font font1 = new Font("SansSerif", Font.BOLD, 20);
        textField5 = new JTextField();
        textField5.setFont(font1);
        panel.add(textField5);
        textField7 = new JTextField();
        textField7.setFont(font1);
        panel.add(textField7);
        textField5.setBounds(20, 10, 100, 50);
        textField5.setColumns(5);
        textField7.setColumns(5);

        textField = new JTextField();
        panel.add(textField);
        textField.setColumns(15);
        textField8 = new JTextField();
        panel.add(textField8);
        textField8.setColumns(15);

        textField2 = new JTextField();
        panel.add(textField2);
        textField2.setColumns(15);
        textField9 = new JTextField();
        panel.add(textField9);
        textField9.setColumns(15);

        textField3 = new JTextField();
        panel.add(textField3);
        textField3.setColumns(15);
        textField10 = new JTextField();
        panel.add(textField10);
        textField10.setColumns(15);

        textField4 = new JTextField();
        panel.add(textField4);
        textField4.setColumns(15);
        textField11 = new JTextField();
        panel.add(textField11);
        textField11.setColumns(15);

        textField6 = new JTextField();
        panel.add(textField6);
        textField6.setColumns(15);
        textField12 = new JTextField();
        panel.add(textField12);
        textField12.setColumns(15);

        CPause = new JCheckBox("Pause");
        panel.add(CPause);
        CStop = new JCheckBox("Stop");
        panel.add(CStop);
        ActionListener actionListener = new TestActionListener();
        ActionListener actionListener2 = new TestActionListener2();
        button1.addActionListener(actionListener);
        button2.addActionListener(actionListener2);
        getContentPane().add(panel);
        setPreferredSize(new Dimension(360, 270));
    }

    private class TestActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            synchronized (this) {
                try {
                    pl.start();
                    paused.set(false);
                } catch (Exception a) {
                    a.printStackTrace();
                }
            }
        }
    }

    private class TestActionListener2 implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (!paused.get()) {
                try {
                    paused.set(true);
                    pl.suspend();
                } catch (Exception a) {
                    a.printStackTrace();
                }
            } else {
                try {
                    paused.set(false);
                    pl.resume();
                } catch (Exception a) {
                    a.printStackTrace();
                }
            }
            textField2.setText(String.valueOf(paused));
        }
    }

    class play extends Thread {
        @Override
        public void run() {
            Table table1 = new Table(0, 0);
            Table table2 = new Table(640, 0);
            String st;
            int num;

            while (true) {
                try {
                    r = new Robot();
                } catch (AWTException e1) {
                    e1.printStackTrace();
                }
                image = r.createScreenCapture(rect);

                /* color = image.getRGB(100, 100);
                 red = (color & 0x00ff0000) >> 16;
                 green = (color & 0x0000ff00) >> 8;
                 blue = color & 0x000000ff;*/
                if (table1.checkOpen(image)) {
                    try {
                        textField2.setText("Ops= " + table1.getOps(image) + " Opps = " + table1.getOpps(image) + "Stack="
                                + table1.getStack(image));
                        textField3.setText("Pot= " + table1.getPot(image) + " Call = " + table1.getCall(image) + " Rs = "
                                + table1.getRaise(image));
                        table1.getMyCards(image);
                        textField4.setText(String.valueOf(table1.myCards.get(0).getValue()) + table1.myCards.get(0).getSuit() +
                                +table1.myCards.get(1).getValue() + table1.myCards.get(1).getSuit() + " Pos = "
                                + table1.readPos(image));
                        textField.setText("Rnd = " + table1.getRnd(image) + " First = "
                                + table1.firstMove(image));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (!table1.checkPause(image) && table1.getStack(image) >= 230 && !table1.canBack(image))
                            table1.pause();
                        if (table1.getStack(image) >= 230 && table1.canBack(image)) table1.close();
                        if (table1.getStack(image) < 230 && table1.canBack(image) && !CPause.isSelected())
                            table1.resume();
                        if (table1.checkFold(image)) {
                            sleep(500);
                            image = r.createScreenCapture(rect);
                        }
                        if (table1.checkFold(image)) {
                            num = table1.moveNum(image);
                            textField5.setText(String.valueOf(num));
                            st = " ";
                            for (Table.Card card : table1.boardCards) {
                                st += String.valueOf(card.getValue()) + card.getSuit() + " ";
                            }
                            if (table1.getRnd(image) > 1) textField6.setText("Comb = " + table1.getCombination() + st);
                            sleep(2000);
                            table1.move(num);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    if (!CPause.isSelected()) table1.Open();
                }


                //=============================Second Table
                image = r.createScreenCapture(rect);
                if (table2.checkOpen(image)) {
                    try {
                        textField9.setText("Ops= " + table2.getOps(image) + " Opps = " + table2.getOpps(image) + "Stack="
                                + table2.getStack(image));
                        textField10.setText("Pot= " + table2.getPot(image) + " Call = " + table2.getCall(image) + " Rs = "
                                + table2.getRaise(image));
                        table2.getMyCards(image);
                        textField11.setText(String.valueOf(table2.myCards.get(0).getValue()) + table2.myCards.get(0).getSuit() +
                                +table2.myCards.get(1).getValue() + table2.myCards.get(1).getSuit() + " Pos = "
                                + table2.readPos(image));
                        textField8.setText("Rnd = " + table2.getRnd(image) + " First = "
                                + table2.firstMove(image));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (!table2.checkPause(image) && table2.getStack(image) >= 230 && !table2.canBack(image))
                            table2.pause();
                        if (table2.getStack(image) >= 230 && table2.canBack(image)) table2.close();
                        if (table2.getStack(image) < 230 && table2.canBack(image) && !CPause.isSelected())
                            table2.resume();
                        if (table2.checkFold(image)) {
                            sleep(500);
                            image = r.createScreenCapture(rect);
                        }
                        if (table2.checkFold(image)) {
                            num = table2.moveNum(image);
                            textField7.setText(String.valueOf(num));
                            st = " ";
                            for (Table.Card card : table2.boardCards) {
                                st += String.valueOf(card.getValue()) + card.getSuit() + " ";
                            }
                            if (table2.getRnd(image) > 1) textField12.setText("Comb = " + table2.getCombination() + st);
                            sleep(2000);
                            table2.move(num);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else {
                    if (!CPause.isSelected()) table2.Open();
                }

            }
        }
    }

    public static void main(String[] args) throws Exception {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame.setDefaultLookAndFeelDecorated(true);
                MyP frame = new MyP();
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                frame.setAlwaysOnTop(true);
                frame.setResizable(false);
            }
        });
    }
}
