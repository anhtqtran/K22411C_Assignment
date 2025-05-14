package com.tranthanhqueanh.connectors;

import com.tranthanhqueanh.models.Product;
import com.tranthanhqueanh.models.ListProduct;

import java.util.ArrayList;

public class ProductConnector {

    ListProduct listProduct;

    public ProductConnector() {
        listProduct = new ListProduct();
        listProduct.generateSampleDataset();
    }

    public ArrayList<Product> get_all_products() {
        if (listProduct == null) {
            listProduct = new ListProduct();
            listProduct.generateSampleDataset();
        }
        return listProduct.getProducts();
    }

    public ArrayList<Product> get_products_by_category(int categoryId) {
        if (listProduct == null) {
            listProduct = new ListProduct();
            listProduct.generateSampleDataset();
        }
        ArrayList<Product> results = new ArrayList<>();
        for (Product product : listProduct.getProducts()) {
            if (product.getCate_id() == categoryId) {
                results.add(product);
            }
        }
        return results;
    }

    public ArrayList<Product> get_products_by_price_range(double minPrice, double maxPrice) {
        if (listProduct == null) {
            listProduct = new ListProduct();
            listProduct.generateSampleDataset();
        }
        ArrayList<Product> results = new ArrayList<>();
        for (Product product : listProduct.getProducts()) {
            if (product.getPrice() >= minPrice && product.getPrice() <= maxPrice) {
                results.add(product);
            }
        }
        return results;
    }
}
