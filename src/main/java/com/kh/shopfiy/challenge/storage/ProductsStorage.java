package com.kh.shopfiy.challenge.storage;

import com.kh.shopfiy.challenge.dao.Product;

import java.util.HashMap;

/**
 * This class mocks database or memory of saving all the products
 *
 * @author Hao Kuang
 * @date 2018/05/13
 */
public final class ProductsStorage {
    private static final HashMap<Integer, Product> productsMap = new HashMap<>();

    /**
     * Get the product by its id.
     *
     * @param productId
     * @return Product object
     */
    public static Product getProductById(Integer productId) {
        return productsMap.get(productId);
    }

    /**
     * Add product into the storage
     *
     * @param product
     * @return return false if product Id has been already in the map. Otherwise, return true;
     */
    public static boolean addProduct(Product product) {
        return productsMap.putIfAbsent(product.getId(), product) == null ? true : false;
    }
}
