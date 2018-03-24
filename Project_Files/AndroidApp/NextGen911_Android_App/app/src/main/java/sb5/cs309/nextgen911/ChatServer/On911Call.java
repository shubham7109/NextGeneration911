package sb5.cs309.nextgen911.ChatServer;

public class On911Call implements{

    private boolean isServer = false;

    private Client createClient(){
        return new Client("10.26.17.136", 5555, data ->{
            messages.appendText(data.toString() + "\n");
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mapView.addMapInializedListener(this);
        try {
            connection.startConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // TODO CHANGE THIS
        Platform.runLater(()->{
            int id = 111;
            setUpUrl(id);
            try {
                setPerons();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void setPerons() throws Exception {
        JSONObject jsonObject = new JSONObject(getHTML(URL));
        personModel= new PersonModel(jsonObject);
        id.setText(String.valueOf(personModel.getId()));
        id.deselect();
        phoneNumber.setText(String.valueOf(personModel.getPhoneNumber()));
        gender.setText(String.valueOf(personModel.getGender()));
        firstName.setText(personModel.getFirstName());
        middleName.setText(personModel.getMiddleName());
        lastName.setText(personModel.getLastName());
        homeAddress.setText(personModel.getHomeAddress());
        city.setText(personModel.getCity());
        state.setText(personModel.getState());
        zipcode.setText(personModel.getZipcode());
        dateOfBirth.setText(personModel.getDateOfBirth());
        licencePlateNumber.setText(personModel.getLicencePlateNumber());
        vehicle.setText(personModel.getVehicle());
        bloodType.setText(personModel.getBloodType());
        heightCentimeters.setText(personModel.getHeightCentimeters());
        weightKilograms.setText(personModel.getWeightKilograms());
        System.out.println(jsonObject);
    }

    private void setUpUrl(int id){
        URL = URL + id;
    }

    private static String getHTML(String urlToRead) throws Exception {
        StringBuilder result = new StringBuilder();
        java.net.URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
    }

}

