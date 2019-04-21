import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Room {
    private static AtomicInteger count1 = new AtomicInteger(0);
    public int roomNum;
    private final Lock lock;
    private Doctor doctor;
    private Patient patient;
    private boolean available;


    public Room() {
        this.doctor = null;
        this.available = true;
        this.patient = null;
        this.lock = new ReentrantLock();
        this.roomNum = count1.incrementAndGet();
    }

    public boolean isAvailable() {
        return this.available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return this.doctor;
    }

    public Lock getLock() {
        return this.lock;
    }


}
