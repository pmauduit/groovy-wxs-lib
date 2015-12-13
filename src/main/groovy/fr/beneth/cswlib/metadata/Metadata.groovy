package fr.beneth.cswlib.metadata

class Metadata {

    String fileIdentifier
    // /gmd:MD_Metadata/gmd:hierarchyLevel/gmd:MD_ScopeCode/@codeListValue
    String scopeCode // Dataset or service
    // /gmd:MD_Metadata/gmd:contact/gmd:CI_ResponsibleParty
    ResponsibleParty responsibleParty

    // /gmd:MD_Metadata/gmd:identificationInfo//gmd:citation//gmd:title/gco:CharacterString
    // /gmd:MD_Metadata/gmd:identificationInfo//gmd:abstract/gco:CharacterString
    String title, _abstract

    // /gmd:MD_Metadata/gmd:distributionInfo/gmd:transferOptions//gmd:onLine/gmd:CI_OnlineResource
    // datasets only
    ArrayList<OnlineResource> onlineResources = new ArrayList<OnlineResource>()

    // /gmd:MD_Metadata/gmd:identificationInfo/srv:SV_ServiceIdentification/srv:coupledResource
    // service only
    ArrayList<CoupledResource> coupledResources = new ArrayList<CoupledResource>()

    // operatesOn
    // /gmd:MD_Metadata/gmd:identificationInfo/srv:SV_ServiceIdentification/srv:operatesOn
    // service only
    ArrayList<LinkedMetadata> operatesOn = new ArrayList<LinkedMetadata>()

    // /gmd:MD_Metadata/gmd:identificationInfo//gmd:graphicOverview//gmd:fileName/gco:CharacterString
    ArrayList<String> graphicOverviewUrls = new ArrayList<String>()

    // /gmd:MD_Metadata/gmd:identificationInfo//gmd:descriptiveKeywords//gmd:keyword/gco:CharacterString
    ArrayList<String> keywords = new ArrayList<String>()

    public String toString() {
        return "uuid: ${fileIdentifier}\r\n"                         +
        "scopeCode: ${scopeCode}\r\n"                                +
        "responsibleParty: ${responsibleParty}\r\n"                  +
        "title: ${title}\r\n"                                        +
        "abstract: ${_abstract}\r\n"                                 +
        "online resources: ${onlineResources}\r\n"                   +
        "coupled resources (service only): ${coupledResources}\r\n"  +
        "operatesOn (service only): ${operatesOn}\r\n"               +
        "graphicOverviewUrls: ${graphicOverviewUrls}\r\n"            +
        "keywords: ${keywords}"
    }

    public static Metadata map(def xml) {
        
        def md = new Metadata()
        md.fileIdentifier = xml.'gmd:fileIdentifier'.'gco:CharacterString'.text()
        md.scopeCode = xml.'gmd:hierarchyLevel'.'gmd:MD_ScopeCode'.'@codeListValue'.toString()
        
        def rspParty = xml.'**'.find { it.name() == "CI_ResponsibleParty" }
        if (rspParty) {
            md.responsibleParty = ResponsibleParty.mapFromXmlFragment(rspParty)
        }

        md.title = xml.'gmd:identificationInfo'.'*'.'gmd:citation'.'*'.
            'gmd:title'.'gco:CharacterString'.text()

        md._abstract = xml.'gmd:identificationInfo'.'*'.'gmd:abstract'.
            'gco:CharacterString'.text()

        // online resources
        xml.'gmd:distributionInfo'.'**'.findAll { it.name() == 'CI_OnlineResource' }.each {
            md.onlineResources <<  OnlineResource.mapFromXmlFragment(it)
        }
        // coupled resources (service only)
        xml.'gmd:identificationInfo'.'**'.findAll { it.name() == 'coupledResource' }.each {
            md.coupledResources << CoupledResource.mapFromXmlFragment(it)
        }
        // operatesOn (service only)
        xml.'gmd:identificationInfo'.'**'.findAll { it.name() == 'operatesOn' }.each {
            md.operatesOn << LinkedMetadata.mapFromXmlFragment(it)
        }
        xml.'gmd:identificationInfo'.'*'.'gmd:graphicOverview'.'*'.'gmd:fileName'.each {
            def lu = it.'gco:CharacterString'.text()
            if (lu.startsWith("http"))
                md.graphicOverviewUrls << lu
        }
        xml.'gmd:identificationInfo'.'*'.'gmd:descriptiveKeywords'.'*'.'gmd:keyword'.each {
            md.keywords << it.'gco:CharacterString'.text()
        }
        return md
    }

    public static Metadata mapFromString(String md) {
        def xml = new XmlSlurper().parseText(md)
        xml.declareNamespace("gmd": "http://www.isotc211.org/2005/gmd",
            "gco"   : "http://www.isotc211.org/2005/gco",
            "srv"   : "http://www.isotc211.org/2005/srv",
            "xlink" : "http://www.w3.org/1999/xlink",
            "gmx"   : "http://www.isotc211.org/2005/gmx")
        return map(xml)
    }

    public static Metadata mapFromXmlDocument(String url) {
        def xml = new XmlSlurper().parse(url)
        xml.declareNamespace("gmd": "http://www.isotc211.org/2005/gmd",
            "gco"   : "http://www.isotc211.org/2005/gco",
            "srv"   : "http://www.isotc211.org/2005/srv",
            "xlink" : "http://www.w3.org/1999/xlink",
            "gmx"   : "http://www.isotc211.org/2005/gmx")
        return map(xml)
    }
}
