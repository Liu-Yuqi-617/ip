package victoria.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import victoria.model.TaskList;

class TaskFileTest {
    @Test
    void loadInto_nullList_reportsError() {
        TaskFile.LoadResult result = TaskFile.loadInto(null);

        assertEquals(TaskFile.LoadStatus.ERROR, result.status());
        assertEquals(0, result.loadedTasks());
    }

    @Test
    void saveAndLoad_tasksRoundTripWithStatusAndDates() throws Exception {
        Path dataFile = Path.of("data", "victoria.txt");
        byte[] original = Files.exists(dataFile) ? Files.readAllBytes(dataFile) : null;
        try {
            TaskList source = new TaskList(10);
            source.add("Buy milk");
            source.add(new victoria.model.Deadline("Submit report", "2026-08-23"));
            source.markDone(1);

            TaskFile.save(source);

            TaskList restored = new TaskList(10);
            TaskFile.LoadResult result = TaskFile.loadInto(restored);

            assertEquals(TaskFile.LoadStatus.LOADED, result.status());
            assertEquals(2, result.loadedTasks());
            assertEquals("Buy milk", restored.getTask(1).getDescription());
            assertTrue(restored.getTask(1).isDone());
            assertEquals("2026-08-23", ((victoria.model.Deadline) restored.getTask(2)).getBy());
        } finally {
            if (original == null) {
                Files.deleteIfExists(dataFile);
            } else {
                Files.write(dataFile, original);
            }
        }
    }
}
