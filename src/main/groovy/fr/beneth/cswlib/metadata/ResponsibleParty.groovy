package fr.beneth.cswlib.metadata

import fr.beneth.cswlib.Address;
import groovy.util.slurpersupport.NodeChild

class ResponsibleParty {
    String individualName, organisationName, positionName
    Address address
    String logoUrl
    String role
    
    
    /**
     * Maps a responsible party from a XML fragment.
     *
     * @param xml
     * @return ResponsibleParty a ResponsibleParty object
     */
    
    public static ResponsibleParty mapFromXmlFragment(NodeChild xml) {
        def rp = new ResponsibleParty()
        rp.individualName = xml."gmd:individualName"."gco:CharacterString".text()
        rp.organisationName = xml."gmd:organisationName"."gco:CharacterString".text()
        rp.positionName = xml."gmd:positionName"."gco:CharacterString".text()
         
        def addr = xml.'**'.find { it.name() == "CI_Address" }
        if (addr) {
            rp.address = Address.mapFromXmlFragment(addr)
        }
        def logo = xml.'**'.find { it.name() == "FileName" }
        if (logo) {
            rp.logoUrl = logo['@src'].toString()
        }
        def role = xml.'**'.find { it.name() == "CI_RoleCode" }
        if (role) {
            rp.role = role["@codeListValue"].toString()
        }
        return rp
    }    
}
