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
package io.github.dgroup.tagyml.format;

import io.github.dgroup.tagyml.Format;
import io.github.dgroup.tagyml.YamlFormatException;
import org.cactoos.Scalar;

/**
 * Envelope of {@link Format}.
 *
 * @param <T> The type of object to be built from YAML file.
 * @since 0.3.0
 */
public class Envelope<T> implements Format<T> {

    /**
     * The version of current YAML format.
     */
    private final String vrsn;

    /**
     * The object to be build from YAML file.
     */
    private final Scalar<T> obj;

    /**
     * Ctor.
     * @param vrsn The version of current YAML format.
     * @param obj The object to be built from YAML file.
     */
    public Envelope(final String vrsn, final Scalar<T> obj) {
        this.vrsn = vrsn;
        this.obj = obj;
    }

    @Override
    public final String version() {
        return this.vrsn;
    }

    @Override
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public final T value() throws YamlFormatException {
        try {
            return this.obj.value();
            // @checkstyle IllegalCatchCheck (3 lines)
        } catch (final Exception exp) {
            throw new YamlFormatException(exp);
        }
    }
}
