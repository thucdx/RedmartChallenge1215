package com.thucdx;
import static java.lang.Math.min;
import static java.lang.Math.max;

public class Box {
    int dim1, dim2, dim3;
    int volume;

    public Box(int dim1, int dim2, int dim3) {
        this.dim1 = min(dim1, min(dim2, dim3));
        this.dim3 = max(dim1, max(dim2, dim3));
        this.dim2 = dim1 + dim2 + dim3 - this.dim1 - this.dim3;
        volume = dim1 * dim2 * dim3;
    }

    public int getDim1() {
        return dim1;
    }

    public int getDim2() {
        return dim2;
    }

    public int getDim3() {
        return dim3;
    }

    public int getVolume() {
        return volume;
    }

    public boolean canContain(Box other) {
        return dim1 >= other.getDim1() && dim2 >= other.getDim2() && dim3 >= other.getDim3();
    }
}
