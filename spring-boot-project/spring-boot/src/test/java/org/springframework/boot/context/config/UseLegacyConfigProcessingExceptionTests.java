/*
 * Copyright 2012-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.boot.context.config;

import org.junit.jupiter.api.Test;

import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.MockConfigurationPropertySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * Tests for {@link UseLegacyConfigProcessingException}.
 *
 * @author Phillip Webb
 * @author Madhura Bhave
 */
class UseLegacyConfigProcessingExceptionTests {

	@Test
	void throwIfRequestedWhenMissingDoesNothing() {
		MockConfigurationPropertySource source = new MockConfigurationPropertySource();
		Binder binder = new Binder(source);
		UseLegacyConfigProcessingException.throwIfRequested(binder);
	}

	@Test
	void throwIfRequestedWhenFalseDoesNothing() {
		MockConfigurationPropertySource source = new MockConfigurationPropertySource();
		source.put("spring.config.use-legacy-processing", "false");
		Binder binder = new Binder(source);
		UseLegacyConfigProcessingException.throwIfRequested(binder);
	}

	@Test
	void throwIfRequestedWhenTrueThrowsException() {
		MockConfigurationPropertySource source = new MockConfigurationPropertySource();
		source.put("spring.config.use-legacy-processing", "true");
		Binder binder = new Binder(source);
		assertThatExceptionOfType(UseLegacyConfigProcessingException.class)
				.isThrownBy(() -> UseLegacyConfigProcessingException.throwIfRequested(binder))
				.satisfies((ex) -> assertThat(ex.getConfigurationProperty().getName())
						.hasToString("spring.config.use-legacy-processing"));
	}

}
