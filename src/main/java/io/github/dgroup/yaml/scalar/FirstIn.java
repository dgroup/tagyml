/*
 * MIT License
 *
 * Copyright (c) 2018 Yurii Dubinka
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom
 * the Software is  furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */
package io.github.dgroup.yaml.scalar;

import java.util.NoSuchElementException;
import org.cactoos.Scalar;
import org.cactoos.iterable.IterableOf;

/**
 * Retrieve the first scalars value, skipping scalars which throw the
 *  exception(s).
 *
 * @param <T> The type of scalar.
 * @since 0.1.0
 */
public final class FirstIn<T> implements Scalar<T> {

    /**
     * Origin.
     */
    private final Scalar<T> slr;

    /**
     * Ctor.
     * @param slrs The scalars to be executed.
     */
    @SafeVarargs
    public FirstIn(final Scalar<T>... slrs) {
        this(
            new IterableOf<>(slrs),
            () -> {
                throw new NoSuchElementException(
                    "No scalar(s) which gives the target object"
                );
            }
        );
    }

    /**
     * Ctor.
     * @param slrs The scalars to be executed.
     * @param alter The alternative in case if no scalars (without exceptions)
     *  found.
     * @checkstyle ReturnCountCheck (20 lines)
     * @checkstyle IllegalCatchCheck (15 lines)
     */
    @SuppressWarnings({
        "PMD.EmptyCatchBlock", "PMD.AvoidCatchingGenericException"})
    public FirstIn(final Iterable<Scalar<T>> slrs, final Scalar<T> alter) {
        this.slr = () -> {
            for (final Scalar<T> scalar : slrs) {
                try {
                    return scalar.value();
                } catch (final Exception exp) {
                }
            }
            return alter.value();
        };
    }

    @Override
    public T value() throws Exception {
        return this.slr.value();
    }
}
