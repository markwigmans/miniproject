package com.ximedes.bitcoin.miniproject.service;

import org.bitcoinj.core.Coin;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * unit test class for {@link FeeService}
 */
public class FeeServiceTest {


    @Test
    public void testFeePerKb() {
        int fee = 30;
        FeeService service = new FixedFeeService(fee);
        assertThat(service.feePerKb(), is(Coin.valueOf(30 * 1000)));
    }
}