package fr.beneth.cswlib.geonetwork

import fr.beneth.cswlib.metadata.Metadata

/**
 * This class is a wrapper to the classic querying interface
 * of GeoNetwork ("/srv/eng/q?").
 *
 * @author pmauduit
 *
 */
class GeoNetworkQuery {

    ArrayList<Metadata> metadatas = new ArrayList<Metadata>()
    
    public static GeoNetworkQuery mapFromUrl(String url) {
        def gnq = new GeoNetworkQuery()
        
        def gnHost = url
        def patGnHost = url =~ /(http(s?):\/\/.*\/geonetwork\/).*$/
        if (patGnHost.matches()) {
            gnHost = patGnHost[0][1]
        }

        def xml = new XmlSlurper().parse(url)
        xml.declareNamespace("geonet": "http://www.fao.org/geonetwork")
        xml.'metadata'.each {
                def newMd = new Metadata()
                it.'keyword'.each { kw -> newMd.keywords << kw.text() }
                newMd.title = it.'title'.text()
                newMd._abstract = it.'abstract'.text()
                // Can be more than one elem !
                it.'image'.each { gov ->
                    def graphicOverview = gov.text()
                    if (graphicOverview) {
                        def splitted = graphicOverview.split("\\|")
                        if (splitted[1]) {
                            if (splitted[1].startsWith("../../"))
                                splitted[1] = splitted[1].replace("../../", gnHost)
                            newMd.graphicOverviewUrls << splitted[1]
                        }
                    }
                }
                newMd.fileIdentifier = it.'geonet:info'.'uuid'.text()
                newMd.scopeCode = it.'type'.text()
                gnq.metadatas << newMd
        }
        return gnq
    }
}
