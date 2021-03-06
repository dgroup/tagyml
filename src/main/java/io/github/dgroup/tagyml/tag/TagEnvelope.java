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
package io.github.dgroup.tagyml.tag;

import io.github.dgroup.tagyml.Tag;
import io.github.dgroup.tagyml.YamlFormatException;
import java.io.UncheckedIOException;
import org.cactoos.Scalar;

/**
 * Envelope of {@link Tag}.
 *
 * @param <T> The type of item.
 * @since 0.1.0
 */
public class TagEnvelope<T> implements Tag<T> {

    /**
     * The origin.
     */
    private final Tag<T> origin;

    /**
     * Ctor.
     * @param tag Current YML tag name.
     * @param yml Object tree loaded from *.yml file with tests.
     */
    public TagEnvelope(final String tag, final Scalar<T> yml) {
        this(
            new Tag<T>() {
                @Override
                public String name() {
                    return tag;
                }

                @Override
                @SuppressWarnings("PMD.AvoidCatchingGenericException")
                public T value() throws YamlFormatException {
                    try {
                        return yml.value();
                        // @checkstyle IllegalCatchCheck (3 lines)
                    } catch (final Exception cause) {
                        throw new YamlFormatException(cause);
                    }
                }
            }
        );
    }

    /**
     * Ctor.
     * @param origin The original tag.
     */
    public TagEnvelope(final Tag<T> origin) {
        this.origin = origin;
    }

    @Override
    public final String name() {
        return this.origin.name();
    }

    @Override
    public final T value() throws YamlFormatException {
        try {
            final T val = this.origin.value();
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
