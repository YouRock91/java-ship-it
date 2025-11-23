package ru.yandex.practicum.delivery;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParcelCostTest {

    @Test
    public void testStandardParcelCost() {
        StandardParcel parcel = new StandardParcel("Книги", 1500, "Москва", 15);
        // 1.5 кг → 2 кг × 2 = 4 руб
        assertEquals(4, parcel.calculateDeliveryCost());
    }

    @Test
    public void testFragileParcelCost() {
        FragileParcel parcel = new FragileParcel("Ваза", 800, "СПб", 16);
        // 0.8 кг → 1 кг × 4 = 4 руб
        assertEquals(4, parcel.calculateDeliveryCost());
    }

    @Test
    public void testPerishableParcelCost() {
        PerishableParcel parcel = new PerishableParcel("Торт", 2000, "Казань", 10, 5);
        // 2 кг × 3 = 6 руб
        assertEquals(6, parcel.calculateDeliveryCost());
    }
}
