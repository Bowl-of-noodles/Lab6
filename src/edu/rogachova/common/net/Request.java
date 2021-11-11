package edu.rogachova.common.net;

import java.io.Serializable;

public class Request<T> implements Serializable
{
    public final String command;
    /**
     * Передаваемая в запросе сущность
     */
    public final T entity;

    public Request(String command, T entity) {
        this.command = command;
        this.entity = entity;
    }
}
