package victoria.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import victoria.model.TaskList;

class TaskFileTest {
    @TempDir Path temporaryDirectory;

    @Test
    void loadInto_nullList_reportsError() {
        TaskFile.LoadResult result = TaskFile.loadInto(null);

        assertEquals(TaskFile.LoadStatus.ERROR, result.status());
        assertEquals(0, result.loadedTasks());
    }

    @Test
    void saveAndLoad_tasksRoundTripWithStatusAndDates() throws Exception {
        Path dataFile = temporaryDirectory.resolve("victoria.txt");
        TaskList source = new TaskList(10);
        source.add("Buy milk");
        source.add(new victoria.model.Deadline("Submit report", "2026-08-23"));
        source.markDone(1);

        assertTrue(TaskFile.save(source, dataFile));

        TaskList restored = new TaskList(10);
        TaskFile.LoadResult result = TaskFile.loadInto(restored, dataFile);

        assertEquals(TaskFile.LoadStatus.LOADED, result.status());
        assertEquals(2, result.loadedTasks());
        assertEquals("Buy milk", restored.getTask(1).getDescription());
        assertTrue(restored.getTask(1).isDone());
        assertEquals("2026-08-23", ((victoria.model.Deadline) restored.getTask(2)).getBy());
    }

    @Test
    void saveAfterDelete_deletedTaskIsNotRestored() throws Exception {
        Path dataFile = temporaryDirectory.resolve("victoria.txt");
        TaskList source = new TaskList(10);
        source.add("Keep this task");
        source.add("Delete this task");
        source.delete(2);
        assertTrue(TaskFile.save(source, dataFile));

        TaskList restored = new TaskList(10);
        TaskFile.loadInto(restored, dataFile);

        assertEquals(1, restored.size());
        assertEquals("Keep this task", restored.getTask(1).getDescription());
    }

    @Test
    void save_targetInsideFile_returnsFailure() throws Exception {
        Path blockingFile = temporaryDirectory.resolve("not-a-directory");
        Files.writeString(blockingFile, "blocks child files");

        assertFalse(TaskFile.save(new TaskList(1), blockingFile.resolve("victoria.txt")));
    }
}
