import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;

public class Patient extends Thread implements PatientDo {
    private static AtomicInteger count = new AtomicInteger(0);
    private Clinic clinic;
    private int orderNum;
    private int timeToHeal;


    public Patient(Clinic clinic) {
        this.timeToHeal = 1000 * (1 + new Random().nextInt(6));
        this.clinic = clinic;
        this.orderNum = count.incrementAndGet();
        Main.LOG.info("Patient № " + this.orderNum + " came to the clinic");
    }

    @Override
    public void run() {
        if (!this.clinic.getDoctors().stream().filter(Thread::isAlive).collect(Collectors.toList()).isEmpty()) {
            if (this.clinic.getQueue().isEmpty()) {
                if (enterToRoom()) {
                }
            } else {
                if (addToQueue()) {
                    if (this.timeToHeal != 0) {
                        this.clinic.getQueue().remove(this);
                        Main.LOG.info("Patient № " + this.orderNum + " waited to0 long");
                    }
                }
            }

        } else
            System.out.println("Clinic closed");

    }

    public boolean enterToRoom() {
        for (Room room : this.clinic.getClinicRoom()) {
            if (room.isAvailable()) {
                room.setPatient(this);
                room.setAvailable(false);
                Main.LOG.info("Patient " + this.orderNum + " enter to room " + room.roomNum);
                Lock lock = room.getLock();
                synchronized (lock) {
                    lock.notify();
                }
                try {
                    this.sleep(this.timeToHeal);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return true;
            }
        }
        return false;
    }


    public boolean addToQueue() {
        boolean isAdd;
        try {
            if (isAdd = this.clinic.getQueue().add(this)) {
                Main.LOG.info("Patient " + this.orderNum + " add to queue");
            }
            try {
                sleep(1000 * (10 + new Random().nextInt(30)));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IllegalStateException ex) {
            Main.LOG.info("Clinic is full, we can't do nothing for patient " + this.orderNum);
            return false;
        }

        return isAdd;
    }

    public void setTimeToHeal(int timeToHeal) {
        this.timeToHeal = timeToHeal;
    }

    public void setClinica(Clinic clinic) {
        this.clinic = clinic;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public int getTimeToHeal() {
        return timeToHeal;
    }
}
