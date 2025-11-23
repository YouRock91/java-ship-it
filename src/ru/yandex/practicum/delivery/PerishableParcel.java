package ru.yandex.practicum.delivery;

public class PerishableParcel extends Parcel {
    private static final int BASE_COST = 3;
    private int timeToLive; // срок годности в днях

    public PerishableParcel(String description, int weight, String deliveryAddress, int sendDay, int timeToLive) {
        super(description, weight, deliveryAddress, sendDay);
        this.timeToLive = timeToLive;
    }

    @Override
    public int getBaseCost() {
        return BASE_COST;
    }

    public boolean isExpired(int currentDay) {
        // Предполагаем, что месяц не меняется
        return (sendDay + timeToLive) < currentDay;
    }

    public int getTimeToLive() {
        return timeToLive;
    }

    public int getExpirationDay() {
        return sendDay + timeToLive;
    }
}
