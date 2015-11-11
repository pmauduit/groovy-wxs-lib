package fr.beneth.cswlib

/**
 * Manages a GetDomain CSW query
 * @author pmauduit
 *
 */
class GetDomain {

    static String DEFAULT_CSW_VERSION = "2.0.2"
    Map domainValues = new HashMap<String, List<String>>()

    /**
     * Execute a GetDomain request on an endpoint, given the property names
     *
     * TODO: does not handle the ParameterName yet.
     *
     * @param endPoint The address of the CSW endpoint
     * @param propertyNames the propertyNames (array of strings)
     * @return the parsed GetDomain response
     */
    public static GetDomain executeGetDomain(String endPoint, List<String> propertyNames) {
        def getDomain = new GetDomain()

        def params = "?service=CSW&request=GetDomain&version=${DEFAULT_CSW_VERSION}&PropertyName="
        params += propertyNames.join(",")
        
        def xmlData = new XmlSlurper().parse(endPoint + params)
        xmlData.declareNamespace("csw": "http://www.opengis.net/cat/csw/2.0.2")
        xmlData.'csw:DomainValues'.each {
            def title  = it.'csw:PropertyName'.text()
            def values = it.'csw:ListOfValues'.'csw:Value'.collect { it.text() }
            getDomain.domainValues[title] = values
        }
        return getDomain
    }
}
