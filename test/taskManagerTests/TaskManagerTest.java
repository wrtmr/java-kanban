package taskManagerTests;
import tasktracker.Infrastructure.TaskStatus;
import tasktracker.managers.HistoryManager;
import tasktracker.managers.Managers;
import tasktracker.managers.TaskManager;
import tasktracker.tasks.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.List;

public class TaskManagerTest {

    private TaskManager taskManager;

    @BeforeEach
    public void beforeEach() {
        Managers managers = new Managers();
        HistoryManager historyManager = managers.getDefaultHistory();
        taskManager = managers.getDefault(historyManager);
    }

    @Test
    public void twoTasksAreEqualIfTheirIdsAreEqual() {
        Task task = new Task("Налить чай", "Поставить чайник кипятиться, " +
                "заварить чай, налить в кружку", TaskStatus.NEW);
        taskManager.createTask(task);
        final int taskId = task.getId();
        final Task savedTask = taskManager.getTaskById(taskId);
        Assertions.assertEquals(task, savedTask, "Задачи не равны");
    }

    @Test
    public void taskIsNotNullAfterCreating() {
        Task task = new Task("Налить чай", "Поставить чайник кипятиться, " +
                "заварить чай, налить в кружку", TaskStatus.NEW);
        taskManager.createTask(task);
        final int taskId = task.getId();
        final Task savedTask = taskManager.getTaskById(taskId);

        Assertions.assertNotNull(savedTask, "Задачи не совпадают");
    }

    @Test
    public void allAddedTasksReturnFromTaskManager() {
        Task task = new Task("Налить чай", "Поставить чайник кипятиться, " +
                "заварить чай, налить в кружку", TaskStatus.NEW);
        taskManager.createTask(task);
        final List<Task> tasks = taskManager.getAllTasks();

        Assertions.assertEquals(1, tasks.size(), "Неверное количество задач");
    }

    @Test
    public void listOfTasksReturnedIsNoNull() {
        Task task = new Task("Налить чай", "Поставить чайник кипятиться, " +
                "заварить чай, налить в кружку", TaskStatus.NEW);
        taskManager.createTask(task);
        final List<Task> tasks = taskManager.getAllTasks();

        Assertions.assertNotNull(tasks, "Задачи не возвращаются");
    }

    @Test
    public void taskAndTaskFromReturnedListAreEqual() {
        Task task = new Task("Налить чай", "Поставить чайник кипятиться, " +
                "заварить чай, налить в кружку", TaskStatus.NEW);
        taskManager.createTask(task);
        final List<Task> tasks = taskManager.getAllTasks();

        Assertions.assertEquals(task, tasks.getFirst(), "Задачи не совпадают");
    }

    @Test
    public void twoSubtasksAreEqualIfTheirIdsAreEqual() {
        Subtask subtask = new Subtask("Перенести коробки",
                "Перенести коробки с обувью", TaskStatus.NEW);
        taskManager.createSubtask(subtask);
        final int subtaskId = subtask.getId();
        final Subtask savedSubtask = taskManager.getSubtaskById(subtaskId);
        Assertions.assertEquals(subtask, savedSubtask, "Задачи не равны");
    }

    @Test
    public void twoEpicsAreEqualIfTheirIdsAreEqual() {
        Subtask subtask = new Subtask("Перенести коробки",
                "Перенести коробки с обувью", TaskStatus.NEW);
        taskManager.createSubtask(subtask);
        HashMap<Integer, Subtask> subtasksMap = new HashMap<>();
        subtasksMap.put(subtask.getId(), subtask);

        Epic epicTask = new Epic("Обустроить домашний кинотеатр",
                "Преобрести все необходимое для домашнего кинотеатра", TaskStatus.NEW, subtasksMap);

        for (Subtask task : subtasksMap.values()) {
            task.setParentTask(epicTask);
        }

        taskManager.createEpicTask(epicTask);
        final int epicId = epicTask.getId();
        final Epic savedEpic = taskManager.getEpicById(epicId);
        Assertions.assertEquals(epicTask, savedEpic, "Задачи не равны");
    }

    @Test
    public void immutableFieldsWhileCreatingNewTask() {
        Task task = new Task("Налить чай", "Поставить чайник кипятиться, " +
                "заварить чай, налить в кружку", TaskStatus.NEW);
        taskManager.createTask(task);

        final int taskId = task.getId();
        final Task savedTask = taskManager.getTaskById(taskId);

        Assertions.assertEquals(task.getId(), savedTask.getId(), "Id задач не равны");
        Assertions.assertEquals(task.getName(), savedTask.getName(), "Имя задач не равны");
        Assertions.assertEquals(task.getDescription(), savedTask.getDescription(), "Описание задач не равны");
        Assertions.assertEquals(task.getStatus(), savedTask.getStatus(), "Статусы задач не равны");
    }

    @Test
    public void checkTaskNotConflictingIdsInsideManager() {
        Task task = new Task("Налить чай", "Поставить чайник кипятиться, " +
                "заварить чай, налить в кружку", TaskStatus.NEW);
        taskManager.createTask(task);
        Task task2 = new Task("Прикрутить полку", "Взять шурупы, взять дрель, взять + " +
                "полку, просверлить отверстия", TaskStatus.NEW);
        task2.setId(task.getId());
        taskManager.createTask(task2);

        Assertions.assertNotEquals(task2.getId(), task.getId(), "Id задач равны. Они не должны быть равны");
    }

    @Test
    public void allIDsInEpicHaveSubtasks() {

        Subtask subtask1 = new Subtask("Перенести коробки",
                "Перенести коробки с обувью", TaskStatus.NEW);
        Subtask subtask2 = new Subtask("Перевезти шкаф",
                "Погрузить шкаф в автомобиль и перевезти на новое место", TaskStatus.NEW);
        Subtask subtask3 = new Subtask("Спустить холодильник",
                "Спустить холодильник на первый этаж и погрузить в машину", TaskStatus.NEW);

        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);
        taskManager.createSubtask(subtask3);

        HashMap<Integer, Subtask> subtasksMap = new HashMap<>();
        subtasksMap.put(subtask1.getId(), subtask1);
        subtasksMap.put(subtask2.getId(), subtask2);
        subtasksMap.put(subtask3.getId(), subtask2);

        Epic epicTask1 = new Epic("Перевозка вещей",
                "Перевозка вещей на другую квартиру", TaskStatus.NEW, subtasksMap);

        taskManager.createEpicTask(epicTask1);

        for (Subtask task : subtasksMap.values()) {
            task.setParentTask(epicTask1);
        }

        Subtask savedSubtask1 = taskManager.getSubtaskById(subtask1.getId());
        Subtask savedSubtask2 = taskManager.getSubtaskById(subtask2.getId());
        Subtask savedSubtask3 = taskManager.getSubtaskById(subtask3.getId());

        Assertions.assertEquals(savedSubtask1, subtask1, "Задачи не равны. Они не должны быть равны");
        Assertions.assertEquals(savedSubtask2, subtask2, "Задачи не равны. Они не должны быть равны");
        Assertions.assertEquals(savedSubtask3, subtask3, "Задачи не равны. Они не должны быть равны");
    }
}

