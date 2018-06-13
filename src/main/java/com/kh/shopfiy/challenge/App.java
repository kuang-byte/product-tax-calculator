package com.kh.shopfiy.challenge;

import com.google.common.base.Strings;
import com.google.gson.JsonSyntaxException;
import com.kh.shopfiy.challenge.helper.JsonReadWriteHelper;

/**
 * Entry point of the program which loads default products and taxes into the memory
 *
 * @author Hao Kuang
 * @date 2018/05/13
 */
public class App {
    static {
        // Load products list into the storage
        JsonReadWriteHelper.loadDefaultProductsIntoStorage();
        // Load taxes into the storage
        JsonReadWriteHelper.loadDefaultTaxesIntoStorage();
    }

    public static void main(String[] args) {
        // Load input into shopping cart
        if (Strings.isNullOrEmpty(args[0])) {
            System.out.println("Error: Cart Json (First input parameter) cannot be null or empty");
            return;
        }
        ReceiptGenerator receiptGenerator = null;
        try {
            receiptGenerator = new ReceiptGenerator(JsonReadWriteHelper.generateShoppingCartFromJson(args[0]));
        } catch (JsonSyntaxException e) {
            System.out.println("Error: Failed to parse the Cart Json: " + e.getMessage());
            e.printStackTrace();
        }

        if (receiptGenerator != null) {
            receiptGenerator.calculateAndPrettyPrint();
        }
    }
}