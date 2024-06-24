package chapter10;

import base.BaseSetup;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import models.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ReadJsonFileTest extends BaseSetup {
    public static void main(String[] args) throws FileNotFoundException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        File file = new File("SeleniumJavaBasic/src/test/data/User.json");
        User newUser = new User();

        newUser.setEmail("AtamCow@atam.cow");
        newUser.setPid("12345678");

        try {
            objectMapper.writeValue(file, newUser);

            System.out.println("Email: " + newUser.getEmail());
            System.out.println("Pis: " + newUser.getPid());

        } catch (IOException e) {
            e.printStackTrace();
        }

//        try {
//            List<User> users;
//            if (file.exists()) {
//                users = objectMapper.readValue(file, new TypeReference<List<User>>() {});
//            } else {
//                users = new ArrayList<>();
//            }
//            users.add(new User("tienthanhvulcu@gmail.com", "", "12341234", "12344321"));
//            users.add(new User("mait6192@mai.tam", "1234qwer", "11223344", "12345432"));
//
//            objectMapper.writeValue(file, newUser);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        System.out.println("Email: " + newUser.getEmail());
        System.out.println("Pis: " + newUser.getPid());

    }

}
