package fr.beneth.cswlib

import org.junit.BeforeClass
import org.junit.Test
import static org.junit.Assert.assertTrue
import static org.junit.Assume.assumeTrue

import java.lang.reflect.Method

import groovy.mock.interceptor.MockFor
import groovy.xml.XmlUtil
import groovyx.net.http.HTTPBuilder

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

    class MockedHttpClient {
        int numcall = 0
        String dataset1, dataset2, service1
        def response = [entity: [ content: [ text: [:] ]]]
        
        def MockedHttpClient() {
            def d1 = this.getClass().getResource("dataset-1.xml")
            def d2 = this.getClass().getResource("dataset-11.xml")
            def s = this.getClass().getResource("service-1.xml")
            assumeTrue((d1 != null) && (d2 != null))
            dataset1 = d1.getText()
            dataset2 = d2.getText()
            service1 = s.getText()
        }

        def post(_,Closure c) {
            if (numcall == 0) {
                response.entity.content.text = dataset1
                numcall++
            } else if (numcall == 1) {
                response.entity.content.text = dataset2
                numcall++
            } else {
                response.entity.content.text = service1
            }
            c.call(response)
        }
    }

    class MockedHttpClientForGetLastModified {
        def response = [entity: [ content: [ text: [:] ]]]
        def post(_,Closure c) {
            response.entity.content.text = this.getClass().getResource("lastModResp.xml").getText()
            c.call(response)
        }
        def MockedHttpClientForGetLastModified() {
            assumeTrue(this.getClass().getResource("lastModResp.xml") != null)
        }
    }
    
    @Test
    public void testGetRecordsFromEndpoint() {
        def hc = new MockedHttpClient()
        def rec1 = GetRecords.getAllMetadatasFromEndpoint("http://localhost:8080/",
            GetRecords.DATASET, hc)
        assertTrue(rec1.metadatas.size() == 15)

        def rec2 = GetRecords.getAllMetadatasFromEndpoint("http://localhost:8080/",
            GetRecords.SERVICE, hc)

        assertTrue(rec2.metadatas.size() == 2)
    }

    @Test
    public void testbuildQueryOrder() {
        def cswRec = GetRecords.buildQueryOrder(1, 10, GetRecords.DATASET, "changeDate", "DESC")
        assertTrue(cswRec.contains("DESC"))
        assertTrue(cswRec.contains("<ogc:PropertyName>changeDate</ogc:PropertyName>"))
        
    }
    
    @Test
    public void testGetLastModifiedMetadatasFromEndpoint() {
        def hc = new MockedHttpClientForGetLastModified()
        def rec1 = GetRecords.getLastModifiedMetadatasFromEndpoint("http://localhost:8080/", hc)
        assertTrue(rec1.metadatas.size() == 10)
    }

}