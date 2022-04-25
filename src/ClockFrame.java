import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.time.LocalTime;

import static java.lang.Thread.sleep;

public class ClockFrame extends JFrame {
    static final int HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
    static final int WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;


    Color colorFirstLight=new Color(0x10,0x10,0x15);
    Color colorFirstDark=new Color(0xef,0xf0,0xc0);





    JPixelLabel[][] field;
    LocalTime lastTime;

    /**
     *
     * Segments:
     *
     *  --0--
     * |     |
     * 1     2
     * |     |
     *  --3--
     * |     |
     * 4     5
     * |     |
     *  --6--
     *
     * **/










    //________________________________INNER_CLASS_____________________________________


    class OptionFrame extends JFrame{

        public OptionFrame() throws HeadlessException, IllegalArgumentException{

        this.setTitle("CLOCK - Set Color");




        this.setLayout(new GridLayout(2,1));
        this.getContentPane().setPreferredSize(new Dimension(200,100));
        this.pack();








            this.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {

                }

                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode()==KeyEvent.VK_ESCAPE)dispose();
                }

                @Override
                public void keyReleased(KeyEvent e) {

                }
            });
            this.setVisible(true);
            this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        }
    }
    //________________________________INNER_CLASS_END_________________________________















    public ClockFrame() throws HeadlessException {
        this.setTitle("CLOCK");
        this.getContentPane().setPreferredSize(new Dimension(450,230));
        this.pack();


        //SETS ICONIMAGE
        URL iconURL = getClass().getResource("icon.png");
        if (iconURL != null) {
            ImageIcon icon = new ImageIcon(iconURL);
            this.setIconImage(icon.getImage());
        }

        this.setLayout(new GridLayout(23,45));
        field = new JPixelLabel[45][23];


        for (int y=0;y<23;y++){
            for (int x=0;x<45;x++){
                field[x][y]= new JPixelLabel(colorFirstLight,colorFirstDark);


            }
        }

        (field[22][9]).setToColor(1);
        (field[22][13]).setToColor(1);




        for (int y=0;y<23;y++){
            for (int x=0;x<45;x++){
                this.add(field[x][y]);
            }
        }
        setToTime(LocalTime.now());

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    setToTime(LocalTime.now());
                    try {
                        sleep(500);
                    } catch (Exception ignored) {
                    }
                }
            }
        }).start();











        /*
        *   -------------------------------------------LISTENERS--------------------------------------------
        *
        * */
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_T){
                    for (int y=0;y<23;y++){
                        for (int x=0;x<45;x++){
                            field[x][y].switchColor();
                        }
                    }
                    repaint();
                }else if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
                    new OptionFrame();
                }else if(e.getKeyCode()==KeyEvent.VK_Z){
                    setAlwaysOnTop(!isAlwaysOnTop());
                    if (isAlwaysOnTop()){
                        setTitle("CLOCK - always on top");
                    }else setTitle("CLOCK");
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });








        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
    public void setToTime(LocalTime time) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                int number = time.getHour()/10;
                if (lastTime==null||number!=lastTime.getHour()/10)setSegment(4,5,number);
        }});
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                int number = time.getHour()%10;
                if (lastTime==null||number!=lastTime.getHour()%10)setSegment(13,5,number);
            }});
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                int number = time.getMinute()/10;
                if (lastTime==null||number!=lastTime.getMinute()/10)setSegment(25,5,number);
            }});
        Thread t4 = new Thread(new Runnable() {
            @Override
            public void run() {
                int number = time.getMinute()%10;
                if (lastTime==null||number!=lastTime.getHour()%10)setSegment(34,5,number);
            }});
        t1.start();
        t2.start();
        t3.start();
        t4.start();

        try{
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        }catch(Exception ignored){}
        lastTime=time;

    }




    public void setSegment(int offsetX, int offsetY, int number){
        //Segment 0
        if (number!=1 && number!= 4){
            (field[offsetX+1][offsetY]).setToColor(1);
            (field[offsetX+2][offsetY]).setToColor(1);
            (field[offsetX+3][offsetY]).setToColor(1);
            (field[offsetX+4][offsetY]).setToColor(1);
            (field[offsetX+5][offsetY]).setToColor(1);

        }else{
            (field[offsetX+1][offsetY]).setToColor(0);
            (field[offsetX+2][offsetY]).setToColor(0);
            (field[offsetX+3][offsetY]).setToColor(0);
            (field[offsetX+4][offsetY]).setToColor(0);
            (field[offsetX+5][offsetY]).setToColor(0);
        }
        //Segment 3
        if (number!=0 && number!= 1 && number !=7){
            (field[offsetX+1][offsetY+6]).setToColor(1);
            (field[offsetX+2][offsetY+6]).setToColor(1);
            (field[offsetX+3][offsetY+6]).setToColor(1);
            (field[offsetX+4][offsetY+6]).setToColor(1);
            (field[offsetX+5][offsetY+6]).setToColor(1);

        }else{
            (field[offsetX+1][offsetY+6]).setToColor(0);
            (field[offsetX+2][offsetY+6]).setToColor(0);
            (field[offsetX+3][offsetY+6]).setToColor(0);
            (field[offsetX+4][offsetY+6]).setToColor(0);
            (field[offsetX+5][offsetY+6]).setToColor(0);
        }

        //Segment 6
        if (number!=1 && number!= 4 && number !=7){
            (field[offsetX+1][offsetY+12]).setToColor(1);
            (field[offsetX+2][offsetY+12]).setToColor(1);
            (field[offsetX+3][offsetY+12]).setToColor(1);
            (field[offsetX+4][offsetY+12]).setToColor(1);
            (field[offsetX+5][offsetY+12]).setToColor(1);

        }else{
            (field[offsetX+1][offsetY+12]).setToColor(0);
            (field[offsetX+2][offsetY+12]).setToColor(0);
            (field[offsetX+3][offsetY+12]).setToColor(0);
            (field[offsetX+4][offsetY+12]).setToColor(0);
            (field[offsetX+5][offsetY+12]).setToColor(0);
        }

        //Segment 1
        if (number!=1 && number!= 2 && number !=3 && number !=7){
            (field[offsetX][offsetY+1]).setToColor(1);
            (field[offsetX][offsetY+2]).setToColor(1);
            (field[offsetX][offsetY+3]).setToColor(1);
            (field[offsetX][offsetY+4]).setToColor(1);
            (field[offsetX][offsetY+5]).setToColor(1);

        }else{
            (field[offsetX][offsetY+1]).setToColor(0);
            (field[offsetX][offsetY+2]).setToColor(0);
            (field[offsetX][offsetY+3]).setToColor(0);
            (field[offsetX][offsetY+4]).setToColor(0);
            (field[offsetX][offsetY+5]).setToColor(0);
        }

        //Segment 2
        if (number!=5 && number!= 6){
            (field[offsetX+6][offsetY+1]).setToColor(1);
            (field[offsetX+6][offsetY+2]).setToColor(1);
            (field[offsetX+6][offsetY+3]).setToColor(1);
            (field[offsetX+6][offsetY+4]).setToColor(1);
            (field[offsetX+6][offsetY+5]).setToColor(1);

        }else{
            (field[offsetX+6][offsetY+1]).setToColor(0);
            (field[offsetX+6][offsetY+2]).setToColor(0);
            (field[offsetX+6][offsetY+3]).setToColor(0);
            (field[offsetX+6][offsetY+4]).setToColor(0);
            (field[offsetX+6][offsetY+5]).setToColor(0);
        }
        //Segment 4
        if (number%2==0&& number!=4){
            (field[offsetX][offsetY+7]).setToColor(1);
            (field[offsetX][offsetY+8]).setToColor(1);
            (field[offsetX][offsetY+9]).setToColor(1);
            (field[offsetX][offsetY+10]).setToColor(1);
            (field[offsetX][offsetY+11]).setToColor(1);

        }else{
            (field[offsetX][offsetY+7]).setToColor(0);
            (field[offsetX][offsetY+8]).setToColor(0);
            (field[offsetX][offsetY+9]).setToColor(0);
            (field[offsetX][offsetY+10]).setToColor(0);
            (field[offsetX][offsetY+11]).setToColor(0);
        }

        //Segment 5
        if (number!=2){
            (field[offsetX+6][offsetY+7]).setToColor(1);
            (field[offsetX+6][offsetY+8]).setToColor(1);
            (field[offsetX+6][offsetY+9]).setToColor(1);
            (field[offsetX+6][offsetY+10]).setToColor(1);
            (field[offsetX+6][offsetY+11]).setToColor(1);

        }else{
            (field[offsetX+6][offsetY+7]).setToColor(0);
            (field[offsetX+6][offsetY+8]).setToColor(0);
            (field[offsetX+6][offsetY+9]).setToColor(0);
            (field[offsetX+6][offsetY+10]).setToColor(0);
            (field[offsetX+6][offsetY+11]).setToColor(0);
        }
    }
    public void updateFieldColors(){
            for (int y=0;y<23;y++){
                for (int x=0;x<45;x++){
                    field[x][y].switchColor();
                    field[x][y].switchColor();
                }
            }
            repaint();
    }
}
