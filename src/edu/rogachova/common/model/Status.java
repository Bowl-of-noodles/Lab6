package edu.rogachova.common.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Status")
@XmlEnum
public enum Status
{
    FIRED,
    HIRED,
    RECOMMENDED_FOR_PROMOTION;

    public static String nameList() {
        StringBuilder nameList = new StringBuilder();
        for (Status status: values()) {
            nameList.append(status.name()).append(", ");
        }
        return nameList.substring(0, nameList.length() - 2);
    }
}
