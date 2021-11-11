package edu.rogachova.common.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="OrganizationType")
@XmlEnum
public enum OrganizationType
{
    COMMERCIAL,
    PUBLIC,
    PRIVATE_LIMITED_COMPANY,
    OPEN_JOINT_STOCK_COMPANY;

    public static String nameList() {
        StringBuilder nameList = new StringBuilder();
        for (OrganizationType type: values()) {
            nameList.append(type.name()).append(", ");
        }
        return nameList.substring(0, nameList.length() - 2);
    }
}
