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

	public Layer findLayerByName(String name) {
		for (Layer it : layers) {
			if (it.name == name) return it
			Layer l = it.findLayerByName(name)
			if (l != null) return l
		}
		return null
	}
	
	public int getLayersCount() {
		int count = layers.size		
		layers.each { count += it.getLayersCount() }
		return count
	}
	
	public int getStylesCount() {
		int count = styles.size
		layers.each { count += it.getStylesCount() }
		return count
	}
	
	public HashMap<String, Style> getAllStyles() {
		HashMap<String, Style> hmStyles = new HashMap<String, Style>()
		styles.each {
			hmStyles.put(it.name, it)
		}
		layers.each {  
			hmStyles << it.getAllStyles()
		}
		hmStyles
	}
	
	static Layer mapFromXmlFragment(NodeChild xml, Layer parent) {
		Layer l = mapFromXmlFragment(xml)
		l.parentLayer = parent
		return l
	}

	
	static Layer mapFromXmlFragment(NodeChild xml) {
		Layer l =  new Layer()
		l.queryable = xml["@queryable"] == 1
		l.opaque = xml["@opaque"] == 1

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
