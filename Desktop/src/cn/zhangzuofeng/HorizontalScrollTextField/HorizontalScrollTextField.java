package cn.zhangzuofeng.HorizontalScrollTextField;

import java.awt.Graphics;
import javax.swing.JTextField;
import javax.swing.text.Document;

public class HorizontalScrollTextField extends JTextField{

    public HorizontalScrollTextField() {
    }

    public HorizontalScrollTextField(String text) {
        super(text);
    }

    public HorizontalScrollTextField(int columns) {
        super(columns);
    }

    public HorizontalScrollTextField(String text, int columns) {
        super(text, columns);
    }

    public HorizontalScrollTextField(Document doc, String text, int columns) {
        super(doc, text, columns);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); 
        
    }

    @Override
    public void setText(String t) {
        super.setText(t); //To change body of generated methods, choose Tools | Templates.
    }
    

}
