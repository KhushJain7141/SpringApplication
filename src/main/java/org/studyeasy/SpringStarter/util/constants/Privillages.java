package org.studyeasy.SpringStarter.util.constants;

public enum Privillages {
    RESENT_ANY_USER_PASSWORD(1l,"RESET_ANY_USER_PASSWORD"),
    ACCESS_ADMIN_PANEL(2l,"ACCESS_ADMIN_PANEL");
    private long Id;
    private String privillage;
    private Privillages(long Id,String privillage)
    {
        this.Id=Id;
        this.privillage=privillage;

    }
    public long getId()
    {
        return Id;
    }
    public String getPrivillage()
    {
        return privillage;
    }
}
