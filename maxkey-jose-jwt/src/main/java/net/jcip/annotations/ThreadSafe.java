/*
 * Copyright 2013 Stephen Connolly.
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

package net.jcip.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The presence of this annotation indicates that the author believes the class to be thread-safe. As such, there should
 * be no sequence of accessing the public methods or fields that could put an instance of this class into an invalid
 * state, irrespective of any rearrangement of those operations by the Java Runtime and without introducing any
 * requirements for synchronization or coordination by the caller/accessor.
 */
@Documented
@Target(TYPE)
@Retention(RUNTIME)
public @interface ThreadSafe {
}
