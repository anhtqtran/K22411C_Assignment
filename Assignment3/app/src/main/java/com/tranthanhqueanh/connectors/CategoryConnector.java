package com.tranthanhqueanh.connectors;

import com.tranthanhqueanh.models.Category;
import com.tranthanhqueanh.models.ListCategory;

import java.util.ArrayList;

public class CategoryConnector {

    ListCategory listCategory;

    public CategoryConnector() {
        listCategory = new ListCategory();
        listCategory.generateSampleDataset();
    }

    public ArrayList<Category> get_all_categories() {
        if (listCategory == null) {
            listCategory = new ListCategory();
            listCategory.generateSampleDataset();
        }
        return listCategory.getCategories();
    }

    public ArrayList<Category> get_categories_by_name(String name) {
        if (listCategory == null) {
            listCategory = new ListCategory();
            listCategory.generateSampleDataset();
        }
        ArrayList<Category> results = new ArrayList<>();
        for (Category category : listCategory.getCategories()) {
            if (category.getName().contains(name)) {
                results.add(category);
            }
        }
        return results;
    }
}
