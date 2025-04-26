package CustomerManagement;

public class Customer {
    private String username;
    private String fullname;
    private String password;
    private String phoneNo;
    private String email;
    private String customerId;

    //constructor for customer details
    public Customer(String line) {
        String[] parts = line.split("\\|"); // Split using "|"
        if (parts.length == 6) { // Ensure the line has exactly 5 parts
            this.customerId = parts[0];
            this.username = parts[1];
            this.fullname = parts[2];
            this.password = parts[3];
            this.phoneNo = parts[4];
            this.email = parts[5];
        } else {
            throw new IllegalArgumentException("Invalid customer data format");
        }
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getFullName(){
        return fullname;
    }

    public void setFullName(String fullname){
        this.fullname = fullname;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPhoneNo(){
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo){
        this.phoneNo = phoneNo;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getCustomerId(){
        return customerId;
    }

    public void setCustomerId(String customerId){
        this.customerId = customerId;
    }

    //display customer details
    @Override
    public String toString() {
        return "Customer {"
                + "username='" + username + '\''
                + ", fullname='" + fullname + '\''
                + ", password='" + password + '\''
                + ", phoneNo='" + phoneNo + '\''
                + ", email= '" + email + '\''
                + ", customer id= '" + customerId + '\''
                + '}';
    }

}
