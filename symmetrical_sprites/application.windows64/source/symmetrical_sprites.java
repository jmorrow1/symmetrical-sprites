import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import controlP5.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class symmetrical_sprites extends PApplet {

//based on Invader Fractal (http://www.complexification.net/gallery/machines/invaderfractal/)
//TODO In a variation on checkerboards, make the black tiles in a checkerboard slide to make an invader
   
int cellsPerRow = 8, cellsPerCol = 6;
int spritesPerRow = 8, spritesPerCol = 6;
int cellSqrt = 15;

boolean mirrorXAxis = false, mirrorYAxis = true;
float cellWeight = 0.5f;

int prevWidth, prevHeight;

public void settings() {
    size(cellSqrt * (cellsPerRow+1) * spritesPerRow, cellSqrt * (cellsPerCol+1) * spritesPerCol);
}

public void setup() {
    new GUI(this);
    noLoop();
}

public void draw() {    
    background(255); 
    for (int i=0; i<spritesPerRow; i++) {
        for (int j=0; j<spritesPerCol; j++) {
            int x1 = i*cellSqrt*(cellsPerRow+1) + cellSqrt/2;
            int y1 = j*cellSqrt*(cellsPerCol+1) + cellSqrt/2;
            int[][] spec = genSpec(cellsPerRow, cellsPerCol, genPalette(x1, y1), new float[] {cellWeight, 1-cellWeight}, mirrorXAxis, mirrorYAxis);   
            invader(x1, y1, spec, cellSqrt, cellsPerRow, cellsPerCol);
        }
    }
}

public void makeDraw() {
    surface.setSize(cellSqrt * (cellsPerRow+1) * spritesPerRow, cellSqrt * (cellsPerCol+1) * spritesPerCol);
    redraw();
}

//SPRITES PER ROW
public void setSpritesPerRow(int spritesPerRow) {
    this.spritesPerRow = spritesPerRow;
}

//SPRITES PER COLUMN
public void setSpritesPerColumn(int spritesPerCol) {
    this.spritesPerCol = spritesPerCol;
}

//CELLS PER ROW
public void setCellsPerRow(int cellsPerRow) {
    this.cellsPerRow = cellsPerRow;
}

//CELLS PER COLUMN
public void setCellsPerColumn(int cellsPerCol) {
    this.cellsPerCol = cellsPerCol;
}

//CELL SIZE
public void setCellSqrt(int cellSqrt) {
    this.cellSqrt = cellSqrt;
}

//CELL WEIGHT
public void setCellWeight(float cellWeight) {
    this.cellWeight = cellWeight;
}

//MIRROR X-AXIS
public void setMirrorXAxis(boolean val) {
    mirrorXAxis = val;
}

//MIRROR Y-AXIS
public void setMirrorYAxis(boolean val) {
    mirrorYAxis = val;
}

//SAVE
public void saveImage() {
    selectOutput("Save as", "saveImage");
}

public void saveImage(File file) {
    println(file.getAbsolutePath());
    if (file != null) {
        save(file.getAbsolutePath());
    }
}


class GUI extends PApplet {
    ControlP5 cp5;
  
    GUI(PApplet listener) {
        PApplet.runSketch(new String[] {this.getClass().getSimpleName()}, this);
    }
    
    public void settings() {
        size(400, 400);
    }
    
    public void setup() {
        background(255);
        
        cp5 = new ControlP5(this);
        
        float[] xs = scan(20, 70, 3);
        float[] ys = scan(20, 30, 10);
        int w = width - 150;
        int h = 20;
        
        //SPRITES PER ROW
        Slider s = cp5.addSlider("sprites per row");
        s.setColorLabel(0);
        s.snapToTickMarks(true);
        s.setRange(1, 30);
        s.setNumberOfTickMarks(30);
        s.setPosition(xs[0], ys[0]);
        s.setWidth(w);
        s.setHeight(h);
        s.setValue(spritesPerRow);
        
        //SPRITES PER COLUMN
        s = cp5.addSlider("sprites per column");
        s.setColorLabel(0);
        s.snapToTickMarks(true);
        s.setRange(1, 30);
        s.setNumberOfTickMarks(30);
        s.setPosition(xs[0], ys[1]);
        s.setWidth(w);
        s.setHeight(h);
        s.setValue(spritesPerCol);
        
        //CELLS PER ROW
        s = cp5.addSlider("cells per row");
        s.setColorLabel(0);
        s.snapToTickMarks(true);
        s.setRange(1, 50);
        s.setNumberOfTickMarks(50);
        s.setPosition(xs[0], ys[2]);
        s.setWidth(w);
        s.setHeight(h);
        s.setValue(cellsPerRow);
        
        //CELLS PER COLUMN
        s = cp5.addSlider("cells per column");
        s.setColorLabel(0);
        s.snapToTickMarks(true);
        s.setRange(1, 50);
        s.setNumberOfTickMarks(50);
        s.setPosition(xs[0], ys[3]);
        s.setWidth(w);
        s.setHeight(h);
        s.setValue(cellsPerCol);

        //CELL SIZE
        s = cp5.addSlider("cell size");
        s.setColorLabel(0);
        s.setRange(1, 30);  
        s.setPosition(xs[0], ys[4]);
        s.setWidth(w);
        s.setHeight(h);
        s.setValue(cellSqrt);
        
        //CELL WEIGHT
        s = cp5.addSlider("cell weight");
        s.setColorLabel(0);
        s.setRange(0, 1);  
        s.setPosition(xs[0], ys[5]);
        s.setWidth(w);
        s.setHeight(h);
        s.setValue(cellWeight);
        
        //MIRROR X-AXIS
        Toggle t = cp5.addToggle("Mirror X-Axis");
        t.setPosition(xs[0], ys[6]);
        t.setColorLabel(0);
        t.setHeight(h);
        t.setValue(mirrorXAxis);
        
        //MIRROR Y-AXIS
        t = cp5.addToggle("Mirror Y-Axis");
        t.setPosition(xs[1], ys[6]);
        t.setColorLabel(0);
        t.setHeight(h);
        t.setValue(mirrorYAxis);
        
        //SAVE
        Button b = cp5.addButton("Save Image");
        b.setWidth(80);
        b.setPosition(xs[2] + 30, ys[6]);
        b.setHeight(h);
    }
    
    public float[] scan(float start, float change, int n) {
        float[] vals = new float[n];
        float val = start;
        for (int i=0; i<n; i++) {
            vals[i] = val;
            val += change;
        }
        return vals;
    }
    
    public void draw() {
    }
    
    public void mouseReleased() {
        makeDraw();
    }
    
    public void controlEvent(ControlEvent e) {
        if (frameCount != 0) {
            switch(e.getController().getLabel()) {
                case "sprites per row" : setSpritesPerRow(round(e.getValue())); break;
                case "sprites per column" : setSpritesPerColumn(round(e.getValue())); break;
                case "cells per row" : setCellsPerRow(round(e.getValue())); break;
                case "cells per column" : setCellsPerColumn(round(e.getValue())); break;
                case "cell size" : setCellSqrt(round(e.getValue())); break;
                case "cell weight" : setCellWeight(e.getValue()); break;
                case "Mirror X-Axis" : setMirrorXAxis(e.getValue() == 1); break;
                case "Mirror Y-Axis" : setMirrorYAxis(e.getValue() == 1); break;
                case "Save Image" : saveImage(); break;
            }
        }
    }
}
public int[] genPalette(int x1, int y1) {
    //colorMode(HSB, 360, 100, 100, 100);
    //float hue = random(360);
    //float hue = map(y1, 0, height, 100, -20) % 360;
    //return new int[] {color(hue, 50, 25), color(hue, 50, 75)};
    return new int[] {color(0), color(255)};
}


public int[][] genSpec(int w, int h, int[] palette, float[] weights, boolean mirrorXAxis, boolean mirrorYAxis) {
    int a = mirrorYAxis ? w/2 : w;
    int b = mirrorXAxis ? h/2 : h;
    if (w % 2 == 1) {
        a++;
    }
    if (h % 2 == 1) {
        b++;
    }
    int[][] cs = new int[a][b];
    for (int i=0; i<cs.length; i++) {
        for (int j=0; j<cs[i].length; j++) {
            cs[i][j] = choose(palette, weights);
        }
    }
    return cs;
}

public int choose(int[] choices, float[] weights) {  
    float sum = 0;
    for (int i=0; i<weights.length; i++) {
        sum += weights[i];
    }

    float r = random(sum);
    float s = 0;
    for (int i=0; i<choices.length; i++) {
        s += weights[i];
        if (r < s) {
            return choices[i];
        }
    }
    return -1;
}

public void invader(int x1, int y1, int[][] cs, int cellSqrt, int w, int h) {
    int i=0;
    int x=x1;
    boolean xAscend = true;
    for (int a=0; a<w; a++) {
        int j=0;
        int y=y1;
        boolean yAscend = true;
        for (int b=0; b<h; b++) {
            noStroke();
            fill(cs[i][j]);
            rect(x, y, cellSqrt, cellSqrt);
            j += (yAscend) ? 1 : -1;
            if (j == cs[i].length) {
                yAscend = false;
                if (h % 2 == 0) {
                    j--;
                } else {
                    j-=2;
                }
            }
            y += cellSqrt;
        }
        i += (xAscend) ? 1 : -1;
        if (i == cs.length) {
            xAscend = false;
            if (w % 2 == 0) {
                i--;
            } else {
                i-=2;
            }
        }
        x += cellSqrt;
    }
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "symmetrical_sprites" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
