package com.example.keywowallet.IRepository;

import com.example.keywowallet.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;


public interface WalletRepository extends JpaRepository<Wallet, Integer> {


    List<Wallet> findAllByWalletAmountGreaterThanEqual(BigDecimal walletAmount);
}
