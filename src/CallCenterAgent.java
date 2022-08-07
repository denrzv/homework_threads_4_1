import java.util.Optional;

public class CallCenterAgent implements Runnable {
    private final CallCenter callCenter;
    private final long SLEEP_TIMER = 3000;
    private final int id;

    public CallCenterAgent(CallCenter callCenter, int id) {
        this.callCenter = callCenter;
        this.id = id;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            Optional<CallTask> task = callCenter.getCallTask();
            task.ifPresentOrElse(
                    call -> System.out.println("Оператор " + id + " -> Принят звонок от " + call),
                    () -> System.out.println("Оператор " + id + " -> Нет входящих звонков, отдыхаю.")
            );
            try {
                Thread.sleep(SLEEP_TIMER);
            } catch (InterruptedException e) {
                System.out.println("Оператор " + id + " завершает рабочий день");
                Thread.currentThread().interrupt();
            }
        }
    }
}
