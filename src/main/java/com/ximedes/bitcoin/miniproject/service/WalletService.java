package com.ximedes.bitcoin.miniproject.service;


import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.core.InsufficientMoneyException;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.utils.BriefLogFormatter;
import org.bitcoinj.wallet.SendRequest;
import org.bitcoinj.wallet.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;

@Slf4j
@Component
public class WalletService {

    private static final String AppName = "MMBP";

    private final FeeService feeService;

    private final NetworkParameters params;
    private final WalletAppKit bitcoin;
    private final String walletFileName;

    /**
     * Constructor
     */
    @Autowired
    public WalletService(FeeService feeService) {
        // Make log output concise.
        BriefLogFormatter.init();

        this.feeService = feeService;

        params = TestNet3Params.get();
        walletFileName = AppName.replaceAll("[^a-zA-Z0-9.-]", "_") + "-" + params.getPaymentProtocolId();
        bitcoin = new WalletAppKit(params, new File("."), walletFileName) {
            @Override
            protected void onSetupCompleted() {
                // Don't make the user wait for confirmations for now, as the intention is they're sending it
                // their own money!
                bitcoin.wallet().allowSpendingUnconfirmedTransactions();
            }
        };
    }

    /**
     * Create an empty transaction
     */
    public Transaction createTransaction() {
        return new Transaction(params);
    }

    /**
     * Send given transaction. Fee is added separately.
     *
     * @param tx transaction to be send
     * @throws InsufficientMoneyException
     */
    public void send(final Transaction tx) throws InsufficientMoneyException {
        final SendRequest request = SendRequest.forTx(tx);
        // set fee
        request.feePerKb = feeService.feePerKb();

        // Send it to the Bitcoin network
        final Wallet.SendResult result = bitcoin.wallet().sendCoins(SendRequest.forTx(tx));
        log.debug("send() : result; '{}", result.tx);
        log.info("transaction ID: '{}'", result.tx.getHashAsString());
    }

    @PostConstruct
    void init() {
        bitcoin.startAsync();
        bitcoin.awaitRunning();
        log.info("Wallet address is: '{}'", bitcoin.wallet().currentReceiveAddress());
        log.info("Wallet balance is: {} Satoshis (at least 1 confirmed)", bitcoin.wallet().getBalance());
    }

    @PreDestroy
    void stop() throws InterruptedException {
        bitcoin.stopAsync();
        bitcoin.awaitTerminated();
    }
}
