package Traini.App.Traini8.TrainiController;
import Traini.App.Traini8.model.TrainiRepo;
import Traini.App.Traini8.model.TrainingCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
public class TrainingCenterService {
    //repo to store the data in the database (ORM)
    @Autowired
    private final TrainiRepo trainingCenterRepository;

    public TrainingCenterService(TrainiRepo trainingCenterRepository) {
        this.trainingCenterRepository = trainingCenterRepository;
    }


@PostMapping("/create_center")
public ResponseEntity<?> createTrainingCenter(@RequestBody TrainingCenter trainingCenter) {
    try {
        // Check if the center code is valid (12 characters)
        if (trainingCenter.getCenterCode().length() != 12) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Center code must be 12 characters long");
        }

        // Check if the email is already in use
        if (trainingCenterRepository.findByContactEmail(trainingCenter.getContactEmail()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists");
        }

        // Check if the phone number is already in use
        if (trainingCenterRepository.findByContactPhone(trainingCenter.getContactPhone()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Phone number already exists");
        }

        TrainingCenter savedTrainingCenter = trainingCenterRepository.save(trainingCenter);
        String message = "New training center information saved successfully!";
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("trainingCenter", savedTrainingCenter, "message", message));
    } catch (Exception e) {
        // Handle any unexpected errors
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An internal server error occurred");
    }

}
   //you can get all created Training center by hitting below api
    @GetMapping("/get_centers")
    public ResponseEntity<List<TrainingCenter>> getAllTrainingCenters() {
        List<TrainingCenter> allTrainingCenters = trainingCenterRepository.findAll();
        return ResponseEntity.ok(allTrainingCenters);
    }
    //if you want to filter Training center then you can use below api
    @GetMapping("/GetAllCenterUsingFilter")
    public ResponseEntity<List<TrainingCenter>> getAllTrainingCenters(@RequestParam(value = "filter", required = false) String filter) {
        try {
            // for validation checking
            List<TrainingCenter> trainingCenters = trainingCenterRepository.findAll();

            if (filter != null && !filter.isEmpty()) {
                // Split the filter string by the colon to get the filter criteria
                String[] key_value= filter.split(":");
                if (key_value.length == 2) {
                    String filterField = key_value[0];
                    String filterValue = key_value[1];

                    // Filter the list based on the provided criteria using stream api
                    trainingCenters = trainingCenters.stream()
                            .filter(centers -> {
                                switch (filterField) {
                                    case "center_code" :
                                        return centers.getCenterCode().contains(filterValue);
                                    case "center_name":
                                        return centers.getCenterName().equalsIgnoreCase(filterValue);
                                    case "city":
                                        return centers.getAddress().getCity().equalsIgnoreCase(filterValue);
                                    case "state":
                                        return centers.getAddress().getState().equalsIgnoreCase(filterValue);
                                    case "pincode":
                                        return centers.getAddress().getPincode().equalsIgnoreCase(filterValue);
                                    case "courses":
                                        return centers.getCoursesOffered().contains(filterValue);
                                    default:
                                        return true;
                                }
                            })
                            .collect(Collectors.toList());
                }
            }

            return ResponseEntity.ok(trainingCenters);
        } catch (Exception e) {
            // for error handle or any bad request
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



    //for fun just to see whether api working or not
    @GetMapping("/test")
    public String test(){
        return "hello world";
    }
}
