package tasktracker;
import tasktracker.Infrastructure.TaskStatus;
import tasktracker.managers.HistoryManager;
import tasktracker.managers.Managers;
import tasktracker.managers.TaskManager;
import tasktracker.tasks.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");
        Managers managers = new Managers();
        HistoryManager historyManager = managers.getDefaultHistory();

        File file = new File("tasks.csv");

        // Create file and write header if not exists
        if (!file.exists()) {
            try {
                file.createNewFile();
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write("id,type,name,status,description,epic\n");
                }
            } catch (IOException e) {
                System.out.println("Ошибка при создании файла.");
                e.printStackTrace();
            }
        }

        TaskManager taskManager = managers.getFileBacked(historyManager, file);


        generateTasks(taskManager);
        //printAllTasks(taskManager);

    }

    private static void printAllTasks(TaskManager manager) {
        System.out.println("Задачи:");
        for (Task task : manager.getAllTasks()) {
            System.out.println(task);
        }
        System.out.println("Эпики:");
        for (Task epic : manager.getAllEpics()) {
            System.out.println(epic);

            for (Task task : manager.getSubtasksFromEpic(epic.getId()).values()) {
                System.out.println("--> " + task);
            }
        }
        System.out.println("Подзадачи:");
        for (Task subtask : manager.getAllSubtasks()) {
            System.out.println(subtask);
        }

        if (manager.getHistory() != null) {
            System.out.println("История:");

            for (Task task : manager.getHistory()) {
                System.out.println(task);
            }
        }
    }

    private static void generateTasks(TaskManager taskManager) {
        //Пользовательский сценарий
        //Создадим 2 задачи
        Task task1 = new Task("Налить чай", "Поставить чайник кипятиться, " +
                "заварить чай, налить в кружку", TaskStatus.NEW);
        Task task2 = new Task("Прикрутить полку", "Взять шурупы, взять дрель, взять + " +
                "полку, просверлить отверстия", TaskStatus.NEW);
        taskManager.createTask(task1);
        taskManager.createTask(task2);

        //Создадим 3 подзадачи и Эпик 1
        System.out.println("Создадим 2 подзадачи и Эпик 1");
        Subtask subtask1 = new Subtask("Перенести коробки",
                "Перенести коробки с обувью", TaskStatus.NEW);
        Subtask subtask2 = new Subtask("Перевезти шкаф",
                "Погрузить шкаф в автомобиль и перевезти на новое место", TaskStatus.NEW);
        Subtask subtask3 = new Subtask("Спустить холодильник",
                "Спустить холодильник на первый этаж и погрузить в машину", TaskStatus.NEW);

        HashMap<Integer, Subtask> subtasksMap = new HashMap<>();
        subtasksMap.put(subtask1.getId(), subtask1);
        subtasksMap.put(subtask2.getId(), subtask2);
        subtasksMap.put(subtask3.getId(), subtask3);

        Epic epicTask1 = new Epic("Перевозка вещей",
                "Перевозка вещей на другую квартиру", TaskStatus.NEW, subtasksMap);
        taskManager.createEpicTask(epicTask1);

        for (Subtask task : subtasksMap.values()) {
            task.setParentTask(epicTask1);
        }

        subtask1.setParentTask(epicTask1);
        subtask2.setParentTask(epicTask1);
        subtask3.setParentTask(epicTask1);

        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);
        taskManager.createSubtask(subtask3);

        //Создадим пустой Эпик.
        subtasksMap = new HashMap<>();
        Epic epicTask2 = new Epic("Сборка нового ПК",
                "Перевозка вещей на другую квартиру", TaskStatus.NEW, subtasksMap);
        taskManager.createEpicTask(epicTask2);

        taskManager.getTaskById(task1.getId());
        taskManager.getEpicById(epicTask2.getId());
        taskManager.getTaskById(task2.getId());
        taskManager.getSubtaskById(subtask3.getId());
        taskManager.getEpicById(epicTask1.getId());

        System.out.println("История:");
        for (Task task : taskManager.getHistory()) {
            System.out.println(task);
        }

        taskManager.removeTaskById(task1.getId());

        System.out.println("История:");
        for (Task task : taskManager.getHistory()) {
            System.out.println(task);
        }

        taskManager.removeEpicById(epicTask1.getId());

        System.out.println("История:");
        for (Task task : taskManager.getHistory()) {
            System.out.println(task);
        }
    }
}
