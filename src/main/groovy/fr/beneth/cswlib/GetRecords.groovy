package fr.beneth.cswlib

import fr.beneth.cswlib.metadata.Metadata
import groovy.xml.XmlUtil
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder

class GetRecords {
    /* pagination utils */
    private def recordsMatched = 0, nextRecord = 0

    public static final String DATASET = "dataset"
    public static final String SERVICE = "service"

    ArrayList<Metadata> metadatas = new ArrayList<Metadata>()

    // GeoNetwork-specific:
    // Possible to search for local MD using the _isHarvested field
    // (even if not promoted in the GetCapabilities)

    public static String buildQuery(int startPosition, String mdType) {
        return """<?xml version="1.0"?>
       <csw:GetRecords xmlns:csw="http://www.opengis.net/cat/csw/2.0.2" xmlns:ogc="http://www.opengis.net/ogc"
        service="CSW" version="2.0.2" resultType="results" outputSchema="csw:IsoRecord" maxRecords="10" startPosition="${startPosition}">
            <csw:Query typeNames="csw:Record">
                <csw:Constraint version="1.1.0">
                    <ogc:Filter>
                        <ogc:And>
                            <ogc:PropertyIsEqualTo>
                                <ogc:PropertyName>Type</ogc:PropertyName>
                                <ogc:Literal>${mdType}</ogc:Literal>
                            </ogc:PropertyIsEqualTo>
                            <ogc:PropertyIsEqualTo>
                                <ogc:PropertyName>_isHarvested</ogc:PropertyName>
                                <ogc:Literal>n</ogc:Literal>
                            </ogc:PropertyIsEqualTo>
                        </ogc:And>
                    </ogc:Filter>
                </csw:Constraint>
            </csw:Query>
        </csw:GetRecords>"""
    }
    
    public static GetRecords getAllMetadatasFromDocument(String document) {
        def ret = new GetRecords()
        def response = new XmlSlurper().parseText(document)
        .declareNamespace(csw: "http://www.opengis.net/cat/csw/2.0.2",
            gmd: "http://www.isotc211.org/2005/gmd")

        ret.recordsMatched = Integer.parseInt(response.'csw:SearchResults'.'@numberOfRecordsMatched'.toString())
        ret.nextRecord = Integer.parseInt(response.'csw:SearchResults'.'@nextRecord'.toString())

        response.'csw:SearchResults'.'gmd:MD_Metadata'.each { md ->
            ret.metadatas << Metadata.mapFromString(XmlUtil.serialize(md))
        }
        return ret
    }

    public static GetRecords getAllMetadatasFromEndpoint(String url, String mdType, def hb = null) {
        def http = hb != null ? hb : new HTTPBuilder(url)
        def ret = new GetRecords()
        def done = false

        int currentIdx = 1
        while (! done) {
            http.post(body: buildQuery(currentIdx, mdType), requestContentType: ContentType.XML) { resp ->
                def parsedRecs = GetRecords.getAllMetadatasFromDocument(resp.entity.content.text)
                ret.metadatas += parsedRecs.metadatas
                if ((parsedRecs.nextRecord > parsedRecs.recordsMatched) || parsedRecs.nextRecord == 0) {
                    done = true
                } else {
                    currentIdx = parsedRecs.nextRecord
                }
            }
        }
        return ret
    }
}
