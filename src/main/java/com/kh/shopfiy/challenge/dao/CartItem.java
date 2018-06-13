package com.kh.shopfiy.challenge.dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * This class represents the item you put into the {@link Cart} which includes
 * productId (refer to {@link Product}, variantId (refer to {@link Variant} and quantity.
 * <p>
 * It's a Json Object class powered by Gson.
 *
 * @author Hao Kuang
 * @date 2018/05/13
 */
public class CartItem {

    @SerializedName("product")
    @Expose
    private Integer product;
    @SerializedName("variant")
    @Expose
    private Integer variant;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;

    public Integer getProduct() {
        return product;
    }

    public void setProduct(Integer product) {
        this.product = product;
    }

    public Integer getVariant() {
        return variant;
    }

    public void setVariant(Integer variant) {
        this.variant = variant;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("product", product).append("variant", variant).append("quantity", quantity).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(product).append(quantity).append(variant).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof CartItem) == false) {
            return false;
        }
        CartItem rhs = ((CartItem) other);
        return new EqualsBuilder().append(product, rhs.product).append(quantity, rhs.quantity).append(variant, rhs.variant).isEquals();
    }

}