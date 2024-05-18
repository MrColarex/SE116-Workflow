import java.util.*;

public class TaskAssignment {

    // Görevi işleyecek uygun bir istasyonu bulma metodu
    public static Station findSuitableStation(Job job, List<Station> stations) {
        List<Station> suitableStations = new ArrayList<>();

        // Görevin türüne uygun olan istasyonları belirleme
        for (Station station : stations) {
            if (station.canProcessJobType(job.getJobType())) {
                suitableStations.add(station);
            }
        }

        // Uygun istasyonlardan birini seçme
        if (!suitableStations.isEmpty()) {
            // Basitçe ilk uygun istasyonu seçme
            return suitableStations.get(0);
        } else {
            return null; // Uygun istasyon yoksa null döndürme
        }
    }

    // Bir göreve istasyon atama işlemini gerçekleştiren metot
    public static void assignStationToJob(Job job, List<Station> stations) {
        Station station = findSuitableStation(job, stations);
        if (station != null) {
            station.addJob(job); // Görevi uygun istasyona ata
            System.out.println("Job " + job.getJobID() + " assigned to station " + station.getStationID());
        } else {
            System.out.println("No suitable station found for job " + job.getJobID());
        }
    }
}
