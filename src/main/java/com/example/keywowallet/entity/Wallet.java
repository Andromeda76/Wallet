package com.example.keywowallet.entity;


import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "Wallet", schema = "TSNA")
public class Wallet extends AbstractEntity {

    @Column(name = "StockAmount")
    private BigDecimal stockAmount;

    @Column(name = "WalletAmount")
    //whole of stocks quantity and reservedAmount
    // and profit that have been bought
    private BigDecimal walletAmount;

    @JoinColumn(name = "StockProperties")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Stock stocks;

    @Column(name = "Reserved_Amount")
    private BigDecimal reservedAmount;//Money that has been left aside to buy stock or what ever else

    @Column(name = "Storage")// Is there some profit of trading
    private BigDecimal profit;

    @Column(name = "WorkingCapital")
    private BigDecimal workingCapital;



    @Override
    Long getId() {
        return this.id;
    }

    @Override
    void setId(Long id) {
        this.id = id;
    }


    public BigDecimal getStockAmount() {
        return stockAmount;
    }

    public void setStockAmount(BigDecimal stockAmount) {
        this.stockAmount = stockAmount;
    }

    public BigDecimal getWalletAmount() {
        return walletAmount;
    }

    public void setWalletAmount(BigDecimal walletAmount) {
        this.walletAmount = walletAmount;
    }

    public Stock getStocks() {
        return stocks;
    }

    public void setStocks(Stock stocks) {
        this.stocks = stocks;
    }

    public BigDecimal getReservedAmount() {
        return reservedAmount;
    }

    public void setReservedAmount(BigDecimal reservedAmount) {
        this.reservedAmount = reservedAmount;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public BigDecimal getWorkingCapital() {
        return workingCapital;
    }

    public void setWorkingCapital(BigDecimal workingCapital) {
        this.workingCapital = workingCapital;
    }

    @Override
    AbstractEntity getInstance() {
        return this;
    }


    //HotStock
    //WarmStock
    //ColdStock
    //AsyncScheduling

}


