package edu.rogachova.common.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Position")
@XmlEnum
public enum Position
{
    MANAGER,
    LABORER,
    HUMAN_RESOURCES,
    COOK,
    CLEANER;

    public static String nameList() {
        StringBuilder nameList = new StringBuilder();
        for (Position position: values()) {
            nameList.append(position.name()).append(", ");
        }
        return nameList.substring(0, nameList.length() - 2);
    }
}
