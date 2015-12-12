package fr.beneth.cswlib

import org.junit.Test
import static org.junit.Assert.assertTrue
import static org.junit.Assume.assumeTrue
import groovy.xml.XmlUtil

class GetRecordsTest {
    @Test
    public void testGetRecords() {
        // Datasets tests
        def d1 = this.getClass().getResource("dataset-1.xml")
        def d2 = this.getClass().getResource("dataset-11.xml")
        
        assumeTrue((d1 != null) && (d2 != null))

        def rec1 = GetRecords.getAllMetadatasFromDocument(d1.getText())
        def rec2 = GetRecords.getAllMetadatasFromDocument(d2.getText())
        assertTrue(rec1.metadatas.size() + rec2.metadatas.size() == 15)

        // services tests
        def s = this.getClass().getResource("service-1.xml")
        
        assumeTrue(s != null)

        def r = GetRecords.getAllMetadatasFromDocument(s.getText())
        assertTrue(r.metadatas.size() == 2)
        
    }
}
