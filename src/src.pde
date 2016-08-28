//based on Invader Fractal (http://www.complexification.net/gallery/machines/invaderfractal/)
  
int cellsPerRow = 8, cellsPerCol = 6;
int spritesPerRow = 8, spritesPerCol = 6;
int cellSqrt = 15;

boolean mirrorXAxis = false, mirrorYAxis = true;
float cellWeight = 0.5;

int prevWidth, prevHeight;

void settings() {
    size(cellSqrt * (cellsPerRow+1) * spritesPerRow, cellSqrt * (cellsPerCol+1) * spritesPerCol);
}

void setup() {
    new GUI(this);
    noLoop();
}

void draw() {    
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

void makeDraw() {
    surface.setSize(cellSqrt * (cellsPerRow+1) * spritesPerRow, cellSqrt * (cellsPerCol+1) * spritesPerCol);
    redraw();
}

//SPRITES PER ROW
void setSpritesPerRow(int spritesPerRow) {
    this.spritesPerRow = spritesPerRow;
}

//SPRITES PER COLUMN
void setSpritesPerColumn(int spritesPerCol) {
    this.spritesPerCol = spritesPerCol;
}

//CELLS PER ROW
void setCellsPerRow(int cellsPerRow) {
    this.cellsPerRow = cellsPerRow;
}

//CELLS PER COLUMN
void setCellsPerColumn(int cellsPerCol) {
    this.cellsPerCol = cellsPerCol;
}

//CELL SIZE
void setCellSqrt(int cellSqrt) {
    this.cellSqrt = cellSqrt;
}

//CELL WEIGHT
void setCellWeight(float cellWeight) {
    this.cellWeight = cellWeight;
}

//MIRROR X-AXIS
void setMirrorXAxis(boolean val) {
    mirrorXAxis = val;
}

//MIRROR Y-AXIS
void setMirrorYAxis(boolean val) {
    mirrorYAxis = val;
}

//SAVE
void saveImage() {
    selectOutput("Save as", "saveImage");
}

void saveImage(File file) {
    println(file.getAbsolutePath());
    if (file != null) {
        save(file.getAbsolutePath());
    }
}