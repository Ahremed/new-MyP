

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Vladyslav on 26.12.2015.
 */
public class MyP extends JFrame {
    private JTextField textField, textField2, textField3, textField4, textField5, textField6;
    private static AtomicBoolean paused = new AtomicBoolean(true);
    private JCheckBox CPause, CStop;
    private play pl = new play();
    private Robot r = null;
    private BufferedImage image;
    private Rectangle rect = new Rectangle(0, 0, 1280, 1024);

    public MyP() {
        super("MyP");
        createGUI();
    }

    public void createGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        JButton button1 = new JButton("Start");
        JButton button2 = new JButton("Stop");
        panel.add(button1);
        panel.add(button2);
        textField = new JTextField();
        panel.add(textField);
        textField.setColumns(20);
        textField2 = new JTextField();
        panel.add(textField2);
        textField2.setColumns(20);
        textField3 = new JTextField();
        panel.add(textField3);
        textField3.setColumns(20);
        textField4 = new JTextField();
        panel.add(textField4);
        textField4.setColumns(20);
        textField5 = new JTextField();
        panel.add(textField5);
        textField5.setColumns(20);
        textField6 = new JTextField();
        panel.add(textField6);
        textField6.setColumns(20);
        CPause = new JCheckBox("Pause");
        panel.add(CPause);
        CStop = new JCheckBox("Stop");
        panel.add(CStop);
        ActionListener actionListener = new TestActionListener();
        ActionListener actionListener2 = new TestActionListener2();
        button1.addActionListener(actionListener);
        button2.addActionListener(actionListener2);
        getContentPane().add(panel);
        setPreferredSize(new Dimension(250, 250));
    }

    public class TestActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            synchronized (this) {
                try {pl.start();
                paused.set(false);}
                catch (Exception a){}
            }
        }
    }

    public class TestActionListener2 implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (paused.get()) {
                try {paused.set(false);
                pl.suspend();}
                catch (Exception a){}
            } else {
                try{paused.set(true);
                pl.resume();}
                catch (Exception a){}
            }
            textField2.setText(String.valueOf(paused));
        }
    }

    class play extends Thread  {
        @Override
        public void run()   {
            Table table1 = new Table(0, 0);
            Date dt, dt2;
            long speed;
            String st;

            while (true)  {
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
                        if (table1.checkFold(image)) {
                            sleep(750);
                            image = r.createScreenCapture(rect);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    textField2.setText("Ops= " + table1.getOps(image) + " Opps = " + table1.getOpps(image) + "Stack="
                            + table1.getStack(image));
                    textField3.setText("Pot= " + table1.getPot(image) + " Call = " + table1.getCall(image) + " Rs = "
                            + table1.getRaise(image));
                    table1.getMyCards(image);
                    textField4.setText(String.valueOf(table1.myCards.get(0).getValue()) + table1.myCards.get(0).getSuit() +
                            +table1.myCards.get(1).getValue() + table1.myCards.get(1).getSuit()
                            + " Back = " + table1.canBack(image));
                    textField5.setText("");
                    textField.setText("Rnd = " + table1.getRnd(image) + " First = "
                            + table1.firstMove(image));
                    if (table1.checkFold(image)) {
                        textField5.setText(String.valueOf(table1.moveNum(image)));
                    }
                    st = " ";
                    for (Table.Card card : table1.boardCards) {
                        st += String.valueOf(card.getValue()) + card.getSuit() + " ";
                    }
                    if (table1.getRnd(image) > 1) textField6.setText("Comb = " + table1.getCombination() + st);
                    //if (!table1.isTableActive(image)&&table1.checkOpen(image)) table1.ActiveTable();
                    try {
                        sleep(500);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
       //             if (!CPause.isSelected()) table1.Open();
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
            }
        });
    }
}
