package ru.yandex.practicum.delivery;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class ParcelBoxTest {

    private ParcelBox<StandardParcel> box;

    @BeforeEach
    public void setUp() {
        box = new ParcelBox<>(5000); // 5 кг максимальный вес
    }

    @Test
    public void testAddParcelWithinWeightLimit() {
        StandardParcel parcel = new StandardParcel("Книга", 2000, "Москва", 1);
        box.addParcel(parcel);

        assertEquals(1, box.getAllParcels().size());
        assertEquals(2000, box.getCurrentWeight());
    }

    @Test
    public void testAddParcelExceedingWeightLimit() {
        StandardParcel parcel1 = new StandardParcel("Книга", 3000, "Москва", 1);
        StandardParcel parcel2 = new StandardParcel("Одежда", 3000, "Москва", 1);

        box.addParcel(parcel1); // добавляется
        box.addParcel(parcel2); // не добавляется (превышение)

        assertEquals(1, box.getAllParcels().size());
        assertEquals(3000, box.getCurrentWeight());
    }

    @Test
    public void testAddParcelExactWeightLimit() {
        StandardParcel parcel = new StandardParcel("Гантеля", 5000, "Москва", 1);
        box.addParcel(parcel);

        assertEquals(1, box.getAllParcels().size());
        assertEquals(5000, box.getCurrentWeight());
    }
}
