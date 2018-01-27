import javax.swing.*;
import java.awt.*;        // Using AWT container and component classes
import java.awt.event.*;  // Using AWT event classes and listener interfaces


public class UI_Window extends Frame implements ActionListener {

    private JButton deployAmbulance;
    private JButton deloyPolice;
    private JButton deployFireFighters;
    private JButton deployFirstResponders;
    private Frame frame;

    private enum Actions {
        Ambulance,
        Police,
        FireFighters,
        FirstResponders
    }

    // Constructor to set up GUI
    public UI_Window(){
        setLayout(new FlowLayout());

        deployAmbulance = new JButton("Ambulance");
        deployAmbulance.setActionCommand(Actions.Ambulance.name());
        deployAmbulance.addActionListener(this);
        add(deployAmbulance);

        deloyPolice = new JButton("Police");
        add(deloyPolice);

        deployFireFighters = new JButton("Fire Fighters");
        add(deployFireFighters);

        deployFirstResponders = new JButton("First Responders");
        add(deployFirstResponders);

        setTitle("911 Locator and Deployment Tool");
        setSize(1000,500);

        // Code check if 'X' (close) button is pressed
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                dispose();
                System.exit(0);
            }
        });
    }

    public static  void main(String[] args){

        UI_Window app = new UI_Window();
        app.setBounds(133,100,532,400);
        app.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent evt) {

        if (evt.getActionCommand() == Actions.Ambulance.name()) {
            JOptionPane.showMessageDialog(null, "Hello");
        }
    }
}
