import java.awt.*;
import java.awt.image.*;

import javax.swing.*;

public class StageCanvas extends JPanel {
    private static final long serialVersionUID = 1L;
    private final int CELL_W = 10;
    private final int CELL_H = 10;
    //double buffering: viewing image, drawing image
    private BufferedImage vImage, dImage;
    
    private SnakeWorld world;
    
    public StageCanvas(SnakeWorld stage) {
        this.world = stage;

        //preferred size
        int w = CELL_W * SnakeWorld.WIDTH;
        int h = CELL_H * SnakeWorld.HEIGHT;
        this.setPreferredSize(new Dimension(w, h));

        //double buffering
        this.vImage = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR); 
        this.dImage = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR); 
    }

    public void repaint() {
        Graphics gc = this.getGraphics();
        if(gc == null)
            return;

        Graphics g = dImage.getGraphics();    
        int width  = dImage.getWidth();
        int height = dImage.getHeight();

        g.setColor(Color.GRAY);
        g.fillRect(0, 0, width,  height);
        
        for(SnakeWorld.Pos pos: world.getWallPos())
            drawCell(g, pos, Color.BLUE);
        
        drawCell(g, world.getApplePos(), Color.RED);
        
        for(SnakeWorld.Pos pos: world.getSnakePos())
            drawCell(g, pos, Color.GREEN);

        gc.drawImage(dImage, 0, 0, null);
        //double buffering
        BufferedImage t = vImage; vImage = dImage; dImage = t;
    }
    
    private void drawCell(Graphics g, SnakeWorld.Pos pos, Color color) {
        g.setColor(color);
        g.fillRect(pos.x * CELL_W,  pos.y * CELL_H,  CELL_W, CELL_H);
    }
}
