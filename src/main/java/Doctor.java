import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;

public class Doctor extends Thread implements DoctorDo {

    private static AtomicInteger count = new AtomicInteger(0);
    private final int dayNorm = 5;
    private int healedToday;
    private Clinic clinic;
    private Room room;
    private int orderNum;

    public Doctor(Clinic clinic) {
        this.healedToday = 0;
        this.clinic = clinic;
        this.orderNum = count.incrementAndGet();
        Main.LOG.info("Doctor " + this.orderNum + " came to work");
    }

    @Override
    public void run() {
        while (this.healedToday < this.dayNorm) {
            if (this.room.isAvailable() && (this.clinic.getQueue().size() == 0)) {
                waitForPatient();
            } else {
                if (this.room.isAvailable()) {
                    getFromQueue();
                }
            }
            continue;
        }
        this.room.setAvailable(false);
        Main.LOG.info("Doctor " + this.orderNum + " finished work today");
    }


    public void getFromQueue() {
        Patient patient = this.clinic.getQueue().poll();
        if (patient != null) {
            this.room.setPatient(patient);
            Main.LOG.info("Doctor " + this.orderNum
                    + "take Patient " + room.getPatient().getOrderNum()
                    + " from queue to room " + this.room.roomNum);
            this.room.setAvailable(false);
            toTreat();
        }
    }

    public void waitForPatient() {
        Lock lock = this.room.getLock();
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            toTreat();
        }
    }

    private void toTreat() {
        int time = this.room.getPatient().getTimeToHeal();
        this.room.getPatient().setTimeToHeal(0);
        try {
            this.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.healedToday++;
        this.room.setAvailable(true);
        Main.LOG.info("Patient " + this.room.getPatient().getOrderNum() + " is healthy, and he went home!");
    }

    public Room getRoom() {
        return room;
    }

    public void setTimeToHeal() {
        this.room.getPatient().setTimeToHeal(0);
    }

    public void setClinica(Clinic clinic) {
        this.clinic = clinic;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

}

