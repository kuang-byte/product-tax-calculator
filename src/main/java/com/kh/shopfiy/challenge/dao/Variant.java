package com.kh.shopfiy.challenge.dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * This class represents Variant object which includes size, price and taxCode (refer
 * to {@link Tax}
 * <p>
 * It's a Json Object class powered by Gson.
 *
 * @author Hao Kuang
 * @date 2018/05/13
 */
public class Variant {

    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("tax_code")
    @Expose
    private Integer taxCode;

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(Integer taxCode) {
        this.taxCode = taxCode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("size", size).append("price", price).append("taxCode", taxCode).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(price).append(taxCode).append(size).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Variant) == false) {
            return false;
        }
        Variant rhs = ((Variant) other);
        return new EqualsBuilder().append(price, rhs.price).append(taxCode, rhs.taxCode).append(size, rhs.size).isEquals();
    }

}
