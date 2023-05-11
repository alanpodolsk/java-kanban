import manager.InMemoryTaskManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class InMemoryTaskManagerTest extends TaskManagerTest {
    @BeforeEach
    public void createManager() {
        manager = new InMemoryTaskManager();
    }
}
