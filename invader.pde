int[] genPalette(int x1, int y1) {
    //colorMode(HSB, 360, 100, 100, 100);
    //float hue = random(360);
    //float hue = map(y1, 0, height, 100, -20) % 360;
    //return new int[] {color(hue, 50, 25), color(hue, 50, 75)};
    return new int[] {color(0), color(255)};
}


int[][] genSpec(int w, int h, int[] palette, float[] weights, boolean mirrorXAxis, boolean mirrorYAxis) {
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

int choose(int[] choices, float[] weights) {  
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

void invader(int x1, int y1, int[][] cs, int cellSqrt, int w, int h) {
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