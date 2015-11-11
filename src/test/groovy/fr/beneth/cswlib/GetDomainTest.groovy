package fr.beneth.cswlib;

import static org.junit.Assert.*
import static org.junit.Assume.assumeTrue
import org.junit.Test

class GetDomainTest {

    @Test
    public void testGetDomain() {
        URL fixture = this.getClass().getResource("sdigeor-type-orgname.xml")
        assumeTrue(fixture != null)
        
        GetDomain ret = GetDomain.executeGetDomain(fixture.toString(),
            ["Type", "organisationName"])
        
        assertTrue(ret.domainValues.keySet().size() == 2)
        assertTrue(ret.domainValues.get("Type") != null)
        assertTrue(ret.domainValues.get("organisationName") != null)        
    }

}
