package fr.beneth.wxslib

import groovy.util.slurpersupport.NodeChild

/**
 * Describes a capability request from a WxS server (GetCapability, GetMap, GetFeatureInfo ...)
 *
 * @author pmauduit
 *
 */
class CapabilityRequest {

	String name
	ArrayList<String> formats = new ArrayList<String>()

	static CapabilityRequest mapFromXmlFragment(NodeChild xml) {
		CapabilityRequest cr = new CapabilityRequest()
		cr.name = xml.name()
		xml.children().each { t ->
			if (t.name() == "Format")
				cr.formats << t
		}
		return cr
	}
	
}
