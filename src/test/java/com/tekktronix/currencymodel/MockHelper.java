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

import org.mockito.Mockito;

/**
 * Created by Tekktronix on 11/5/2015.
 *
 * Helper class to create mock objects
 */
class MockHelper {
    static ICurrency getMockCurrency(ICurrencySet currencySet, String isoCode, String name) {
        ICurrency currency = Mockito.mock(ICurrency.class);
        Mockito.when(currency.getIsoCode()).thenReturn(isoCode);
        Mockito.when(currency.getName()).thenReturn(name);
        Mockito.when(currency.toString()).thenReturn(isoCode);
        if (currencySet != null) {
            Mockito.when(currencySet.getCurrency(currency.getIsoCode())).thenReturn(currency);
            Mockito.when(currency.getCurrencySet()).thenReturn(currencySet);
        }
        return currency;
    }

    static ICurrencySet getMockCurrencySet()  {
        return Mockito.mock(ICurrencySet.class);
    }

    static void stubExchangeRate(ICurrency thisCurrency, ICurrency targetCurrency, double value) {
        ICurrencySet currencySet = thisCurrency.getCurrencySet();
        Mockito.when(currencySet.getExchangeRate(thisCurrency, targetCurrency)).thenReturn(value);
        Mockito.when(currencySet.getExchangeRate(targetCurrency, thisCurrency)).thenReturn(1/value);

        double rate1 = currencySet.getExchangeRate(thisCurrency, targetCurrency);
        double rate2 = currencySet.getExchangeRate(targetCurrency, thisCurrency);
        Mockito.when(thisCurrency.getExchangeRate(targetCurrency)).thenReturn(rate1);
        Mockito.when(targetCurrency.getExchangeRate(thisCurrency)).thenReturn(rate2);
    }
}
