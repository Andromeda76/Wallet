package com.example.keywowallet.behaviors;


import com.example.keywowallet.entity.Stock;
import com.example.keywowallet.entity.Wallet;

import java.math.BigDecimal;
import java.util.List;


public interface WalletResourceManagement {


    void updateWalletStatusForNewStock(Stock stock);
    List<Wallet> setStockOnEligibleWallets();
    void setWallets(BigDecimal walletAmount);

}
