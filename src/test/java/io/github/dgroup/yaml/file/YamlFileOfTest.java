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

import io.github.dgroup.yaml.YamlFormatException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsEqual;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Test case for {@link YamlFileOf}.
 *
 * @since 0.1.0
 * @checkstyle MagicNumberCheck (500 lines)
 * @checkstyle JavadocTypeCheck (500 lines)
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle JavadocVariableCheck (500 lines)
 * @checkstyle VisibilityModifierCheck (500 lines)
 * @checkstyle EmptyLineSeparatorCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class YamlFileOfTest {

    @Rule
    public ExpectedException cause = ExpectedException.none();

    @Test
    public void read() throws YamlFormatException {
        final YamlTeams file = new YamlFileOf(
            Paths.get("src", "test", "resources", "yaml", "teams.yaml"),
            StandardCharsets.UTF_8
        ).read(1.0, YamlTeams.class);
        MatcherAssert.assertThat(
            "The tag <version> has value 1.0",
            file.version, new IsEqual<>(1.0)
        );
        MatcherAssert.assertThat(
            "The tag <teams> contains 2 teams",
            file.teams, Matchers.hasSize(2)
        );
        MatcherAssert.assertThat(
            "The 1st team has expected <team/1st/id>",
            file.teams.get(0).id, new IsEqual<>(100)
        );
    }

    @Test
    public void missingFile() throws YamlFormatException {
        this.cause.expect(YamlFormatException.class);
        this.cause.expectMessage("teams.yaml doesn't exist");
        new YamlFileOf(
            Paths.get("src", "test", "teams.yaml"),
            StandardCharsets.UTF_8
        ).read(1.0, YamlTeams.class);
    }

    @Test
    public void unsupportedVersion() throws YamlFormatException {
        this.cause.expect(YamlFormatException.class);
        this.cause.expectMessage("Unsupported YAML format: '1.0'");
        new YamlFileOf(
            Paths.get("src", "test", "resources", "yaml", "teams.yaml"),
            StandardCharsets.UTF_8
        ).read(1.1, YamlTeams.class);
    }

    @Test
    public void brokenFormat() throws YamlFormatException {
        this.cause.expect(YamlFormatException.class);
        this.cause.expectMessage("- id: 1bc");
        new YamlFileOf(
            Paths.get("src", "test", "resources", "yaml", "bad-format.yaml"),
            StandardCharsets.UTF_8
        ).read(1.0, YamlTeams.class);
    }

    private static class YamlTeams {
        public Double version;
        public List<YamlTeam> teams;
    }

    private static class YamlTeam {
        public int id;
        public String name;
        public YamlUser lead;
        public Map<String, String> properties;
    }

    private static class YamlUser {
        public String id;
        public String name;
        public String email;
    }

}
