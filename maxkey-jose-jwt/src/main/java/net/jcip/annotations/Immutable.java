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
 * The presence of this annotation indicates that the author believes the class to be immutable and hence inherently
 * thread-safe. An immutable class is one where the state of an instance cannot be <i>seen</i> to change. As a result
 * <ul>
 * <li>All public fields must be {@code final}</li>
 * <li>All public final reference fields are either {@code null} or refer to other immutable objects</li>
 * <li>Constructors and methods do not publish references to any potentially mutable internal state.</li>
 * </ul>
 * Performance optimization may mean that instances of an immutable class may have mutable internal state. The
 * critical point is that callers cannot tell the difference. For example {@link String} is an immutable class, despite
 * having an internal int that is non-final but used as a cache for {@link String#hashCode()}.
 * <p/>
 * Immutable objects are inherently thread-safe; they may be passed between threads or published without
 * synchronization.
 */
@Documented
@Target(TYPE)
@Retention(RUNTIME)
public @interface Immutable {
}
