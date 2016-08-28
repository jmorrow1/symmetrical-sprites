import controlP5.*;

class GUI extends PApplet {
    ControlP5 cp5;
  
    GUI(PApplet listener) {
        PApplet.runSketch(new String[] {this.getClass().getSimpleName()}, this);
    }
    
    void settings() {
        size(400, 400);
    }
    
    void setup() {
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
    
    float[] scan(float start, float change, int n) {
        float[] vals = new float[n];
        float val = start;
        for (int i=0; i<n; i++) {
            vals[i] = val;
            val += change;
        }
        return vals;
    }
    
    void draw() {
    }
    
    void mouseReleased() {
        makeDraw();
    }
    
    void controlEvent(ControlEvent e) {
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