package com.ximedes.bitcoin.miniproject.service;

import lombok.extern.log4j.Log4j;
import org.bitcoinj.core.Address;
import org.bitcoinj.core.BlockChain;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Peer;
import org.bitcoinj.core.PeerAddress;
import org.bitcoinj.core.PeerGroup;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.script.ScriptBuilder;
import org.bitcoinj.script.ScriptOpCodes;
import org.bitcoinj.store.BlockStore;
import org.bitcoinj.store.MemoryBlockStore;
import org.bitcoinj.wallet.Wallet;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetAddress;

@Log4j
@Component
public class StoreService {

    NetworkParameters params;
    Address address;
    PeerGroup peerGroup;

    @PostConstruct
    void init() throws Exception {
        log.info("initialisation");
        params = TestNet3Params.get();

        Wallet wallet = new Wallet(params);
        BlockStore blockStore = new MemoryBlockStore(params);
        BlockChain chain = new BlockChain(params, blockStore);
        peerGroup = new PeerGroup(params, chain);
        PeerAddress addr = new PeerAddress(params, InetAddress.getLocalHost());
        peerGroup.addAddress(addr);
        peerGroup.addWallet(wallet);
        peerGroup.start();
        peerGroup.waitForPeers(1).get();
        Peer peer = peerGroup.getConnectedPeers().get(0);


        Address a = wallet.currentReceiveAddress();

        // TODO address
        address = Address.fromBase58(params, "mgZGD31XczMwGW3gLnQekj2zQ6V829xfXF");
    }

    public void store(String arg) {
        Transaction tx = new Transaction(params);
        tx.addOutput(Transaction.MIN_NONDUST_OUTPUT, new ScriptBuilder().op(ScriptOpCodes.OP_RETURN).data(arg.getBytes()).build());
    }

    @PreDestroy
    void destroy() {
        log.info("destroying");
        peerGroup.stopAsync();
    }
}
