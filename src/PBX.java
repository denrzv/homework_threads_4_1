import java.util.Random;

public class PBX implements Runnable {
    private final CallCenter callCenter;
    private final long PHONE_ORIGIN = 900_000_00_00L;
    private final long PHONE_BOUND = 999_000_00_00L;
    private final byte CPS;
    private final int CALLS_TO_CALL;
    private int callsCounter;

    public PBX(CallCenter callCenter, byte CPS, int callsToCall) {
        this.callCenter = callCenter;
        this.CPS = CPS;
        CALLS_TO_CALL = callsToCall;
    }

    @Override
    public void run() {
        try {
            int currentCallsCount = 0;
            while (callsCounter < CALLS_TO_CALL & currentCallsCount < CPS & !Thread.currentThread().isInterrupted()) {
                for (int i = 0; i < CPS; i++) {
                    callCenter.addCallTask(createCallTask());
                    currentCallsCount++;
                    callsCounter++;
                }
                if (callsCounter > CALLS_TO_CALL) throw new InterruptedException();
            }
        } catch (InterruptedException e) {
            System.out.println("АТС останавливается...");
            Thread.currentThread().interrupt();
        }
    }

    private CallTask createCallTask() {
        return new CallTask(createPhoneNumber(PHONE_ORIGIN, PHONE_BOUND));
    }

    private long createPhoneNumber(long origin, long bound) {
        return new Random().nextLong(origin, bound);
    }

    public int getCallsCounter() {
        return callsCounter;
    }
}
