import java.util.ArrayList;
import java.util.List;

/** Stores and updates the tasks belonging to the user. */
public class TaskList {
    private final List<Task> tasks = new ArrayList<>();
    private final int capacity;

    /** Creates an empty task list with the given capacity. */
    public TaskList(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Task list capacity cannot be negative");
        }
        this.capacity = capacity;
    }

    /** Adds a task when the list has capacity. */
    public boolean add(String description) {
        return add(new Task(description));
    }

    /** Adds a task object when the list has capacity. */
    public boolean add(Task task) {
        if (task == null) {
            return false;
        }
        if (task.getDescription() == null || task.getDescription().isBlank()) {
            return false;
        }
        if (tasks.size() >= capacity) {
            return false;
        }
        tasks.add(task);
        return true;
    }

    /** Marks a one-based task number as done, or returns null if it is invalid. */
    public Task markDone(int taskNumber) {
        Task task = get(taskNumber);
        if (task != null) {
            task.markDone();
        }
        return task;
    }

    /** Marks a one-based task number as not done, or returns null if it is invalid. */
    public Task markNotDone(int taskNumber) {
        Task task = get(taskNumber);
        if (task != null) {
            task.markNotDone();
        }
        return task;
    }

    /** Returns a task by its one-based number, or null if the number is invalid. */
    public Task getTask(int taskNumber) {
        return get(taskNumber);
    }

    /** Returns the number of tasks currently stored. */
    public int size() {
        return tasks.size();
    }

    /** Prints all stored tasks using one-based numbering. */
    public void printTasks() {
        System.out.println(" Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(" " + (i + 1) + "." + tasks.get(i));
        }
    }

    private Task get(int taskNumber) {
        int index = taskNumber - 1;
        return index >= 0 && index < tasks.size() ? tasks.get(index) : null;
    }
}
