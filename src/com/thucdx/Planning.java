package com.thucdx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Planning {
    static final Box TOTE = new Box(45, 30, 35);
    List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        if (TOTE.canContain(product))
            products.add(product);
    }

    /**
     * Find short list of product should be considered to minimize search range
     * We call product A is BETTER than product B if A has SMALLER VOLUME and HIGHER PRICE.
     *
     * Obviously, a product should NOT be considered if many products (>= x) BETTER than it.
     * In this function, I choose x  = TOTE_volume / min_volume
     */
    private List<Product> findShortList() {
        int maxProduct = TOTE.getVolume() / products.get(0).getVolume();

        Collections.sort(products, (o1, o2) -> {
            if (o1.getVolume() != o2.getVolume()) {
                return ((Integer) o1.getVolume()).compareTo(o2.getVolume());
            }
            return ((Integer) o1.getPrice()).compareTo(o2.getPrice());
        });

        List<Product> shortList = new ArrayList<>();
        for (Product pro: products) {
            int count = 0;
            for (Product s: shortList) {
                if (s.getPrice() >= pro.getPrice()) ++count;
            }

            if (count <= maxProduct) {
                shortList.add(pro);
            }
        }

        return shortList;
    }

    /**
     * Step1: Find short list of product
     * Step2: Use dynamic programming
     */
    public void findBest() {
        products = findShortList();
        DPState[][][] dp = new DPState[TOTE.getVolume() + 1][products.size()][2];

        for (int volume = products.get(0).getVolume(); volume <= TOTE.getVolume(); ++volume) {
            dp[volume][0][1] = new DPState(products.get(0).getPrice(), products.get(0).getWeight(), products.get(0).getId());

            for (int p = 1; p < products.size() && products.get(p).getVolume() <= volume; ++p) {
                dp[volume][p][0] = DPState.max(dp[volume][p - 1][0], dp[volume][p - 1][1]);
                int lastStep = volume - products.get(p).getVolume();
                DPState bestState = DPState.max(dp[lastStep][p - 1][0], dp[lastStep][p - 1][1]);
                dp[volume][p][1] = bestState.add(products.get(p));
            }
        }

        DPState bestState = DPState.max(dp[TOTE.getVolume()][products.size() - 1][0], dp[TOTE.getVolume()][products.size() - 1][1]);
        System.out.println(bestState);
    }

    static class DPState {
        int totalPrice, totalWeight, sumOfId;
        public DPState(int price, int weight, int sumOfId) {
            this.totalPrice = price;
            this.totalWeight = weight;
            this.sumOfId = sumOfId;
        }

        static DPState max(DPState a, DPState b) {
            if (a == null && b == null) return new DPState(0, 0, 0);
            if (a == null) return b;
            if (b == null) return a;

            if (a.totalPrice > b.totalPrice) {
                return a;
            } else if (a.totalPrice < b.totalPrice) {
                return b;
            } else if (a.totalWeight < b.totalWeight) {
                return a;
            } else return b;
        }

        DPState add(Product product) {
            return new DPState(totalPrice + product.getPrice(), totalWeight + product.getWeight(), sumOfId + product.getId());
        }

        @Override
        public String toString() {
            return "DPState{" +
                    "totalPrice=" + totalPrice +
                    ", totalWeight=" + totalWeight +
                    ", sumOfId=" + sumOfId +
                    '}';
        }
    }
}
