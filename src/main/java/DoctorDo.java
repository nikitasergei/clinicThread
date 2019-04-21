

public interface DoctorDo {

    /*
     *Try to take patient from queue, and fix his health (timeToHeal).
     */
    void getFromQueue();

    /*
    Doctor take room and wait for patient comes.
    Then doctor fix patient's health (timeToHeal)/

     */
    void waitForPatient();

}
