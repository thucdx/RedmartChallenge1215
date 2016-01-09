package com.thucdx;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(new File("products.csv"));
        Planning p = new Planning();

        while (in.hasNextLine()) {
            String[] info = in.nextLine().split(",");
            int[] parts = new int[info.length];
            for (int i = 0; i < info.length; ++i) {
                parts[i] = Integer.parseInt(info[i]);
            }
            Product product = new Product(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
            p.addProduct(product);
        }

        p.findBest();
    }
}
