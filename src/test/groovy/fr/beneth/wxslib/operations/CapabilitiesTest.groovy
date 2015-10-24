package fr.beneth.wxslib.operations;


import fr.beneth.wxslib.Layer
import org.junit.Test
import static org.junit.Assert.assertTrue


class CapabilitiesTest {
	@Test
	void testSdiGeorchestraOrgWmsCapabilities() {
		def capInfo = Capabilities.mapFromDocument("http://sdi.georchestra.org/geoserver/wms?service=wms&request=getcapabilities")
		
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
	}
}
