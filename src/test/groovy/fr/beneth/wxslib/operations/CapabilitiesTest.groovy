package fr.beneth.wxslib.operations;


import fr.beneth.wxslib.Layer
import org.junit.Test
import static org.junit.Assert.assertTrue


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
		println capInfo.getStylesCount()
		// grep -A1 '<Style' src/test/resources/fr/beneth/wxslib/operations/sdi-geor.getcap.xml | grep Name | sort -u | wc -l
		assertTrue(capInfo.getStylesCount() == 17)
	}
}
