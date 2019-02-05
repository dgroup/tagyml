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
import org.cactoos.Scalar;

/**
 * The tag with alternative value in case if original is absent.
 *
 * @param <T> The type of tag.
 * @since 0.4.0
 */
public class Alt<T> extends TagEnvelope<T> {

    /**
     * Ctor.
     * @param src The original tag.
     * @param alt The alternative value of tag.
     */
    public Alt(final Tag<T> src, final T alt) {
        this(src, () -> alt);
    }

    /**
     * Ctor.
     * @param src The original tag.
     * @param alt The alternative value of tag.
     */
    public Alt(final Tag<T> src, final Scalar<T> alt) {
        super(
            // @checkstyle AnonInnerLengthCheck (30 lines)
            new Tag<T>() {

                @Override
                public String name() {
                    return src.name();
                }

                @Override
                @SuppressWarnings({
                    "PMD.PreserveStackTrace",
                    "PMD.AvoidCatchingGenericException"})
                public T value() throws YamlFormatException {
                    T val;
                    try {
                        val = src.value();
                        // @checkstyle IllegalCatchCheck (10 lines)
                    } catch (final Exception expected) {
                        try {
                            val = alt.value();
                        } catch (final Exception unexpected) {
                            throw new YamlFormatException(
                                "Not able to evaluate the alternative value",
                                unexpected
                            );
                        }
                    }
                    return val;
                }
            }
        );
    }
}
