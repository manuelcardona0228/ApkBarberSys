package co.edu.manuelcardona.brsys;

public class Barbershop {
    private String businessName;
    private int id;

    public Barbershop()
    {

    }

    public Barbershop(String businessName, int id)
    {
        this.businessName = businessName;
        this.id = id;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
