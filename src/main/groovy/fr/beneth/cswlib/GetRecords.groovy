package fr.beneth.cswlib

import fr.beneth.cswlib.metadata.Metadata
import groovy.util.slurpersupport.NodeChild
import groovy.xml.MarkupBuilder
import groovy.xml.StreamingMarkupBuilder
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.ContentType
import groovyx.net.http.Method
import groovyx.net.http.RESTClient


class GetRecords {
    def cswEntryPointUrl
    ArrayList<Metadata> metadatas
    /* pagination utils */
    private def maxRecords = 10, currentPosition = 0

    // Possible to search for local MD using the _isHarvested field
    // (even if not promoted in the GetCapabilities)
    
    public static String buildQuery(int startPosition, String type) {
        return """<?xml version="1.0"?>
       <csw:GetRecords xmlns:csw="http://www.opengis.net/cat/csw/2.0.2" xmlns:ogc="http://www.opengis.net/ogc"
        service="CSW" version="2.0.2" resultType="results" outputSchema="csw:IsoRecord" maxRecords="10" startPosition="${startPosition}">
            <csw:Query typeNames="csw:Record">
                <csw:Constraint version="1.1.0">
                    <ogc:Filter>
                        <ogc:And>
                            <ogc:PropertyIsEqualTo>
                                <ogc:PropertyName>Type</ogc:PropertyName>
                                <ogc:Literal>${type}</ogc:Literal>
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
    
    public static GetRecords mapFromRequest(String url, NodeChild payload) {
        
    }
    
    private void parseResponse(def resp) {
        println resp
    }

    public static GetRecords getAllDatasetMetadatas(String url) {
        def http = new HTTPBuilder(url)
        def ret = new GetRecords()
        def finished = true

        while (finished) {
            http.post(body: buildQuery(1, "dataset"), requestContentType: ContentType.XML) { resp ->
                ret.parseResponse(resp)
                finished = false
            }
            
        }
        return ret
    }
}
