import javax.swing.*;
import java.awt.*;        // Using AWT container and component classes
import java.awt.event.*;  // Using AWT event classes and listener interfaces


public class UI_Window extends Frame implements ActionListener {

    private JButton deployAmbulance;
    private JButton deloyPolice;
    private JButton deployFireFighters;
    private JButton deployFirstResponders;

    private Frame frame;

    private Label map;
    private Label callerInfo;

    private Label name;
    private Label gender;
    private Label age;
    private Label dob;
    private Label bloodType;
    private Label medicalHistory;

    private TextField callerName;
    private TextField callerGender;
    private TextField callerAge;
    private TextField callerDOB;
    private TextField callerBloodType;
    private TextField callerMedicalHistory;


    private enum Actions {
        Ambulance,
        Police,
        FireFighters,
        FirstResponders
    }

    // Constructor to set up GUI
    public UI_Window(){

        Font font = new Font("Arial", Font.PLAIN, 24);

        map = new Label("Google Map API will come here");
        map.setAlignment(Label.CENTER);
        map.setFont(font);
        map.setBounds(600,300,400,40);
        add(map);

        callerInfo = new Label("Caller information:");
        font = new Font("Arial", Font.PLAIN, 16);
        callerInfo.setFont(font);
        callerInfo.setBounds(10,50,200,40);
        add(callerInfo);

        name = new Label("Name:");
        font = new Font("Arial", Font.PLAIN, 16);
        name.setFont(font);
        name.setBounds(10,100,100,40);
        add(name);

        gender = new Label("Gender:");
        font = new Font("Arial", Font.PLAIN, 16);
        gender.setFont(font);
        gender.setBounds(10,150,100,40);
        add(gender);

        age = new Label("Age:");
        font = new Font("Arial", Font.PLAIN, 16);
        age.setFont(font);
        age.setBounds(10,200,100,40);
        add(age);

        dob = new Label("Date of Birth:");
        font = new Font("Arial", Font.PLAIN, 16);
        dob.setFont(font);
        dob.setBounds(10,250,100,40);
        add(dob);

        bloodType = new Label("Blood Type:");
        font = new Font("Arial", Font.PLAIN, 16);
        bloodType.setFont(font);
        bloodType.setBounds(10,300,100,40);
        add(bloodType);

        medicalHistory = new Label("Med History:");
        font = new Font("Arial", Font.PLAIN, 16);
        medicalHistory.setFont(font);
        medicalHistory.setBounds(10,350,100,40);
        add(medicalHistory);

        callerName = new TextField("Michael Dapaah");
        font = new Font("Arial", Font.PLAIN, 16);
        callerName.setFont(font);
        callerName.setEditable(false);
        callerName.setBounds(150,100,250,40);
        add(callerName);

        callerGender = new TextField("Male");
        font = new Font("Arial", Font.PLAIN, 16);
        callerGender.setFont(font);
        callerGender.setEditable(false);
        callerGender.setBounds(150,150,250,40);
        add(callerGender);

        callerAge = new TextField("26");
        font = new Font("Arial", Font.PLAIN, 16);
        callerAge.setFont(font);
        callerAge.setEditable(false);
        callerAge.setBounds(150,200,250,40);
        add(callerAge);

        callerDOB = new TextField("Jan 11 1991");
        font = new Font("Arial", Font.PLAIN, 16);
        callerDOB.setFont(font);
        callerDOB.setEditable(false);
        callerDOB.setBounds(150,250,250,40);
        add(callerDOB);

        callerBloodType = new TextField("O+");
        font = new Font("Arial", Font.PLAIN, 16);
        callerBloodType.setFont(font);
        callerBloodType.setEditable(false);
        callerBloodType.setBounds(150,300,250,40);
        add(callerBloodType);

        callerMedicalHistory = new TextField("Body temperature is not hot");
        font = new Font("Arial", Font.PLAIN, 16);
        callerMedicalHistory.setFont(font);
        callerMedicalHistory.setEditable(false);
        callerMedicalHistory.setBounds(150,350,250,40);
        add(callerMedicalHistory);




        callerInfo = new Label("Caller Information");
        add(callerInfo);

        deployAmbulance = new JButton("Ambulance");
        deployAmbulance.setActionCommand(Actions.Ambulance.name());
        deployAmbulance.addActionListener(this);


        deloyPolice = new JButton("Police");
        deloyPolice.setActionCommand(Actions.Police.name());
        deloyPolice.addActionListener(this);


        deployFireFighters = new JButton("Fire Fighters");
        deployFireFighters.setActionCommand(Actions.FireFighters.name());
        deployFireFighters.addActionListener(this);


        deployFirstResponders = new JButton("First Responders");
        deployFirstResponders.setActionCommand(Actions.FirstResponders.name());
        deployFirstResponders.addActionListener(this);

        setButtonPositions();

        setTitle("911 Locator and Deployment Tool");
        setSize(1000,1000);

        // Code check if 'X' (close) button is pressed
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                dispose();
                System.exit(0);
            }
        });

        setLayout(null);
        setVisible(true);
    }

    private void setButtonPositions(){
        deployAmbulance.setBounds(600,700,170,40);
        add(deployAmbulance);
        deployFirstResponders.setBounds(800,700,170,40);
        add(deployFirstResponders);
        deployFireFighters.setBounds(600,900,170,40);
        add(deployFireFighters);
        deloyPolice.setBounds(800,900,170,40);
        add(deloyPolice);
    }

    public static  void main(String[] args){

        UI_Window app = new UI_Window();

    }

    @Override
    public void actionPerformed(ActionEvent evt) {

        if (evt.getActionCommand() == Actions.Ambulance.name()) {
            JOptionPane.showMessageDialog(null, "Ambulance");
        }
        if (evt.getActionCommand() == Actions.Police.name()) {
            JOptionPane.showMessageDialog(null, "Police");
        }
        if (evt.getActionCommand() == Actions.FireFighters.name()) {
            JOptionPane.showMessageDialog(null, "Fire Fighters");
        }
        if (evt.getActionCommand() == Actions.FirstResponders.name()) {
            JOptionPane.showMessageDialog(null, "First Responders");
        }

    }
}
