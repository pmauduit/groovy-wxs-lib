package fr.beneth.cswlib

import org.junit.Test
import static org.junit.Assume.assumeTrue
import static org.junit.Assert.assertTrue

class GetCapabilitiesTest {

    @Test
    public void testGetCapabilities() {
        def sdiCswGetCap = this.getClass().getResource("sdi-geor-csw-getcap.xml")
        assumeTrue(sdiCswGetCap != null)

        def gc = GetCapabilities.mapFromXmlDocument(sdiCswGetCap.toString())
        assertTrue(gc.title != null)
        assertTrue(gc._abstract != null)
        assertTrue(gc.keywords.size() > 0)
        assertTrue(gc.serviceProvider != null)
        assertTrue(gc.serviceProvider.providerSite == "http://sdi.georchestra.org/geonetwork")
        assertTrue(gc.serviceProvider.providerName == "geOrchestra PSC")
        assertTrue(gc.operations.size() > 0)
    }
}
