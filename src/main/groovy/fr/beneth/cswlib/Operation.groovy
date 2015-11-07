package fr.beneth.cswlib

import groovy.util.slurpersupport.NodeChild

class Operation {

    String name
    ArrayList<Parameter> parameters = new ArrayList<Parameter>()
    
    public static Operation mapFromXmlFragment(NodeChild xml) {
        Operation op = new Operation()
        op.name = xml.'@name'
        xml.'ows:Parameter'.each {
            op.parameters << Parameter.mapFromXmlFragment(it)
        }
        return op
    }
}
