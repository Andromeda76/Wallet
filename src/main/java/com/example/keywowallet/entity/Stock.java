package com.example.keywowallet.entity;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;


@Entity
@Table(name = "Stock", schema = "TSNA")
public class Stock extends AbstractEntity {

    @Column(name = "Symbol")
    private String symbol;

    @Column(name = "Quantity")
    private BigInteger quantity;

    @Column(name = "TotalPrice")
    private BigDecimal totalPrice;

    @OneToMany(mappedBy = "stocks", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Wallet> wallet;

    @Column(name = "StockRatio")
    private Double stockRatio;


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigInteger getQuantity() {
        return quantity;
    }

    public void setQuantity(BigInteger quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<Wallet> getWallet() {
        return wallet;
    }

    public void setWallet(List<Wallet> wallet) {
        this.wallet = wallet;
    }

    public Double getStockRatio() {
        return stockRatio;
    }

    public void setStockRatio(Double stockRatio) {
        this.stockRatio = stockRatio;
    }

    @Override
    AbstractEntity getInstance() {
        return this;
    }

    //compare today value with yesterday
    //average

}
