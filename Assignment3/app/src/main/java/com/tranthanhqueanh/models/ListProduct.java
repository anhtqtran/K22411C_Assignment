package com.tranthanhqueanh.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ListProduct implements Serializable {
    private ArrayList<Product> products;

    public ListProduct() {
        this.products = new ArrayList<>();
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public void addProduct(Product p) {
        products.add(p);
    }

    public void generateSampleDataset() {
        products.clear();
        Random random = new Random();

        // Define product pools for each category
        Map<String, List<String>> namesByCategory = new HashMap<>();
        Map<String, List<String>> descByCategory = new HashMap<>();
        Map<String, double[]> priceByCategory = new HashMap<>();

        // Medications
        namesByCategory.put("Medications", Arrays.asList(
                "Paracetamol 500mg", "Ibuprofen 200mg", "Amoxicillin 500mg", "Aspirin 81mg", "Loratadine 10mg"
        ));
        descByCategory.put("Medications", Arrays.asList(
                "Pain reliever", "Anti-inflammatory", "Antibiotic", "Blood thinner", "Antihistamine"
        ));
        priceByCategory.put("Medications", new double[]{3.99, 15.99});

        // Medical Equipment
        namesByCategory.put("Medical Equipment", Arrays.asList(
                "Blood Pressure Monitor", "Pulse Oximeter", "Nebulizer", "Thermometer", "Stethoscope"
        ));
        descByCategory.put("Medical Equipment", Arrays.asList(
                "Monitors blood pressure", "Measures oxygen levels", "Respiratory aid", "Temperature reader", "Heart exam tool"
        ));
        priceByCategory.put("Medical Equipment", new double[]{19.99, 99.99});

        // Vitamins & Supplements
        namesByCategory.put("Vitamins & Supplements", Arrays.asList(
                "Vitamin C 1000mg", "Omega-3 Fish Oil", "Multivitamin Tablets", "Vitamin D3 2000IU", "Calcium 600mg"
        ));
        descByCategory.put("Vitamins & Supplements", Arrays.asList(
                "Immune support", "Heart health", "Daily nutrition", "Bone health", "Bone strength"
        ));
        priceByCategory.put("Vitamins & Supplements", new double[]{7.99, 19.99});

        // Personal Care
        namesByCategory.put("Personal Care", Arrays.asList(
                "Hand Sanitizer", "Face Mask (50-pack)", "Adult Diapers", "Shampoo", "Toothpaste"
        ));
        descByCategory.put("Personal Care", Arrays.asList(
                "Kills germs", "Protective masks", "Incontinence care", "Hair cleaner", "Cavity protection"
        ));
        priceByCategory.put("Personal Care", new double[]{2.99, 29.99});

        // Diagnostic Tools
        namesByCategory.put("Diagnostic Tools", Arrays.asList(
                "Blood Glucose Meter", "Pregnancy Test Kit", "Digital Thermometer", "Cholesterol Tester", "Urine Test Strips"
        ));
        descByCategory.put("Diagnostic Tools", Arrays.asList(
                "Diabetes management", "Pregnancy testing", "Temperature check", "Cholesterol monitor", "Urine screening"
        ));
        priceByCategory.put("Diagnostic Tools", new double[]{8.99, 49.99});

        // First Aid Supplies
        namesByCategory.put("First Aid Supplies", Arrays.asList(
                "Adhesive Bandages", "Antiseptic Wipes", "First Aid Kit", "Gauze Pads", "Medical Tape"
        ));
        descByCategory.put("First Aid Supplies", Arrays.asList(
                "For minor wounds", "Wound cleaner", "Emergency kit", "Wound dressing", "Secures dressings"
        ));
        priceByCategory.put("First Aid Supplies", new double[]{4.99, 29.99});

        // Mobility Aids
        namesByCategory.put("Mobility Aids", Arrays.asList(
                "Adjustable Cane", "Wheelchair", "Walker with Wheels", "Crutches", "Shower Chair"
        ));
        descByCategory.put("Mobility Aids", Arrays.asList(
                "Walking support", "Folding wheelchair", "Mobility aid", "Temporary support", "Safe bathing"
        ));
        priceByCategory.put("Mobility Aids", new double[]{19.99, 199.99});

        // Nutritional Products
        namesByCategory.put("Nutritional Products", Arrays.asList(
                "Protein Powder", "Meal Replacement Shake", "Electrolyte Drink Mix", "Energy Bars", "Fiber Supplement"
        ));
        descByCategory.put("Nutritional Products", Arrays.asList(
                "Protein supplement", "Weight management", "Hydration support", "On-the-go nutrition", "Digestive health"
        ));
        priceByCategory.put("Nutritional Products", new double[]{9.99, 29.99});

        // Generate products
        ListCategory listCategory = new ListCategory();
        listCategory.generateSampleDataset();
        int productId = 1;
        for (Category category : listCategory.getCategories()) {
            String catName = category.getName();
            int cateId = category.getId();
            int numProducts = 3 + random.nextInt(3); // 3-5 products

            // Get category data
            List<String> names = namesByCategory.getOrDefault(catName, Arrays.asList("Generic Product"));
            List<String> descriptions = descByCategory.getOrDefault(catName, Arrays.asList("Generic description"));
            double[] prices = priceByCategory.getOrDefault(catName, new double[]{5.0, 50.0});

            // Use first N names to avoid duplicates
            int maxProducts = Math.min(numProducts, names.size());
            for (int i = 0; i < maxProducts; i++) {
                String name = names.get(i); // Take names in order
                int quantity = 10 + random.nextInt(91); // 10-100
                double price = prices[0] + (prices[1] - prices[0]) * random.nextDouble();
                String description = descriptions.get(random.nextInt(descriptions.size()));
                addProduct(new Product(productId++, name, quantity, Math.round(price * 100.0) / 100.0, cateId, description));
            }
        }
    }
}