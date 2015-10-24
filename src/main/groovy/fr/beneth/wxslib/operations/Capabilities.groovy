package fr.beneth.wxslib.operations

class Capabilities {

	// WMS_Capabilities.Service
	String name, title, _abstract

	// KeywordList
	ArrayList<String> keywords = new ArrayList<String>()

	static Capabilities mapFromDocument(String uriEndpoint) {
		def xmlData = new XmlSlurper().parse(uriEndpoint)
		Capabilities cap = new Capabilities()

		cap.name = xmlData.Service.Name
		cap.title = xmlData.Service.Title
		cap._abstract = xmlData.Service.Abstract
		xmlData.Service.KeywordList.children().each { kw ->
			cap.keywords.add(kw)
		}
		return cap
	}
}
