<?xml version="1.0"?>
<!--
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
 -->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns="http://www.w3.org/1999/xhtml" version="1.0">
    <xsl:template match="/page">
        <xsl:text disable-output-escaping="yes">&lt;!DOCTYPE html&gt;</xsl:text>
        <html lang="en">
            <head>
                <meta charset="UTF-8"/>
                <meta name="description" content="nerodesk"/>
                <meta name="keywords" content="cloud file storage"/>
                <meta name="author" content="nerodesk.com"/>
                <link rel="stylesheet" type="text/css" media="all" href="/css/main.css?{version/revision}"/>
                <xsl:apply-templates select="." mode="head"/>
            </head>
            <body>
                <nav role="navigation" class="menu">
                    <xsl:if test="not(identity)">
                        <span>
                            <a href="{links/link[@rel='takes:facebook']/@href}" title="login via Facebook">
                                <xsl:text>login</xsl:text>
                            </a>
                        </span>
                    </xsl:if>
                    <xsl:apply-templates select="identity"/>
                    <xsl:apply-templates select="version"/>
                    <xsl:apply-templates select="flash"/>
                </nav>
                <xsl:apply-templates select="." mode="body"/>
            </body>
        </html>
    </xsl:template>
    <xsl:template match="version">
        <span title="currently deployed version is {name}">
            <xsl:value-of select="name"/>
        </span>
        <span title="server time to build this page">
            <xsl:attribute name="style">
                <xsl:text>color:</xsl:text>
                <xsl:choose>
                    <xsl:when test="/page/millis &gt; 5000">
                        <xsl:text>red</xsl:text>
                    </xsl:when>
                    <xsl:when test="/page/millis &gt; 1000">
                        <xsl:text>orange</xsl:text>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:text>inherit</xsl:text>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:attribute>
            <xsl:call-template name="millis">
                <xsl:with-param name="millis" select="/page/millis"/>
            </xsl:call-template>
        </span>
        <span title="server load average">
            <xsl:attribute name="style">
                <xsl:text>color:</xsl:text>
                <xsl:choose>
                    <xsl:when test="/page/@sla &gt; 6">
                        <xsl:text>red</xsl:text>
                    </xsl:when>
                    <xsl:when test="/page/@sla &gt; 3">
                        <xsl:text>orange</xsl:text>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:text>inherit</xsl:text>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:attribute>
            <xsl:value-of select="/page/@sla"/>
        </span>
    </xsl:template>
    <xsl:template match="flash">
        <div>
            <xsl:attribute name="style">
                <xsl:text>color:</xsl:text>
                <xsl:choose>
                    <xsl:when test="level = 'INFO'">
                        <xsl:text>#348C62</xsl:text>
                    </xsl:when>
                    <xsl:when test="level = 'WARNING'">
                        <xsl:text>orange</xsl:text>
                    </xsl:when>
                    <xsl:when test="level = 'SEVERE'">
                        <xsl:text>red</xsl:text>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:text>inherit</xsl:text>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:attribute>
            <xsl:value-of select="message"/>
            <xsl:if test="msec &gt; 0">
                <xsl:text> (in </xsl:text>
                <xsl:call-template name="millis">
                    <xsl:with-param name="millis" select="msec"/>
                </xsl:call-template>
                <xsl:text>)</xsl:text>
            </xsl:if>
        </div>
    </xsl:template>
    <xsl:template match="identity[urn!='urn:test:1']">
        <span title="Facebook account logged in: {urn}">
            <xsl:value-of select="name"/>
        </span>
        <span>
            <a title="log out" href="{/page/links/link[@rel='takes:logout']/@href}">
                <xsl:text>logout</xsl:text>
            </a>
        </span>
    </xsl:template>
    <xsl:template match="identity[urn='urn:test:1']">
        <span>
            <xsl:text>tester</xsl:text>
        </span>
    </xsl:template>
    <xsl:template name="millis">
        <xsl:param name="millis"/>
        <xsl:choose>
            <xsl:when test="not($millis)">
                <xsl:text>?</xsl:text>
            </xsl:when>
            <xsl:when test="$millis &gt; 60000">
                <xsl:value-of select="format-number($millis div 60000, '0')"/>
                <xsl:text>min</xsl:text>
            </xsl:when>
            <xsl:when test="$millis &gt; 1000">
                <xsl:value-of select="format-number($millis div 1000, '0.0')"/>
                <xsl:text>s</xsl:text>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="format-number($millis, '0')"/>
                <xsl:text>ms</xsl:text>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
</xsl:stylesheet>