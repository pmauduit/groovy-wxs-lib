package fr.beneth.cswlib.geonetwork

import static org.junit.Assert.*
import static org.junit.Assume.assumeTrue

import org.junit.Test

class GeoNetworkQueryTest {

    @Test
    public void geonetworkQueryTest() {
        URL fixture = this.getClass().getResource("query-sdi.xml")
        assumeTrue(fixture != null)
                
        GeoNetworkQuery q = GeoNetworkQuery.mapFromUrl(fixture.toString())
        assertTrue(q.metadatas.size() == 32)
        def myMd = q.metadatas.find { it.title == "Recensement des armoires de rues du réseau de "+
            "fibres optiques sur l'agglomération de Chambéry"}
        assertTrue(myMd != null)
        assertTrue(myMd.fileIdentifier != "")
        assertTrue(myMd.keywords.size()  == 3)
        assertTrue(myMd.graphicOverviewUrls.size() == 2)
        assertTrue(myMd.scopeCode != "")
        assertTrue(myMd._abstract != "")
    }

}
