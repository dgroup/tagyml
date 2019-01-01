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
package org.dgroup.yaml.scalar;

import java.util.NoSuchElementException;
import org.hamcrest.MatcherAssert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.llorllale.cactoos.matchers.ScalarHasValue;

/**
 * Test case for {@link FirstIn}.
 *
 * @since 0.1.0
 * @checkstyle MagicNumberCheck (500 lines)
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle JavadocVariableCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class FirstInTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void value() {
        MatcherAssert.assertThat(
            new FirstIn<>(
                () -> {
                    throw new IllegalArgumentException("1");
                },
                () -> {
                    throw new IllegalArgumentException("2");
                },
                () -> 3,
                () -> 4
            ),
            new ScalarHasValue<>(3)
        );
    }

    @Test
    public void empty() throws Exception {
        this.exception.expect(NoSuchElementException.class);
        this.exception.expectMessage(
            "No scalar(s) which gives the target object"
        );
        new FirstIn<>(
            () -> {
                throw new IllegalArgumentException();
            },
            () -> {
                throw new IllegalArgumentException();
            }
        ).value();
    }
}
