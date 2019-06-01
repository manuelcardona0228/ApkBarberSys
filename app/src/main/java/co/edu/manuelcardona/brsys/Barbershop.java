package co.edu.manuelcardona.brsys;

public class Barbershop {
    private String businessName;

    public Barbershop()
    {

    }

    public Barbershop(String businessName)
    {
        this.businessName = businessName;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }
}
