package com.example.keywowallet.behaviorImpl;


import java.math.BigDecimal;
import java.util.List;


import com.example.keywowallet.behaviors.WalletResourceManagement;
import com.example.keywowallet.entity.Stock;
import com.example.keywowallet.entity.Wallet;
import org.springframework.stereotype.Service;


@Service
public class WalletResourceManagementWrapper {


    private final WalletResourceManagement walletResourceManagement;

    public WalletResourceManagementWrapper(WalletResourceManagement walletResourceManagement) {
        this.walletResourceManagement = walletResourceManagement;
    }

    public List<Wallet> callWalletStockManagementService(Stock stock) {
        walletResourceManagement.updateWalletStatusForNewStock(stock);
        walletResourceManagement.setWallets(BigDecimal.valueOf(200_000));
        return walletResourceManagement.setStockOnEligibleWallets();
    }

}
