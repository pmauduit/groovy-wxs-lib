package fr.beneth.cswlib

import fr.beneth.cswlib.metadata.Metadata
import groovyx.net.http.ContentType;
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.URIBuilder;;;

class GetRecordById {

    public static String buildQuery(String identifier) {
        return """<?xml version="1.0"?>
          <csw:GetRecordById xmlns:csw="http://www.opengis.net/cat/csw/2.0.2" resultType="results"
        service="CSW" version="2.0.2" outputSchema="csw:IsoRecord" ElementSetName="full">
            <csw:Id>${identifier}</csw:Id>
        </csw:GetRecordById>"""
    }

    public static Metadata getMetadataFromResponse(String document) {
        def response = new XmlSlurper().parseText(document)
        response.declareNamespace(csw: "http://www.opengis.net/cat/csw/2.0.2",
                    gmd: "http://www.isotc211.org/2005/gmd")

        def md = response.children().first()
        if (md) {
            def mappedMd = Metadata.map(md)
            // Something probably went wrong with parsing
            // returning null
            if (mappedMd.fileIdentifier != "") {
                return mappedMd
            }
        }
        return null
    }

    public static Metadata getRecordById(def cswEndPoint, def identifier, def hb = null, boolean preferGetRequest = true) {
        def http = hb != null ? hb : new HTTPBuilder(cswEndPoint)
        if (! preferGetRequest) {
            http.post(body: buildQuery(identifier),
            requestContentType: ContentType.XML) { resp ->
                return GetRecordById.getMetadataFromResponse(resp.entity.content.text)
            }
        } else {
            def uri = new URIBuilder(cswEndPoint)
            uri.addQueryParams([request:"GetRecordById", service: "CSW", version: "2.0.2", elementSetName: "full",
                id: identifier, outputSchema: "csw:IsoRecord"])
            
            return GetRecordById.getMetadataFromResponse(uri.toURL().getText())
        }
    }
}
