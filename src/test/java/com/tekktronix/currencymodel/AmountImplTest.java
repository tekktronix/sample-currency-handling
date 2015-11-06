/*
 *
 * The MIT License (MIT)
 *
 * Copyright (c)  ${YEAR} Tekktronix
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

import org.junit.*;
import org.mockito.*;

/**
 * Created by Tekktronix on 10/24/2015.
 *
 * Test class for AmountImpl
 */
public class AmountImplTest {
    @Test(expected=IllegalArgumentException.class)
    public void testConstructor_NullCurrency_ThrowsException() {
        new AmountImpl(null, 10);
    }

    @Test
    public void testConstructor() {
        ICurrency currency = MockHelper.getMockCurrency(null, "USD", "US Dollar");
        AmountImpl amount = new AmountImpl(currency, 10.0);
        Assert.assertEquals("Amount's denomination was not set correctly", 10.0, amount.getDenomination(), .000001);
        Assert.assertEquals("Amounts currency was not set correctly", currency, amount.getCurrency());
    }

    @Test(expected=IllegalArgumentException.class)
    public void testGetDenominationInCurrency_NullTargetCurrency_ThrowsException() {
        ICurrency currency = MockHelper.getMockCurrency(null, "USD", "US Dollar");
        AmountImpl amount = new AmountImpl(currency, 10.0);
        amount.getDenominationInCurrency(null);
    }

    @Test
    public void testGetDenominationInCurrency_TargetCurrencySameAsAmountCurrency_ReturnsAmountDenomination() {
        ICurrencySet currencySet = MockHelper.getMockCurrencySet();
        ICurrency currency = MockHelper.getMockCurrency(currencySet, "USD", "US Dollar");

        // Ensure GetExchangeRate is never called when both amounts have the same currency
        Mockito.verify(currencySet, Mockito.never()).getExchangeRate(currency, currency);
        AmountImpl amount = new AmountImpl(currency, 10.0);
        Assert.assertEquals("Incorrect denomination", amount.getDenomination(), amount.getDenominationInCurrency(currency), 0.001);
    }

