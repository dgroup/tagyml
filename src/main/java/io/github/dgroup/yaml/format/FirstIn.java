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

import io.github.dgroup.yaml.Format;
import io.github.dgroup.yaml.YamlFormatException;
import org.cactoos.BiProc;
import org.cactoos.Scalar;
import org.cactoos.Text;
import org.cactoos.func.UncheckedBiProc;
import org.cactoos.iterable.IterableOf;

/**
 * Retrieve the first successfully parsed object in accordance with the format,
 * skipping formats which throw the exception(s).
 *
 * @param <T> The type of object parsed by particular YAML format.
 * @since 0.1.0
 */
public final class FirstIn<T> implements Scalar<T> {

    /**
     * The YAML parsing procedure based on supported formats.
     */
    private final Scalar<T> origin;

    /**
     * Ctor.
     * @param fbk The procedure to handle the exceptions within the
     *  {@link Format#value()}.
     * @param err The exception message in case if there are no any scalars
     *  which able to parse the target YAML file.
     * @param fts The formats to be applied to YAML file in order to parse.
     */
    @SafeVarargs
    public FirstIn(
        final BiProc<String, Exception> fbk, final Text err,
        final Format<T>... fts
    ) {
        this(fbk, err, new IterableOf<>(fts));
    }

    /**
     * Ctor.
     * @param fbk The procedure to handle the exceptions within the
     *  {@link Format#value()}.
     * @param err The exception message in case if there are no any scalars
     *  which able to parse the target YAML file.
     * @param fts The formats to be applied to YAML file in order to parse.
     * @checkstyle IllegalCatchCheck (100 lines)
     */
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public FirstIn(
        final BiProc<String, Exception> fbk, final Text err,
        final Iterable<Format<T>> fts
    ) {
        this.origin = () -> {
            for (final Format<T> format : fts) {
                try {
                    return format.value();
                } catch (final Exception exp) {
                    new UncheckedBiProc<>(fbk).exec(format.version(), exp);
                }
            }
            throw new YamlFormatException(err);
        };
    }

    @Override
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public T value() throws YamlFormatException {
        try {
            return this.origin.value();
        } catch (final Exception cause) {
            throw new YamlFormatException(cause);
        }
    }
}
