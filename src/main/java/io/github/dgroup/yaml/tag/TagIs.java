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
package io.github.dgroup.yaml.tag;

import io.github.dgroup.yaml.Tag;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 * Hamcrest matcher to check the implementation of {@link Tag}.
 *
 * @param <T> The type of tag.
 * @since 0.1.0
 */
public final class TagIs<T> extends TypeSafeMatcher<Tag<T>> {

    /**
     * The name of YML tag.
     */
    private final String name;

    /**
     * The value of YML tag.
     */
    private final T value;

    /**
     * Ctor.
     * @param name The name of YML tag.
     * @param value The value of YML tag.
     */
    public TagIs(final String name, final T value) {
        super();
        this.name = name;
        this.value = value;
    }

    @Override
    public void describeTo(final Description desc) {
        describe(desc, this.name, this.value);
    }

    @Override
    protected boolean matchesSafely(final Tag<T> tag) {
        return this.name.equals(tag.name())
            && this.value.equals(new TgUnchecked<>(tag).value());
    }

    @Override
    protected void describeMismatchSafely(
        final Tag<T> tag, final Description desc
    ) {
        describe(desc, tag.name(), new TgUnchecked<>(tag).value());
    }

    /**
     * Build hamcrest message about expected/actual tags.
     * @param desc The hamcrest description.
     * @param name The name of YML tag.
     * @param val The value of YML tag.
     */
    private static void describe(
        final Description desc, final String name, final Object val
    ) {
        desc.appendText("Tag ").appendValue(name)
            .appendText(" is ").appendValue(val)
            .appendText(".");
    }

}
