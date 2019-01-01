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

import org.dgroup.yaml.Tag;

/**
 * Fake implementation of {@link Tag} for unit testing purposes.
 *
 * @param <T> The type of tag.
 * @since 0.1.0
 */
public final class TgFake<T> implements Tag<T> {

    /**
     * The name of YML tag.
     */
    private final String tag;

    /**
     * The value of YML tag.
     */
    private final T val;

    /**
     * Ctor.
     * @param tag The name of YML tag.
     * @param val The value of YML tag.
     */
    public TgFake(final String tag, final T val) {
        this.tag = tag;
        this.val = val;
    }

    @Override
    public String name() {
        return this.tag;
    }

    @Override
    public T value() {
        return this.val;
    }
}