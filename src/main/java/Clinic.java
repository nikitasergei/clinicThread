import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;


public class Clinic {

    private Collection<Room> clinicRoom = new ArrayList<Room>();
    private Queue<Patient> queue;
    private Set<Doctor> doctors;
    private int queueQuantity;


    public Clinic(int roomQuantity, int queueQuantity) {
        this.queue = new ArrayBlockingQueue<>(queueQuantity);
        this.queueQuantity = queueQuantity;
        doctors = new HashSet<>();
        for (int i = 0; i < roomQuantity; i++) {
            clinicRoom.add(new Room());
        }
        for (Room room : clinicRoom) {
            Doctor doc = new Doctor(this);
            doctors.add(doc);
            doc.setRoom(room);
            doc.start();
        }
        Main.LOG.info("Clinic is open");
    }

    public void patientCome(Patient patient) {
        patient.start();
    }

    public Collection<Room> getClinicRoom() {
        return clinicRoom;
    }

    public Queue<Patient> getQueue() {
        return this.queue;
    }

    public void setQueue(Queue<Patient> queue) {
        this.queue = queue;
    }

    public Set<Doctor> getDoctors() {
        return doctors;
    }

    public void setClinicRoom(Collection<Room> clinicRoom) {
        this.clinicRoom = clinicRoom;
    }

    public int getQueueQuantity() {
        return queueQuantity;
    }


}
