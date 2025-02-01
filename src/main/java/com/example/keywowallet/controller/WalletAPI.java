package com.example.keywowallet.controller;


import com.example.keywowallet.IService.WalletService;
import com.example.keywowallet.entity.Wallet;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;


@RestController
@RequestMapping("/WalletAPI")
public class WalletAPI {

    private final WalletService walletService;

    public WalletAPI(WalletService walletService) {
        this.walletService = walletService;
    }


    @PostMapping("/insertWallet")
    public ResponseEntity<Wallet> insertWallet(@RequestBody Wallet wallet) {
        return ResponseEntity.ok(walletService.insertWallet(wallet));
    }

    @GetMapping("GetWallet/walletAmountGreaterThan/{walletAmount}")
    public ResponseEntity<List<Wallet>> getWalletAmountGreaterThan(@PathVariable("walletAmount") BigDecimal walletAmount) {
        return ResponseEntity.ok(walletService.getWalletsWithWalletAmountFilter(walletAmount));
    }

}
