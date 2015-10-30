package fr.beneth.wxslib

import groovy.util.slurpersupport.NodeChild

class Layer {

	boolean queryable, opaque
	String name, title, _abstract
	ArrayList<String> keywords = new ArrayList<String>()
	
	String attributionTitle
	ArrayList<MetadataUrl> metadataUrls = new ArrayList<MetadataUrl>()

	ArrayList<Style> styles = new ArrayList<Style>()

	Layer parentLayer = null
			
	// A layer can contain sub-layers
	ArrayList<Layer> layers = new ArrayList<Layer>()

	Layer findLayerByName(String name) {
		for (Layer it : layers) {
			if (it.name == name) return it
			Layer l = it.findLayerByName(name)
			if (l != null) return l
		}
		return null
	}
	
	static Layer mapFromXmlFragment(NodeChild xml, Layer parent) {
		Layer l = mapFromXmlFragment(xml)
		l.parentLayer = parent
		return l
	}

	
	static Layer mapFromXmlFragment(NodeChild xml) {
		Layer l =  new Layer()
		l.queryable = xml["@queryable"]
		l.opaque = xml["@opaque"]
		
		l.name = xml.Name
		l.title = xml.Title
		l._abstract = xml.Abstract
		xml.KeywordList.children().each { kw ->
			l.keywords << kw
		}
		
		l.attributionTitle = xml.Attribution.Title

		xml.children().each {
			child ->
			if (child.name() == "Layer") {
				l.layers << Layer.mapFromXmlFragment(child, l)
			} else if (child.name() == "Style") {
				l.styles << Style.mapFromXmlFragment(child)
			} else if (child.name() == "MetadataURL") {
				l.metadataUrls << MetadataUrl.mapFromXmlFragment(child)
			}
		}
		return l
	}
	
}
