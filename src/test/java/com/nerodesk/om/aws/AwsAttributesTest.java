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
package com.nerodesk.om.aws;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.nerodesk.om.Attributes;
import java.io.IOException;
import java.util.Date;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Tests for {@link AwsAttributes}.
 *
 * @author Dmitry Zaytsev (dmitry.zaytsev@gmail.com)
 * @author Paul Polishchuk (ppol@ua.fm)
 * @version $Id$
 * @since 0.3.30
 */
public final class AwsAttributesTest {
    /**
     * AwsAttributes can return correct size.
     * @throws IOException If unsuccessful.
     */
    @Test
    public void returnsCorrectSize() throws IOException {
        final ObjectMetadata meta = new ObjectMetadata();
        meta.setContentLength(1L);
        MatcherAssert.assertThat(
            new AwsAttributes(meta).size(),
            Matchers.is(1L)
        );
    }

    /**
     * AwsAttributes can return correct creation date.
     * @throws IOException If unsuccessful.
     */
    @Test
    public void returnsCorrectDate() throws IOException {
        final Date date = new Date();
        final ObjectMetadata meta = new ObjectMetadata();
        meta.setHeader(Headers.DATE, date);
        MatcherAssert.assertThat(
            new AwsAttributes(meta).created(),
            Matchers.is(date)
        );
    }

    /**
     * AwsAttributes can return correct type.
     * @throws IOException If unsuccessful.
     */
    @Test
    public void returnsCorrectType() throws IOException {
        final String type = "application/xml";
        final ObjectMetadata meta = new ObjectMetadata();
        meta.setContentType(type);
        MatcherAssert.assertThat(
            new AwsAttributes(meta).type(),
            Matchers.is(type)
        );
    }

    /**
     * AwsAttributes can provide visibility attribute change it.
     * @throws Exception in case of error.
     */
    @Test
    public void changesVisibility() throws Exception {
        final ObjectMetadata meta = new ObjectMetadata();
        meta.addUserMetadata("visible", "false");
        final Attributes attributes = new AwsAttributes(meta);
        MatcherAssert.assertThat(
            attributes.visible(),
            Matchers.is(false)
        );
        attributes.show(true);
        MatcherAssert.assertThat(
            attributes.visible(),
            Matchers.is(true)
        );
    }
}
