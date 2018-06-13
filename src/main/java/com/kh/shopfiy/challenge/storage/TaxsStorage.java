package com.kh.shopfiy.challenge.storage;

import com.kh.shopfiy.challenge.dao.Tax;

import java.util.HashMap;


/**
 * This class mocks database or memory of saving all kinds of taxes
 *
 * @author Hao Kuang
 * @date 2018/05/13
 */
public final class TaxsStorage {
    private static final HashMap<Integer, Tax> taxesMap = new HashMap<>();

    /**
     * Get tax object by taxcode
     *
     * @param taxCode
     * @return Tax Object
     */
    public static Tax getTaxByCode(Integer taxCode) {
        return taxesMap.get(taxCode);
    }

    /**
     * Add tax into the storage
     *
     * @param tax
     * @return return false if tax code has been already in the map. Otherwise, return true;
     */
    public static boolean addTax(Tax tax) {
        return taxesMap.putIfAbsent(tax.getCode(), tax) == null ? true : false;
    }
}
