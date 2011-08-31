/**
 * Copyright 2003-2009 OFFIS, Henri Tremblay
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
package org.easymock.samples;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import org.easymock.classextension.EasyMockSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Example of how to perform partial mocking
 */
public class PartialClassMockTest extends EasyMockSupport {

    public static class Rect {

        private int x;

        private int y;

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getArea() {
            return getX() * getY();
        }
    }

    private Rect rect;

    @Before
    public void setUp() throws Exception {
        rect = createMockBuilder(Rect.class).addMockedMethods("getX", "getY")
                .createMock();
    }

    @After
    public void tearDown() {
        rect = null;
    }

    @Test
    public void testGetArea() {
        expect(rect.getX()).andReturn(4);
        expect(rect.getY()).andReturn(5);
        replayAll();
        assertEquals(20, rect.getArea());
        verifyAll();
    }
}
