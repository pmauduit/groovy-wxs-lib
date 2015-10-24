package fr.beneth.wxslib.operations

import fr.beneth.wxslib.CapabilityRequest
import fr.beneth.wxslib.Layer

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
	
	static Capabilities mapFromDocument(String uriEndpoint) {
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
