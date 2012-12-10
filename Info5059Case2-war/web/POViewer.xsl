<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : PODisplay.xsl
    Created on : February 22, 2011, 4:02 PM
    Author     : Evan
    Description: Translate PO in XML to HTML.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>

    <xsl:template match="PO">
        <xsl:apply-templates select="//vendor"/> <!-- Vendor Content -->
        <div class="grid_5" style="font-size:20px;font-weight:bold;padding-bottom:10px;">
           PO Number: 
           <xsl:value-of select="@num"/>
        </div>
         <div class="grid_4" style="font-size:12px;font-weight:bold;padding-bottom:10px;">
           Date: 
           <xsl:value-of select="@podate"/>
        </div>
 
        <div class="grid_10">
            <div class="grid_2">
                <pre> </pre>
            </div>
            <div class="grid_3 push_2 " style="text-align: left;">
                <font size="3" style="font-weight: bold;">Code</font>
            </div>
            <div class="grid_1" style="text-align: right;">
                <font size="3" style="font-weight: bold;">QTY</font>
            </div>
            <div class="grid_2" style="text-align: right;">
                <font size="3" style="font-weight: bold;">Price</font>
            </div>
            <div class="grid_2" style="text-align: right;">
                <font size="3" style="font-weight: bold;">Extended</font>
            </div>
        </div>

        <xsl:apply-templates select="//polineitem"/> <!-- Line Item Content -->
            <div class="grid_8" style="padding: 0px 0px 0px 5px; text-align: right">
                --------------
            </div>
            <div class="grid_1">
                <br/>
            </div>
            <div class="grid_7" style="padding: 0px 0px 0px 5px; text-align: right;">
                Sub:
            </div>
            <div class="grid_1" style="padding: 0px 0px 0px 5px; text-align: right">
                <xsl:value-of select="@total"/>
            </div>
            <div class="grid_1">
                <br />
            </div>
            <div class="grid_7" style="padding: 0px 0px 0px 5px; text-align: right;">
                Tax:
            </div>
            <div class="grid_1" style="padding: 0px 0px 0px 5px; text-align: right">
                <xsl:value-of select="@tax"/>
            </div>
            <div class="grid_1">
                <br />
            </div>
            <div class="grid_7" style="padding: 0px 0px 20px 5px; text-align: right;">
                Total:
            </div>
            <div class="grid_1" style="padding: 0px 0px 0px 5px; text-align: right">
                <xsl:value-of select="@pototal"/>
            </div>    
    </xsl:template>

    <xsl:template match="//vendor">
        <div class="grid_1" style="font-weight:bold;text-align:left;padding-left:15px;">Name:</div>
        <div class="grid_2" style="font-weight:normal;text-align:left;">
            <xsl:value-of select="@name"/>	
        </div>
        <div class="grid_1" style="font-weight:bold;text-align:left;">Email:</div>
        <div class="grid_2" style="font-weight:normal;text-align:left;">
            <xsl:value-of select="@email"/>
        </div>
        <div class="grid_1" style="font-weight:bold;text-align:left;">Address:</div>
        <div class="grid_2" style="font-weight:normal;text-align:left;">
            <xsl:value-of select="@address"/>
        </div>
        
        <div class="grid_3"><br/></div>
        
        <div class="grid_1" style="font-weight:bold;text-align:left;;padding-left:15px;">City:</div>
        <div class="grid_2" style="font-weight:normal;text-align:left;">
            <xsl:value-of select="@city"/>
        </div>
        <div class="grid_9" style="text-align: center; padding: 0px 0px 0px 15px;">
            <hr />
        </div>
    </xsl:template>

    <xsl:template match="//polineitem">
       <div class="grid_10">        
           <div class="grid_3 push_2" style="text-align:left;">
                <font size="2">
                    <xsl:value-of select="@prodcode"/>
                </font>
            </div>
            <div class="grid_1" style="text-align: right;">
                <xsl:value-of select="@qty"/>
            </div>
            <div class="grid_2" style="text-align: right">
                <xsl:value-of select="@price"/>
            </div>
            <div class="grid_2" style="text-align: right">
                <xsl:value-of select="@ext"/>
            </div>
        </div>

    </xsl:template>
</xsl:stylesheet>