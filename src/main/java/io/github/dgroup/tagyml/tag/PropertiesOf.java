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

import io.github.dgroup.tagyml.YamlFormatException;
import java.util.Map;
import java.util.Properties;

/**
 * The <em>properties</em> tag.
 *
 * @since 0.1.0
 */
public final class PropertiesOf extends TagEnvelope<Properties> {
    /**
     * Ctor.
     * @param name The name of YML tag.
     * @param tag The value of YML tag.
     * @param <T> The type of properties.
     */
    public <T> PropertiesOf(final String name, final Map<T, T> tag) {
        super(
            name,
            () -> {
                if (tag == null) {
                    throw new YamlFormatException("Tag '%s' is absent", name);
                }
                return new org.cactoos.scalar.PropertiesOf(tag).value();
            }
        );
    }
}
