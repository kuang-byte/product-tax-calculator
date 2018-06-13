package com.kh.shopfiy.challenge.helper;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.kh.shopfiy.challenge.dao.Product;
import com.kh.shopfiy.challenge.dao.Cart;
import com.kh.shopfiy.challenge.dao.CartItem;
import com.kh.shopfiy.challenge.dao.Tax;
import com.kh.shopfiy.challenge.storage.ProductsStorage;
import com.kh.shopfiy.challenge.storage.TaxsStorage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * This class provides the functionalities of converting json file or json string into corresponding
 * Java Object which is powered by Gson.
 *
 * @author Hao Kuang
 * @date 2018/05/13
 */
public final class JsonReadWriteHelper {

    private static final String DEFAULT_PRODUCTS_JSON_FILE_NAME = "default_products.json";
    private static final String DEFAULT_TAXS_JSON_FILE_NAME = "default_taxes.json";
    private static final Gson gson = new Gson();

    /**
     * Convert shoppingCartJson string into Cart object directly
     * Below is one example of the {@param cartJson}
     * [
     * {
     * "product": 1000,
     * "variant": 0,
     * "quantity": 1
     * },
     * {
     * "product": 2000,
     * "variant": 1,
     * "quantity": 10
     * }
     * ]
     *
     * @param cartJson
     * @return
     * @throws JsonSyntaxException
     */
    public static Cart generateShoppingCartFromJson(String cartJson) throws JsonSyntaxException {
        Type founderListType = new TypeToken<ArrayList<CartItem>>() {
        }.getType();
        List<CartItem> items = gson.fromJson(cartJson, founderListType);
        return new Cart(items);
    }

    /**
     * Since we don't have database included in this code challenge, so a static map
     * inside {@link ProductsStorage} is used to save all the products in advance.
     * <p>
     * This method is used to load all the products data from resources/default_product.json
     * into {@link ProductsStorage}.
     * If the productId has been already inside the storage, the others will be ignored.
     */
    public static void loadDefaultProductsIntoStorage() {
        loadJsonArrayIntoStorage(DEFAULT_PRODUCTS_JSON_FILE_NAME, bufferedReader -> {
            Product[] productList = gson.fromJson(bufferedReader, Product[].class);
            Arrays.stream(productList).forEach(product -> {
                if (!ProductsStorage.addProduct(product)) {
                    System.err.println(String.format("Product id = %s is ignored which has been already in the " +
                            "storage", product.getId()));
                } else {
                    System.out.println(String.format("Product id = %s has been loaded successfully", product.getId()));
                }
            });
        });
    }

    /**
     * Since we don't have database included in this code challenge, so a static map
     * inside {@link TaxsStorage} is used to save all the taxes in advance.
     * <p>
     * This method is used to load all kinds of taxes from resources/default_taxes.json
     * into {@link TaxsStorage}.
     * <p>
     * If the tax code has been already inside the storage, the others will be ignored.
     */
    public static void loadDefaultTaxesIntoStorage() {
        loadJsonArrayIntoStorage(DEFAULT_TAXS_JSON_FILE_NAME, bufferedReader -> {
            Tax[] taxList = gson.fromJson(bufferedReader, Tax[].class);
            Arrays.stream(taxList).forEach(tax -> {
                if (!TaxsStorage.addTax(tax)) {
                    System.err.println(String.format("Tax code = %s is ignored which has been already in the " +
                            "storage", tax.getCode()));
                } else {
                    System.out.println(String.format("Tax code = %s has been loaded successfully", tax.getCode()));
                }
            });
        });
    }

    /**
     * A helper method to read file from resources folder.
     *
     * @param fileName File name
     * @param consumer interface of providing ability to handle BufferedReader for different methods
     */

    private static void loadJsonArrayIntoStorage(String fileName, Consumer<BufferedReader> consumer) {
        BufferedReader br = null;
        try {
            ClassLoader classLoader = JsonReadWriteHelper.class.getClassLoader();
            br = new BufferedReader(new FileReader(classLoader.getResource(fileName).getFile()));
            consumer.accept(br);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println(String.format("File: %s cannot be found under resources folder", fileName));
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.err.println("Got exception to close the BufferedReader. " + e.getMessage());
                }
            }
        }
    }

}
