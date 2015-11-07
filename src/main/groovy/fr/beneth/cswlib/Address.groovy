package fr.beneth.cswlib

import groovy.util.slurpersupport.NodeChild;

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

    public static Address mapFromXmlFragmentCswGetCapabilities(NodeChild xml) {
        def addr = new Address()
        addr.deliveryPoint =  xml.'ows:DeliveryPoint'.text()
        addr.city = xml.'ows:City'.text()
        addr.postalCode = xml.'ows:PostalCode'.text()
        addr.electronicMailAddress = xml.'ows:ElectronicMailAddress'.text()
        return addr
    }
    
}