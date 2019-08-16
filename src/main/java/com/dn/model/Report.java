package com.dn.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(namespace = "report")
@XmlAccessorType(XmlAccessType.FIELD)
public class Report {

    @XmlElement(name = "rendering")
    private List<Rendering> renderingList;

    @XmlElement(name = "summary")
    private Summary summary;
}