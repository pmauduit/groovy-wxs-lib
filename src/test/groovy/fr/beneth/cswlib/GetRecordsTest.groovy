package fr.beneth.cswlib

import org.junit.Test
import static org.junit.Assert.assertTrue
import groovy.xml.XmlUtil

class GetRecordsTest {
    @Test
    public void testGetRecords() {
        def rec = GetRecords.getAllDatasetMetadatas("http://sdi.georchestra.org/geonetwork/srv/eng/csw")
        assertTrue(rec.metadatas.size() == 15)
    }
}
