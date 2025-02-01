package com.example.keywowallet.IService;


import com.example.keywowallet.IRepository.StockRepository;
import com.example.keywowallet.behaviors.StockResourceManagement;
import com.example.keywowallet.entity.Stock;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


@Service
public class StockService {


    private final StockResourceManagement stockResourceManagement;

    public StockService(StockResourceManagement stockResourceManagement) {
        this.stockResourceManagement = stockResourceManagement;
        }


    public List<Stock> insertStocks(List<Stock> stockList) {
        return setStocks(stockList);
    }


    private List<Stock> setStocks(List<Stock> stocks) {
        try (var exc = Executors.newVirtualThreadPerTaskExecutor()) {
            for (Stock stock : stocks) {
                    exc.submit(() -> {
                        stockResourceManagement.stockResourceManagement(stock);
                    });
                }
            exc.shutdown();
        }
        return stocks;
    }

}
