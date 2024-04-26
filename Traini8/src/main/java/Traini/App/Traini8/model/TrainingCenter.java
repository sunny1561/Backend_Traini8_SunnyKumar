
package Traini.App.Traini8.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.Instant;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class TrainingCenter {
         //auto incrementing the id (primary key)
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        public String getCenterName() {
                return centerName;
        }


   //NotBlank used to ensure that field should not be empty
        @NotBlank
        @Size(max = 40)
        @Column(name = "center_name")
        private String centerName;

        @NotBlank
        @Size(min = 12, max = 12)
        @Pattern(regexp = "[a-zA-Z0-9]+")
        @Column(name = "center_code")
        private String centerCode;

        public String getCenterCode() {
                return centerCode;
        }


       //Embeddable will put the address in the database with only their attributes (city,pincode,state etc)
        @Embeddable
        @Data
        public static class Address {
                @NotBlank
                @Column(name = "detailed_address")
                private String detailedAddress;

                public String getCity() {
                        return city;
                }

                @NotBlank
                @Column(name = "city")
                private String city;

                public String getState() {
                        return state;
                }

                @NotBlank
                @Column(name = "state")
                private String state;

                public String getPincode() {
                        return pincode;
                }

                @NotBlank
                @Column(name = "pincode")
                @Pattern(regexp = "\\d{6}")
                private String pincode;
        }

        @Embedded
        private Address address;

        @Column(name = "student_capacity")
        private Integer studentCapacity;


        @Column(name = "created_on")
        @CreationTimestamp
        private Instant createdOn;

        public String getContactEmail() {
                return contactEmail;
        }

        public void setContactEmail(String contactEmail) {
                this.contactEmail = contactEmail;
        }

        @Email
        @Column(name = "contact_email")
        private String contactEmail;

        public String getContactPhone() {
                return contactPhone;
        }

        public void setContactPhone(String contactPhone) {
                this.contactPhone = contactPhone;
        }
        //used regular expression is used to ensure that a string consists of exactly 10 digits.
        @NotBlank
        @Pattern(regexp = "\\d{10}")
        @Column(name = "contact_phone")
        private String contactPhone;


        public Address getAddress() {
                return address;
        }



        public List<String> getCoursesOffered() {
                return coursesOffered;
        }

        @ElementCollection
        @CollectionTable(name = "center_courses", joinColumns = @JoinColumn(name = "center_id"))
        @Column(name = "course_offered")
        private List<String> coursesOffered;


}
