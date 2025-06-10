package tasktracker.managers;
import tasktracker.Exceptions.*;
import tasktracker.Infrastructure.*;
import tasktracker.tasks.Epic;
import tasktracker.tasks.Subtask;
import tasktracker.tasks.Task;

import java.io.*;

public class FileBackedTaskManager extends InMemoryTaskManager {

    private static File localFile;

    public FileBackedTaskManager(HistoryManager historyManager, File fileSaveTo) {
        super(historyManager);
        localFile = fileSaveTo;
        loadFromFile();
    }

    @Override
    public void createTask(Task task) {
        super.createTask(task);
        try {
            save(task);
        } catch (ManagerSaveException e) {
            System.out.println("Не удалось сохранить задачу: " + e.getMessage());
        }
    }

    @Override
    public void createSubtask(Subtask task) {
        super.createSubtask(task);
        try {
            save(task);
        } catch (ManagerSaveException e) {
            System.out.println("Не удалось сохранить задачу: " + e.getMessage());
        }
    }

    @Override
    public void createEpicTask(Epic task) {
        super.createEpicTask(task);
        try {
            save(task);
        } catch (ManagerSaveException e) {
            System.out.println("Не удалось сохранить задачу: " + e.getMessage());
        }
    }

    private void save(Task task) {
        TaskType taskType;
        if (task instanceof Epic) {
            taskType = TaskType.EPIC;
        } else if (task instanceof Subtask) {
            taskType = TaskType.SUBTASK;
        } else {
            taskType = TaskType.TASK;
        }

        String csvLine = createStringFromTask(task, taskType);

        try (FileWriter writer = new FileWriter(localFile, true)) { // append mode
            writer.write(csvLine + "\n");
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка сохранения задачи в файл.", e);
        }
    }

    private static String createStringFromTask(Task task, TaskType taskType) {

        String commonPart = String.format("%d,%s,%s,%s,%s",
                task.getId(),
                taskType,
                task.getName(),
                task.getStatus(),
                task.getDescription());

        String csvLine;

        if (taskType == TaskType.SUBTASK) {
            Subtask subtask = (Subtask) task;
            csvLine = String.format("%s,%d", commonPart, subtask.getParentTask().getId());
        } else {
            csvLine = String.format("%s,", commonPart);
        }

        return csvLine;
    }

    private static void loadFromFile() {
        if (!localFile.exists()) {
            throw new ManagerLoadException("Файл не существует: " + localFile.getAbsolutePath());
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(localFile))) {
            String header = reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] values = line.split(",", 6);

                int id = Integer.parseInt(values[0]);
                TaskType type = TaskType.valueOf(values[1]);
                String name = values[2];
                TaskStatus status = TaskStatus.valueOf(values[3]);
                String description = values[4];

                Task task = null;

                switch (type) {
                    case TASK:
                        task = new Task(name, description, status);
                        task.setId(id);
                        tasks.put(task.getId(), task);
                        break;
                    case EPIC:
                        task = new Epic(name, description, status);
                        task.setId(id);
                        epics.put(task.getId(), (Epic)task);
                        break;
                    case SUBTASK:
                        int epicId = Integer.parseInt(values[5]);
                        Subtask subtask = new Subtask(name, description, status);
                        subtask.setId(id);
                        subtask.setEpicId(epicId);
                        subtasks.put(subtask.getId(), subtask);
                        break;
                    default:
                        throw new ManagerLoadException("Неизвестный тип задачи: " + values[1]);
                }
            }

        } catch (FileNotFoundException e) {
            throw new ManagerLoadException("Файл не найден.", e);
        } catch (IOException e) {
            throw new ManagerLoadException("Ошибка ввода-вывода при чтении файла.", e);
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            throw new ManagerLoadException("Некорректный формат данных в файле.", e);
        }

        linkSubtasksEpics();
    }

    private static void linkSubtasksEpics() {
        for (Integer key : subtasks.keySet()) {
            if (subtasks.get(key) != null) {
                if (subtasks.get(key).getParentTask() == null) {
                    subtasks.get(key).setParentTask(epics.get(subtasks.get(key).getEpicId()));
                } else {
                    epics.get(subtasks.get(key).getEpicId()).addSubtask(subtasks.get(key));
                }
            }
        }
    }

    private static Epic getEpicByIdStatic(Integer id) {
        for (Integer key : epics.keySet()) {
            if (key.equals(id)) {
                return epics.get(key);
            }
        }

        return null;
    }
}
