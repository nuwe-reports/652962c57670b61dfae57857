package com.example.demo;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.example.demo.entities.Appointment;
import com.example.demo.entities.Doctor;
import com.example.demo.entities.Patient;
import com.example.demo.entities.Room;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestInstance(Lifecycle.PER_CLASS)
class EntityUnitTest {

    @Autowired
    private TestEntityManager entityManager;

    private Doctor d1;

    private Patient p1;

    private Room r1;

    private Appointment a1;
    private Appointment a2;
    private Appointment a3;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

    // Create objects for d1, p1 ,r1 and a1,a2 and a3
    @BeforeAll
    public void setup() {
        d1 = new Doctor("Juan", "Perez Romero", 35, "juan.perez@email.com");
        p1 = new Patient("Maria", "Ponce Lopez", 52, "maria.ponce@email.com");
        r1 = new Room("Pediatric");
        a1 = new Appointment(p1, d1, r1, LocalDateTime.parse("19:00 24/04/2023", formatter),
                LocalDateTime.parse("19:35 24/04/2023", formatter));
        a2 = new Appointment(p1, d1, r1, LocalDateTime.parse("17:00 24/04/2023", formatter),
                LocalDateTime.parse("17:45 24/04/2023", formatter));
        a3 = new Appointment(p1, d1, r1, LocalDateTime.parse("19:00 25/04/2023", formatter),
                LocalDateTime.parse("19:35 25/04/2023", formatter));
    }

    // Test for doctor
    @Test
    public void should_create_doctor() {
        d1 = new Doctor("Juan", "Perez Romero", 35, "juan.perez@email.com");
        entityManager.merge(d1);
        entityManager.persist(d1);

        Doctor foundDoctor = entityManager.find(Doctor.class, d1.getId());

        assertThat(foundDoctor).isEqualTo(d1);
    }

    // Test for empty doctor constructor
    @Test
    public void should_create_doctor_empty_constructor() {
        Doctor doctor = new Doctor();
        assertThat(doctor).isNotNull();
    }

    // Test for doctor getId
    @Test
    public void should_test_doctor_getId() {
        Long id = d1.getId();

        assertThat(id).isNotNull();
    }

    // Test for doctor setId
    @Test
    public void should_test_doctor_setId() {
        d1.setId(1);

        assertThat(d1.getId()).isEqualTo(1);
    }

    // Test for patient
    @Test
    public void should_create_a_patient() {
        p1 = new Patient("Maria", "Ponce Lopez", 52, "maria.ponce@email.com");
        entityManager.persist(p1);

        assertThat(p1.getFirstName()).isEqualTo("Maria");
        assertThat(p1.getLastName()).isEqualTo("Ponce Lopez");
        assertThat(p1.getAge()).isEqualTo(52);
        assertThat(p1.getEmail()).isEqualTo("maria.ponce@email.com");
    }

    // Test for patient empty constructor
    @Test
    public void should_create_a_patient_empty_constructor() {
        Patient patient = new Patient();
        assertThat(patient).isNotNull();
    }

    // Test for patient getId
    @Test
    public void should_test_patient_getId() {

        Long id = p1.getId();

        assertThat(id).isNotNull();

    }

    // Test for patient setId
    @Test
    public void should_test_patient_setId() {

        p1.setId(1);

        assertThat(d1.getId()).isEqualTo(1);

    }

    // Test for room
    @Test
    public void should_create_a_room() {
        r1 = new Room("Pediatric");
        entityManager.persist(r1);

        assertThat(r1.getRoomName()).isEqualTo("Pediatric");
    }

    /**
     * Tests for Appointment Entity class
     */

    // Test for empty constructor
    @Test
    public void should_create_an_appointment_empty_constructor() {

        Appointment appointment = new Appointment();
        assertThat(appointment).isNotNull();
    }

