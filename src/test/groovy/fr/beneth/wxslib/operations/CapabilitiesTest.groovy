package fr.beneth.wxslib.operations;


import fr.beneth.wxslib.Layer
import sun.security.jgss.GSSHeader;

import org.junit.Test
import static org.junit.Assert.assertTrue
import static org.junit.Assert.assertFalse


class CapabilitiesTest {
	@Test
	void testSdiGeorchestraOrgWmsCapabilities() {
		def getCapResp = this.getClass().getResource("sdi-geor.getcap.xml")
		def capInfo = Capabilities.mapFromDocument(getCapResp.toString())
		
		assertTrue(capInfo.name == "WMS")
		assertTrue(capInfo._abstract ==~ /.*modular and opensource spatial data infrastructure.*/)
		assertTrue(capInfo.keywords.size == 2)
		assertTrue(capInfo.layers.size == 1)
		assertTrue(capInfo.title == "geOrchestra Web Map Service")
		assertTrue(capInfo.contactEmail == "psc@georchestra.org")
		assertTrue(capInfo.contact == "geOrchestra PSC")
		
		Layer fo = capInfo.findLayerByName("pmauduit_test:armoires-fo")
		assertTrue(fo != null)
		assertTrue(fo.styles.size == 1)
		assertTrue(capInfo.getLayersCount() == 29)
		// grep -A1 '<Style' src/test/resources/fr/beneth/wxslib/operations/sdi-geor.getcap.xml | grep Name | sort -u | wc -l
		assertTrue(capInfo.getStylesCount() == 17)
	}
	
	@Test
	void testOpaqueQueryableLayer() {
		def getCapResp = this.getClass().getResource("sdi-geor.getcap.xml")
		def capInfo = Capabilities.mapFromDocument(getCapResp.toString())
		def gshhsLayer = capInfo.findLayerByName("gshhs:GSHHS_l_L1")
		def mainLayer = (Layer) capInfo.layers.first()
		assertFalse(gshhsLayer.opaque)
		assertTrue(gshhsLayer.queryable)
		assertFalse(mainLayer.opaque)
		assertFalse(mainLayer.queryable)
	}
    
    @Test
    void testLayerBboxParsing() {
        def getCapResp = this.getClass().getResource("sdi-geor.getcap.xml")
        def capInfo = Capabilities.mapFromDocument(getCapResp.toString())
        def gshhsLayer = capInfo.findLayerByName("gshhs:GSHHS_l_L1")
        
        assertTrue(gshhsLayer.boundingBoxes.size() == 5)
        assertTrue(gshhsLayer.boundingBoxes.find { it.crs == "EPSG:4326" } != null)
        assertTrue(capInfo.layers.first().boundingBoxes.size() == 5)
        assertTrue(capInfo.layers.first().boundingBoxes.find { it.crs == "EPSG:4326" } != null)
        assertTrue(capInfo.layers.first().boundingBoxes.find { it.crs == "EPSG:4326" }.miny == "-180.0" )
        assertTrue(capInfo.layers.first().boundingBoxes.find { it.crs == "EPSG:4326" }.maxx == "90.0" )
        
    }
}
