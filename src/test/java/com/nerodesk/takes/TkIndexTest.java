/**
 * Copyright (c) 2015, nerodesk.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met: 1) Redistributions of source code must retain the above
 * copyright notice, this list of conditions and the following
 * disclaimer. 2) Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided
 * with the distribution. 3) Neither the name of the nerodesk.com nor
 * the names of its contributors may be used to endorse or promote
 * products derived from this software without specific prior written
 * permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.nerodesk.takes;

import com.jcabi.matchers.XhtmlMatchers;
import com.nerodesk.om.mock.MkBase;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.takes.rq.RqFake;
import org.takes.rq.RqWithHeader;
import org.takes.rs.RsPrint;

/**
 * Tests for {@code TkIndex}.
 *
 * @author Grzegorz Gajos (grzegorz.gajos@opentangerine.com)
 * @version $Id$
 * @since 0.2
 */
public final class TkIndexTest {

    /**
     * TkIndex can return XML.
     * @throws Exception If fails.
     */
    @Test
    public void returnsXml() throws Exception {
        MatcherAssert.assertThat(
            new RsPrint(
                new TkIndex(new MkBase()).act(new RqFake())
            ).printBody(),
            XhtmlMatchers.hasXPath("/page/millis")
        );
    }

    /**
     * TkIndex can return HTML.
     * @throws Exception If fails.
     */
    @Test
    public void returnsHtml() throws Exception {
        MatcherAssert.assertThat(
            new RsPrint(
                new TkIndex(new MkBase()).act(
                    new RqWithHeader(new RqFake(), "Accept: text/html")
                )
            ).printBody(),
            XhtmlMatchers.hasXPath("/xhtml:html")
        );
    }
}