/*
 * MIT License
 *
 * Copyright (c) 2018-2019 Yurii Dubinka
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
package io.github.dgroup.yaml.format;

import io.github.dgroup.yaml.YamlFormatException;
import org.cactoos.Scalar;
import org.cactoos.Text;
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
     * The error message in case of illegal YAML format.
     */
    private final Text error;

    /**
     * The supported formats.
     */
    private final Iterable<Scalar<T>> formats;

    /**
     * Ctor.
     * @param formats The formats to be applied to YAML file in order to parse.
     */
    @SafeVarargs
    public FirstIn(final Scalar<T>... formats) {
        this(() -> "The file has unsupported YAML format", formats);
    }

    /**
     * Ctor.
     * @param formats The formats to be applied to YAML file in order to parse.
     * @param error The exception message in case if there are no any scalars
     *  which able to parse the target YAML file.
     * @checkstyle IllegalCatchCheck (15 lines)
     */
    @SafeVarargs
    public FirstIn(final Text error, final Scalar<T>... formats) {
        this.formats = new IterableOf<>(formats);
        this.error = error;
    }

    @Override
    @SuppressWarnings({
        "PMD.EmptyCatchBlock", "PMD.AvoidCatchingGenericException"})
    public T value() throws YamlFormatException {
        for (final Scalar<T> format : this.formats) {
            try {
                return format.value();
            } catch (final Exception exp) {
            }
        }
        throw new YamlFormatException(this.error);
    }
}
