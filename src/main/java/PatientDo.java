

public interface PatientDo {


    /*
     * Method check all rooms in clinic. If free room was found, patient notify doctor in this room, wait @timeToHeal
     * and @return true, otherwise @return false
     */
    boolean enterToRoom();

    /*
     * Inserts the patient into this queue if it is possible to do so
     * immediately without violating capacity restrictions, returning
     * {@code true} upon success and throwing an {@code IllegalStateException}
     * if no space is currently available.
     */
    boolean addToQueue();

}
