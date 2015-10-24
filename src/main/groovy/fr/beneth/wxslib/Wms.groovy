package fr.beneth.wxslib

import org.w3c.dom.Document

class Wms {

	URL endpoint
	Document capabilities
	
	Wms(String endpoint) {
		this.endpoint = new URL(endpoint);
	}
	
	Wms(URL endpoint) {
		this.endpoint = endpoint;
	}

}
