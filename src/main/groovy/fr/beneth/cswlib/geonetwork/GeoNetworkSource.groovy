package fr.beneth.cswlib.geonetwork

/**
 * Fetches the sources from the GeoNetwork xml.info entry point.
 * 
 * The purpose of this class is to obtain the sources (organisms)
 * that feed the GeoNetwork catalogue with its metadata.
 * Since I could not manage to find the actual CSW GetDomain request
 * which would have produced the same result.
 *
 * @author pmauduit
 *
 */

class GeoNetworkSource {

    private static String cswEndPoint = "srv/eng/xml.info?type=sources"

    String uuid, name


    public static ArrayList<GeoNetworkSource> mapFromGeonetwork(String gnEndPoint) {
        def sources = new ArrayList<GeoNetworkSource>()
        
        def xmlData = new XmlSlurper().parse(gnEndPoint + cswEndPoint)
        xmlData.sources.children().each { t ->
            def s = new GeoNetworkSource()
            s.uuid = t.uuid.text()
            s.name = t.name.text()
            sources << s
        }
        return sources
    }
}
