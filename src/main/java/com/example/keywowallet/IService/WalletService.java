package com.example.keywowallet.IService;


import java.math.BigDecimal;
import java.util.List;

import com.example.keywowallet.IRepository.WalletRepository;
import com.example.keywowallet.entity.Wallet;
import org.springframework.stereotype.Service;


@Service
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }


    public Wallet insertWallet(Wallet wallet) {
        return walletRepository.save(wallet);
    }

    public List<Wallet> getWalletsWithWalletAmountFilter(BigDecimal balance) {
        return walletRepository.findAllByWalletAmountGreaterThanEqual(balance);
    }

}
