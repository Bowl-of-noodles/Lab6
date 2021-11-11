package edu.rogachova.common;

import edu.rogachova.common.net.CommandResult;
import edu.rogachova.common.net.Request;

public abstract class DataManager {
        public abstract CommandResult insert(Request<?> request);
        public abstract CommandResult clear(Request<?> request);
        public abstract CommandResult countLessSalary(Request<?> request);
        public abstract CommandResult filterStartWith(Request<?> request);
        public abstract CommandResult info(Request<?> request);
        public abstract CommandResult printDSCEnd(Request<?> request);
        public abstract CommandResult removeByKey(Request<?> request);
        public abstract CommandResult removeGreater(Request<?> request);
        public abstract CommandResult removeGreaterKey(Request<?> request);
        public abstract CommandResult replaceLowerKey(Request<?> request);
        public abstract CommandResult update(Request<?> request);
        public abstract CommandResult show(Request<?> request);
        public void save(){}

}
