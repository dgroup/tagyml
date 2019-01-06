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
import org.cactoos.Scalar;

/**
 * YAML file format.
 *
 * @param <T> The type of object to be built from YAML file.
 * @since 0.2.0
 */
public final class FormatOf<T> implements Format<T> {

    /**
     * The version of current YAML format.
     */
    private final String vrsn;

    /**
     * The object to be build from YAML file.
     */
    private final Scalar<T> frmt;

    /**
     * Ctor.
     * @param vrsn The version of current YAML format.
     * @param frmt The object to be built from YAML file.
     */
    public FormatOf(final String vrsn, final Scalar<T> frmt) {
        this.vrsn = vrsn;
        this.frmt = frmt;
    }

    @Override
    public String version() {
        return this.vrsn;
    }

    @Override
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public T value() throws YamlFormatException {
        try {
            return this.frmt.value();
            // @checkstyle IllegalCatchCheck (3 lines)
        } catch (final Exception exp) {
            throw new YamlFormatException(exp);
        }
    }
}
