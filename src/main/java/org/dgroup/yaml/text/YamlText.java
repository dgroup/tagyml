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
package org.dgroup.yaml.text;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import org.cactoos.Text;
import org.cactoos.io.BytesOf;
import org.cactoos.io.InputOf;
import org.cactoos.text.TextEnvelope;
import org.cactoos.text.TextOf;

/**
 * Read *.yml file to text.
 *
 * @since 0.1.0
 */
public final class YamlText extends TextEnvelope {

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
     * @param charset The source file charset.
     */
    public YamlText(final Path src, final Charset charset) {
        super(
            (Text) () -> {
                try (InputStream inp = new InputOf(src).stream()) {
                    return new TextOf(new BytesOf(inp), charset).asString();
                } catch (final FileNotFoundException cause) {
                    throw new UncheckedIOException(cause);
                }
            }
        );
    }
}
