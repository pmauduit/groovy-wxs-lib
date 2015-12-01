package fr.beneth.cswlib

import fr.beneth.cswlib.metadata.Metadata
import groovy.util.slurpersupport.NodeChild

class GetRecords {
    ArrayList<Metadata> metadatas

    // Dataset || Services
    /* <?xml version="1.0"?>
       <csw:GetRecords xmlns:csw="http://www.opengis.net/cat/csw/2.0.2"
       xmlns:gmd="http://www.isotc211.org/2005/gmd" service="CSW" version="2.0.2" resultType="results" outputSchema="csw:IsoRecord" maxRecords="2" startPosition="34">
            <csw:Query typeNames="csw:Record">
                <csw:Constraint version="1.1.0">
                    <Filter xmlns="http://www.opengis.net/ogc" xmlns:gml="http://www.opengis.net/gml">
                        <PropertyIsEqualTo>
                            <PropertyName>Type</PropertyName>
                            <Literal>service</Literal>
                        </PropertyIsEqualTo>
                    </Filter>
                </csw:Constraint>
            </csw:Query>
        </csw:GetRecords>
    */
    
    public static GetRecords mapFromRequest(String url, NodeChild payload) {
        
    }
}
