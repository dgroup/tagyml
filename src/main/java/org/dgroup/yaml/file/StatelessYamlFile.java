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
package org.dgroup.yaml.file;

import java.nio.charset.Charset;
import java.nio.file.Path;
import org.cactoos.text.UncheckedText;
import org.dgroup.yaml.YamlFile;
import org.dgroup.yaml.YamlFormatException;
import org.dgroup.yaml.text.YamlText;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

/**
 * The {@link YamlFile} which read YAML file and transforms to target object.
 *
 * @since 0.1.0
 */
public final class StatelessYamlFile implements YamlFile {

    /**
     * The path to YAML file.
     */
    private final Path path;

    /**
     * The YAML file charset to read.
     */
    private final Charset charset;

    /**
     * Ctor.
     * @param path The path to YAML file.
     * @param charset The YAML file charset to read.
     */
    public StatelessYamlFile(final Path path, final Charset charset) {
        this.path = path;
        this.charset = charset;
    }

    @Override
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public <T> T read(final Object version, final Class<T> type)
        throws YamlFormatException {
        try {
            return new Yaml(new Constructor(type)).load(
                new UncheckedText(
                    new YamlText(this.path, this.charset)
                ).asString()
            );
            // @checkstyle IllegalCatchCheck (5 lines)
        } catch (final RuntimeException exp) {
            throw new YamlFormatException(exp);
        }
    }
}
