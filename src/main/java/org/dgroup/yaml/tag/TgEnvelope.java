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
package org.dgroup.yaml.tag;

import java.io.UncheckedIOException;
import org.cactoos.Scalar;
import org.cactoos.scalar.UncheckedScalar;
import org.dgroup.yaml.Tag;
import org.dgroup.yaml.YamlFormatException;

/**
 * Envelope of {@link Tag}.
 *
 * @param <T> The type of item.
 * @since 0.1.0
 */
public class TgEnvelope<T> implements Tag<T> {

    /**
     * YML tag name.
     */
    private final String tag;

    /**
     * YML tags tree.
     */
    private final Scalar<T> yml;

    /**
     * Ctor.
     * @param tag Current YML tag name.
     * @param yml Object tree loaded from *.yml file with tests.
     */
    public TgEnvelope(final String tag, final Scalar<T> yml) {
        this.tag = tag;
        this.yml = yml;
    }

    @Override
    public final String name() {
        return this.tag;
    }

    @Override
    public final T value() throws YamlFormatException {
        try {
            final T val = new UncheckedScalar<>(this.yml).value();
            if (val == null
                || val.toString().chars().allMatch(Character::isWhitespace)) {
                throw new YamlFormatException(this);
            }
            return val;
        } catch (final UncheckedIOException cause) {
            throw new YamlFormatException(cause);
        }
    }
}
