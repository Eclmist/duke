package duke.io;

import duke.command.AddCommand;
import duke.command.Command;
import duke.command.DeleteCommand;
import duke.command.DoneCommand;
import duke.command.ExitCommand;
import duke.command.FindCommand;
import duke.command.ListCommand;
import duke.exception.DukeException;
import duke.task.Task;

public class Parser {

    /**
     * Parse a user's input into one or more supported Commands.
     *
     * @param input The input command.
     * @return A command created using the parsed inputs.
     * @throws DukeException If the command is not recognized.
     */
    public Command parse(String input) throws DukeException {
        if (input.equals("")) {
            // Assume is rogue enter input
            return null;
        }

        String[] inputs = input.split(" ", 2);
        String command = inputs[0];
        String args = inputs.length > 1 ? inputs[1] : "";
        int operationIndex;
        try {
            operationIndex = Integer.parseInt(args) - 1; // Human index starts from 1. Converting to real index.
        } catch (NumberFormatException e) {
            operationIndex = -1;
        }

        switch (command) {
        case "bye":
            // Fallthrough
        case "quit":
            // Fallthrough
        case "exit":
            // Fallthrough
        case "drop_module":
            // Fallthrough
        case "dropout":
            // Fallthrough
        case "die":
            // Fallthrough
        case "kys":
            // Fallthrough
        case "fuck":
            // Fallthrough
        case "withdraw_from_uni":
            return new ExitCommand();
        case "list":
            return new ListCommand();
        case "find":
            return new FindCommand(args);
        case "todo":
            return new AddCommand(Task.TaskType.TASK_TYPE_TODO, args);
        case "deadline":
            return new AddCommand(Task.TaskType.TASK_TYPE_DEADLINE, args);
        case "event":
            return new AddCommand(Task.TaskType.TASK_TYPE_EVENT, args);
        case "delete":
            return new DeleteCommand(operationIndex);
        case "done":
            return new DoneCommand(operationIndex);
        default:
            throw new DukeException("'" + command + "' is not a recognized command." +
                    "\nYou are advised to stop trying to break the system.");
        }
    }
}
