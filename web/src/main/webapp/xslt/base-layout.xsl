<?xml version="1.0" encoding="UTF-8"?>
<!-- The main entry point for all user interface generated
from XSLT. 
-->
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:output omit-xml-declaration="yes" method="html" doctype-public="html" indent="yes"
    encoding="UTF-8"/>
  
  <xsl:include href="common/base-variables.xsl"/>
  
  <xsl:include href="base-layout-cssjs-loader.xsl"/>
  
  <xsl:template match="/">
    <html ng-app="{$angularApp}" lang="{$lang}">
      <head>
        <title><xsl:value-of
          select="concat($env/site/name, ' - ', $env/site/organisation)"/></title>
        <meta charset="utf-8" />
        <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
        <meta name="apple-mobile-web-app-capable" content="yes" />
        
        <meta name="description" content="" />
        <meta name="keywords" content="" />
        
        <xsl:call-template name="css-load"/>
        
      </head>
      <body data-ng-controller="GnCatController">
        <xsl:choose>
          <xsl:when test="ends-with($service, 'nojs')">
            <div>
              <!-- TODO: Add header/footer -->
            <xsl:apply-templates mode="content" select="."/>
            </div>
          </xsl:when>
          <xsl:otherwise>
            <div data-ng-cloak="" class="ng-cloak">
              <div class="navbar navbar-fixed-top" data-ng-include="'{$uiResourcesPath}templates/top-toolbar.html'">
              </div>
              
              <xsl:apply-templates mode="content" select="."/>
              
              <xsl:if test="$isJsEnabled">
                <xsl:call-template name="javascript-load"/>
              </xsl:if>
            </div>
            <xsl:if test="$isJsEnabled">
              <xsl:call-template name="no-js-alert"/>
            </xsl:if>
          </xsl:otherwise>
        </xsl:choose>
      </body>
    </html>
  </xsl:template>
  
  
  
  
  
  <xsl:template name="no-js-alert">
    <noscript>
    <div class="alert" ng-hide="">
      <strong>Warning!</strong> Javascript is not enabled. Enable it or click 
        <a href="catalog.search.nojs">here to search</a> in a degraded mode.
    </div>
    </noscript>
  </xsl:template>

</xsl:stylesheet>