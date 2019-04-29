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

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The presence of this annotation indicates that the field or method must only be accessed when holding the specified
 * lock.
 */
@Documented
@Target(value = {FIELD, METHOD})
@Retention(RUNTIME)
public @interface GuardedBy {
    /**
     * The specified lock that guards the annotated field or method. Valid values are:
     * <ul>
     * <li>{@code this} indicates the intrinsic lock of the instance containing the field or method.</li>
     * <li><code><i>class-name</i>.this</code> which allows for disambiguation of which {@code this} when dealing
     * with inner classes</li>
     * <li>{@code itself} which is valid for reference fields only, and indicates that the referenced instance's
     * own intrinsic lock should be used as the guard</li>
     * <li><code><i>field-name</i></code> indicates the named instance or static field is to be used as the guard. If
     * the field type is not a sub-type of {@link java.util.concurrent.locks.Lock} then the intrinsic lock of
     * the referenced instance is to be used</li>
     * <li><code><i>class-name</i>.<i>field-name</i></code> indicates the named static field is to be used as the
     * guard. If the field type is not a sub-type of {@link java.util.concurrent.locks.Lock} then the intrinsic lock of
     * the referenced instance is to be used</li>
     * <li><code><i>method-name</i>()</code> indicates that the zero-argument method should be called to obtain the
     * lock object. If the return type is not a sub-type of {@link java.util.concurrent.locks.Lock} then the intrinsic
     * lock of the returned instance is to be used</li>
     * <li><code><i>class-name</i>.class</code> indicates that the intrinsic lock of the specified class should be used
     * as the guard</li>
     * </ul>
     *
     * @return The specified lock that guards the annotated field or method
     */
    String value();
}
