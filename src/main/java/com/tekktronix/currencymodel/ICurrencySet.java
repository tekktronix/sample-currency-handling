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

/**
 * Created by Tekktronix on 10/25/2015.
 *
 * Represents a currency set. Each currency instance should be uniquely identified using
 * its ISO code.
 *
 * The foreign exchange rate provider is also closely coupled with this interface.
 */
public interface ICurrencySet {
    /**
     * Gets the currency instance from the ISO code of the currency. The ISO code should
     * be unique for each currency in this set.
     * @param isoCode The iso code for the required currency.
     * @return The currency with the matching ISO code, if found. otherwise, null.
     */
    ICurrency getCurrency(String isoCode);

    /**
     * Gets the foreign exchange rates between the two currencies specified by their ISO codes.
     * @param sourceCurrencyIsoCode The ISO code of the source currency.
     * @param targetCurrencyIsoCode The ISO code of the target currency.
     * @return The exchange rates between the two specified currencies.
     * @exception java.lang.IllegalArgumentException If the source or target
     * currency ISO codes are invalid or if the exchange rates between the corresponding
     * currencies are not available in the exchange rate provider instance.
     */
    double getExchangeRate(String sourceCurrencyIsoCode, String targetCurrencyIsoCode);

    /**
     * Gets the foreign exchange rates between the two currencies specified by their ISO codes.
     * @param sourceCurrency The source currency.
     * @param targetCurrency The target currency.
     * @return The exchange rates between the two specified currencies.
     * @exception java.lang.IllegalArgumentException If the source or target
     * currency ISO codes are invalid or if the exchange rates between the corresponding
     * currencies are not available in the exchange rate provider instance.
     */
    default double getExchangeRate(ICurrency sourceCurrency, ICurrency targetCurrency) {
        return getExchangeRate(sourceCurrency.getIsoCode(), targetCurrency.getIsoCode());
    }
}
