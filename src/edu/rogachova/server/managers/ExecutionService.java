package edu.rogachova.server.managers;

import edu.rogachova.common.DataManager;
import edu.rogachova.common.net.CommandResult;
import edu.rogachova.common.net.Request;
import edu.rogachova.common.net.ResultStatus;

import java.util.HashMap;

public class ExecutionService
{
    private HashMap<String, Executable> commands = new HashMap<>();
    private DataManager dataManager;

    public ExecutionService(DataManager dataManager) {
        this.dataManager = dataManager;
        initCommands();
    }

    private void initCommands() {
        commands.put("clear", dataManager::clear);
        commands.put("count_less_than_salary", dataManager::countLessSalary);
        commands.put("filter_starts_with_name", dataManager::filterStartWith);
        commands.put("info", dataManager::info);
        commands.put("insert", dataManager::insert);
        commands.put("print_field_descending_end_date", dataManager::printDSCEnd);
        commands.put("remove_key", dataManager::removeByKey);
        commands.put("remove_greater_key", dataManager::removeGreaterKey);
        commands.put("remove_greater", dataManager::removeGreater);
        commands.put("replace_if_lower", dataManager::replaceLowerKey);
        commands.put("update", dataManager::update);
        commands.put("show", dataManager::show);
    }

    public CommandResult executeCommand(Request<?> request) {
        if (!commands.containsKey(request.command))
            return new CommandResult(ResultStatus.ERROR, "Такой команды на сервере нет.");
        return commands.get(request.command).execute(request);
    }
}
