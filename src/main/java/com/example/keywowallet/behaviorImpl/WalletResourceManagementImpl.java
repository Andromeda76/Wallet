package com.example.keywowallet.behaviorImpl;


import com.example.keywowallet.IRepository.WalletRepository;
import com.example.keywowallet.behaviors.StockResourceManagement;
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

    private Stock stock;
    private final WalletRepository walletRepository;

    public WalletResourceManagementImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public void updateWalletStatusForNewStock(Stock stock) {
        this.stock = stock;
    }


    @Override
    public Wallet walletResourceManagement() {
        List<Wallet> wallets = walletRepository.findAll();
        /*
        wallets amount should be upper than200_000
         */
        wallets.forEach(walletRs -> {
                    if (getRequestedPortionOfStockQuantity(walletRs, stock)) {
                        System.out.println("HelloWorld From Wallet : " + stock.getQuantity());
                        walletRs.setStocks(stock);
                        walletRepository.save(walletRs);
                    }
                });

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

}





