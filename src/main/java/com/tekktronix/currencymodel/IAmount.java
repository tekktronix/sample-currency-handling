/**
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
 * Created by Tekktronix on 10/24/2015.
 *
 */
public interface IAmount {
    /**
     * Gets the denomination of the amount.
     * @return The denomination.
     */
    double getDenomination();

    /**
     * Gets the currency of the amount.
     * @return The currency.
     */
    ICurrency getCurrency();

    /**
     * Gets the denomination of this amount in the target currency after applying the
     * foreign exchange rate between the currency of this amount and the target currency.
     * @param targetCurrency The target currency.
     * @return The denomination in the target currency.
     */
    double getDenominationInCurrency(ICurrency targetCurrency);

    /**
     * Adds two amounts together in the currency of the left hand side (LHS) amount.
     * @param other The amount to be added to the LHS amount.
     * @return A new Amount instance in the LHS amount's currency.
     */
    IAmount add(IAmount other);

    /**
     * Subtracts the right hand side amount from the left hand side (LHS) amount in the LHS amount's currency.
     * @param other The amount to be subtracted from the LHS amount.
     * @return A new Amount instance in the LHS Amount's currency.
     */
    IAmount subtract(IAmount other);

    /**
     * Performs a scalar multiplication operation on the amount.
     * @param multiplicand The multiplicand.
     * @return The resulting amount.
     */
    IAmount scalarMultiply(double multiplicand);

    /**
     * Performs a scalar division on the amount.
     * @param divisor The divisor.
     * @return The resulting amount.
     */
    IAmount scalarDivide(double divisor);

    /**
     * Converts the Amount to the specified currency after applying the appropriate foreign exchange rate.
     * @param targetCurrency The target currency.
     * @return The Amount converted to the specified currency.
     */
    IAmount convertToCurrency(ICurrency targetCurrency);

    /**
     * Instantiates an amount given the denomination and currency.
     * @param currency The amount's currency.
     * @param denomination The amount's denomination.
     * @return A newly instantiated amount.
     */
    static IAmount makeAmount(ICurrency currency, double denomination) {
        return new AmountImpl(currency, denomination);
    }
}