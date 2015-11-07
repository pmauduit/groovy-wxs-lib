package fr.beneth.cswlib.metadata

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

class Address {
    String deliveryPoint, city, postalCode, electronicMailAddress
    
    public static Address mapFromXmlFragment(NodeChild xml) {
        def addr = new Address()
        addr.deliveryPoint = xml."gmd:deliveryPoint"."gco:CharacterString".text()
        addr.city = xml."gmd:city"."gco:CharacterString".text()
        addr.postalCode = xml."gmd:postalCode"."gco:CharacterString".text()
        addr.electronicMailAddress = xml."gmd:electronicMailAddress"."gco:CharacterString".text()
        return addr
    }
    
}
