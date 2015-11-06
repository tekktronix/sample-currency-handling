/*
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Tekktronix
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 */

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
