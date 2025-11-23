package ru.yandex.practicum.delivery;

import java.util.ArrayList;
import java.util.List;

public class ParcelBox<T extends Parcel> {
    private List<T> parcels = new ArrayList<>();
    private int maxWeight; // максимальный вес в граммах
    private int currentWeight = 0;

    public ParcelBox(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    public void addParcel(T parcel) {
        if (currentWeight + parcel.getWeight() <= maxWeight) {
            parcels.add(parcel);
            currentWeight += parcel.getWeight();
            System.out.println("Посылка <<" + parcel.getDescription() + ">> добавлена в коробку. " +
                    "Текущий вес: " + getFormattedCurrentWeight() + "/" + getFormattedMaxWeight());
        } else {
            System.out.println("⚠️  Предупреждение: посылка <<" + parcel.getDescription() +
                    ">> не помещается в коробку. Превышен максимальный вес " + getFormattedMaxWeight());
        }
    }

    public List<T> getAllParcels() {
        return new ArrayList<>(parcels);
    }

    public int getCurrentWeight() {
        return currentWeight;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    private String getFormattedCurrentWeight() {
        if (currentWeight >= 1000) {
            return String.format("%.2f кг", currentWeight / 1000.0);
        } else {
            return currentWeight + " г";
        }
    }

    private String getFormattedMaxWeight() {
        if (maxWeight >= 1000) {
            return String.format("%.2f кг", maxWeight / 1000.0);
        } else {
            return maxWeight + " г";
        }
    }
}
