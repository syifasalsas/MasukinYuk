/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tubes;

import java.awt.*;

public class Penghalang {
    private int xStart;
    private int yStart;
    private int widht;
    private int height;
    private Color color;
    
    
    public Penghalang(int x, int y, int widht, int height, Color color){
        xStart = x;
        yStart = y;
        this.widht = widht;
        this.height = height;
        this.color = color;
    }
    
    public void rectangle(Graphics x){
        x.setColor(color);
        x.fillRect(xStart, yStart, widht, height);
    }
    
    
    
    
    
}
