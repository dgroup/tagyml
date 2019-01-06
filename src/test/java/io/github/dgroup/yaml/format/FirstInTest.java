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

import io.github.dgroup.yaml.YamlFormatException;
import java.util.Collection;
import java.util.LinkedList;
import org.cactoos.Text;
import org.cactoos.text.FormattedText;
import org.cactoos.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.core.StringContains;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.llorllale.cactoos.matchers.ScalarHasValue;
import org.llorllale.cactoos.matchers.TextHasString;

/**
 * Test case for {@link FirstIn}.
 *
 * @since 0.1.0
 * @checkstyle MagicNumberCheck (200 lines)
 * @checkstyle StringLiteralsConcatenationCheck (200 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (200 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class FirstInTest {

    /**
     * The junit rule to catch and verify the exceptions within the tests.
     */
    @Rule
    public ExpectedException caused = ExpectedException.none();

    /**
     * The '3' number represent the object which was parsed by the first
     * supported format.
     */
    @Test
    public void matched() {
        MatcherAssert.assertThat(
            "The 3rd object was 'parsed', has value '3' and returned for usage",
            new FirstIn<>(
                (version, exception) -> {
                },
                () -> "Ok",
                new FormatOf<>(
                    "1.0",
                    () -> {
                        throw new YamlFormatException("1");
                    }
                ),
                new FormatOf<>(
                    "2.0",
                    () -> {
                        throw new YamlFormatException("2");
                    }
                ),
                new FormatOf<>("3.0", () -> 3),
                new FormatOf<>("4.0", () -> 4)
            ),
            new ScalarHasValue<>(3)
        );
    }

    /**
     * Check the default error message to be thrown in case no any formats which
     * support the YAML file structure.
     *
     * @throws YamlFormatException Usually thrown in case if YAML file has
     *  unsupported/illegal format. In the current case it will be catch by
     *  {@link this#caused}.
     */
    @Test
    public void failed() throws YamlFormatException {
        this.caused.expect(YamlFormatException.class);
        this.caused.expectMessage("The file has unsupported YAML format");
        new FirstIn<>(
            (version, exception) -> {
            },
            new TextOf("The file has unsupported YAML format"),
            new FormatOf<>(
                "1.0",
                () -> {
                    throw new IllegalArgumentException("Can't parse version 1");
                }
            ),
            new FormatOf<>(
                "2.0",
                () -> {
                    throw new IllegalArgumentException("Can't parse version 2");
                }
            )
        ).value();
    }

    /**
     * The fallback procedure, for example to log the exception.
     */
    @Test
    public void fallback() {
        final Collection<Text> fllback = new LinkedList<>();
        try {
            new FirstIn<>(
                (version, exception) -> fllback.add(
                    new FormattedText(
                        "version %s is failed due to %s",
                        version, new TextOf(exception)
                    )
                ),
                () -> "The YAML file 'abc.yml' has unsupported format",
                new FormatOf<>(
                    "1.0",
                    () -> {
                        throw new IllegalArgumentException("NPE");
                    }
                )
            ).value();
        } catch (final YamlFormatException cause) {
            MatcherAssert.assertThat(
                cause.getMessage(),
                new StringContains(
                    "The YAML file 'abc.yml' has unsupported format"
                )
            );
        }
        MatcherAssert.assertThat(
            fllback.iterator().next(),
            Matchers.allOf(
                new TextHasString(
                    "version 1.0 is failed due to "
                        + "io.github.dgroup.yaml.YamlFormatException"
                ),
                new TextHasString(
                    "Caused by: java.lang.IllegalArgumentException: NPE"
                )
            )
        );
    }
}
