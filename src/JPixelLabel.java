import javax.swing.*;
import java.awt.*;

public class JPixelLabel extends JPanel {
    private Color color2;
    private Color color1;
    private int state;




    //CONSTRUCTOR
    public JPixelLabel(Color color2, Color color1) {
        this.color2 = color2;
        this.color1 = color1;
        this.setBackground(color2);
        state=0;
        this.repaint();
    }

    //Set colors
    public void setColors(Color light, Color dark){
        color1 =light;
        color2 =dark;

    }

    /**
     *
     * 0 - dark
     * x - light
     *
     * **/


    public void setToColor(int index){
        if(index==0){
            this.setBackground(color2);
            state = 0;
        }else{
            this.setBackground(color1);
            state=1;
        }
        repaint();
    }

    public void switchColor(){
        Color help = color1;
        color1=color2;
        color2 = help;
        if (state==0){
            this.setBackground(color2);
        }else{
            this.setBackground(color1);
        }
        this.repaint();

    }
}
