package fr.beneth.wxslib.operations;


import org.junit.Test
import static org.junit.Assert.assertTrue


class CapabilitiesTest {
	@Test
	void testSdiGeorchestraOrgWmsCapabilities() {
		def capInfo = Capabilities.mapFromDocument("http://sdi.georchestra.org/geoserver/wms?service=wms&request=getcapabilities")

		assertTrue(capInfo.name == "WMS")
		assertTrue(capInfo._abstract ==~ /.*modular and opensource spatial data infrastructure.*/)
	}
}
