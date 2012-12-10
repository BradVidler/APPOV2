<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:template match="/">
    <html>
      <body bgcolor="#ffffff">
        <h1>Business Cards rendered from XML to HTML in JAVA</h1>
        <xsl:apply-templates  select="//card" />
      </body>
    </html>
 </xsl:template>

  <xsl:template match="card">
        <table border="3">
          <tr>
            <td>
              <xsl:value-of select="@name"/><br/>
              <xsl:value-of select="@title"/><br/>
              <xsl:value-of select="@email"/><br/>
              Phone: <xsl:value-of select="@phone"/><br/>
            </td>
            <td><img src="{@logo}" /></td>
          </tr>
        </table>
        <hr></hr>
  </xsl:template>

</xsl:stylesheet>