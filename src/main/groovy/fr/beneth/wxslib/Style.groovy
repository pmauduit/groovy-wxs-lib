package fr.beneth.wxslib

import groovy.util.slurpersupport.NodeChild

class Style {
	String name, title, _abstract
	LegendUrl legendUrl
	
	static mapFromXmlFragment(NodeChild xml) {
		def style = new Style()
		style.name = xml.Name
		style.title = xml.Title
		style._abstract = xml.Abstract

		def lgdUrl = new LegendUrl()
		lgdUrl.width = xml.LegendURL["@width"].toInteger()
		lgdUrl.height = xml.LegendURL["@height"].toInteger()
		lgdUrl.format = xml.LegendURL.Format
		lgdUrl.url = xml.LegendURL.OnlineResource["@xlink:href"]

		style.legendUrl = lgdUrl

		return style
	}
}

class LegendUrl {
	String format
	int width, height
	String url
}