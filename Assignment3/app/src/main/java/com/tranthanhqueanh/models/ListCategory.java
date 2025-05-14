package com.tranthanhqueanh.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ListCategory implements Serializable {
    private ArrayList<Category> categories;

    public ListCategory() {
        this.categories = new ArrayList<>();
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public void addCategory(Category c) {
        categories.add(c);
    }

    public void generateSampleDataset() {
        categories.clear();
        Random random = new Random();

        // Pool of healthcare category names
        List<String> categoryPool = Arrays.asList(
                "Medications",
                "Medical Equipment",
                "Vitamins & Supplements",
                "Personal Care",
                "Diagnostic Tools",
                "First Aid Supplies",
                "Mobility Aids",
                "Nutritional Products",
                "Respiratory Care",
                "Wound Care"
        );

        // Shuffle and select 5-8 categories
        Collections.shuffle(categoryPool, random);
        int numCategories = 5 + random.nextInt(4); // Randomly select 5 to 8 categories
        for (int i = 0; i < numCategories; i++) {
            addCategory(new Category(i + 1, categoryPool.get(i)));
        }
    }
}