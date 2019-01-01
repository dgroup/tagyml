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
import java.util.Map;
import org.cactoos.text.UncheckedText;
import org.dgroup.yaml.YamlFile;
import org.dgroup.yaml.YamlFormatException;
import org.dgroup.yaml.text.YamlText;
import org.yaml.snakeyaml.Yaml;

/**
 * The {@link YamlFile} which checks the expected version of YAML file.
 *
 * @since 0.1.0
 */
public final class SupportedYamlFile implements YamlFile {

    /**
     * The path to YAML file.
     */
    private final Path path;

    /**
     * Origin.
     */
    private final YamlFile origin;

    /**
     * The YAML file as {@link org.cactoos.Text}.
     */
    private final UncheckedText yaml;

    /**
     * Ctor.
     * @param path The path to YAML file.
     * @param charset The YAML file charset to read.
     * @param origin The origin.
     */
    public SupportedYamlFile(
        final Path path, final Charset charset, final YamlFile origin
    ) {
        this.path = path.toAbsolutePath();
        this.yaml = new UncheckedText(new YamlText(path, charset));
        this.origin = origin;
    }

    @Override
    public <T> T read(final Object version, final Class<T> type)
        throws YamlFormatException {
        if (!this.path.toFile().exists()) {
            throw new YamlFormatException("The %s doesn't exist", this.path);
        }
        final Map<String, Object> tree = new Yaml().loadAs(
            this.yaml.asString(), Map.class
        );
        final Object vrsn = tree.get("version");
        if (vrsn == null
            || vrsn.toString().chars().allMatch(Character::isWhitespace)) {
            throw new YamlFormatException("The %s has missing <version> tag");
        }
        if (version.toString().equals(vrsn.toString())) {
            return this.origin.read(version, type);
        } else {
            throw new YamlFormatException(
                "Unsupported YAML format: '%s'", vrsn
            );
        }
    }
}
