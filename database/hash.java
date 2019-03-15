package database;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;

public class hash {

    static public ArrayList a=new ArrayList<String>();
    public static void main(String[] args) throws NoSuchAlgorithmException

    {

        String passwordToHash = "abc123";

        byte[] salt = getSalt();

        //String securePassword = get_SHA_1_SecurePassword(passwordToHash, salt);

        //System.out.println(securePassword);



        String securePassword = get_SHA_256_SecurePassword(passwordToHash, salt);
        a.add(securePassword);
        byte[] salt1 = getSalt();
        String b=passwordToHash;
        String c=get_SHA_256_SecurePassword(b,salt1);
        if(c==securePassword){
            System.out.println("true");
        }

        System.out.println(securePassword);




    }

    public static String get_SHA_256_SecurePassword(String passwordToHash, byte[] salt)

    {

        String generatedPassword = "null";

        try {

            MessageDigest md = MessageDigest.getInstance("SHA-256");

            md.update(salt);

            byte[] bytes = md.digest(passwordToHash.getBytes());

            StringBuilder sb = new StringBuilder();

            for(int i=0; i< bytes.length ;i++)

            {

                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));

            }

            generatedPassword = sb.toString();

        }

        catch (NoSuchAlgorithmException e)

        {

            e.printStackTrace();

        }

        return generatedPassword;

    }


    public static byte[] getSalt() throws NoSuchAlgorithmException

    {

        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");

        byte[] salt = new byte[16];

        sr.nextBytes(salt);

        return salt;

    }


}
