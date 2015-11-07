package fr.beneth.cswlib

class ServiceProvider {
    String providerName
    String providerSite
    String individualName
    Address contactInfoAddress

    public static ServiceProvider mapFromXmlFragment(xml) {
        ServiceProvider sp = new ServiceProvider()
        sp.providerName = xml.'ows:ProviderName'.text()
        sp.providerSite = xml.'ows:ProviderSite'.'@xlink:href'
        sp.individualName = xml.'ows:IndividualName'.text()

        def cia = xml.'ows:ServiceContact'.'ows:ContactInfo'.'ows:Address'
        if (cia.size()> 0) {
            sp.contactInfoAddress = Address.mapFromXmlFragmentCswGetCapabilities(cia[0])
        }
        return sp
    }
}