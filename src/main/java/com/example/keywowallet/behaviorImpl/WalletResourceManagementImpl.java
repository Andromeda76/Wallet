package com.example.keywowallet.behaviorImpl;


import com.example.keywowallet.IService.WalletService;
import com.example.keywowallet.behaviors.WalletResourceManagement;
import com.example.keywowallet.entity.Stock;
import com.example.keywowallet.entity.Wallet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
public class WalletResourceManagementImpl implements WalletResourceManagement {


    private Stock stock;
    private List<Wallet> wallets;
    private final WalletService walletService;
    private final ThreadLocal<Stock> threadLocal;


    public WalletResourceManagementImpl(WalletService walletService) {
        this.walletService = walletService;
        threadLocal = new ThreadLocal<>();
    }

    @Override
    public void updateWalletStatusForNewStock(Stock stock) {
        this.stock = stock;
    }

    @Override
    public void setWallets(BigDecimal walletAmount) {
        this.wallets =  walletService.getWalletsWithWalletAmountFilter(walletAmount);;
    }


    @Override
    @Transactional
    public List<Wallet> setStockOnEligibleWallets() {

        List<Wallet> wallets = new ArrayList<>();
        /*
            Wallets amount should be upper more than 200_000
         */
        if (Objects.nonNull(getWallets()) && !getWallets().isEmpty()){
            getWallets().forEach(walletRs -> {
                    if (getRequestedPortionOfStockQuantity(walletRs, stock)) {
                        System.out.println("HelloWorld From Wallet : " + stock.getQuantity());
                        walletRs.setStocks(stock);
                        Wallet wallet = walletService.insertWallet(walletRs);
                        wallets.add(wallet);
                    }
                });
            }
        threadLocal.remove();
        /**
            We may need to write remote between interfaces
         */
        return wallets;
    }


    private boolean getRequestedPortionOfStockQuantity(Wallet wallet, Stock stock) {
        boolean addingStockPermission = Boolean.FALSE;
        if (Objects.isNull(wallet.getStocks())) {
            BigDecimal appointedStockQuantity =
                    wallet.getWalletAmount().divide(BigDecimal.valueOf(10), 1, RoundingMode.DOWN);
            BigInteger stockCapacity = stock.getQuantity().subtract(appointedStockQuantity.toBigInteger());

            if (stockCapacity.compareTo(BigInteger.ZERO) >= 0) {
                System.out.println("Stock under modification : " + stock.getId());
                stock.setQuantity(stockCapacity);
                wallet.setWalletAmount(wallet.getWalletAmount().subtract(appointedStockQuantity));
                addingStockPermission = Boolean.TRUE;
            }
        }
        return addingStockPermission;
    }

    private List<Wallet> getWallets() {
        return wallets;
    }


}





