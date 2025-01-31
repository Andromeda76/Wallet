package com.example.keywowallet.controller;


import java.util.List;

import com.example.keywowallet.IService.StockService;
import com.example.keywowallet.entity.Stock;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/StockAPI")
public class StockAPI {


    private final StockService stockService;

    public StockAPI(StockService stockService) {
        this.stockService = stockService;
    }


    @PostMapping("/insertStocks")
    public ResponseEntity<List<Stock>> insertStocks(@RequestBody List<Stock> stocks) {
        return ResponseEntity.ok(stockService.insertStocks(stocks));
    }

}
