public class CallTask {
    private final long phoneNumber;

    public CallTask(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return Long.toString(phoneNumber);
    }
}
