package pro.sky.JD2AnimalShelterBot.service;

import org.junit.jupiter.api.Test;
import pro.sky.JD2AnimalShelterBot.handlers.Commands;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static pro.sky.JD2AnimalShelterBot.handlers.Commands.CALL_VOLUNTEER_COMAND;

class CommandsTest {

    @Test
    void parseCommandTest() {

        assertTrue(Commands.parseCommand("Bla bla bla").equals(Optional.empty()));

        var actual = Commands.parseCommand("/call_volunteer").get();
        assertEquals(CALL_VOLUNTEER_COMAND, actual);
    }
}