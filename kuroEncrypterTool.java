
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author maindesktop
 */
public class kuroEncrypterTool {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("");
        System.out.println("             __ __                       __                \n"
                + "            / //_/_ _________  ___  ___ / /_____           \n"
                + "           / ,< / // / __/ _ \\/ _ \\/ -_)  '_/ _ \\          \n"
                + "          /_/|_|\\_,_/_/  \\___/_//_/\\__/_/\\_\\\\___/          \n"
                + "                                                           \n"
                + "   ____                       __         ______          __\n"
                + "  / __/__  __________ _____  / /____ ___/_  __/__  ___  / /\n"
                + " / _// _ \\/ __/ __/ // / _ \\/ __/ -_) __// / / _ \\/ _ \\/ / \n"
                + "/___/_//_/\\__/_/  \\_, / .__/\\__/\\__/_/  /_/  \\___/\\___/_/  \n"
                + "                 /___/_/                                   \n"
                + "");
        System.out.println("***********************************************************");
        System.out.println("***********************************************************");
        System.out.println("** Select 1 to decrypt text                              **");
        System.out.println("** Select 2 to encrypt text                              **");
        System.out.println("** Select 3 to open encrypted file                       **");
        System.out.println("***********************************************************");
        System.out.println("***********************************************************");
        System.out.println("");
        int selection = sc.nextInt();
        if (selection == 1) {
            Scanner encryptedTextInput = new Scanner(System.in);
            System.out.println("Insert encrypted text: ");
            String encryptedText = encryptedTextInput.nextLine();
            System.out.println("");
            System.out.println("*************** Text Decrypted Successfully! **************");
            System.out.println(loadHexToString(encryptedText));
            System.out.println("***********************************************************");
        } else if (selection == 2) {
            Scanner textInput = new Scanner(System.in);
            System.out.println("Insert the text you want to encrypt: ");
            String normalText = textInput.nextLine();
            System.out.println("");
            System.out.println("Insert filename: ");
            String filename = textInput.nextLine();
            System.out.println("");
            System.out.println("***********************************************************");
            System.out.println("This is your encrypted Text: " + saveTextToHex(normalText, filename));
            System.out.println("***********************************************************");
        } else if (selection == 3) {
            Scanner encryptedFileInput = new Scanner(System.in);
            System.out.println("Insert filename without extension");
            System.out.println("(the file must be in the same folder as the application): ");
            String encryptedFile = encryptedFileInput.nextLine();
            System.out.println("");
            System.out.println("************* File Decrypted Successfully! ****************");
            System.out.println(loadFileType(encryptedFile));
            System.out.println("***********************************************************");
        } else {
            System.out.println("");
            System.out.println("***********************************************************");
            System.out.println("********** You need to select a valid option **************");
            System.out.println("***********************************************************");
        }

    }

    private static String loadHexToString(String input) { //input mus be encrypted text before encrypted with this tool
        String inputCutFirstPart = ""; //Store first cut
        String inputCutSecondPart = ""; //Store second cut
        StringBuffer inputBuffered = new StringBuffer(input); //buffered input
        String inputConvertToString = ""; //store final String
        try {
            inputBuffered.reverse().toString();  //reverse input
            inputConvertToString = convertHexToString(inputBuffered.toString()); //convert hexadecimal input to string (still be hexa because need a second convert)

            //check lenght of input if no pair
            if (inputConvertToString.length() % 2 == 0) {
                inputCutFirstPart = inputConvertToString.substring(0, inputConvertToString.length() / 2); //cut input
                inputCutSecondPart = inputConvertToString.substring(inputConvertToString.length() / 2, inputConvertToString.length()); //second cut

            } else {
                inputCutFirstPart = inputConvertToString.substring(0, inputConvertToString.length() / 2); //cut input
                inputCutSecondPart = inputConvertToString.substring((inputConvertToString.length() / 2) - 1, inputConvertToString.length()); //second cut

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertHexToString(inputCutSecondPart + inputCutFirstPart); //join inputs and convert again from hex to string

    }

    private static String loadFileType(String filename) { //input must be filename without his extension
        File file = new File(filename + getConfigParameters().getProperty("fileExtension")); //declare filetype
        String fileTemporaryContainer = "";  // storage for file content
        String inputCutFirstPart = ""; // store first part
        String inputCutSecondPart = ""; //store second part
        StringBuffer inputBuffered = new StringBuffer(); //store string read file
        String inputConvertToString = ""; // store final String
        Object[] lines;

        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            lines = br.lines().toArray();      //read file and store content on variable
            for (int i = 0; i < lines.length; i++) {
                fileTemporaryContainer = fileTemporaryContainer + lines[i].toString();
            }
            inputBuffered.append(fileTemporaryContainer);//buffered input
            inputBuffered.reverse().toString();  //reverse input
            inputConvertToString = convertHexToString(inputBuffered.toString()); //convert hexadecimal input to string (still be hexa because need a second convert)

            //check lenght of input if no pair
            if (inputConvertToString.length() % 2 == 0) {
                inputCutFirstPart = inputConvertToString.substring(0, inputConvertToString.length() / 2); //cut input
                inputCutSecondPart = inputConvertToString.substring(inputConvertToString.length() / 2, inputConvertToString.length()); //second cut

            } else {
                inputCutFirstPart = inputConvertToString.substring(0, inputConvertToString.length() / 2); //cut input
                inputCutSecondPart = inputConvertToString.substring((inputConvertToString.length() / 2) - 1, inputConvertToString.length()); //second cut

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return convertHexToString(inputCutSecondPart + inputCutFirstPart); //join inputs and convert again from hex to string

    }

    private static String saveTextToHex(String input, String filename) { //input must be normal text
        String randomCode = randomString(Integer.parseInt(getConfigParameters().getProperty("fileNameLenght"))); //generate random filename
        File file = new File(filename + "_" + randomCode + getConfigParameters().getProperty("fileExtension")); //declare filename with his location
        String inputCutFirstPart = ""; //store first cut
        String inputCutSecondPart = ""; //store second cut
        String secondInputBuffered = ""; //join parts
        StringBuffer secondHexInputContainer = new StringBuffer(); //store final hex

        try {
            FileWriter fw = new FileWriter(file); // write file
            BufferedWriter bw = new BufferedWriter(fw); //buffered file

            inputCutFirstPart = convertStringToHex(input).substring(0, convertStringToHex(input).length() / 2); // cut input
            inputCutSecondPart = convertStringToHex(input).substring(convertStringToHex(input).length() / 2, convertStringToHex(input).length()); //second cut

            secondInputBuffered = inputCutSecondPart + inputCutFirstPart; //Store input in a second input buffered (turn second cut first and second to first)    
            secondHexInputContainer.append(convertStringToHex(secondInputBuffered)); //String to hex again and store on hex container

            secondHexInputContainer.reverse(); // reverse file text
            bw.write(secondHexInputContainer.toString()); //write file text
            bw.newLine();
            bw.close();
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return secondHexInputContainer.toString(); //return text

    }

    private static String convertHexToString(String hex) {

        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();

        //49204c6f7665204a617661 split into two characters 49, 20, 4c...
        for (int i = 0; i < hex.length() - 1; i += 2) {

            //grab the hex in pairs
            String output = hex.substring(i, (i + 2));
            //convert hex to decimal
            int decimal = Integer.parseInt(output, 16);
            //convert the decimal to character
            sb.append((char) decimal);

            temp.append(decimal);
        }

        return sb.toString();
    }

    private static String convertStringToHex(String text) {

        StringBuffer inputBuffered = new StringBuffer(text); //buffered input
        char[] charzedInput = (new StringBuffer(inputBuffered).toString()).toCharArray(); //cut input to char
        StringBuffer hexInputContainer = new StringBuffer(); //declare hexBuffered container
        for (int i = 0; i < charzedInput.length; i++) { // String to hex and store on hex container
            hexInputContainer.append(Integer.toHexString((int) charzedInput[i]));
        }
        return hexInputContainer.toString();
    }

    private static String randomString(int len) {
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }

    private static Properties getConfigParameters() {
        Properties config = new Properties();
        try {
            InputStream myConfig = new FileInputStream(new File("config.properties")); //config file location
            config.load(myConfig); //load config
        } catch (IOException e) {
            e.printStackTrace();
        }
        return config;
    }
}