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
package io.github.dgroup.tagyml.yaml;

import io.github.dgroup.tagyml.Yaml;
import io.github.dgroup.tagyml.YamlFormatException;
import io.github.dgroup.tagyml.text.YamlText;
import org.cactoos.Func;

/**
 * The envelope for {@link Yaml}.
 *
 * @param <T> The type of the target object.
 * @since 0.3.0
 */
public class Envelope<T> implements Yaml<T> {

    /**
     * The function to evaluate the target object from YAML text.
     */
    private final Func<YamlText, T> origin;

    /**
     * Ctor.
     * @param fnc The function to evaluate the target object from YAML text.
     */
    public Envelope(final Func<YamlText, T> fnc) {
        this.origin = fnc;
    }

    @Override
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public final T apply(final YamlText text) throws YamlFormatException {
        try {
            return this.origin.apply(text);
            // @checkstyle IllegalCatchCheck (5 lines)
        } catch (final Exception cause) {
            throw new YamlFormatException(cause);
        }
    }
}
