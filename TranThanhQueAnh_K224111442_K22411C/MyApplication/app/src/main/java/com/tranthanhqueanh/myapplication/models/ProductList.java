package com.tranthanhqueanh.myapplication.models;

import java.util.ArrayList;
import java.util.List;

public class ProductList {
    private static List<Products> productList = new ArrayList<>();

    static {
        // Khởi tạo 20 sản phẩm mẫu (URL ảnh thật)
        productList.add(new Products(1, "P001", "Lý Thuyết Trò Chơi", 100000, "https://salt.tikicdn.com/cache/750x750/ts/product/8a/b6/ba/1d95b88597f28e42d8ca91e3b3ff600f.jpg.webp"));
        productList.add(new Products(2, "P002", "Đàn Ông Sao Hỏa Đàn Bà Sao Kim", 150000, "https://salt.tikicdn.com/cache/750x750/ts/product/0a/f6/38/bc10734989977da424642a1c4750eee2.jpg.webp"));
        productList.add(new Products(3, "P003", "Deep Work - Làm Ra Làm, Chơi Ra Chơi (Tái Bản)", 200000, "https://salt.tikicdn.com/cache/750x750/ts/product/34/73/6b/2582628bf067160b459d9b3f425050a9.jpg.webp"));
        productList.add(new Products(4, "P004", "Sobotta Atlas Giải Phẫu Người", 250000, "https://salt.tikicdn.com/cache/750x750/ts/product/3c/77/83/8d5e04adcd94d8a916cc35a58b84d1ec.jpg.webp"));
        productList.add(new Products(5, "P005", "Bạch Dạ Hành (Tái Bản)", 300000, "https://salt.tikicdn.com/cache/750x750/ts/product/80/67/af/753a66ef25086f469826f3ee4716f751.jpg.webp"));
        productList.add(new Products(6, "P006", "Phía Sau Nghi Can X", 350000, "https://salt.tikicdn.com/cache/750x750/ts/product/23/56/86/a538698ead7dc2f693d1e9778417317d.jpg.webp"));
        productList.add(new Products(7, "P007", "Án Mạng Mười Một Chữ", 400000, "https://salt.tikicdn.com/cache/750x750/ts/product/32/2e/b7/fcd82c276d5e2a662ccadc1fb7e26cb4.jpg.webp"));
        productList.add(new Products(8, "P008", "Mật Mã 6:20", 450000, "https://salt.tikicdn.com/cache/750x750/ts/product/48/8a/94/c3acba8c7cc9a72482dfb23f1de478ad.jpg.webp"));
        productList.add(new Products(9, "P009", "Chân Dung Một Gián Điệp", 500000, "https://salt.tikicdn.com/cache/750x750/ts/product/ba/ff/a5/7ca465ea32dcbb3fadce659e57126589.jpg.webp"));
        productList.add(new Products(10, "P010", "Án mạng trên sông Nile", 550000, "https://salt.tikicdn.com/cache/750x750/ts/product/a7/2d/a1/4da490a4ddfc1edd780cef0e76b96eb0.jpg.webp"));
        productList.add(new Products(11, "P011", "Sau Tang Lễ", 600000, "https://salt.tikicdn.com/cache/750x750/ts/product/14/af/08/e6a27680d0d0b25317fcad0ecbc49c17.jpg.webp"));
        productList.add(new Products(12, "P012", "Agatha Christie. Bi kịch ba hồi", 650000, "https://salt.tikicdn.com/cache/750x750/ts/product/0c/63/d8/fa1f5b6dbf058daa54ade6a7d194fa86.jpg.webp"));
        productList.add(new Products(13, "P013", "Nhân Chứng Buộc Tội", 700000, "https://salt.tikicdn.com/cache/750x750/ts/product/27/fe/d2/ff499f66f5aa63df7a66c9d3d3a51fdd.jpg.webp"));
        // Sửa URL ảnh cho sản phẩm P014 - Vọng Lâu Tử Thần (thay bằng URL thật)
        productList.add(new Products(14, "P014", "Vọng Lâu Tử Thần", 750000, "https://salt.tikicdn.com/cache/750x750/ts/product/0b/42/88/1c6b1248b88e73a97a59a8119a994ac0.jpg.webp")); // Ví dụ URL đã thay
        productList.add(new Products(15, "P015", "Những Vụ Kỳ Án Của Sherlock Holmes", 800000, "https://salt.tikicdn.com/cache/750x750/media/catalog/product/i/m/img930_2.jpg.webp"));
        productList.add(new Products(16, "P016", "Thung Lũng Bất Hạnh", 850000, "https://salt.tikicdn.com/cache/750x750/ts/product/3f/48/9f/a7e10070f3c09c169f80d765bd13bb00.jpg.webp"));
        productList.add(new Products(17, "P017", "Papillon Người Tù Khổ Sai", 900000, "https://salt.tikicdn.com/cache/750x750/ts/product/d6/ec/df/ce8db2a7c1b6392acaf88d8ce701d9e0.jpg.webp"));
        productList.add(new Products(18, "P018", "Trước Lúc Anh Đi", 950000, "https://salt.tikicdn.com/cache/750x750/media/catalog/product/t/r/truoc%20luc%20anh%20di.u547.d20160912.t093224.173753.jpg.webp"));
        productList.add(new Products(19, "P019", "Nhà Giả Kim", 1000000, "https://salt.tikicdn.com/cache/750x750/ts/product/79/d2/5e/d6f8ca49544b686a84336517e82796ee.jpg.webp"));
        productList.add(new Products(20, "P020", "Những Chiếc Cầu Ở Quận Madisson", 1050000, "https://salt.tikicdn.com/cache/750x750/ts/product/9a/15/6f/d11f5c7ba2de627bd0c3b442e3f46007.jpg.webp"));
    }

    // Sửa kiểu trả về thành List<Products>
    public static List<Products> getProducts() {
        return productList;
    }

    public static void addProduct(Products product) {
        productList.add(product);
    }
}