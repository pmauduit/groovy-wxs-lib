package fr.beneth.wxslib

import groovy.util.slurpersupport.NodeChild

class MetadataUrl {

	String format, url

	static MetadataUrl mapFromXmlFragment(NodeChild xml) {
		MetadataUrl u = new MetadataUrl()
		u.format = xml.Format
		u.url = xml.OnlineResource["@xlink:href"]
		return u
	}

}