    // Test for appointment constructor
    @Test
    public void should_create_an_appointment() {

        d1 = new Doctor("Juan", "Perez Romero", 35, "juan.perez@email.com");
        p1 = new Patient("Maria", "Ponce Lopez", 52, "maria.ponce@email.com");
        r1 = new Room("Pediatric");

        a1 = new Appointment(p1, d1, r1, LocalDateTime.parse("19:00 24/04/2023", formatter),
                LocalDateTime.parse("19:35 24/04/2023", formatter));

        entityManager.persist(a1);

        assertThat(a1.getPatient()).isEqualTo(p1);
        assertThat(a1.getDoctor()).isEqualTo(d1);
        assertThat(a1.getRoom()).isEqualTo(r1);
        assertThat(a1.getStartsAt()).isEqualTo(LocalDateTime.parse("19:00 24/04/2023", formatter));
        assertThat(a1.getFinishesAt()).isEqualTo(LocalDateTime.parse("19:35 24/04/2023", formatter));
    }

    // Test for Appointment getId
    @Test
    public void should_test_appointment_getId() {

        Long id = a1.getId();

        assertThat(id).isNotNull();
    }

    // Test for appointment setId
    @Test
    public void should_test_appointment_setId() {

        a1.setId(1);

        assertThat(a1.getId()).isEqualTo(1);
    }

    // Test for getStartsAt
    @Test
    public void should_test_appointment_getStartsAt() {

        LocalDateTime startsAt = a1.getStartsAt();

        assertThat(startsAt).isNotNull();
    }

    // Test for setStartsAt
    @Test
    public void should_test_appointment_setStartsAt() {

        LocalDateTime startsAt = a1.getStartsAt();

        a1.setStartsAt(startsAt);

        assertThat(a1.getStartsAt()).isEqualTo(startsAt);
    }

    // Test for getFinishesAt
    @Test
    public void should_test_appointment_getFinishesAt() {

        LocalDateTime finishesAt = a1.getFinishesAt();

        assertThat(finishesAt).isNotNull();

    }

    // Test for setFinishesAt
    @Test
    public void should_test_appointment_setFinishesAt() {

        LocalDateTime finishesAt = a1.getFinishesAt();

        a1.setFinishesAt(finishesAt);

        assertThat(a1.getFinishesAt()).isEqualTo(finishesAt);

    }

    // Test getPatient
    @Test
    public void should_test_appointment_getPatient() {

        Patient patient = a1.getPatient();

        assertThat(patient).isNotNull();
    }

    // Test setPatient
    @Test
    public void should_test_appointment_setPatient() {

        Patient patient = a1.getPatient();

        a1.setPatient(patient);

        assertThat(a1.getPatient()).isEqualTo(patient);

    }

    // Test getDoctor
    @Test
    public void should_test_appointment_getDoctor() {

        Doctor doctor = a1.getDoctor();

        assertThat(doctor).isNotNull();
    }

    // Test setDoctor
    @Test
    public void should_test_appointment_setDoctor() {

        Doctor doctor = a1.getDoctor();

        a1.setDoctor(doctor);

        assertThat(a1.getDoctor()).isEqualTo(doctor);
    }

    // Test getRoom
    @Test
    public void should_test_appointment_getRoom() {

        Room room = a1.getRoom();

        assertThat(room).isNotNull();
    }

    // Test setRoom
    @Test
    public void should_test_appointment_setRoom() {

        Room room = a1.getRoom();

        a1.setRoom(room);

        assertThat(a1.getRoom()).isEqualTo(room);
    }

    // Test overlaps Appointment
    @Test
    public void should_test_appointment_overlaps() {

        a1 = new Appointment(p1, d1, r1, LocalDateTime.parse("19:00 24/04/2023", formatter),
                LocalDateTime.parse("19:35 24/04/2023", formatter));
        a2 = new Appointment(p1, d1, r1, LocalDateTime.parse("19:30 24/04/2023", formatter),
                LocalDateTime.parse("19:45 24/04/2023", formatter));
        a3 = new Appointment(p1, d1, r1, LocalDateTime.parse("19:00 25/04/2023", formatter),
                LocalDateTime.parse("19:35 25/04/2023", formatter));

        boolean overlaps = a1.overlaps(a2);

        assertThat(overlaps).isTrue();

        boolean overlaps2 = a1.overlaps(a3);

        assertThat(overlaps2).isFalse();

    }
}
