package fr.beneth.wxslib;

import groovy.util.slurpersupport.NodeChild;

public class BoundingBox {

    String crs, minx, miny, maxx, maxy
    
    public static BoundingBox mapFromXmlElement(NodeChild xml) {
        BoundingBox ret = new BoundingBox()
        ret.crs = xml["@CRS"]
        ret.minx = xml["@minx"]
        ret.miny = xml["@miny"]
        ret.maxx = xml["@maxx"]
        ret.maxy = xml["@maxy"]
        return ret
    }
}
