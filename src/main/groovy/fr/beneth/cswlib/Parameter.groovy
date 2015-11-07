package fr.beneth.cswlib

class Parameter {

    String name
    ArrayList<String> values = new ArrayList<String>()

    public static Parameter mapFromXmlFragment(xml) {
        def param = new Parameter()
        param.name = xml.'@name'
        xml.'ows:Value'.each {
            param.values << it.text()
        }
        return param
    }
}
