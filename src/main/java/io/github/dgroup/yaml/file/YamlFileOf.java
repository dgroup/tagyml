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
package io.github.dgroup.yaml.file;

import io.github.dgroup.yaml.YamlFile;
import io.github.dgroup.yaml.YamlFormatException;
import java.nio.charset.Charset;
import java.nio.file.Path;

/**
 * The {@link YamlFile} which:
 *  - check the input arguments for {@link YamlFile#read(Object, Class)};
 *  - check the version of the YAML file;
 *  - transform the YAML file to target object.
 *
 * @since 0.1.0
 */
public final class YamlFileOf implements YamlFile {

    /**
     * The path to YAML file.
     */
    private final Path src;

    /**
     * The YAML file charset to read.
     */
    private final Charset charset;

    /**
     * Ctor.
     * @param path The path to YAML file.
     * @param charset The YAML file charset to read.
     */
    public YamlFileOf(final Path path, final Charset charset) {
        this.src = path;
        this.charset = charset;
    }

    @Override
    public <T> T read(final Object version, final Class<T> type)
        throws YamlFormatException {
        return new StrictYamlFile(
            new SupportedYamlFile(
                this.src, this.charset,
                new StatelessYamlFile(this.src, this.charset)
            )
        ).read(version, type);
    }
}
