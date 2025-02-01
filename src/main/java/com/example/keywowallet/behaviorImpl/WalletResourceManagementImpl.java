package com.example.keywowallet.behaviorImpl;


import com.example.keywowallet.IService.WalletService;
import com.example.keywowallet.behaviors.WalletResourceManagement;
import com.example.keywowallet.entity.Stock;
import com.example.keywowallet.entity.Wallet;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;


@Service
public class WalletResourceManagementImpl implements WalletResourceManagement {


    private final WalletService walletService;
    private final ThreadLocal<Stock> threadLocal;
    private List<Wallet> wallets;


    public WalletResourceManagementImpl(WalletService walletService) {
        this.walletService = walletService;
        threadLocal = new ThreadLocal<>();
    }

    @Override
    public void updateWalletStatusForNewStock(Stock stock) {
        threadLocal.set(stock);
    }


    @Override
    public Wallet walletResourceManagement() {
        Stock stock = threadLocal.get();

        /*
        wallets amount should be upper than200_000
         */

        
        if (Objects.nonNull(getWallets()) && getWallets().size() != 0){
            getWallets().forEach(walletRs -> {
                    if (getRequestedPortionOfStockQuantity(walletRs, stock)) {
                        System.out.println("HelloWorld From Wallet : " + stock.getQuantity());
                        walletRs.setStocks(stock);
                        walletService.insertWallet(walletRs);
                    }
                });
        threadLocal.remove();
        }
        /**
        we may need to write remote between interfaces
         */
        return null;
    }


    private boolean getRequestedPortionOfStockQuantity(Wallet wallet, Stock stock) {
        boolean addingStockPermission = Boolean.FALSE;
        BigDecimal appointedStockQuantity =
                wallet.getWalletAmount().divide(BigDecimal.valueOf(10), 1, RoundingMode.DOWN);
        BigInteger stockCapacity = stock.getQuantity().subtract(appointedStockQuantity.toBigInteger());

        if (stockCapacity.compareTo(BigInteger.ZERO) >= 0) {
            stock.setQuantity(stockCapacity);
            wallet.setWalletAmount(wallet.getWalletAmount().subtract(appointedStockQuantity));
            addingStockPermission = Boolean.TRUE;
        }
        return addingStockPermission;
    }

    public List<Wallet> getWallets() {
        return wallets;
    }

    public void setWallets() {
        this.wallets = walletService.getWalletsWithWalletAmountFilter(BigDecimal.valueOf(200_000));;
    }
}





