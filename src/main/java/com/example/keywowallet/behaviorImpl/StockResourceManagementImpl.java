package com.example.keywowallet.behaviorImpl;


import java.math.BigInteger;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

import com.example.keywowallet.IRepository.StockRepository;
import com.example.keywowallet.behaviors.StockResourceManagement;
import com.example.keywowallet.behaviors.WalletResourceManagement;
import com.example.keywowallet.entity.Stock;
import org.springframework.stereotype.Service;


@Service
public class StockResourceManagementImpl implements StockResourceManagement {

    private final StockRepository stockRepository;
    private final BlockingQueue<Stock> stockBlockingQueue;
    private final WalletResourceManagementWrapper walletResourceManagementWrapper;


    public StockResourceManagementImpl(StockRepository stockRepository,
                                       WalletResourceManagementWrapper walletResourceManagementWrapper) {

        this.stockRepository = stockRepository;
        this.stockBlockingQueue = new LinkedBlockingQueue<>(6);
        this.walletResourceManagementWrapper = walletResourceManagementWrapper;
    }


    @Override
    public Stock stockResourceManagement(Stock stock) {
        setOnStockQueue(stock);
        System.out.println("what the hell happened ??");
        return stockBlockingQueue.peek();
    }


    public Stock getStockQueue(Stock stock) {
        return stockBlockingQueue.contains(stock) ? stock : setOnStockQueue(stock).poll();
    }


    public BlockingQueue<Stock> setOnStockQueue(Stock stock)  {
        AtomicBoolean isAddedToQueue = new AtomicBoolean(false);
        CompletableFuture.supplyAsync(() ->
                        addStockToBlockingQueue(stockBlockingQueue, stock, isAddedToQueue))
                .thenComposeAsync(added -> {
                    if (!added) {
                    System.out.println("alarm to your post it should not ok to accept new stock node");
                     return offerStockToQueueWithTurningCheckedToUncheckedException(stockBlockingQueue, stock, isAddedToQueue);
                }
                    return CompletableFuture.completedFuture(Boolean.TRUE);
            }).thenComposeAsync(futureResponse -> {
                 insertStockAndUpdateWalletBasedOnStock(stockBlockingQueue, isAddedToQueue, walletResourceManagementWrapper);
            return CompletableFuture.completedFuture(futureResponse);
            }).exceptionallyAsync(ex -> {
                    throw new RuntimeException(ex);
                });

        return stockBlockingQueue;
    }


    private Boolean addStockToBlockingQueue(BlockingQueue<Stock> stocks ,Stock stock, AtomicBoolean isAdded) {
        isAdded.set(Boolean.FALSE);
        if (stocks.remainingCapacity() > 0) {
            stocks.add(stock);
            isAdded.set(Boolean.TRUE);
        }
        return isAdded.get();
    }


    private CompletableFuture<Boolean> offerStockToQueueWithTurningCheckedToUncheckedException(
            BlockingQueue<Stock> stocks, Stock stock, AtomicBoolean isAdded) {

        CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("offerStockWithTurningCheckedToUncheckedException : " + stock.getQuantity());
                isAdded.compareAndSet(Boolean.TRUE, stocks.offer(stock, 1, TimeUnit.MINUTES));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return isAdded;
        });
        return CompletableFuture.completedFuture(isAdded.get());
    }


    private void insertStockAndUpdateWalletBasedOnStock(BlockingQueue<Stock> stocks, AtomicBoolean isAddedToQueue,
                                                        WalletResourceManagementWrapper walletResourceManagementWrapper) {

        if (isAddedToQueue.get() && !stocks.isEmpty()) {
                stocks.forEach(inserted -> {
                    inserted = stockRepository.saveAndFlush(inserted);
                    walletResourceManagementWrapper.callWalletStockManagementService(inserted);
                    if (Objects.equals(inserted.getQuantity(), BigInteger.ZERO)){
                        boolean b = stockBlockingQueue.remove(inserted);
                        System.out.println("Does any stock removed : " + b);
                    }
                });
        }
    }

}
