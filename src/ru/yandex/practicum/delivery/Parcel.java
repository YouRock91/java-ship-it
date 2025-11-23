package ru.yandex.practicum.delivery;

public abstract class Parcel {
    protected String description;
    protected int weight; // вес в граммах
    protected String deliveryAddress;
    protected int sendDay; // день месяца (1-31)

    public Parcel(String description, int weight, String deliveryAddress, int sendDay) {
        this.description = description;
        this.weight = weight;
        this.deliveryAddress = deliveryAddress;
        this.sendDay = sendDay;
    }

    public void packageItem() {
        System.out.println("Посылка <<" + description + ">> упакована");
    }

    public void deliver() {
        System.out.println("Посылка <<" + description + ">> доставлена по адресу " + deliveryAddress);
    }

    public abstract int getBaseCost();

    public int calculateDeliveryCost() {
        // Переводим граммы в килограммы для расчета стоимости (округляем вверх)
        int weightInKg = (int) Math.ceil(weight / 1000.0);
        return weightInKg * getBaseCost();
    }

    // Геттеры
    public String getDescription() { return description; }
    public int getWeight() { return weight; }
    public String getDeliveryAddress() { return deliveryAddress; }
    public int getSendDay() { return sendDay; }

    // Форматированный вывод веса
    public String getFormattedWeight() {
        if (weight >= 1000) {
            return String.format("%.2f кг", weight / 1000.0);
        } else {
            return weight + " г";
        }
    }
}
