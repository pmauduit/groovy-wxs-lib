package fr.beneth.cswlib.geonetwork

import static org.junit.Assert.assertTrue
import static org.junit.Assume.assumeTrue

import org.junit.Test

class GeoNetworkSourceTest {

    @Test
    public void testParsingSources() {
        URL endpoint = this.getClass().getResource("xml-info-sample.xml")
        assumeTrue(endpoint != null)
        GeoNetworkSource.cswEndPoint = ""

        def srcs = GeoNetworkSource.mapFromGeonetwork(endpoint.toString())

        assertTrue(srcs.size() == 192)
        assertTrue(srcs.find { it.uuid == "ERDF"} != null)

    }

}