    @Test
    public void testGetDenominationInCurrency() {
        ICurrencySet currencySet = MockHelper.getMockCurrencySet();
        ICurrency usd = MockHelper.getMockCurrency(currencySet, "USD", "US Dollar");
        ICurrency gbp = MockHelper.getMockCurrency(currencySet, "GBP", "Great Britain Pound");
        MockHelper.stubExchangeRate(usd, gbp, 2.0);

        AmountImpl amount = new AmountImpl(usd, 10.0);
         Assert.assertEquals("Incorrect denomination", 20.0, amount.getDenominationInCurrency(gbp), 0.001);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testAdd_NullOtherAmount_ThrowsException() {
        ICurrency currency = MockHelper.getMockCurrency(null, "USD", "US Dollar");
        AmountImpl amount = new AmountImpl(currency, 10.0);
        amount.add(null);
    }

    @Test
    public void testAdd_AmountsWithSameCurrency() {
        ICurrency currency = MockHelper.getMockCurrency(null, "USD", "US Dollar");

        // test with both amounts having positive denominations
        AmountImpl amount1 = new AmountImpl(currency, 10.0);
        AmountImpl amount2 = new AmountImpl(currency, 2.0);
        IAmount result =  amount1.add(amount2);
        Assert.assertSame("Unexpected amount currency", currency, result.getCurrency());
        Assert.assertEquals("Incorrect result after addition", 12.0, result.getDenomination(), 0.00000001);

        // test with first amount having negative denomination
        amount1 = new AmountImpl(currency, -10.0);
        amount2 = new AmountImpl(currency, 2.0);
        result =  amount1.add(amount2);
        Assert.assertSame("Unexpected amount currency", currency, result.getCurrency());
        Assert.assertEquals("Incorrect result after addition", -8.0, result.getDenomination(), 0.00000001);

        // test with second amount having negative denomination
        amount1 = new AmountImpl(currency, 10.0);
        amount2 = new AmountImpl(currency, -2.0);
        result =  amount1.add(amount2);
        Assert.assertSame("Unexpected amount currency", currency, result.getCurrency());
        Assert.assertEquals("Incorrect result after addition", 8.0, result.getDenomination(), 0.00000001);

        // test with both amounts having negative denomination
        amount1 = new AmountImpl(currency, -10.0);
        amount2 = new AmountImpl(currency, -2.0);
        result =  amount1.add(amount2);
        Assert.assertSame("Unexpected amount currency", currency, result.getCurrency());
        Assert.assertEquals("Incorrect result after addition", -12.0, result.getDenomination(), 0.00000001);
    }

    @Test
    public void testAdd_AmountsWithDifferentCurrencies(){
        ICurrencySet currencySet = MockHelper.getMockCurrencySet();
        ICurrency usd = MockHelper.getMockCurrency(currencySet, "USD", "US Dollar");
        ICurrency gbp = MockHelper.getMockCurrency(currencySet, "GBP", "Great Britain Pound");
        MockHelper.stubExchangeRate(usd, gbp, 2.0);

        // test with both amounts having positive denominations
        AmountImpl amount1 = new AmountImpl(usd, 10.0);
        AmountImpl amount2 = new AmountImpl(gbp, 2.0);
        IAmount result =  amount1.add(amount2);
        Assert.assertSame("Unexpected amount currency", usd, result.getCurrency());
        Assert.assertEquals("Incorrect result after addition", 11.0, result.getDenomination(), 0.00000001);

        // test with first amount having negative denomination
        amount1 = new AmountImpl(usd, -10.0);
        amount2 = new AmountImpl(gbp, 2.0);
        result =  amount1.add(amount2);
        Assert.assertSame("Unexpected amount currency", usd, result.getCurrency());
        Assert.assertEquals("Incorrect result after addition", -9.0, result.getDenomination(), 0.00000001);

        // test with second amount having negative denomination
        amount1 = new AmountImpl(usd, 10.0);
        amount2 = new AmountImpl(gbp, -2.0);
        result =  amount1.add(amount2);
        Assert.assertSame("Unexpected amount currency", usd, result.getCurrency());
        Assert.assertEquals("Incorrect result after addition", 9.0, result.getDenomination(), 0.00000001);

        // test with both amounts having negative denomination
        amount1 = new AmountImpl(usd, -10.0);
        amount2 = new AmountImpl(gbp, -2.0);
        result =  amount1.add(amount2);
        Assert.assertSame("Unexpected amount currency", usd, result.getCurrency());
        Assert.assertEquals("Incorrect result after addition", -11.0, result.getDenomination(), 0.00000001);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testSubtract_NullOtherAmount_ThrowsException() {
        ICurrency currency = MockHelper.getMockCurrency(null, "USD", "US Dollar");
        AmountImpl amount = new AmountImpl(currency, 10.0);
        amount.subtract(null);
    }

    @Test
    public void testSubtract_AmountsWithSameCurrency() {
        ICurrency currency = MockHelper.getMockCurrency(null, "USD", "US Dollar");

        // test with both amounts having positive denominations
        AmountImpl amount1 = new AmountImpl(currency, 10.0);
        AmountImpl amount2 = new AmountImpl(currency, 2.0);
        IAmount result =  amount1.subtract(amount2);
        Assert.assertSame("Unexpected amount currency", currency, result.getCurrency());
        Assert.assertEquals("Incorrect denomination after subtraction", 8.0, result.getDenomination(), 0.00000001);

        // test with first amount having negative denomination
        amount1 = new AmountImpl(currency, -10.0);
        amount2 = new AmountImpl(currency, 2.0);
        result =  amount1.subtract(amount2);
        Assert.assertSame("Unexpected amount currency", currency, result.getCurrency());
        Assert.assertEquals("Incorrect denomination after subtraction", -12.0, result.getDenomination(), 0.00000001);

        // test with second amount having negative denomination
        amount1 = new AmountImpl(currency, 10.0);
        amount2 = new AmountImpl(currency, -2.0);
        result =  amount1.subtract(amount2);
        Assert.assertSame("Unexpected amount currency", currency, result.getCurrency());
        Assert.assertEquals("Incorrect denomination after subtraction", 12.0, result.getDenomination(), 0.00000001);

        // test with both amounts having negative denomination
        amount1 = new AmountImpl(currency, -10.0);
        amount2 = new AmountImpl(currency, -2.0);
        result =  amount1.subtract(amount2);
        Assert.assertSame("Unexpected amount currency", currency, result.getCurrency());
        Assert.assertEquals("Incorrect denomination after subtraction", -8.0, result.getDenomination(), 0.00000001);
    }

    @Test
    public void testSubtract_AmountsWithDifferentCurrencies(){
        ICurrencySet currencySet = MockHelper.getMockCurrencySet();
        ICurrency usd = MockHelper.getMockCurrency(currencySet, "USD", "US Dollar");
        ICurrency gbp = MockHelper.getMockCurrency(currencySet, "GBP", "Great Britain Pound");
        MockHelper.stubExchangeRate(usd, gbp, 2.0);

        // test with both amounts having positive denominations
        AmountImpl amount1 = new AmountImpl(usd, 10.0);
        AmountImpl amount2 = new AmountImpl(gbp, 2.0);
        IAmount result =  amount1.subtract(amount2);
        Assert.assertSame("Unexpected amount currency", usd, result.getCurrency());
        Assert.assertEquals("Incorrect denomination after subtraction", 9.0, result.getDenomination(), 0.00000001);

        // test with first amount having negative denomination
        amount1 = new AmountImpl(usd, -10.0);
        amount2 = new AmountImpl(gbp, 2.0);
        result =  amount1.subtract(amount2);
        Assert.assertSame("Unexpected amount currency", usd, result.getCurrency());
        Assert.assertEquals("Incorrect denomination after subtraction", -11.0, result.getDenomination(), 0.00000001);

        // test with second amount having negative denomination
        amount1 = new AmountImpl(usd, 10.0);
        amount2 = new AmountImpl(gbp, -2.0);
        result =  amount1.subtract(amount2);
        Assert.assertSame("Unexpected amount currency", usd, result.getCurrency());
        Assert.assertEquals("Incorrect denomination after subtraction", 11.0, result.getDenomination(), 0.00000001);

        // test with both amounts having negative denomination
        amount1 = new AmountImpl(usd, -10.0);
        amount2 = new AmountImpl(gbp, -2.0);
        result =  amount1.subtract(amount2);
        Assert.assertSame("Unexpected amount currency", usd, result.getCurrency());
        Assert.assertEquals("Incorrect denomination after subtraction", -9.0, result.getDenomination(), 0.00000001);
    }

    @Test
    public void testScalarMultiply() {
        ICurrency currency = MockHelper.getMockCurrency(null, "USD", "US Dollar");
        AmountImpl amount = new AmountImpl(currency, 10.0);
        IAmount result = amount.scalarMultiply(2.0);
        Assert.assertSame("Unexpected amount currency", currency, result.getCurrency());
        Assert.assertEquals("Incorrect denomination after multiplication", 20.0, result.getDenomination(), 0.00000001);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testScalarDivide_ZeroDivisor_ThrowsException() {
        ICurrency currency = MockHelper.getMockCurrency(null, "USD", "US Dollar");
        AmountImpl amount = new AmountImpl(currency, 10.0);
        amount.scalarDivide(0);
    }

    @Test
    public void testScalarDivide() {
        ICurrency currency = MockHelper.getMockCurrency(null, "USD", "US Dollar");
        AmountImpl amount = new AmountImpl(currency, 10.0);
        IAmount result = amount.scalarDivide(2.0);
        Assert.assertSame("Unexpected amount currency", currency, result.getCurrency());
        Assert.assertEquals("Incorrect denomination after division", 5.0, result.getDenomination(), 0.00000001);
    }


    @Test(expected=IllegalArgumentException.class)
    public void testConvertToCurrency_NullTargetCurrency_ThrowsException() {
        ICurrency currency = MockHelper.getMockCurrency(null, "USD", "US Dollar");
        AmountImpl amount = new AmountImpl(currency, 10.0);
        amount.convertToCurrency(null);
    }

    @Test
    public void testConvertToCurrency() {
        ICurrencySet currencySet = MockHelper.getMockCurrencySet();
        ICurrency usd = MockHelper.getMockCurrency(currencySet, "USD", "US Dollar");
        ICurrency gbp = MockHelper.getMockCurrency(currencySet, "GBP", "Great Britain Pound");
        MockHelper.stubExchangeRate(usd, gbp, 2.0);

        AmountImpl amount = new AmountImpl(usd, 10.0);
        IAmount result = amount.convertToCurrency(gbp);
        Assert.assertSame("Unexpected amount currency", gbp, result.getCurrency());
        Assert.assertEquals("Incorrect denomination after conversion to GBP", 20.0, result.getDenomination(), 0.00000001);
    }

    @Test
    public void testEquals_SameInstance_ReturnsTrue() {
        ICurrency currency = MockHelper.getMockCurrency(null, "USD", "US Dollar");
        AmountImpl amount = new AmountImpl(currency, 10.0);
        Assert.assertTrue("Equals check with the same instance should return true", amount.equals(amount));
    }

    @Test
    public void testEquals_NullObject_ReturnsFalse() {
        ICurrency currency = MockHelper.getMockCurrency(null, "USD", "US Dollar");
        AmountImpl amount = new AmountImpl(currency, 10.0);
        Assert.assertFalse("Equals check with a null object should return false", amount.equals(null));
    }

    @Test
    public void testEquals_ObjectOfDifferentClass_ReturnsFalse() {
        ICurrency currency = MockHelper.getMockCurrency(null, "USD", "US Dollar");
        AmountImpl amount = new AmountImpl(currency, 10.0);
        Assert.assertFalse("Equals check with an object of a different class should return false", amount.equals(new Object()));
    }

    @Test
    public void testEquals_AmountWithSameCurrencyAndDenomination_ReturnsTrue() {
        ICurrency currency = MockHelper.getMockCurrency(null, "USD", "US Dollar");
        AmountImpl amount1 = new AmountImpl(currency, 10.0);
        AmountImpl amount2 = new AmountImpl(currency, 10.0);
        Assert.assertTrue("Equals check with an amount with same currency and denomination should return true", amount1.equals(amount2));
    }

    @Test
    public void testEquals_AmountWithSameCurrencyAndDifferentDenomination_ReturnsFalse() {
        ICurrency currency = MockHelper.getMockCurrency(null, "USD", "US Dollar");
        AmountImpl amount1 = new AmountImpl(currency, 10.0);
        AmountImpl amount2 = new AmountImpl(currency, 20.0);
        Assert.assertFalse("Equals check with an amount with same currency but different denomination should return false", amount1.equals(amount2));
    }

    @Test
    public void testEquals_AmountWithDifferentCurrencyAndSameDenomination_ReturnsTrue() {
        ICurrency currency1 = MockHelper.getMockCurrency(null, "USD", "US Dollar");
        ICurrency currency2 = MockHelper.getMockCurrency(null, "GBP", "Great Britain Pound");
        AmountImpl amount1 = new AmountImpl(currency1, 10.0);
        AmountImpl amount2 = new AmountImpl(currency2, 10.0);
        Assert.assertFalse("Equals check with an amount with different currency but same denomination should return false", amount1.equals(amount2));
    }

    @Test
    public void testEquals_AmountWithDifferentCurrencyInstanceAndSameDenomination_ReturnsTrue() {
        ICurrency currency1 = MockHelper.getMockCurrency(null, "USD", "US Dollar");
        ICurrency currency2 = MockHelper.getMockCurrency(null, "USD", "US Dollar"); // ISO Code etc is the same but different instance
        AmountImpl amount1 = new AmountImpl(currency1, 10.0);
        AmountImpl amount2 = new AmountImpl(currency2, 10.0);
        Assert.assertFalse("Equals check with an amount with different currency but same denomination should return false", amount1.equals(amount2));
    }

    @Test
    public void testEquals_AmountWithDifferentCurrencyAndDifferentDenomination_ReturnsTrue() {
        ICurrency currency1 = MockHelper.getMockCurrency(null, "USD", "US Dollar");
        ICurrency currency2 = MockHelper.getMockCurrency(null, "GBP", "Great Britain Pound");
        AmountImpl amount1 = new AmountImpl(currency1, 10.0);
        AmountImpl amount2 = new AmountImpl(currency2, 20.0);
        Assert.assertFalse("Equals check with an amount with different currency but same denomination should return false", amount1.equals(amount2));
    }

    @Test
    public void testHashCode_EqualsAmounts_ReturnsEqualHashCode() {
        ICurrency currency1 = MockHelper.getMockCurrency(null, "USD", "US Dollar");

        AmountImpl amount1 = new AmountImpl(currency1, 10.0);
        AmountImpl amount2 = new AmountImpl(currency1, 10.0);
        Assert.assertEquals("Expecting the same hashcode for the two amounts", amount1.hashCode(), amount2.hashCode());

        amount1 = new AmountImpl(currency1, -10.0);
        amount2 = new AmountImpl(currency1, -10.0);
        Assert.assertEquals("Expecting the same hashcode for the two amounts", amount1.hashCode(), amount2.hashCode());

        amount1 = new AmountImpl(currency1, 0.0);
        amount2 = new AmountImpl(currency1, 0.0);
        Assert.assertEquals("Expecting the same hashcode for the two amounts", amount1.hashCode(), amount2.hashCode());

        ICurrency currency2 = MockHelper.getMockCurrency(null, "USD", "US Dollar");
        amount1 = new AmountImpl(currency2, 10.0);
        amount2 = new AmountImpl(currency2, 10.0);
        Assert.assertEquals("Expecting the same hashcode for the two amounts", amount1.hashCode(), amount2.hashCode());

        amount1 = new AmountImpl(currency2, -10.0);
        amount2 = new AmountImpl(currency2, -10.0);
        Assert.assertEquals("Expecting the same hashcode for the two amounts", amount1.hashCode(), amount2.hashCode());

        amount1 = new AmountImpl(currency2, 0.0);
        amount2 = new AmountImpl(currency2, 0.0);
        Assert.assertEquals("Expecting the same hashcode for the two amounts", amount1.hashCode(), amount2.hashCode());
    }

    @Test
    public void testHashCode_UnequalAmounts_ReturnsDifferentHashCode() {
        ICurrency currency1 = MockHelper.getMockCurrency(null, "USD", "US Dollar");

        AmountImpl amount1 = new AmountImpl(currency1, 10.0);
        AmountImpl amount2 = new AmountImpl(currency1, 20.0);
        Assert.assertNotEquals("Expecting the different hashcodes for the two amounts", amount1.hashCode(), amount2.hashCode());

        amount1 = new AmountImpl(currency1, 10.0);
        amount2 = new AmountImpl(currency1, 0);
        Assert.assertNotEquals("Expecting different hashcodes for the two amounts", amount1.hashCode(), amount2.hashCode());

        amount1 = new AmountImpl(currency1, 10.0);
        amount2 = new AmountImpl(currency1, -10.0);
        Assert.assertNotEquals("Expecting different hashcodes for the two amounts", amount1.hashCode(), amount2.hashCode());

        ICurrency currency2 = MockHelper.getMockCurrency(null, "USD", "US Dollar");
        amount1 = new AmountImpl(currency1, 10.0);
        amount2 = new AmountImpl(currency2, 10.0);
        Assert.assertNotEquals("Expecting the different hashcodes for the two amounts", amount1.hashCode(), amount2.hashCode());

        amount1 = new AmountImpl(currency1, 10.0);
        amount2 = new AmountImpl(currency2, 0.0);
        Assert.assertNotEquals("Expecting the different hashcodes for the two amounts", amount1.hashCode(), amount2.hashCode());

        amount1 = new AmountImpl(currency1, 10.0);
        amount2 = new AmountImpl(currency2, -10.0);
        Assert.assertNotEquals("Expecting the different hashcodes for the two amounts", amount1.hashCode(), amount2.hashCode());
    }

    @Test
    public void testToString() {
        ICurrency currency = MockHelper.getMockCurrency(null, "USD", "US Dollar");
        AmountImpl amount = new AmountImpl(currency, 10.0);
        Assert.assertEquals("10.0 USD", amount.toString());
    }
}