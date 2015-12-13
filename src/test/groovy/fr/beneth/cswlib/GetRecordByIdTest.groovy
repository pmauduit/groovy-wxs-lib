package fr.beneth.cswlib

import static org.junit.Assert.*
import static org.junit.Assume.assumeTrue

import org.junit.Test


class GetRecordByIdTest {

    @Test
    public void testGetRecordByIdViaGetRequest() {
        def cg29 = this.getClass().getResource("cg29.wfs.xml")
        assumeTrue(cg29 != null)

        def md = GetRecordById.getRecordById(cg29.toURI(),
            "fr-org.geobretagne.rectorat.wfs", null, true)
        assertTrue(md.coupledResources.size() == 42)
        assertTrue(md.fileIdentifier == "fr-org.geobretagne.cg29.wfs")
        assertTrue(md.scopeCode == "service")

        def unexpectedResponse = this.getClass().getResource("sdi-geor-csw-getcap.xml")
        assumeTrue(unexpectedResponse != null)

        md = GetRecordById.getRecordById(unexpectedResponse.toURI(),
            "fr-org.geobretagne.rectorat.wfs", null, true)
        assertTrue(md == null)
    }
}
