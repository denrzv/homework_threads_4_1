import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CallCenter {
    private final ConcurrentLinkedQueue<CallTask> queue;

    public CallCenter() {
        queue = new ConcurrentLinkedQueue<>();
    }

    public void addCallTask(CallTask task) {
        try {
            queue.offer(task);
            System.out.println(">>> В контакт-центр поступил звонок от " + task);
        } catch (NullPointerException e) {
            System.err.println("Элемент не существует!");
        }
    }

    public Optional<CallTask> getCallTask() {
        return Optional.ofNullable(queue.poll());
    }
}
