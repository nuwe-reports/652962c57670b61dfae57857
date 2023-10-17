
package com.example.demo;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.controllers.DoctorController;
import com.example.demo.controllers.PatientController;
import com.example.demo.controllers.RoomController;
import com.example.demo.entities.Doctor;
import com.example.demo.entities.Patient;
import com.example.demo.entities.Room;
import com.example.demo.repositories.DoctorRepository;
import com.example.demo.repositories.PatientRepository;
import com.example.demo.repositories.RoomRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(DoctorController.class)
class DoctorControllerUnitTest {

    @MockBean
    private DoctorRepository doctorRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldCreateDoctor() throws Exception {
        Doctor newDoctor = new Doctor("Maria", "Zambrano", 30, "maria@example.com");

        mockMvc.perform(post("/api/doctor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newDoctor)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldNotCreateDoctor() throws Exception {

        Doctor newDoctor = new Doctor(null, "Zambrano", 30, "maria@example.com");

        mockMvc.perform(post("/api/doctor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newDoctor)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldGetAllDoctors() throws Exception {

        List<Doctor> doctors = new ArrayList<Doctor>();
        when(doctorRepository.findAll()).thenReturn(doctors);
        mockMvc.perform(get("/api/doctors"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetDoctorById() throws Exception {

        Doctor newDoctor = new Doctor("Maria", "Zambrano", 30, "maria@example.com");

        newDoctor.setId(1);

        Optional<Doctor> opt = Optional.of(newDoctor);

        assertThat(opt).isPresent();
        assertThat(opt.get().getId()).isEqualTo(newDoctor.getId());
        assertThat(newDoctor.getId()).isEqualTo(1);

        when(doctorRepository.findById(newDoctor.getId())).thenReturn(opt);
        mockMvc.perform(get("/api/doctors/" + newDoctor.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetNoDoctors() throws Exception {

        List<Doctor> doctors = new ArrayList<Doctor>();
        when(doctorRepository.findAll()).thenReturn(doctors);
        mockMvc.perform(get("/api/doctors"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldGetNoDoctorsById() throws Exception {

        long doctorId = 20;

        mockMvc.perform(get("/api/doctors/" + doctorId))
                .andExpect(status().isNotFound());

    }

    @Test
    public void shouldDeleteDoctorById() throws Exception {

        Doctor newDoctor = new Doctor("Maria", "Zambrano", 30, "maria@example.com");

        newDoctor.setId(1);

        Optional<Doctor> opt = Optional.of(newDoctor);

        assertThat(opt).isPresent();
        assertThat(opt.get().getId()).isEqualTo(newDoctor.getId());
        assertThat(newDoctor.getId()).isEqualTo(1);

        when(doctorRepository.findById(newDoctor.getId())).thenReturn(opt);
        mockMvc.perform(delete("/api/doctors/" + newDoctor.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldNotDeleteDoctor() throws Exception {

        long doctorId = 20;

        mockMvc.perform(delete("/api/doctors/" + doctorId))
                .andExpect(status().isNotFound());

    }

    @Test
    public void shouldDeleteDoctors() throws Exception {
        List<Doctor> doctors = new ArrayList<Doctor>();
        mockMvc.perform(delete("/api/doctors"))
                .andExpect(status().isOk());
    }
}

@WebMvcTest(PatientController.class)
class PatientControllerUnitTest {

    @MockBean
    private PatientRepository patientRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldGetPatients() throws Exception {

        List<Patient> patients = new ArrayList<Patient>();
        when(patientRepository.findAll()).thenReturn(patients);
        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetNoPatients() throws Exception {
        List<Patient> patients = new ArrayList<Patient>();
        when(patientRepository.findAll()).thenReturn(patients);
        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldCreatePatient() throws Exception {

        Patient newPatient = new Patient("Maria", "Zambrano", 30, "maria@example.com");

        mockMvc.perform(post("/api/patient")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newPatient)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetPatientById() throws Exception {

        Patient newPatient = new Patient("Maria", "Zambrano", 30, "maria@example.com");

        newPatient.setId(1);

        Optional<Patient> opt = Optional.of(newPatient);

        assertThat(opt).isPresent();
        assertThat(opt.get().getId()).isEqualTo(newPatient.getId());
        assertThat(newPatient.getId()).isEqualTo(1);

        when(patientRepository.findById(newPatient.getId())).thenReturn(opt);
        mockMvc.perform(get("/api/patients/" + newPatient.getId()))
                .andExpect(status().isOk());

    }

    @Test
    public void shouldNoCreatePatient() throws Exception {

        Patient newPatient = new Patient(null, "Zambrano", 30, "maria@example.com");

        mockMvc.perform(post("/api/patient")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newPatient)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldGetNoPatientById() throws Exception {

        long patientId = 20;

        mockMvc.perform(get("/api/patients/" + patientId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldDeletePatientById() throws Exception {

        Patient newPatient = new Patient("Maria", "Zambrano", 30, "maria@example.com");

        newPatient.setId(1);

        Optional<Patient> opt = Optional.of(newPatient);

        assertThat(opt).isPresent();
        assertThat(opt.get().getId()).isEqualTo(newPatient.getId());
        assertThat(newPatient.getId()).isEqualTo(1);

        when(patientRepository.findById(newPatient.getId())).thenReturn(opt);
        mockMvc.perform(delete("/api/patients/" + newPatient.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldNotDeletePatient() throws Exception {

        long patientId = 20;

        mockMvc.perform(delete("/api/patients/" + patientId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldDelteAllPatients() throws Exception {
        
        mockMvc.perform(delete("/api/patients"))
                .andExpect(status().isOk());
    }
}

@WebMvcTest(RoomController.class)
class RoomControllerUnitTest {

    @MockBean
    private RoomRepository roomRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldGetRooms() throws Exception {

        List<Room> rooms = new ArrayList<Room>();
        when(roomRepository.findAll()).thenReturn(rooms);
        mockMvc.perform(get("/api/rooms"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetNoRooms() throws Exception {
        List<Room> rooms = new ArrayList<Room>();
        when(roomRepository.findAll()).thenReturn(rooms);
        mockMvc.perform(get("/api/rooms"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldCreateRoom() throws Exception {

        Room newRoom = new Room("Dermatology");

        mockMvc.perform(post("/api/room")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newRoom)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetRoomName() throws Exception {

        Room newRoom = new Room("Dermatology");

        Optional<Room> opt = Optional.of(newRoom);

        assertThat(opt).isPresent();
        assertThat(opt.get().getRoomName()).isEqualTo(newRoom.getRoomName());
        assertThat(newRoom.getRoomName()).isEqualTo("Dermatology");

        when(roomRepository.findByRoomName(newRoom.getRoomName())).thenReturn(opt);
        mockMvc.perform(get("/api/rooms/" + newRoom.getRoomName()))
                .andExpect(status().isOk());

    }

    @Test
    public void shouldNoCreateRoom() throws Exception {

        Room newRoom = new Room(null);

        mockMvc.perform(post("/api/room")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newRoom)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldGetNoRoombyName() throws Exception {

        String roomName = "Cardiology";

        mockMvc.perform(get("/api/rooms/" + roomName))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldDeleteRoomByName() throws Exception {

        Room newRoom = new Room("Dermatology");

        Optional<Room> opt = Optional.of(newRoom);

        assertThat(opt).isPresent();
        assertThat(opt.get().getRoomName()).isEqualTo(newRoom.getRoomName());
        assertThat(newRoom.getRoomName()).isEqualTo("Dermatology");

        when(roomRepository.findByRoomName(newRoom.getRoomName())).thenReturn(opt);
        mockMvc.perform(delete("/api/rooms/" + newRoom.getRoomName()))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldNotDeleteRoom() throws Exception {

        String roomName = "Cardiology";

        mockMvc.perform(delete("/api/rooms/" + roomName))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldDeleteAllPatients() throws Exception {
        
        mockMvc.perform(delete("/api/rooms"))
                .andExpect(status().isOk());
    }

}
