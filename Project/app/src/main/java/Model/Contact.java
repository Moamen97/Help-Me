package Model;

public class Contact {
    private String Name;
    private String phone;
    private int image;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public Contact(String name, String phone, int image) {
        Name = name;
        this.phone = phone;
        this.image = image;
    }

}
