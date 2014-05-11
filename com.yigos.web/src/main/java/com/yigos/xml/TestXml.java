package com.yigos.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "TestXml")
public class TestXml {
	private String name;
	private int quanlity;
	public String getName() {
		return name;
	}
	@XmlElement
	public void setName(String name) {
		this.name = name;
	}
	public int getQuanlity() {
		return quanlity;
	}
	@XmlElement
	public void setQuanlity(int quanlity) {
		this.quanlity = quanlity;
	}
}
