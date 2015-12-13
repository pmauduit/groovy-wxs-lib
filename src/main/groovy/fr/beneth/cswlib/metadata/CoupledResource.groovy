package fr.beneth.cswlib.metadata

import groovy.util.slurpersupport.NodeChild

class CoupledResource {

    String operationName, identifier, scopedName

    /**
     * Parses an XML fragment and returns an CoupledResource object,
     * example of a XML fragment:
     *
     * <pre>
     * <srv:coupledResource>
     *   <srv:SV_CoupledResource>
     *     <srv:operationName>
     *       <gco:CharacterString>GetMap</gco:CharacterString>
     *     </srv:operationName>
     *     <srv:identifier>
     *       <gco:CharacterString>0b05e1ee-0fb0-443e-8895-285785466745</gco:CharacterString>
     *     </srv:identifier>
     *     <gco:ScopedName>L_ACCES_LITTORAL_P_029</gco:ScopedName>
     *   </srv:SV_CoupledResource>
     * </srv:coupledResource>
     * </pre>
     *
     * @param xmlFragment
     * @return an OnlineResource object
     */
    public static CoupledResource mapFromXmlFragment(NodeChild xmlFragment) {
        def cres = new CoupledResource()

        cres.operationName = xmlFragment."srv:SV_CoupledResource"."srv:operationName"
            ."gco:CharacterString".text()
        cres.identifier = xmlFragment."srv:SV_CoupledResource"."srv:identifier"
            ."gco:CharacterString".text()
        cres.scopedName = xmlFragment."srv:SV_CoupledResource"."gco:ScopedName".text()

        return cres
    }
}
