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

    // Dataset || Services
    /* <?xml version="1.0"?>
       <csw:GetRecords xmlns:csw="http://www.opengis.net/cat/csw/2.0.2"
        service="CSW" version="2.0.2" resultType="results" outputSchema="csw:IsoRecord" maxRecords="2" startPosition="34">
            <csw:Query typeNames="csw:Record">
                <csw:Constraint version="1.1.0">
                    <Filter xmlns="http://www.opengis.net/ogc">
                        <PropertyIsEqualTo>
                            <PropertyName>Type</PropertyName>
                            <Literal>service</Literal>
                        </PropertyIsEqualTo>
                    </Filter>
                </csw:Constraint>
            </csw:Query>
        </csw:GetRecords>
    */
    
    // No way of knowing if a MD has been harvested or not ...
    // Maybe using the GN extensions though
    // resultType="results_with_summary" instead of results, then forget every MD that are not gmd:MD_Metadata/geonet:info/isHarvested/text() != 'n'
    
    private NodeChild buildQuery(def startPosition, def type) {
        return new XmlSlurper().parseText("""<?xml version="1.0"?>
       <csw:GetRecords xmlns:csw="http://www.opengis.net/cat/csw/2.0.2"
        service="CSW" version="2.0.2" resultType="results_with_summary" outputSchema="csw:IsoRecord" maxRecords="10" startPosition="${startPosition}">
            <csw:Query typeNames="csw:Record">
                <csw:Constraint version="1.1.0">
                    <Filter xmlns="http://www.opengis.net/ogc">
                        <PropertyIsEqualTo>
                            <PropertyName>Type</PropertyName>
                            <Literal>${type}</Literal>
                        </PropertyIsEqualTo>
                    </Filter>
                </csw:Constraint>
            </csw:Query>
        </csw:GetRecords>""")
        
    }
    
    public static GetRecords mapFromRequest(String url, NodeChild payload) {
        
    }
    
    public static GetRecords getAllDatasetMetadatas(String url) {
        def http = new HTTPBuilder(url)
    }
}
