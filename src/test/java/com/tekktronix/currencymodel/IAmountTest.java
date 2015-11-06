package com.tekktronix.currencymodel;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Tekktronix on 11/5/2015.
 *
 * Test class for the IAmount interface
 */
public class IAmountTest {
    @Test
    public void testMakeAmount() {
        ICurrency currency = MockHelper.getMockCurrency(null, "USD", "US Dollar");
        IAmount amount = IAmount.makeAmount(currency, 10.0);
        Assert.assertNotNull("Failed to instantiate an object", amount);
        Assert.assertSame("Failed to instantiate an AmountImpl object", AmountImpl.class, amount.getClass());
        Assert.assertSame("Currency instance in amount does not match currency used in the makeAmount call", currency, amount.getCurrency());
        Assert.assertEquals("Denomination in amount does not match currency used in the makeAmount call", 10.0, amount.getDenomination(), 0.00000001);
    }
}
