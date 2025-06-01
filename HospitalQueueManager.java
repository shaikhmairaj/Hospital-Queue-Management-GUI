import java.util.*;

public class HospitalQueueManager {
    PriorityQueue<Patient> queue;
    List<Patient> dischargedPatients;

    public HospitalQueueManager() {
        queue = new PriorityQueue<>();
        dischargedPatients = new ArrayList<>();
    }

    public void addPatient(Patient p) {
        queue.add(p);
    }

    public Patient callNextPatient() {
        Patient next = queue.poll();
        if (next != null) {
            dischargedPatients.add(next); // Add to discharged list
        }
        return next;
    }

    public Patient findPatient(String name, int age) {
        for (Patient p : queue) {
            if (p.name.equalsIgnoreCase(name) && p.age == age) {
                return p;
            }
        }
        return null;
    }

    public List<Patient> getDischargedPatients() {
        return dischargedPatients;
    }
}
