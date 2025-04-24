import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");
        TaskManager taskManager = new TaskManager();

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
        for(Subtask task : subtasksMap.values()){
            task.setParentTask(epicTask1);
        }
        System.out.println("Распечатаем все задачи Эпика 1");
        System.out.println(taskManager.getSubtasksFromEpic(epicTask1));
        System.out.println();

        //Создадим подзадачу и Эпик 2
        Subtask subtask3 = new Subtask("Купить проектор",
                "Съездить в магазин, выбрать проектор, купить проектор", TaskStatus.NEW);
        taskManager.createSubtask(subtask3);
        subtasksMap = new HashMap<>();
        subtasksMap.put(subtask3.getId(), subtask3);

        Epic epicTask2 = new Epic("Обустроить домашний кинотеатр",
                "Преобрести все необходимое для домашнего кинотеатра", TaskStatus.NEW, subtasksMap);
        taskManager.createEpicTask(epicTask2);
        for(Subtask task : subtasksMap.values()){
            task.setParentTask(epicTask2);
        }

        //Создадим эпик 3
        subtasksMap = new HashMap<>();
        Epic epicTask3 = new Epic("Обустроить домашний кинотеатр",
                "Преобрести все необходимое для домашнего кинотеатра", TaskStatus.NEW, subtasksMap);
        taskManager.createEpicTask(epicTask3);
        System.out.println("Эпик 3 после создания: ");
        System.out.println(taskManager.getEpicById(epicTask3.getId()));
        System.out.println();

        //Oбновим эпик 3
        subtasksMap = new HashMap<>();
        Epic updatedEpicTask3 = new Epic("Обустроить домашний кинотеатр и кухню",
                "Преобрести все необходимое для домашнего кинотеатра и кухни", TaskStatus.NEW, subtasksMap);
        taskManager.updateEpicById(epicTask3.getId(), updatedEpicTask3);
        System.out.println("Эпик 3 после обновления: ");
        System.out.println(taskManager.getEpicById(epicTask3.getId()));
        System.out.println();

        //Поменяем статусы обычных задач
        Task newTask1 = new Task("Налить чай", "Поставить чайник кипятиться, " +
                "заварить чай, налить в кружку", TaskStatus.IN_PROGRESS);
        Task newTask2 = new Task("Прикрутить полку", "Взять шурупы, взять дрель, взять + " +
                "полку, просверлить отверстия", TaskStatus.DONE);

        taskManager.updateTaskById(task1.getId(), newTask1);
        taskManager.updateTaskById(task2.getId(), newTask2);

        System.out.println("Обычные задачаи после изменения: ");
        System.out.println(taskManager.getTaskById(task1.getId()));
        System.out.println(taskManager.getTaskById(task2.getId()));
        System.out.println();

        //Поменяем статус сабтасков для Эпика 1
        System.out.println("Поменяем статус подзадач для Эпика 1");
        Subtask newSubtask1 = new Subtask("Перенести коробки",
                "Перенести коробки с обувью", TaskStatus.DONE);
        Subtask newSubtask2 = new Subtask("Перевезти шкаф",
                "Погрузить шкаф в автомобиль и перевезти на новое место", TaskStatus.DONE);
        taskManager.updateSubtaskById(subtask1.getId(), newSubtask1);
        taskManager.updateSubtaskById(subtask2.getId(), newSubtask2);

        System.out.println("Эпик 1 после изменения подзадачи: ");
        System.out.println(epicTask1);
        System.out.println();

        //Поменяем статус подзадачи для Эпика 2
        System.out.println("Поменяем статус подзадачи для Эпика 2");
        Subtask newSubtask3 = new Subtask("Купить проектор",
                "Съездить в магазин, выбрать проектор, купить проектор", TaskStatus.DONE);
        taskManager.updateSubtaskById(subtask3.getId(), newSubtask3);
        System.out.println("Эпик 2 после изменения подзадачи: ");
        System.out.println(epicTask2);
        System.out.println();

        System.out.println("Выведем текущие задачи:");
        System.out.println("Обычные задачи: " + taskManager.getAllTasks());
        System.out.println("Подзадачи: " + taskManager.getAllSubtasks());
        System.out.println("Эпики: " + taskManager.getAllEpics());
        System.out.println();

        System.out.println("Удаление задач");
        System.out.println("Удалим подзадачу из Эпика 1");
        taskManager.removeSubtaskById(newSubtask2.getId());

        System.out.println("Удалим подзадачу из Эпика 2");
        taskManager.removeSubtaskById(newSubtask3.getId());

        System.out.println("Выведем текущие задачи:");
        System.out.println("Обычные задачи: " + taskManager.getAllTasks());
        System.out.println("Подзадачи: " + taskManager.getAllSubtasks());
        System.out.println("Эпики: " + taskManager.getAllEpics());
        System.out.println();

        System.out.println("Удалим Эпик 2");
        taskManager.removeEpicById(epicTask2.getId());

        System.out.println("Выведем окончательный список задач:");
        System.out.println("Обычные задачи: " + taskManager.getAllTasks());
        System.out.println("Подзадачи: " + taskManager.getAllSubtasks());
        System.out.println("Эпики: " + taskManager.getAllEpics());
        System.out.println();

        System.out.println("Очистим все задачи:");
        taskManager.clearAllTasks();

        System.out.println("Выведем список задач после зачистки:");
        System.out.println("Обычные задачи: " + taskManager.getAllTasks());
        System.out.println("Подзадачи: " + taskManager.getAllSubtasks());
        System.out.println("Эпики: " + taskManager.getAllEpics());
        System.out.println();
    }
}
