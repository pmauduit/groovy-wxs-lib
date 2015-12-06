package fr.beneth.cswlib

import org.junit.Test
import static org.junit.Assert.assertTrue
import groovy.xml.XmlUtil

class GetRecordsTest {
    @Test
    public void testGetRecords() {
        def rec = GetRecords.getAllMetadatas("http://sdi.georchestra.org/geonetwork/srv/eng/csw", GetRecords.DATASET)
        assertTrue(rec.metadatas.size() == 15)

        rec = GetRecords.getAllMetadatas("http://sdi.georchestra.org/geonetwork/srv/eng/csw", GetRecords.SERVICE)
        assertTrue(rec.metadatas.size() == 2)
        
    }
}
