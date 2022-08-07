import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final long PBX_RUN_RATE = 1;
    private static final long PBX_INIT_DELAY = 1;
    private static final int CALLS_TO_CALL = 500;
    private static final byte CPS = 60;
    private static final int AGENTS_COUNT = 5;

    public static void main(String[] args) {
        CallCenter callCenter = new CallCenter();
        PBX pbx = new PBX(callCenter, CPS, CALLS_TO_CALL);

        ExecutorService agentsPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for (int i = 0; i < AGENTS_COUNT; i++) {
            agentsPool.submit(new CallCenterAgent(callCenter, i + 1));
        }

        ScheduledExecutorService pbxExecutor = Executors.newSingleThreadScheduledExecutor();
        pbxExecutor.scheduleAtFixedRate(pbx, PBX_INIT_DELAY, PBX_RUN_RATE, TimeUnit.SECONDS);

        while (!pbxExecutor.isShutdown()) {
            if (pbx.getCallsCounter() > CALLS_TO_CALL) {
                pbxExecutor.shutdownNow();
                agentsPool.shutdownNow();
            }
        }
    }
}