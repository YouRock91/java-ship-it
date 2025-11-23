package ru.yandex.practicum.delivery;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PerishableParcelTest {

    @Test
    public void testNotExpired() {
        PerishableParcel parcel = new PerishableParcel("Торт", 1000, "Москва", 10, 5);
        // отправка 10 + срок 5 = 15, текущий 12 - не просрочена
        assertFalse(parcel.isExpired(12));
    }

    @Test
    public void testExpired() {
        PerishableParcel parcel = new PerishableParcel("Торт", 1000, "Москва", 10, 5);
        // отправка 10 + срок 5 = 15, текущий 16 - просрочена
        assertTrue(parcel.isExpired(16));
    }

    @Test
    public void testBoundaryExpiration() {
        PerishableParcel parcel = new PerishableParcel("Торт", 1000, "Москва", 10, 5);
        // отправка 10 + срок 5 = 15, текущий 15 - последний день срока
        assertFalse(parcel.isExpired(15));
    }
}
