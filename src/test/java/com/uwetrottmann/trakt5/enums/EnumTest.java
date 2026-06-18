/*
 * Copyright 2026 Uwe Trottmann <uwe@uwetrottmann.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.uwetrottmann.trakt5.enums;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EnumTest {

    @Test
    public void rating_fromValue() {
        Rating[] values = Rating.values();
        for (Rating rating : values) {
            assertThat(Rating.fromValue(rating.value)).isEqualTo(rating);
        }

        assertThat(Rating.fromValue(0)).isNull();
        assertThat(Rating.fromValue(11)).isNull();
    }
}