package fr.beneth.cswlib.metadata

import groovy.util.slurpersupport.NodeChild

class OnlineResource {
    
    
    String url, protocol, name, description
    
    /**
     * Parses an XML fragment and returns an OnlineResource object,
     * example of a XML fragment:
     * 
     * <gmd:CI_OnlineResource>
     *    <gmd:linkage>
     *       <gmd:URL>https://path/to/doc.pdf</gmd:URL>
     *    </gmd:linkage>
     *    <gmd:protocol>
     *       <gco:CharacterString>WWW:DOWNLOAD-1.0-http--download</gco:CharacterString>
     *    </gmd:protocol>
     *    <gmd:name>
     *       <gmx:MimeFileType type="application/pdf">blah</gmx:MimeFileType>
     *    </gmd:name>
     *    <gmd:description>
     *       <gco:CharacterString>desc</gco:CharacterString>
     *    </gmd:description>
     * </gmd:CI_OnlineResource>
     * 
     * 
     * @param xmlFragment
     * @return an OnlineResource object
     */
    
    public static OnlineResource mapFromXmlFragment(NodeChild xmlFragment) {
        def ores = new OnlineResource()

        ores.url = xmlFragment."gmd:linkage"."gmd:URL".text()
        ores.protocol = xmlFragment."gmd:protocol"."gco:CharacterString".text()
        ores.name = xmlFragment."gmd:name"."gco:CharacterString".text()
        ores.description = xmlFragment."gmd:description"."gco:CharacterString".text()

        return ores
    }
}
