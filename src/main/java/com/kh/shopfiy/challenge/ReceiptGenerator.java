package com.kh.shopfiy.challenge;

import com.kh.shopfiy.challenge.dao.*;
import com.kh.shopfiy.challenge.helper.FormattedTableBuilder;
import com.kh.shopfiy.challenge.storage.ProductsStorage;
import com.kh.shopfiy.challenge.storage.TaxsStorage;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is used to calculate the taxes and generate the receipt
 * Since each item has it's corresponding tax kind and for multiple items,
 * their taxes kind may be duplicated, we use a {@link HashMap<Integer, Double>}
 * to save the appeared the tax and accumulating the amount and will be easily
 * printed out finally.
 *
 * @author Hao Kuang
 * @date 2018/05/13
 */
public class ReceiptGenerator {
    private static final DecimalFormat PERCENTAGE_DECIMAL_FORMAT = new DecimalFormat("#%");
    private static final DecimalFormat PRICE_DECIMAL_FORMAT = new DecimalFormat("#0.00");

    private Cart cart = null;
    private Double subTotal = 0.0d;
    private Double taxTotal = 0.0d;
    private HashMap<Integer, Double> taxCodeAmount = new HashMap<>();
    private FormattedTableBuilder receiptBuilder = new FormattedTableBuilder();

    /**
     * Main function of this class which does
     * - calculate price and tax of each cart item
     * - calculate the subtotal price
     * - calculate the all kinds of related taxes
     * - calculate the total prices
     * - pretty print the receipt
     */
    public void calculateAndPrettyPrint() {
        addColumnNames();
        calculateItems();
        calculateSubTotal();
        calculateTaxAmounts();
        calculateTotal();
        receiptBuilder.output(receipt -> {
            System.out.println(getHeader());
            System.out.println(receipt);
        });
    }

    public ReceiptGenerator(Cart cart) {
        this.cart = cart;
    }

    /**
     * Add empty line in the receipt table
     */
    private void addNewLine() {
        receiptBuilder.addEmptyLine();
    }

    /**
     * Calculate the total number of price and add it into the receipt table
     */
    private void calculateTotal() {
        receiptBuilder.addLine("", "TOTAL:", "",
                "$" + PRICE_DECIMAL_FORMAT.format(subTotal + taxTotal));
    }

    /**
     * Calculate the subtotal price and add generated string into the receipt table
     */
    private void calculateSubTotal() {
        addNewLine();
        receiptBuilder.addLine("", "SUBTOTAL:", "", String.valueOf(subTotal));
        addNewLine();
    }

    /**
     * Calculate each item in the shopping cart add generated string into the receipt table
     */
    private void calculateItems() {
        cart.getCartItemList().stream().forEach(cartItem -> calculateItemPriceEntry(cartItem));
    }

    /**
     * Calculate cart item entry and add generated string into the receipt table
     * For each item, the generated line in the table will be like:
     * "Quantity ProductName-VariantSize VariantTaxCode Price"
     *
     * @param cartItem CartItem Object
     */
    private void calculateItemPriceEntry(CartItem cartItem) {
        // Get product info of this item
        Product product = ProductsStorage.getProductById(cartItem.getProduct());
        // Get Variant info of this item
        Variant variant = product.getVariants().get(cartItem.getVariant());
        // Get Tax info of this item
        Tax tax = TaxsStorage.getTaxByCode(variant.getTaxCode());
        double price = cartItem.getQuantity() * variant.getPrice();
        double taxPrice = price * tax.getRate();
        subTotal += price;
        taxCodeAmount.put(variant.getTaxCode(),
                taxCodeAmount.getOrDefault(variant.getTaxCode(), 0.0d) + taxPrice);

        receiptBuilder.addLine(String.valueOf(cartItem.getQuantity()),
                product.getName() + " - " + variant.getSize(),
                String.valueOf(variant.getTaxCode()),
                PRICE_DECIMAL_FORMAT.format(price));
    }

    /**
     * Calculate the related taxes saved in the hashmap and add generated string into the receipt table
     */
    private void calculateTaxAmounts() {
        taxCodeAmount.forEach((key, value) -> calculateTaxAmountEntry(key, value));
    }

    /**
     * Calculate the tax and add generated string into receipt table
     * the format is like
     * "TaxCode-TaxName TaxRate Amount"
     *
     * @param taxCode
     * @param amount
     */
    private void calculateTaxAmountEntry(Integer taxCode, Double amount) {
        Tax tax = TaxsStorage.getTaxByCode(taxCode);
        taxTotal += amount;
        receiptBuilder.addLine("",
                String.valueOf(taxCode) + "-" + tax.getName(),
                PERCENTAGE_DECIMAL_FORMAT.format(tax.getRate()),
                PRICE_DECIMAL_FORMAT.format(amount));
    }

    private void addColumnNames() {
        receiptBuilder.addLine("Quantity", "Description", "Tax Code", "Amount");
    }

    private String getHeader() {
        return "============================================================\n" +
                "                    Coding Challenge Store                  \n" +
                "============================================================\n";
    }

}
