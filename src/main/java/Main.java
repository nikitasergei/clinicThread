import org.apache.log4j.Logger;

public class Main {
    final static public Logger LOG = Logger.getLogger("logger");

    public static void main(String[] args) {

        Clinic clinic = new Clinic(2, 5);
        for (int i = 0; i < 15; i++) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Patient p = new Patient(clinic);
            clinic.patientCome(p);
        }
    }
}
