package fr.beneth.wxslib.georchestra;

/**
 * Created by pmauduit on 10/27/15.
 */
public class Instance {
    private static String SDI_LIST_ENDPOINT = "http://sdi.georchestra.org/geoserver/wfs?request=getfeature&service=wfs&typeName=geor:sdi"

    String title, url, logo_url, _abstract
    boolean isPublic, isInProduction
    
    float lat,lon

    static ArrayList<Instance> loadGeorchestraInstances() {
        def xmlData = new XmlSlurper().parse(SDI_LIST_ENDPOINT)
        xmlData.declareNamespace('geor': 'http://sdi.georchestra.org/geor',
                                 'gml': 'http://www.opengis.net/gml/3.2')
        def instances = new ArrayList<Instance>()
        xmlData.children().each { wfsMember ->
            def curInst =  new Instance()

            curInst.title = wfsMember.'geor:sdi'.'geor:title'
            curInst.url = wfsMember.'geor:sdi'.'geor:url'
            curInst.logo_url = wfsMember.'geor:sdi'.'geor:logo_url'
            curInst._abstract = wfsMember.'geor:sdi'.'geor:abstract'
            curInst.isPublic = wfsMember.'geor:sdi'.'geor:is_public' == "true"
            curInst.isInProduction = wfsMember.'geor:sdi'.'geor:is_production' == "true"

            try {
                def pos = wfsMember.'geor:sdi'.'geor:the_geom'.'gml:MultiPoint'.'gml:pointMember'.
                        'gml:Point'.'gml:pos'.text()
                def ll = pos.split(" ")
                curInst.lat = Float.parseFloat(ll[0])
                curInst.lon = Float.parseFloat(ll[1])
            } catch (NumberFormatException e) {
              curInst.lat = 0.0
              curInst.lon = 0.0
            }
            instances << curInst
        }
        return instances
    }

}
