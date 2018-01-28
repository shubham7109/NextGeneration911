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

    private enum Actions {
        Ambulance,
        Police,
        FireFighters,
        FirstResponders
    }

    // Constructor to set up GUI
    public UI_Window(){

        map = new Label("Google Map API will come here");
        map.setAlignment(Label.CENTER);
        map.setBounds(600,300,400,40);
        add(map);

        callerInfo = new Label("Caller information:");
        map.setBounds(600,300,400,40);
        add(map);



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
