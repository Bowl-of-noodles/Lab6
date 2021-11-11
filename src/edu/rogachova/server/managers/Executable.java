package edu.rogachova.server.managers;

import edu.rogachova.common.net.CommandResult;
import edu.rogachova.common.net.Request;

public interface Executable {
    CommandResult execute(Request<?> request);
}
