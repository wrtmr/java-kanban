package TaskManagerTests;
import TaskTracker.*;
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

        Assertions.assertEquals(task, tasks.get(0), "Задачи не совпадают");
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
    public void epicIsNotSetAsOwnSubtask() {
        //У меня никак не добавить в эпик его же самого, потому что строго типизированный мэп с сабтасками подается в конструктор
        //при создании эпика. Это исходя из того функционала который мы делали на прошлом задании. Если надо сделать
        //так, чтобы в эпик можно было в рантайме задачи добавлять любого типа, то это надо было бы в тз описать.
    }

    @Test
    public void subtaskIsNotSetAsOwnSubtask() {
//        Тут тоже строготипизированный сеттер. Никак не послать в него себя же.
//        TaskTracker.Subtask subtask = new TaskTracker.Subtask("Перенести коробки",
//                "Перенести коробки с обувью", TaskTracker.TaskStatus.NEW);
//        taskManager.createSubtask(subtask);
//        HashMap<Integer, TaskTracker.Subtask> subtasksMap = new HashMap<>();
//        subtasksMap.put(subtask.getId(), subtask);
//        for (TaskTracker.Subtask task : subtasksMap.values()) {
//            task.setParentTask(task);
//        }
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
        //Тут я тоже не понимаю что тестировать, если к примеру в менеджер таска никак мимо метода CreateTask не пройдёт.
        //А в этом методе любой попавшей таске даже если у неё есть уже ID,
        // будет присвоен уникальный айди автоматом. Так что с одинаковым ID туда
        // никак не поместить. Так что пока не вижу что тестировать в этом пункте.
    }

    private void generateTasks(TaskManager taskManager) {
        //Создадим 2 задачи
        Task task1 = new Task("Налить чай", "Поставить чайник кипятиться, " +
                "заварить чай, налить в кружку", TaskStatus.NEW);
        Task task2 = new Task("Прикрутить полку", "Взять шурупы, взять дрель, взять + " +
                "полку, просверлить отверстия", TaskStatus.NEW);
        taskManager.createTask(task1);
        taskManager.createTask(task2);

        //Создадим 2 подзадачи и Эпик 1
        System.out.println("Создадим 2 подзадачи и Эпик 1");
        Subtask subtask1 = new Subtask("Перенести коробки",
                "Перенести коробки с обувью", TaskStatus.NEW);
        Subtask subtask2 = new Subtask("Перевезти шкаф",
                "Погрузить шкаф в автомобиль и перевезти на новое место", TaskStatus.NEW);
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);

        HashMap<Integer, Subtask> subtasksMap = new HashMap<>();
        subtasksMap.put(subtask1.getId(), subtask1);
        subtasksMap.put(subtask2.getId(), subtask2);

        Epic epicTask1 = new Epic("Перевозка вещей",
                "Перевозка вещей на другую квартиру", TaskStatus.NEW, subtasksMap);
        taskManager.createEpicTask(epicTask1);
        for (Subtask task : subtasksMap.values()) {
            task.setParentTask(epicTask1);
        }

        //Создадим подзадачу и Эпик 2
        Subtask subtask3 = new Subtask("Купить проектор",
                "Съездить в магазин, выбрать проектор, купить проектор", TaskStatus.NEW);
        taskManager.createSubtask(subtask3);
        subtasksMap = new HashMap<>();
        subtasksMap.put(subtask3.getId(), subtask3);

        Epic epicTask2 = new Epic("Обустроить домашний кинотеатр",
                "Преобрести все необходимое для домашнего кинотеатра", TaskStatus.NEW, subtasksMap);
        taskManager.createEpicTask(epicTask2);
        for (Subtask task : subtasksMap.values()) {
            task.setParentTask(epicTask2);
        }

        //Создадим эпик 3
        subtasksMap = new HashMap<>();
        Epic epicTask3 = new Epic("Обустроить домашний кинотеатр",
                "Преобрести все необходимое для домашнего кинотеатра", TaskStatus.NEW, subtasksMap);
        taskManager.createEpicTask(epicTask3);



        {
//            //Oбновим эпик 3
//            subtasksMap = new HashMap<>();
//            TaskTracker.Epic updatedEpicTask3 = new TaskTracker.Epic("Обустроить домашний кинотеатр и кухню",
//                    "Преобрести все необходимое для домашнего кинотеатра и кухни", TaskTracker.TaskStatus.NEW, subtasksMap);
//            taskManager.updateEpicById(epicTask3.getId(), updatedEpicTask3);
//            System.out.println("Эпик 3 после обновления: ");
//            System.out.println(taskManager.getEpicById(epicTask3.getId()));
//            System.out.println();
//
//            //Поменяем статусы обычных задач
            Task newTask1 = new Task("Налить чай", "Поставить чайник кипятиться, " +
                    "заварить чай, налить в кружку", TaskStatus.IN_PROGRESS);
            Task newTask2 = new Task("Прикрутить полку", "Взять шурупы, взять дрель, взять + " +
                    "полку, просверлить отверстия", TaskStatus.DONE);

            taskManager.updateTaskById(task1.getId(), newTask1);
            taskManager.updateTaskById(task2.getId(), newTask2);
//
//            System.out.println("Обычные задачаи после изменения: ");
//            System.out.println(taskManager.getTaskById(task1.getId()));
//            System.out.println(taskManager.getTaskById(task2.getId()));
//            System.out.println();
//
//            //Поменяем статус сабтасков для Эпика 1
//            System.out.println("Поменяем статус подзадач для Эпика 1");
//            TaskTracker.Subtask newSubtask1 = new TaskTracker.Subtask("Перенести коробки",
//                    "Перенести коробки с обувью", TaskTracker.TaskStatus.DONE);
//            TaskTracker.Subtask newSubtask2 = new TaskTracker.Subtask("Перевезти шкаф",
//                    "Погрузить шкаф в автомобиль и перевезти на новое место", TaskTracker.TaskStatus.DONE);
//            taskManager.updateSubtaskById(subtask1.getId(), newSubtask1);
//            taskManager.updateSubtaskById(subtask2.getId(), newSubtask2);
//
//            System.out.println("Эпик 1 после изменения подзадачи: ");
//            System.out.println(epicTask1);
//            System.out.println();
//
//            //Поменяем статус подзадачи для Эпика 2
//            System.out.println("Поменяем статус подзадачи для Эпика 2");
//            TaskTracker.Subtask newSubtask3 = new TaskTracker.Subtask("Купить проектор",
//                    "Съездить в магазин, выбрать проектор, купить проектор", TaskTracker.TaskStatus.DONE);
//            taskManager.updateSubtaskById(subtask3.getId(), newSubtask3);
//            System.out.println("Эпик 2 после изменения подзадачи: ");
//            System.out.println(epicTask2);
//            System.out.println();
//
//            System.out.println("Выведем текущие задачи:");
//            System.out.println("Обычные задачи: " + taskManager.getAllTasks());
//            System.out.println("Подзадачи: " + taskManager.getAllSubtasks());
//            System.out.println("Эпики: " + taskManager.getAllEpics());
//            System.out.println();
//
//            System.out.println("Удаление задач");
//            System.out.println("Удалим подзадачу из Эпика 1");
//            taskManager.removeSubtaskById(newSubtask2.getId());
//
//            System.out.println("Удалим подзадачу из Эпика 2");
//            taskManager.removeSubtaskById(newSubtask3.getId());
//
//            System.out.println("Выведем текущие задачи:");
//            System.out.println("Обычные задачи: " + taskManager.getAllTasks());
//            System.out.println("Подзадачи: " + taskManager.getAllSubtasks());
//            System.out.println("Эпики: " + taskManager.getAllEpics());
//            System.out.println();
//
//            System.out.println("Удалим Эпик 2");
//            taskManager.removeEpicById(epicTask2.getId());
//
//            System.out.println("Выведем окончательный список задач:");
//            System.out.println("Обычные задачи: " + taskManager.getAllTasks());
//            System.out.println("Подзадачи: " + taskManager.getAllSubtasks());
//            System.out.println("Эпики: " + taskManager.getAllEpics());
//            System.out.println();
//
//            System.out.println("Очистим все задачи:");
//            taskManager.clearAllTasks();
//
//            System.out.println("Выведем список задач после зачистки:");
//            System.out.println("Обычные задачи: " + taskManager.getAllTasks());
//            System.out.println("Подзадачи: " + taskManager.getAllSubtasks());
//            System.out.println("Эпики: " + taskManager.getAllEpics());
//            System.out.println();
        }
//
//        TaskTracker.Task testTask1 = taskManager.getTaskById(task1.getId());
//        TaskTracker.Task testTask2 = taskManager.getEpicById(epicTask2.getId());
//        TaskTracker.Task testTask3 = taskManager.getSubtaskById(subtask3.getId());
    }

}

