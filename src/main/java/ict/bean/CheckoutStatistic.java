package ict.bean;

public class CheckoutStatistic extends Equipment {

    private int count;

    public CheckoutStatistic(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
