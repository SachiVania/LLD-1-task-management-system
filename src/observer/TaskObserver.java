package observer;

import models.Task;

public interface TaskObserver {
    void update(Task task, String changeType);
}
