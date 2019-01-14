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
package io.github.dgroup.tagyml.text;

import io.github.dgroup.tagyml.YamlFormatException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import org.cactoos.Text;
import org.cactoos.io.BytesOf;
import org.cactoos.io.InputOf;
import org.cactoos.text.FormattedText;
import org.cactoos.text.TextOf;
import org.cactoos.text.UncheckedText;

/**
 * Read *.yml file to text.
 *
 * @since 0.1.0
 */
public final class YamlText implements Text {

    /**
     * The path to the source file.
     */
    private final Path src;

    /**
     * The charset of the source file.
     */
    private final Charset cset;

    /**
     * Ctor.
     * @param src The path to source file.
     */
    public YamlText(final Path src) {
        this(src, StandardCharsets.UTF_8);
    }

    /**
     * Ctor.
     * @param src The path to source file.
     * @param charset The charset of the source file.
     */
    public YamlText(final Path src, final Charset charset) {
        this.src = src;
        this.cset = charset;
    }

    @Override
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public String asString() throws YamlFormatException {
        try (InputStream inp = new InputOf(this.src).stream()) {
            return new TextOf(new BytesOf(inp), this.cset).asString();
            // @checkstyle IllegalCatchCheck (3 lines)
        } catch (final Exception cause) {
            throw new YamlFormatException(
                new FormattedText("%s doesn't exist", this.src), cause
            );
        }
    }

    @Override
    public String toString() {
        return new UncheckedText(this).asString();
    }
}
