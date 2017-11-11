package com.ximedes.bitcoin.miniproject.service;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * unit test class for {@link StoreService}
 */
public class StoreServiceTest {

    @Test
    public void testToHexString() {
        StoreService service = new StoreService(null);
        byte[] message = new byte[]{1, 2, 3, 4, (byte) 0xff};
        assertThat(service.toHexString(message), is("01020304ff"));
    }
}