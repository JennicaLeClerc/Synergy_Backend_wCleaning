package com.revature.shms.servicetests;

import com.revature.shms.enums.ReservationStatus;
import com.revature.shms.models.Reservation;
import com.revature.shms.models.User;
import com.revature.shms.repositories.ReservationRepository;
import com.revature.shms.repositories.UserRepository;
import com.revature.shms.services.ReservationService;
import com.revature.shms.services.UserService;
import javafx.beans.binding.When;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTests {
	@InjectMocks ReservationService reservationService ;
	@Mock ReservationRepository reservationRepository;

	@InjectMocks UserService userService ;
	@Mock UserRepository userRepository;

	@Test
	public void returnAllReservationsTest(){
		List<Reservation> test = new ArrayList<>();
		Reservation reservation = new Reservation();
		reservation.setReservationID(1);
		test.add(reservation);
		when(reservationRepository.findAll()).thenReturn(test);
		assertEquals(test, reservationService.findAll());
	}

	@Test
	public void getSpecificReservation() throws NotFound {
		int id = 1;
		Reservation reservation = new Reservation();
		reservation.setReservationID(id);
		when(reservationRepository.findByUserReserve_UserID(1)).thenReturn(java.util.Optional.of(reservation));
		assertEquals(id, reservationService.findReservationOfUser(1).getReservationID());
	}

	@Test
	public void createUserTest(){
		Reservation reservation = new Reservation();
		reservation.setStatus(ReservationStatus.APPROVED);
		when(reservationRepository.save(reservation)).thenReturn(reservation);
		assertEquals(reservation, reservationService.createReservation(reservation));
	}

	@Test
	public void deleteUserTest(){
		reservationService.deleteReservation(1231245);
		verify(reservationRepository,times(1)).deleteByUserReserve_UserID(anyInt());
	}

    	@Test
	public void changeStatusOfReservationTest() throws NotFound {
		User user = new User();
       		Reservation reservation = new Reservation();
		Reservation update = new Reservation();
		update.setUserReserve(user);
		update.setStatus(ReservationStatus.REJECTED);
       		reservation.setReservationID(1);
        	reservation.setStatus(ReservationStatus.APPROVED);
        	update.setStatus(ReservationStatus.CANCELLED);
        	when(reservationRepository.findByReservationID(1)).thenReturn(Optional.of(reservation));
		when(reservationRepository.save(any())).thenReturn(reservation);
		assertEquals(reservation, reservationService.changeStatusOfReservation(1,ReservationStatus.APPROVED));
    	}

	@Test 
	public void changeDateOfReservationTest() throws NotFound {
		Reservation reservation = new Reservation();
		reservation.setReservationID(1);
		Reservation update = new Reservation();
		update.setReservationID(1);
		reservation.setStatus(ReservationStatus.APPROVED);
		update.setStatus(ReservationStatus.CANCELLED);
		when(reservationRepository.findByUserReserve_UserID(1)).thenReturn(java.util.Optional.of(reservation));
		when(reservationRepository.save(any())).thenReturn(reservation);
		assertEquals(reservation, reservationService.changeDateOfReservation(1,"1", "1"));
	}

	@Test 
	public void getReservationWithReservationIdTest () throws NotFound {
		int id = 1;
		Reservation reservation = new Reservation();
		reservation.setReservationID(id);
		when(reservationRepository.findByReservationID(1)).thenReturn(java.util.Optional.of(reservation));
		assertEquals(id, reservationService.findReservationWithReservationId(1).getReservationID());
	}

	@Test
	public void reserveTest(){
		Reservation reservation = new Reservation();
		Reservation customReservation = new Reservation();
       		User user = new User();
        	customReservation.setUserReserve(user);
		customReservation.setStartDate("1");
		customReservation.setEndDate("1");
		customReservation.setAmenities("1");
        	when(reservationRepository.save(any())).thenReturn(reservation);
		assertEquals(reservation, reservationService.setReservation(customReservation));
	}

	@Test
	public void gettersSetters(){
		ReservationRepository reservationRepository = null;
		ReservationService reservationService = new ReservationService();
		reservationService.setReservationRepository(reservationRepository);
		assertNull(reservationService.getReservationRepository());
		reservationService.setReservationRepository(null);
	}
}
