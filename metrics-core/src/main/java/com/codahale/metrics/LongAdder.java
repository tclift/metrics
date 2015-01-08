/*
 * Just in case anyone wants to use this, being clear that it's the same license as Metrics itself:
 *
 * Copyright 2015 PaperCut Software International Pty. Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.codahale.metrics;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

/**
 * The
 * <a href="https://github.com/dropwizard/metrics/blob/master/metrics-core/src/main/java/com/codahale/metrics/LongAdder.java">
 * stock Metrics version of LongAdder
 * </a>
 * makes use of an App Engine blacklisted class (sun.misc.Unsafe). This class replaces method implementations with
 * AtomicLong equivalents. The result is a class that will run in App Engine, but has more contention (within an
 * instance) (and a bit less memory overhead).
 *<p>
 * Supposedly Striped64 will be in Java 8, so if App Engine ever update there will be no need for this hack.
 *
 * @see <a href="https://github.com/dropwizard/metrics/issues/539">Issue asking for removal of Unsafe</a>
 */
class LongAdder extends AtomicLong implements Serializable {
    private static final long serialVersionUID = 7249069246863182397L;

    LongAdder() {
    }

    public void add(long x) {
        addAndGet(x);
    }

    public void increment() {
        incrementAndGet();
    }

    public void decrement() {
        decrementAndGet();
    }

    public long sum() {
        return get();
    }

    public void reset() {
        set(0);
    }

    public long sumThenReset() {
        return getAndSet(0);
    }

    private void writeObject(java.io.ObjectOutputStream s)
            throws java.io.IOException {
        s.defaultWriteObject();
        s.writeLong(get());
    }

    private void readObject(java.io.ObjectInputStream s)
            throws java.io.IOException, ClassNotFoundException {
        s.defaultReadObject();
        set(s.readLong());
    }
}
