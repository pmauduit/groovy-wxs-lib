package fr.beneth.wxslib.operations

import fr.beneth.wxslib.CapabilityRequest
import fr.beneth.wxslib.Layer
import fr.beneth.wxslib.Style

class Capabilities {

	// WMS_Capabilities.Service
	String name, title, _abstract

	// KeywordList
	ArrayList<String> keywords = new ArrayList<String>()

	// Contact
	String contact, contactEmail
	
	// Access constraints
	String accessConstraints

	// list of capability requests
	ArrayList<CapabilityRequest> capabilityRequests = new ArrayList<CapabilityRequest>();
	
	// list of available layers in this server
	ArrayList<Layer> layers = new ArrayList<Layer>();
	
	public Layer findLayerByName(String name) {
		for (Layer it : layers) { 
			if (it.name == name)
				return it
			Layer l = it.findLayerByName(name)
			if (l != null) return l
		}
		return null
	}
	
	public int getLayersCount() {
		int count = layers.size
		layers.each  { count += it.getLayersCount() }
		return count
	}
	
	public int getStylesCount() {
		getAllStyles().size
	}
	
	public ArrayList<Style> getAllStyles() {
		HashMap<String, Style> styles = new HashMap<String, Style>()
		layers.each {
			styles << it.getAllStyles()
		}
		styles.values()
	}
	
	public static Capabilities mapFromDocument(String uriEndpoint) {
		def xmlData = new XmlSlurper().parse(uriEndpoint)
		Capabilities cap = new Capabilities()

		// Service related informations
		cap.name = xmlData.Service.Name
		cap.title = xmlData.Service.Title
		cap._abstract = xmlData.Service.Abstract
		xmlData.Service.KeywordList.children().each { kw ->
			cap.keywords << kw
		}
		cap.contact = xmlData.Service.ContactInformation.ContactPersonPrimary.ContactPerson
		cap.contactEmail = xmlData.Service.ContactInformation.ContactElectronicMailAddress 
		cap.accessConstraints = xmlData.Service.AccessConstraints

		// Operations available on this WMS
		xmlData.Capability.Request.children().each { c ->
			cap.capabilityRequests << CapabilityRequest.mapFromXmlFragment(c)
		}
		
		// Layers available
		xmlData.Capability.children().each {
			child ->
			if (child.name() == "Layer") {
				cap.layers << Layer.mapFromXmlFragment(child)
			}
		}
		
		return cap
	}
}
