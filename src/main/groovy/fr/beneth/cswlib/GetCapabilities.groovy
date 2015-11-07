package fr.beneth.cswlib

class GetCapabilities {

    // /csw:Capabilities/ows:ServiceIdentification/ows:Title
    // /csw:Capabilities/ows:ServiceIdentification/ows:Abstract
    String title, _abstract

    // /csw:Capabilities/ows:ServiceIdentification/ows:Keywords/ows:Keyword
    ArrayList<String> keywords = new ArrayList<String>()

    // ows:ServiceProvider/ows:ProviderName
    ServiceProvider serviceProvider

    // /csw:Capabilities/ows:OperationsMetadata/ows:Operation
    ArrayList<Operation> operations = new ArrayList<Operation>()

    String fees, accessConstraints

    public static GetCapabilities mapFromXmlDocument(String url) {
        def xml = new XmlSlurper().parse(url)
        xml.declareNamespace('ows' : 'http://www.opengis.net/ows',
            'xlink': 'http://www.w3.org/1999/xlink')

        def cswGetCap = new GetCapabilities()
        cswGetCap.title = xml.'ows:ServiceIdentification'.'ows:Title'.text()
        cswGetCap._abstract = xml.'ows:ServiceIdentification'.'ows:Abstract'.text()

        cswGetCap.fees = xml.'ows:ServiceIdentification'.'ows:Fees'.text()
        cswGetCap.accessConstraints = xml.'ows:ServiceIdentification'.
            'ows:AccessConstraints'.text()
        xml.'ows:ServiceIdentification'.'ows:Keywords'.'ows:Keyword'.each {
            cswGetCap.keywords << it.text()
        }
        xml.'ows:OperationsMetadata'.'ows:Operation'.each {
            cswGetCap.operations << Operation.mapFromXmlFragment(it)
        }
        def sceProv = xml.'ows:ServiceProvider'
        if (sceProv) {
            cswGetCap.serviceProvider = ServiceProvider.mapFromXmlFragment(sceProv)
        }
        return cswGetCap
    }
}

