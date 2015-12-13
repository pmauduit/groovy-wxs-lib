package fr.beneth.cswlib.metadata

import static org.junit.Assert.*
import static org.junit.Assume.assumeTrue
import org.junit.Test


class MetadataTest {

    @Test
    public void testParseResponsibleParty() {
        def testMd = this.getClass().getResource("/fr/beneth/cswlib/geonetwork/md-service-19139.xml")
        assumeTrue(testMd != null)
        
        def xml = new XmlSlurper().parse(testMd.toString())
        xml.declareNamespace("gmd": "http://www.isotc211.org/2005/gmd",
            "gco" : "http://www.isotc211.org/2005/gco",
            "gmx" : "http://www.isotc211.org/2005/gmx")
        def respP =  xml.'**'.find {
            it.name() == "CI_ResponsibleParty"
        }
        def respParty = ResponsibleParty.mapFromXmlFragment(respP)
        assertTrue(respParty.address != null)
        assertTrue(respParty.address.city != null)
        assertTrue(respParty.address.deliveryPoint != null)
        assertTrue(respParty.address.electronicMailAddress != null)
        assertTrue(respParty.address.postalCode != null)
        assertTrue(respParty.individualName == "GIP ATGeRi")
        assertTrue(respParty.logoUrl != null)
        assertTrue(respParty.organisationName != null)
        assertTrue(respParty.role != null)
    }
    
    @Test
    public void testMapFromXmlDocumentDataMd() {
        def testMd = this.getClass().getResource("/fr/beneth/cswlib/geonetwork/erdf-transfo-19139fra.xml")
        assumeTrue(testMd != null)
        
        def md = Metadata.mapFromXmlDocument(testMd.toString())
        assertTrue(md.fileIdentifier  == '26a3f3f4-a3a1-49af-a7d3-5e535e16b935')
        assertTrue(md.scopeCode  == 'dataset')
        assertTrue(md.responsibleParty != null)
        assertTrue(md.title  ==~ /(?s).*Postes de transformation.*/)
        assertTrue(md._abstract != null)
        assertTrue(md.onlineResources.size() > 0)
        assertTrue(md.graphicOverviewUrls.size() > 0)
        assertTrue(md.keywords.size() > 0)   
    }

    @Test
    public void testToString() {
        def testMd = this.getClass().getResource("/fr/beneth/cswlib/geonetwork/erdf-transfo-19139fra.xml")
        assumeTrue(testMd != null)

        def md = Metadata.mapFromXmlDocument(testMd.toString())
        def str = md.toString()
        assertTrue(! str.contains("keywords: []"))
        assertTrue(! str.contains("keywords: []"))
        assertTrue(! str.contains("online resources: []"))
        assertTrue(str.contains("uuid: 26a3f3f4-a3a1-49af-a7d3-5e535e16b935"))
    }

    @Test
    public void testMapFromXmlDocumentServiceMd() {
        def testMd = this.getClass().getResource("/fr/beneth/cswlib/geonetwork/md-service-19139.xml")
        assumeTrue(testMd != null)
        
        def md = Metadata.mapFromXmlDocument(testMd.toString())
        assertTrue(md.fileIdentifier  == '52f5d3e0-49a8-49fc-9e45-cf035cc6e0cf')
        assertTrue(md.scopeCode  == 'service')
        assertTrue(md.responsibleParty != null)
        assertTrue(md.title  == 'DRJSCS (WMS)')
        assertTrue(md._abstract != null)
        assertTrue(md.onlineResources.size() > 0)
        assertTrue(md.coupledResources.size() > 0)
        assertTrue(md.operatesOn.size() > 0)
        assertTrue(md.keywords.size() > 0)
    }

    @Test
    public void testMapFromXmlDocumentCigal() {
        def testMd = this.getClass().getResource("/fr/beneth/cswlib/geonetwork/data-md-cigal.xml")
        assumeTrue(testMd != null)
        
        def md = Metadata.mapFromXmlDocument(testMd.toString())
        assertTrue(md.fileIdentifier  != null)
        assertTrue(md.scopeCode  == 'dataset')
        assertTrue(md.responsibleParty != null)
        assertTrue(md.title  != null)
        assertTrue(md._abstract != null)
        assertTrue(md.onlineResources.size() == 3)
        assertTrue(md.graphicOverviewUrls.size() > 0)
        assertTrue(md.keywords.size() > 0)
    }

    @Test
    public void testMapFromXmlDocumentGeopicardie() {
        def testMd = this.getClass().getResource("/fr/beneth/cswlib/geonetwork/data-md-geopic.xml")
        assumeTrue(testMd != null)

        def md = Metadata.mapFromXmlDocument(testMd.toString())
        assertTrue(md.fileIdentifier  != null)
        assertTrue(md.scopeCode  == 'dataset')
        assertTrue(md.responsibleParty != null)
        assertTrue(md.title  != null)
        assertTrue(md._abstract != null)
        assertTrue(md.onlineResources.size() == 1)
        assertTrue(md.graphicOverviewUrls.size() > 0)
        assertTrue(md.keywords.size() > 0)
    }

    @Test
    public void testServiceMetadataFromGeob() {
        def testMd = this.getClass().getResource("/fr/beneth/cswlib/geob-service-md.xml")
        assumeTrue(testMd != null)

        def md = Metadata.mapFromXmlDocument(testMd.toString())
        assertTrue(md.operatesOn.size() == 48)
    }
}
